(function() {
  var __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  Collab.CubeDemo = (function(_super) {

    __extends(CubeDemo, _super);

    function CubeDemo() {
      CubeDemo.__super__.constructor.apply(this, arguments);
    }

    CubeDemo.prototype.settings = {
      renderer: {
        antialias: true
      },
      camera: {
        angle: 45,
        near: .1,
        far: 10000
      }
    };

    CubeDemo.prototype.data = {
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

    CubeDemo.prototype.update = function() {};

    CubeDemo.prototype.initScene = function() {
      this.plane = new THREE.Mesh(new THREE.PlaneGeometry(500, 500, 10, 10), new THREE.MeshBasicMaterial({
        color: 0x555555,
        wireframe: !true
      }));
      this.plane.rotation.x = -90 * Math.PI / 180;
      this.plane.name = "plane";
      this.scene.add(this.plane);
      return this.addCube(2, 2);
    };

    CubeDemo.prototype.addCube = function(x, y) {
      var cube;
      cube = new THREE.Mesh(this.data.geo.cube, this.data.materials.cube);
      cube.position.x = -250 + x * 50 + 25;
      cube.position.z = 250 - y * 50 - 25;
      cube.position.y = 25;
      cube.name = "cube";
      this.scene.add(cube);
      return Collab.message("New Cube added at " + x + ", " + y + ".", 'info');
    };

    return CubeDemo;

  })(Collab);

}).call(this);
