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
		
	userJoined: (user, time) ->
		@log 'statusChanged userStatusChanged userJoined', "#{@formatUser(user)} has joined the session.", time
	userLeft: (user, time) ->
		@log 'statusChanged userStatusChanged userLeft', "#{@formatUser(user)} has left the session.", time
		
	userlist: (users, time) ->
		userlist = (@formatUser(user) for uid, user of users).join(", ")
		@log 'statusChanged userlist', "in this session: #{userlist}", time
		
		
	log: (clazz, content, time) ->
		@append "<div class='message #{clazz}'><div class='time'>#{@getTime(time)}</div><div class='content'>#{content}</div></div>"
	
	getTime: (timestamp) ->
		date = new Date(timestamp)
		h = date.getHours()
		h = "0" + h if h < 10
		m = date.getMinutes()
		m = "0" + m if m < 10
		h + ":" + m
		
	formatUser: (user) ->
		"<span class='username' data-uid='#{user.uid}'>#{user.name}</span>"