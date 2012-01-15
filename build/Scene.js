(function() {

  Collab.Scene = (function() {

    Scene.prototype.mouse2D = new THREE.Vector3(0, 0, 0);

    Scene.prototype.inLoop = false;

    Scene.prototype.selected = false;

    function Scene() {}

    Scene.prototype.init = function(container) {
      var aspect;
      this.container = $(container);
      this.renderer = new THREE.WebGLRenderer(this.settings.renderer);
      this.renderer.setSize(this.container.innerWidth(), this.container.innerHeight());
      this.container.append(this.renderer.domElement);
      this.scene = new THREE.Scene;
      this.scene.add(new THREE.AmbientLight(0xB0B0B0));
      aspect = this.settings.camera.aspect || this.container.innerWidth() / this.container.innerHeight();
      this.camera = new THREE.PerspectiveCamera(this.settings.camera.angle, aspect, this.settings.camera.near, this.settings.camera.far);
      this.camera.position.z = 550;
      this.camera.position.y = 350;
      this.cameraTarget = new THREE.Vector3(0, 0, 0);
      this.camera.lookAt(this.cameraTarget);
      this.scene.add(this.camera);
      this.projector = new THREE.Projector();
      return this.initScene();
    };

    Scene.prototype.initScene = function() {};

    Scene.prototype.render = function() {
      return this.renderer.render(this.scene, this.camera);
    };

    Scene.prototype.update = function() {};

    Scene.prototype.startLoop = function() {
      this.inLoop = true;
      return this._loop();
    };

    Scene.prototype.stopLoop = function() {
      return this.inLoop = false;
    };

    Scene.prototype._loop = function() {
      var _this = this;
      this.update();
      this.render();
      if (this.inLoop) {
        return requestAnimationFrame(function() {
          return _this._loop();
        });
      }
    };

    Scene.prototype._mouseMove = function(event) {
      var hover, i, intersects, ray, _i, _j, _len, _len2, _results;
      this.mouse2D.x = ((event.pageX - this.container.offset().left) / this.container.innerWidth()) * 2 - 1;
      this.mouse2D.y = -((event.pageY - this.container.offset().top) / this.container.innerHeight()) * 2 + 1;
      ray = this.projector.pickingRay(this.mouse2D.clone(), this.camera);
      intersects = ray.intersectScene(this.scene);
      if (this.selected) {
        _results = [];
        for (_i = 0, _len = intersects.length; _i < _len; _i++) {
          i = intersects[_i];
          if (i.object.name === "plane") {
            this.selected.position.x += i.point.x - this.positionOnPlane.x;
            this.selected.position.z += i.point.z - this.positionOnPlane.z;
            _results.push(this.positionOnPlane = i.point);
          } else {
            _results.push(void 0);
          }
        }
        return _results;
      } else {
        hover = false;
        for (_j = 0, _len2 = intersects.length; _j < _len2; _j++) {
          i = intersects[_j];
          if (i.object.name === "cube") hover = true;
        }
        return this.container.css('cursor', hover ? 'pointer' : 'auto');
      }
    };

    Scene.prototype._click = function(event) {};

    Scene.prototype._mouseDown = function(event) {
      var i, intersects, ray, _i, _len, _results;
      ray = this.projector.pickingRay(this.mouse2D.clone(), this.camera);
      intersects = ray.intersectScene(this.scene);
      _results = [];
      for (_i = 0, _len = intersects.length; _i < _len; _i++) {
        i = intersects[_i];
        if (i.object.name === "cube") {
          i.object.material.color.setHex(0xFF0000);
          i.object.dynamic = true;
          _results.push(this.select(i.object));
        } else if (i.object.name === "plane") {
          _results.push(this.positionOnPlane = i.point);
        } else {
          _results.push(void 0);
        }
      }
      return _results;
    };

    Scene.prototype._mouseUp = function(event) {
      this.select(false);
      return this.positionOnPlane = null;
    };

    Scene.prototype.select = function(object) {
      if (object) {
        this.selected = object;
      } else {
        this.selected = false;
      }
      return this.container.css('cursor', object ? 'move' : 'auto');
    };

    return Scene;

  })();

  Collab.message = function(message, level, classes) {
    var $message, log;
    if (level == null) level = "info";
    if (classes == null) classes = [];
    classes = classes.join(" ");
    log = console.log;
    if (typeof console[level] === "function") log = console[level];
    $message = $('<div class="message ' + classes + '">' + message + '</div>').hide();
    Collab.message.container.append($message);
    return $message.slideDown().delay(5000).fadeOut();
  };

  $(function() {
    return Collab.message.container = $('#messages');
  });

  window.round = function(n, digits) {
    var power;
    if (digits == null) digits = 0;
    power = Math.pow(10, digits);
    n = n * power;
    n = Math.round(n);
    return n = n / power;
  };

}).call(this);
