(function() {
  var __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  Collab.Connection = (function(_super) {

    __extends(Connection, _super);

    function Connection() {
      Connection.__super__.constructor.apply(this, arguments);
    }

    Connection.prototype.socket = null;

    Connection.prototype.connect = function(url) {
      var _this = this;
      this.socket = new WebSocket(url);
      this.socket.onopen = function() {
        return _this.trigger("open");
      };
      this.socket.onmessage = function(event) {
        return _this.triggerMessage(event);
      };
      return this.socket.onclose = function() {
        return _this.trigger("close");
      };
    };

    Connection.prototype.close = function() {
      return this.socket.close();
    };

    Connection.prototype.triggerMessage = function(event) {
      var data, message;
      data = JSON.parse(event.data);
      message = {
        action: data[0],
        content: data[1]
      };
      this.trigger("message", message);
      return this.trigger(message.action, message);
    };

    Connection.prototype.state = function() {
      return this.socket.readyState;
    };

    Connection.prototype.connected = function() {
      return this.state() === 1;
    };

    Connection.prototype.message = function(fn) {
      return this.bind('message', fn);
    };

    Connection.prototype.send = function(action, message) {
      if (!this.connected()) throw new Exception("not connected");
      this.trigger("send", action, message);
      this.socket.send(JSON.stringify([action, message]));
      return this;
    };

    return Connection;

  })(Collab.EventManager);

  if (window.MozWebSocket) window.WebSocket = window.MozWebSocket;

}).call(this);
