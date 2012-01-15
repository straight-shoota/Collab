class Collab.Log
	constructor: (connection) ->
		@connection = connection
		
		$ => @init()
		
		@connection.bind "send", (action, message) =>
			console.log "send: " + action
			console.log message
		
		@connection.bind "message", (message) =>
			console.log "message: " + message.action
			console.log message.content
		
		#connection.bind "server.info", ->
			#connection.send "session.listUsers"
			#connection.send "chat.history"
		@connection.bind "chat.message",	(message) =>
			m = message.content
			sender = @userlist[m.sender]
			if(sender == undefined)
				sender = 
					name: 'client' + m.sender
			@logElem.append "<div class='message'><div class='time'>#{m.date}</div><div class='sender'>#{sender.name}</div><div class='messageText'>#{m.text}</div></div>"
		
		#@connection.bind "session.info", (message) ->
		#	$('#userlist').html (for user in message.content.users
		#		userlist[user.uid] = user
		#		"<div class='user' id='user-#{user.uid}'>#{user.name}</div>"
		#	).join ''
			
		@connection.bind 'close', =>
			@textElem.attr('disabled', 'disabled')
	
	sendMessage: (text) ->
		console.log text
		@connection.send "chat.message",
			text: text
	
	init: ->
		# this goes on jQuery.ready:
		
		@logElem = $('#log')
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