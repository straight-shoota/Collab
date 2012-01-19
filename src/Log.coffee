class Collab.Log
	constructor: ->
		$ =>
			@logElem = $('#log')
	
	append: (content) ->
		elem = $(content).hide()
		@logElem.append(elem)
		elem.fadeIn().slideDown()
	
	message: (sender, text, time) ->
		@log 'chatMessage', "<div class='sender'>#{sender}</div><div class='messageText'>#{text}</div>", time
		
	userJoined: (name, uid, time) ->
		@log 'statusChanged userStatusChanged userJoined', "<strong class='username' data-uid='#{uid}'>#{name}</strong> has joined the session.", time
	userLeft: (name, uid, time) ->
		@log 'statusChanged userStatusChanged userLeft', "<strong class='username' data-uid='#{uid}'>#{name}</strong> has left the session.", time
		
	log: (clazz, content, time) ->
		@append "<div class='message #{clazz}'><div class='time'>#{@getTime(time)}</div><div class='content'>#{content}</div></div>"
	
	getTime: (timestamp) ->
		date = new Date(timestamp)
		h = date.getHours()
		h = "0" + h if h < 10
		m = date.getMinutes()
		m = "0" + m if m < 10
		h + ":" + m