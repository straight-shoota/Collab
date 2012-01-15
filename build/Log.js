(function() {

  Collab.Log = (function() {

    function Log(url) {
      var _this = this;
      this.connection = new Collab.Connection;
      this.url = url;
      this.userlist = {};
      $(function() {
        return _this.init();
      });
      this.connection.bind("send", function(action, message) {
        console.log("send: " + action);
        return console.log(message);
      });
      this.connection.bind("message", function(message) {
        console.log("message: " + message.action);
        return console.log(message.content);
      });
      this.connection.bind("chat.message", function(message) {
        var m, sender;
        m = message.content;
        sender = _this.userlist[m.sender];
        if (sender === void 0) {
          sender = {
            name: 'client' + m.sender
          };
        }
        return _this.logElem.append("<div class='message'><div class='time'>" + m.date + "</div><div class='sender'>" + sender.name + "</div><div class='messageText'>" + m.text + "</div></div>");
      });
      this.connection.bind('close', function() {
        return _this.textElem.attr('disabled', 'disabled');
      });
    }

    Log.prototype.sendMessage = function(text) {
      console.log(text);
      return this.connection.send("chat.message", {
        text: text
      });
    };

    Log.prototype.init = function() {
      var _this = this;
      this.logElem = $('#log');
      this.inputElem = $('#text');
      this.inputElem.keyup(function(event) {
        if (event.which === Collab.Key.ENTER) {
          _this.sendMessage(_this.inputElem.val());
          return _this.inputElem.val('');
        }
      });
      return this.connection.connect(this.url);
    };

    return Log;

  })();

  Collab.Key = {
    ENTER: 13,
    LEFT: 37,
    UP: 38,
    RIGHT: 39,
    DOWN: 40,
    SPACE: 32,
    PAGE_UP: 33,
    PAGE_DOWN: 34,
    TAB: 9
  };

}).call(this);
