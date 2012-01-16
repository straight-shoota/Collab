(function() {

  Collab.Session = (function() {

    Session.prototype.userlist = {};

    function Session(connection) {
      this.connection = connection;
    }

    Session.prototype.getUser = function(id) {
      return this.userlist[id] || {
        name: 'client' + id
      };
    };

    return Session;

  })();

}).call(this);
