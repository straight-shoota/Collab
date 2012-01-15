(function() {
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; },
    __indexOf = Array.prototype.indexOf || function(item) { for (var i = 0, l = this.length; i < l; i++) { if (i in this && this[i] === item) return i; } return -1; };

  Collab.MouseState = (function(_super) {

    __extends(MouseState, _super);

    MouseState.prototype.pressedState = {
      left: false,
      middle: false,
      right: false
    };

    MouseState.prototype.position = {
      x: 0,
      y: 0
    };

    MouseState.prototype.position2D = {
      x: 0,
      y: 0
    };

    MouseState.prototype.mouseDown = false;

    function MouseState(container) {
      var _this = this;
      if (container == null) container = window.document;
      this._onMouseUp = __bind(this._onMouseUp, this);
      this._onMouseDown = __bind(this._onMouseDown, this);
      this._onMouseMove = __bind(this._onMouseMove, this);
      this._setPosition = __bind(this._setPosition, this);
      this.container = $(container);
      this.container.mousemove(this._onMouseMove);
      this.container.mousedown(this._onMouseDown);
      this.container.mouseup(this._onMouseUp);
      this.bind('mousemove', function(destination, source) {
        _this.position2D.x = (destination.x / _this.container.innerWidth()) * 2 - 1;
        return _this.position2D.y = -(destination.y / _this.container.innerHeight()) * 2 + 1;
      });
    }

    MouseState.prototype.destroy = function() {
      this.container.unbind('mousedown', this._onMouseDown);
      this.container.unbind('mouseup', this._onMouseUp);
      return this.container.unbind('mousemove', this._onMouseMove);
    };

    MouseState.prototype._setPosition = function(event) {
      return this.position = {
        x: event.offsetX,
        y: event.offsetY
      };
    };

    MouseState.prototype._onMouseMove = function(event) {
      var oldPosition;
      oldPosition = this.position;
      this._setPosition(event);
      return this.trigger('mousemove', this.position, oldPosition, event);
    };

    MouseState.prototype._onMouseDown = function(event) {
      return this._onMouseChange(event, true);
    };

    MouseState.prototype._onMouseUp = function(event) {
      return this._onMouseChange(event, false);
    };

    MouseState.prototype._onMouseChange = function(event, pressed) {
      var button;
      event.preventDefault();
      this._setPosition(event);
      button = Collab.MouseState.buttons[event.which];
      this.pressedState[button] = pressed;
      return this.trigger('mousechange', button, pressed, event);
    };

    MouseState.prototype.pressed = function(button) {
      var button, key, state;
      if (button == null) button = null;
      if (button === null) {
        return (function() {
          var _ref, _results;
          _ref = this.pressedState;
          _results = [];
          for (button in _ref) {
            state = _ref[button];
            if (state) _results.push(button);
          }
          return _results;
        }).call(this);
      } else {
        if (__indexOf.call(this.pressedState, button) >= 0) {
          return this.pressedState[button];
        } else {
          key = Collab.MouseState.buttons[button];
          if (__indexOf.call(this.pressedState, key) >= 0) {
            return this.pressedState[key];
          }
        }
      }
      return false;
    };

    MouseState.prototype.dragend = function(fn) {
      return this.bind('dragend', fn);
    };

    return MouseState;

  })(EventManager);

  Collab.MouseState.buttons = {
    1: 'left',
    2: 'middle',
    3: 'right'
  };

}).call(this);
