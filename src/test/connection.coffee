connection = new Collab.Connection
connection.connect('ws://localhost:3141')

Key =
	ENTER: 13

$ ->
	log = $('#log')
	$('#send').click sendMessage
	$('#text').keyup (event) ->
		if(event.which == Key.ENTER)
			sendMessage()
	
sendMessage = ->
	text = $('#text').val()
	$('#text').val('')
	console.log text
	connection.send "chat.message",
		text: text

userlist = {}
	
connection.bind "send", (action, message) ->
	console.log "send: " + action
	console.log message
connection.bind "message", (message) ->
	console.log "message: " + message.action
	console.log message.content
#connection.bind "server.info", ->
	#connection.send "session.listUsers"
	#connection.send "chat.history"
connection.bind "chat.message",	(message) ->
	m = message.content
	sender = userlist[m.sender]
	$(log).append "<div class='message'><div class='time'>#{m.date}</div><div class='sender'>#{sender.name}</div><div class='messageText'>#{m.text}</div></div>"
connection.bind "session.info", (message) ->
	$('#userlist').html (for user in message.content.users
		userlist[user.uid] = user
		"<div class='user' id='user-#{user.uid}'>#{user.name}</div>"
	).join ''
	
connection.bind 'close', ->
	$('#send').attr('disabled', 'disabled')