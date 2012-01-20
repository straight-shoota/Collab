(function() {

  Collab.Chat = (function() {

    function Chat(session, log) {
      var _this = this;
      this.session = session;
      this.log = log;
      $(function() {
        return _this.init();
      });
      this.session.connection.bind("chat.message", function(message) {
        var m, sender;
        m = message.content;
        sender = _this.session.getUser(m.sender);
        return _this.log.message(sender.name, m.text, m.timestamp);
      });
      this.session.connection.bind("session.info", function(message) {
        _this.session.setUserlist(message.content.users);
        return _this.log.userlist(_this.session.getUserlist(), message.content.timestamp);
      });
      this.session.connection.bind('session.userJoined', function(message) {
        var m;
        m = message.content;
        _this.session.addUser(m);
        return _this.log.userJoined(m, m.timestamp);
      });
      this.session.connection.bind('session.userLeft', function(message) {
        var m;
        m = message.content;
        _this.session.removeUser(m);
        return _this.log.userLeft(m, m.timestamp);
      });
      this.session.connection.bind('server.info', function() {
        return _this.session.connection.send("session.listUsers");
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
