class Collab.Connection extends EventManager
	socket: null
	connected: false
	initialized: false
	connect: (url) ->
		@initialized = false
		@socket = new WebSocket(url)
		@socket.onopen = =>
			@connected = true
			@initialized = true
			@trigger "open"
		@socket.onmessage = (event) => @triggerMessage(event)
		@socket.onclose = =>
			@connected = false
			@trigger "close"
		
	close: ->
		@socket.close()
		
	triggerMessage: (event) ->
		data = JSON.parse(event.data)
		
		message = 
			action: data[0]
			content: data[1]
		
		@trigger "message", message
		@trigger message.action, message
		
	state: ->
		@socket.readyState
		
	message: (fn) ->
		@bind 'message', fn
	
	send: (action, message) ->
		if not @connected then throw new Exception "not connected"
		
		@trigger "send", action, message
		@socket.send JSON.stringify([action, message])
		
		@ #chainable	


if (window.MozWebSocket)
	window.WebSocket = window.MozWebSocket;
