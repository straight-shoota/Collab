class window.EventManager
	callbacks: {}
	bind: (event, callback) ->
		@callbacks[event] or= []
		@callbacks[event].push callback
		@ # chainable
		
	unbind: (event, callback) ->
		@callbacks[event][i] = null for reference, i in @callbacks[event] || [] when reference == callback
		@ # chainable
	
	trigger: (event, args...) ->
		callback args... for callback in @callbacks[event] || []
		@ # chainable