class Collab.Session
	userlist: {}
	constructor: (connection) ->
		@connection = connection
		
	getUser: (id) ->
		@userlist[id] || 
				name: 'client' + id
	setUserlist: (users) ->
		@userlist = {}
		@addUser(user) for uid, user of users
		
	getUserlist: () ->
		@userlist
	
	addUser: (user) ->
		@userlist[user.uid] = user
		
	removeUser: (user) ->
		@userlist[user.uid] = null
		