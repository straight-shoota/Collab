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
      return this.append(this.createElem('message', "<div class='sender'>" + sender + "</div><div class='messageText'>" + text + "</div>", time));
    };

    Log.prototype.createElem = function(clazz, content, time) {
      return "<div class='" + clazz + "'><div class='time'>" + (this.getTime(time)) + "</div><div class='content'>" + content + time + "</div></div>";
    };

    Log.prototype.getTime = function(timestamp) {
      var date, h, m;
      date = new Date(timestamp);
      h = date.getHours();
      if (h < 10) h = "0" + h;
      m = date.getMinutes();
      if (m < 10) h = "0" + m;
      return "" + h + ":" + m;
    };

    return Log;

  })();

}).call(this);
