class Collab.Session
	userlist: {}
	constructor: (connection) ->
		@connection = connection
		
	getUser: (id) ->
		@userlist[id] || 
				name: 'client' + id