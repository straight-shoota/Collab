class Collab.Log
	constructor: ->
		$ =>
			@logElem = $('#log')
	
	append: (content) ->
		@logElem.append(content)