(function() {

  Collab.Chat = (function() {

    function Chat(session, log) {
      var _this = this;
      this.session = session;
      this.log = log;
      $(function() {
        return _this.init();
      });
      this.session.connection.bind("send", function(action, message) {
        console.log("send: " + action);
        return console.log(message);
      });
      this.session.connection.bind("message", function(message) {
        console.log("message: " + message.action);
        return console.log(message.content);
      });
      this.session.connection.bind("chat.message", function(message) {
        var date, m, sender;
        m = message.content;
        sender = _this.session.getUser(m.sender);
        date = new Date(m.timestamp);
        date = "" + (date.getHours()) + ":" + (date.getMinutes());
        return _this.log.append("<div class='message'><div class='time'>" + date + "</div><div class='sender'>" + sender.name + "</div><div class='messageText'>" + m.text + "</div></div>");
      });
      this.session.connection.bind('close', function() {
        return _this.textElem.attr('disabled', 'disabled');
      });
    }

    Chat.prototype.sendMessage = function(text) {
      console.log(text);
      return this.session.connection.send("chat.message", {
        text: text
      });
    };

    Chat.prototype.init = function() {
      var _this = this;
      this.inputElem = $('#text');
      return this.inputElem.keyup(function(event) {
        if (event.which === Collab.Key.ENTER) {
          _this.sendMessage(_this.inputElem.val());
          return _this.inputElem.val('');
        }
      });
    };

    return Chat;

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
