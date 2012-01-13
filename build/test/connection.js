(function() {
  var Key, connection, sendMessage, userlist;

  connection = new Collab.Connection;

  connection.connect('ws://localhost:3141');

  Key = {
    ENTER: 13
  };

  $(function() {
    var log;
    log = $('#log');
    $('#send').click(sendMessage);
    return $('#text').keyup(function(event) {
      if (event.which === Key.ENTER) return sendMessage();
    });
  });

  sendMessage = function() {
    var text;
    text = $('#text').val();
    $('#text').val('');
    console.log(text);
    return connection.send("chat.message", {
      text: text
    });
  };

  userlist = {};

  connection.bind("send", function(action, message) {
    console.log("send: " + action);
    return console.log(message);
  });

  connection.bind("message", function(message) {
    console.log("message: " + message.action);
    return console.log(message.content);
  });

  connection.bind("chat.message", function(message) {
    var m, sender;
    m = message.content;
    sender = userlist[m.sender];
    return $(log).append("<div class='message'><div class='time'>" + m.date + "</div><div class='sender'>" + sender.name + "</div><div class='messageText'>" + m.text + "</div></div>");
  });

  connection.bind("session.info", function(message) {
    var user;
    return $('#userlist').html(((function() {
      var _i, _len, _ref, _results;
      _ref = message.content.users;
      _results = [];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        user = _ref[_i];
        userlist[user.uid] = user;
        _results.push("<div class='user' id='user-" + user.uid + "'>" + user.name + "</div>");
      }
      return _results;
    })()).join(''));
  });

  connection.bind('close', function() {
    return $('#send').attr('disabled', 'disabled');
  });

}).call(this);
