(function() {
  var __slice = Array.prototype.slice;

  window.EventManager = (function() {

    function EventManager() {}

    EventManager.prototype.callbacks = {};

    EventManager.prototype.bind = function(event, callback) {
      var _base;
      (_base = this.callbacks)[event] || (_base[event] = []);
      this.callbacks[event].push(callback);
      return this;
    };

    EventManager.prototype.unbind = function(event, callback) {
      var i, reference, _len, _ref;
      _ref = this.callbacks[event] || [];
      for (i = 0, _len = _ref.length; i < _len; i++) {
        reference = _ref[i];
        if (reference === callback) this.callbacks[event][i] = null;
      }
      return this;
    };

    EventManager.prototype.trigger = function() {
      var args, callback, event, _i, _len, _ref;
      event = arguments[0], args = 2 <= arguments.length ? __slice.call(arguments, 1) : [];
      _ref = this.callbacks[event] || [];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        callback = _ref[_i];
        callback.apply(null, args);
      }
      return this;
    };

    return EventManager;

  })();

}).call(this);
