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

    Session.prototype.setUserlist = function(users) {
      var uid, user, _results;
      this.userlist = {};
      _results = [];
      for (uid in users) {
        user = users[uid];
        _results.push(this.addUser(user));
      }
      return _results;
    };

    Session.prototype.getUserlist = function() {
      return this.userlist;
    };

    Session.prototype.addUser = function(user) {
      return this.userlist[user.uid] = user;
    };

    Session.prototype.removeUser = function(user) {
      return this.userlist[user.uid] = null;
    };

    return Session;

  })();

}).call(this);
