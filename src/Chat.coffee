class Collab.Chat
	constructor: (session, log) ->
		@session = session
		@log = log
		
		$ => @init()
		
		#@session.connection.bind "send", (action, message) =>
		#	console.log "send: " + action
		#	console.log message
		
		#@session.connection.bind "message", (message) =>
		#	console.log "message: " + message.action
		#	console.log message.content
		
		#connection.bind "server.info", ->
			#connection.send "session.listUsers"
			#connection.send "chat.history"
		@session.connection.bind "chat.message",	(message) =>
			m = message.content
			sender = @session.getUser(m.sender)
			@log.message sender.name, m.text, m.timestamp
			#@log.append "<div class='message'><div class='time'>#{date}</div><div class='sender'>#{sender.name}</div><div class='messageText'>#{m.text}</div></div>"
		
		@session.connection.bind "session.info", (message) =>
			@session.setUserlist(message.content.users)
			@log.userlist @session.getUserlist(), message.content.timestamp
		@session.connection.bind 'session.userJoined', (message) =>
			m = message.content
			@session.addUser(m)
			@log.userJoined m, m.timestamp
		@session.connection.bind 'session.userLeft', (message) =>
			m = message.content
			@session.removeUser(m)
			@log.userLeft m, m.timestamp		
			
		@session.connection.bind 'server.info', =>
			@session.connection.send "session.listUsers"
			
		@session.connection.bind 'close', =>
			@textElem.attr('disabled', 'disabled')
	
	sendMessage: (text) ->
		console.log text
		@session.connection.send "chat.message",
			text: text
	
	init: ->
		# this goes on jQuery.ready:
		@inputElem = $('#text');
		@inputElem.keyup (event) =>
			if(event.which == Collab.Key.ENTER)
				@sendMessage(@inputElem.val())
				@inputElem.val('')
				
		
Collab.Key =
	ENTER		: 13
	LEFT		: 37
	UP			: 38
	RIGHT		: 39
	DOWN		: 40
	SPACE		: 32
	PAGE_UP		: 33
	PAGE_DOWN	: 34
	TAB			: 9