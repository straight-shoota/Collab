(function() {

  Collab.Scene = (function() {

    Scene.prototype.mouse2D = new THREE.Vector3(0, 0, 0);

    Scene.prototype._inLoop = false;

    Scene.prototype.selected = false;

    function Scene() {}

    Scene.prototype.init = function(container) {
      var aspect;
      this.container = $(container);
      this.renderer = new THREE.WebGLRenderer(this.settings.renderer);
      this.renderer.setSize(this.container.innerWidth(), this.container.innerHeight());
      this.container.append(this.renderer.domElement);
      this.scene = new THREE.Scene;
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
      this._inLoop = true;
      return this._loop();
    };

    Scene.prototype.stopLoop = function() {
      return this._inLoop = false;
    };

    Scene.prototype._loop = function() {
      var _this = this;
      this.update();
      this.render();
      if (this._inLoop) {
        return requestAnimationFrame(function() {
          return _this._loop();
        });
      }
    };

    Scene.prototype.select = function(object) {
      if (object) {
        this.selected = object;
      } else {
        this.selected = false;
      }
      return this.highlight(object);
    };

    Scene.prototype.highlight = function(object) {
      return this.container.css('pointer', object ? 'move' : 'auto');
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
