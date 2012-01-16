(function() {

  Collab.Log = (function() {

    function Log() {
      var _this = this;
      $(function() {
        return _this.logElem = $('#log');
      });
    }

    Log.prototype.append = function(content) {
      return this.logElem.append(content);
    };

    return Log;

  })();

}).call(this);
