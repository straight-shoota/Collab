(function() {

  Collab.Log = (function() {

    function Log() {
      var _this = this;
      $(function() {
        return _this.logElem = $('#log');
      });
    }

    Log.prototype.append = function(content) {
      var elem;
      elem = $(content).hide();
      this.logElem.append(elem);
      return elem.fadeIn().slideDown();
    };

    Log.prototype.message = function(sender, text, time) {
      return this.log('chatMessage', "<div class='sender'>" + sender + "</div><div class='messageText'>" + text + "</div>", time);
    };

    Log.prototype.userJoined = function(user, time) {
      return this.log('statusChanged userStatusChanged userJoined', "" + (this.formatUser(user)) + " has joined the session.", time);
    };

    Log.prototype.userLeft = function(user, time) {
      return this.log('statusChanged userStatusChanged userLeft', "" + (this.formatUser(user)) + " has left the session.", time);
    };

    Log.prototype.userlist = function(users, time) {
      var uid, user, userlist;
      userlist = ((function() {
        var _results;
        _results = [];
        for (uid in users) {
          user = users[uid];
          _results.push(this.formatUser(user));
        }
        return _results;
      }).call(this)).join(", ");
      return this.log('statusChanged userlist', "in this session: " + userlist, time);
    };

    Log.prototype.log = function(clazz, content, time) {
      return this.append("<div class='message " + clazz + "'><div class='time'>" + (this.getTime(time)) + "</div><div class='content'>" + content + "</div></div>");
    };

    Log.prototype.getTime = function(timestamp) {
      var date, h, m;
      date = new Date(timestamp);
      h = date.getHours();
      if (h < 10) h = "0" + h;
      m = date.getMinutes();
      if (m < 10) m = "0" + m;
      return h + ":" + m;
    };

    Log.prototype.formatUser = function(user) {
      return "<span class='username' data-uid='" + user.uid + "'>" + user.name + "</span>";
    };

    return Log;

  })();

}).call(this);
