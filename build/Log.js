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

    Log.prototype.userJoined = function(name, uid, time) {
      return this.log('statusChanged userStatusChanged userJoined', "<strong class='username' data-uid='" + uid + "'>" + name + "</strong> has joined the session.", time);
    };

    Log.prototype.userLeft = function(name, uid, time) {
      return this.log('statusChanged userStatusChanged userLeft', "<strong class='username' data-uid='" + uid + "'>" + name + "</strong> has left the session.", time);
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

    return Log;

  })();

}).call(this);
