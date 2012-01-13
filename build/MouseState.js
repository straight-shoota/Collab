(function() {
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __indexOf = Array.prototype.indexOf || function(item) { for (var i = 0, l = this.length; i < l; i++) { if (i in this && this[i] === item) return i; } return -1; };

  Collab.MouseState = (function() {

    MouseState.prototype.pressedState = {
      left: false,
      middle: false,
      right: false
    };

    MouseState.prototype.position = {
      x: 0,
      y: 0
    };

    MouseState.prototype.drag = false;

    function MouseState(container) {
      var name, _i, _len, _ref;
      if (container == null) container = window.document;
      this._onMouseUp = __bind(this._onMouseUp, this);
      this._onMouseDown = __bind(this._onMouseDown, this);
      this._onMouseMove = __bind(this._onMouseMove, this);
      this._setPosition = __bind(this._setPosition, this);
      this.container = $(container);
      this.container.mousemove(this._onMouseMove);
      this.container.mousedown(this._onMouseDown);
      this.container.mouseup(this._onMouseUp);
      this.mouseDown = false;
      this.dragend(function(drag) {
        return alert("It works");
      });
      _ref = ['dragstart', 'dragend', 'dragmove'];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        name = _ref[_i];
        this[name] = function(data, fn) {
          if (fn === null) {
            fn = data;
            data = null;
          }
          if (arguments.length > 0) {
            return this.on(name, null, data, fn);
          } else {
            return this.trigger(name);
          }
        };
      }
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
      console.log;
      if (this.pressedState.left) {
        this.drag = {
          startPosition: this.oldPosition,
          position: this.position,
          startTime: event.timeStamp,
          button: 'left'
        };
        this.trigger('dragstart', this.drag);
      }
      if (this.drag) return this.drag.position = this.position;
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
      if (button === 'left' && !pressed && this.drag) {
        this.drag.endPosition = this.position;
        this.drag.endTime = event.timeStamp;
        this.trigger('dragend', this.drag);
        console.log('dragend');
        return this.drag = false;
      }
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

  })();

  Collab.MouseState.prototype = $.extend(Collab.MouseState.prototype, $.fn);

  Collab.MouseState.buttons = {
    1: 'left',
    2: 'middle',
    3: 'right'
  };

}).call(this);
