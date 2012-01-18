(function() {
  var __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  Collab.MouseState.Drag = (function(_super) {

    __extends(Drag, _super);

    Drag.prototype.drag = false;

    function Drag(container) {
      var _this = this;
      Drag.__super__.constructor.call(this, container);
      this.bind('mousemove', function(destination, source) {
        if (_this.pressedState.left) {
          if (_this.drag) {
            _this.drag.position = destination;
            return _this.trigger('dragmove', _this.drag, source, destination);
          }
        }
      });
      this.bind('mousechange', function(button, pressed) {
        var intersect;
        if (button === 'left') {
          if (pressed) {
            if (intersect = _this.isDraggable(_this.position)) {
              _this.startDrag(_this.position, intersect);
            }
          }
          if (!pressed && _this.drag) {
            _this.drag.endPosition = _this.position;
            _this.trigger('dragend', _this.drag);
            console.log('dragend');
            return _this.drag = false;
          }
        }
      });
    }

    Drag.prototype.isDraggable = function(position) {
      return false;
    };

    Drag.prototype.startDrag = function(position, intersect) {
      this.drag = {
        startPosition: position,
        objectStartPosition: intersect.object.position.clone(),
        position: this.position,
        button: 'left',
        intersect: intersect,
        object: intersect.object
      };
      return this.trigger('dragstart', this.drag);
    };

    return Drag;

  })(Collab.MouseState);

}).call(this);
