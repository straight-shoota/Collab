(function() {

  Collab.EventManager = (function() {

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

    EventManager.prototype.trigger = function(event, message) {
      var callback, _i, _len, _ref;
      _ref = this.callbacks[event] || [];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        callback = _ref[_i];
        callback(message);
      }
      return this;
    };

    return EventManager;

  })();

}).call(this);
