class Collab.Log
	constructor: ->
		$ =>
			@logElem = $('#log')
	
	append: (content) ->
		elem = $(content).hide()
		@logElem.append(elem)
		elem.fadeIn().slideDown()
	
	message: (sender, text, time) ->
		@append @createElem 'message', "<div class='sender'>#{sender}</div><div class='messageText'>#{text}</div>", time
		
	createElem: (clazz, content, time) ->
		"<div class='#{clazz}'><div class='time'>#{@getTime(time)}</div><div class='content'>#{content}</div></div>"
	
	getTime: (timestamp) ->
		date = new Date(timestamp)
		h = date.getHours()
		h = "0" + h if h < 10
		m = date.getMinutes()
		h = "0" + m if m < 10
		"#{h}:#{m}"