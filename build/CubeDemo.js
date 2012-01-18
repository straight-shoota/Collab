(function() {
  var __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  Collab.CubeDemo = (function() {

    function CubeDemo() {
      var _this = this;
      this.scene = new Collab.CubeDemo.Scene;
      this.scene.init($('#renderingContainer'));
      this.scene.triggerAction = function(action, data) {
        return _this.connection.send('scene.' + action, data);
      };
      this.scene.startLoop();
      this.log = new Collab.Log();
      this.connection = new Collab.Connection();
      this.scene.initConnectionHandlers(this.connection);
      this.session = new Collab.Session(this.connection);
      this.chat = new Collab.Chat(this.session, this.log);
      this.init();
    }

    CubeDemo.prototype.init = function() {
      return this.connection.connect('ws://localhost:3141');
    };

    return CubeDemo;

  })();

  Collab.CubeDemo.Scene = (function(_super) {

    __extends(Scene, _super);

    function Scene() {
      Scene.__super__.constructor.apply(this, arguments);
    }

    Scene.prototype.settings = {
      renderer: {
        antialias: true
      },
      camera: {
        angle: 45,
        near: .1,
        far: 10000
      }
    };

    Scene.prototype.data = {
      materials: {
        cube: new THREE.MeshLambertMaterial({
          color: 0x00ff80,
          ambient: 0x00ff80,
          shading: THREE.FlatShading
        }),
        cubeHighlight: new THREE.MeshLambertMaterial({
          color: 0x0000FF,
          ambient: 0x00ff80,
          shading: THREE.FlatShading
        })
      },
      geo: {
        cube: new THREE.CubeGeometry(50, 50, 50)
      }
    };

    Scene.prototype.objects = [];

    Scene.prototype.update = function() {
      return $('#stat').html("" + this.mouseState.position2D.x + " | " + this.mouseState.position2D.y + "<br/>" + (this.mouseState.pressed().join(', ')));
    };

    Scene.prototype.initScene = function() {
      var lamp,
        _this = this;
      this.renderer.shadowMapEnabled = true;
      this.plane = new THREE.Mesh(new THREE.CubeGeometry(500, 500, 10), new THREE.MeshBasicMaterial({
        color: 0x555555,
        wireframe: !true
      }));
      this.plane.rotation.x = Math.PI / -2;
      this.plane.position.y = -5;
      this.plane.name = "plane";
      this.plane.receiveShadow = true;
      this.plane.castShadow = true;
      this.scene.add(this.plane);
      this.zeroLayer = new THREE.Mesh(new THREE.PlaneGeometry(10000, 10000, 10, 10), new THREE.MeshBasicMaterial({
        opacity: 0
      }));
      this.zeroLayer.rotation.x = -90 * Math.PI / 180;
      this.zeroLayer.name = "zeroLayer";
      this.scene.add(this.zeroLayer);
      lamp = new THREE.SpotLight;
      lamp.position.set(0, 300, 50);
      lamp.castShadow = true;
      this.scene.add(lamp);
      this.mouseState = new Collab.MouseState.Drag($('#renderingContainer'));
      this.mouseState.bind('dragend', function(drag) {
        return $('#drag').html("" + drag.startPosition.x + " | " + drag.startPosition.y + " => " + drag.endPosition.x + " | " + drag.endPosition.y);
      });
      this.mouseState.isDraggable = function(position) {
        return _this.pickObjects('draggable')[0];
      };
      this.mouseState.bind("mousemove", function() {
        return _this.container.css('cursor', _this.pickObjects('draggable').length ? 'pointer' : 'auto');
      });
      this.mouseState.bind("dragstart", function(drag) {
        drag.offset = new THREE.Vector3();
        drag.offset.sub(drag.object.position, drag.intersect.point);
        _this.zeroLayer.position.y = drag.intersect.point.y;
        console.log("dragstart ", drag.intersect.point);
        drag.object.material.color.setHex(0xFF0000);
        drag.object.position.y += 10;
        drag.object.dynamic = true;
        return _this.select(drag.object);
      });
      this.mouseState.bind("dragend", function(drag) {
        _this.zeroLayer.position.y = 0;
        drag.object.position.y -= 10;
        _this.select(false);
        return _this.triggerAction('drag', {
          target: drag.object.uid,
          source: drag.objectStartPosition,
          destination: drag.object.position
        });
      });
      return this.mouseState.bind("dragmove", function(drag, source, destination) {
        var intersects, pos, ray;
        ray = _this.projector.pickingRay(new THREE.Vector3(_this.mouseState.position2D.x, _this.mouseState.position2D.y, 0), _this.camera);
        intersects = ray.intersectObject(_this.zeroLayer)[0];
        pos = new THREE.Vector3();
        pos.add(intersects.point, drag.offset);
        drag.object.position.x = pos.x;
        return drag.object.position.z = pos.z;
      });
    };

    Scene.prototype.initConnectionHandlers = function(connection) {
      var _this = this;
      connection.bind('server.info', function() {
        return connection.send("scene.get");
      });
      return connection.bind('scene.content', function(message) {
        var o, _i, _len, _ref, _results;
        console.log(message.content);
        _ref = message.content.objects;
        _results = [];
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          o = _ref[_i];
          _results.push(_this.createCube(o));
        }
        return _results;
      });
    };

    Scene.prototype.pickObjects = function(flag) {
      var i, intersects, ray, _i, _len, _results;
      if (flag == null) flag = false;
      ray = this.projector.pickingRay(new THREE.Vector3(this.mouseState.position2D.x, this.mouseState.position2D.y, 0), this.camera);
      intersects = ray.intersectObjects(this.objects);
      if (!flag) return intersects;
      _results = [];
      for (_i = 0, _len = intersects.length; _i < _len; _i++) {
        i = intersects[_i];
        if (i.object[flag]) _results.push(i);
      }
      return _results;
    };

    Scene.prototype.addCube = function(x, y) {
      var cube;
      cube = new THREE.Mesh(this.data.geo.cube, this.data.materials.cube);
      cube.position.set(-250 + x * 50 + 25, 25, 250 - y * 50 - 25);
      cube.name = "cube";
      cube.uid = 1;
      cube.receiveShadow = true;
      cube.castShadow = true;
      cube.draggable = true;
      this.scene.add(cube);
      this.objects.push(cube);
      return Collab.message("New Cube added at " + x + ", " + y + ".", 'info');
    };

    Scene.prototype.createCube = function(object) {
      var cube;
      cube = new THREE.Mesh(this.data.geo.cube, this.data.materials.cube);
      cube.position.copy(object.position);
      cube.name = "cube";
      cube.uid = object.uid;
      cube.receiveShadow = true;
      cube.castShadow = true;
      cube.draggable = true;
      this.scene.add(cube);
      return this.objects.push(cube);
    };

    return Scene;

  })(Collab.Scene);

}).call(this);
