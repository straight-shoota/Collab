(function() {
  var __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  Collab.CubeDemo = (function() {

    function CubeDemo() {
      this.scene = new Collab.CubeDemo.Scene;
      this.scene.init($('#renderingContainer'));
      this.scene.startLoop();
      this.log = new Collab.Log();
      this.connection = new Collab.Connection();
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

    Scene.prototype.update = function() {
      return $('#stat').html("" + this.mouseState.position2D.x + " | " + this.mouseState.position2D.y + "<br/>" + (this.mouseState.pressed().join(', ')));
    };

    Scene.prototype.initScene = function() {
      var _this = this;
      this.plane = new THREE.Mesh(new THREE.PlaneGeometry(500, 500, 10, 10), new THREE.MeshBasicMaterial({
        color: 0x555555,
        wireframe: !true
      }));
      this.plane.rotation.x = -90 * Math.PI / 180;
      this.plane.name = "plane";
      this.scene.add(this.plane);
      this.zeroLayer = new THREE.Mesh(new THREE.PlaneGeometry(10000, 10000, 10, 10), new THREE.MeshBasicMaterial({
        opacity: 0
      }));
      this.zeroLayer.rotation.x = -90 * Math.PI / 180;
      this.zeroLayer.name = "zeroLayer";
      this.scene.add(this.zeroLayer);
      this.mouseState = new Collab.MouseState.Drag($('#renderingContainer'));
      this.mouseState.bind('dragend', function(drag) {
        return $('#drag').html("" + drag.startPosition.x + " | " + drag.startPosition.y + " => " + drag.endPosition.x + " | " + drag.endPosition.y);
      });
      this.mouseState.isDraggable = function(position) {
        var i, intersects, ray, _i, _len;
        ray = _this.projector.pickingRay(new THREE.Vector3(_this.mouseState.position2D.x, _this.mouseState.position2D.y, 0), _this.camera);
        intersects = ray.intersectScene(_this.scene);
        for (_i = 0, _len = intersects.length; _i < _len; _i++) {
          i = intersects[_i];
          if (i.object.name === "cube") {
            i.object.material.color.setHex(0xFF0000);
            i.object.dynamic = true;
            _this.select(i.object);
            return i;
          } else if (i.object.name === "plane") {
            _this.positionOnPlane = i.point;
          }
        }
      };
      this.mouseState.bind("dragstart", function(drag) {
        drag.offset = new THREE.Vector3();
        drag.offset.sub(drag.object.position, drag.intersect.point);
        return console.log("dragstart ", drag.object.position, drag.intersect.point);
      });
      this.mouseState.bind("dragmove", function(drag, source, destination) {
        var intersects, pos, ray;
        ray = _this.projector.pickingRay(new THREE.Vector3(_this.mouseState.position2D.x, _this.mouseState.position2D.y, 0), _this.camera);
        intersects = ray.intersectObject(_this.zeroLayer)[0];
        pos = new THREE.Vector3();
        pos.add(intersects.point, drag.offset);
        drag.object.position.x = pos.x;
        return drag.object.position.z = pos.z;
      });
      return this.addCube(2, 2);
    };

    Scene.prototype.addCube = function(x, y) {
      var cube;
      cube = new THREE.Mesh(this.data.geo.cube, this.data.materials.cube);
      cube.position.x = -250 + x * 50 + 25;
      cube.position.z = 250 - y * 50 - 25;
      cube.position.y = 25;
      cube.name = "cube";
      this.scene.add(cube);
      return Collab.message("New Cube added at " + x + ", " + y + ".", 'info');
    };

    return Scene;

  })(Collab.Scene);

}).call(this);
