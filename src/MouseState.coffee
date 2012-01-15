class Collab.MouseState extends EventManager
	pressedState:
		left: false
		middle: false
		right: false
	position:
		x: 0
		y: 0
	position2D:
		x: 0
		y: 0
	mouseDown: false
	
	constructor: (container = window.document) ->
		@container = $(container)
		@container.mousemove(@_onMouseMove)
		@container.mousedown(@_onMouseDown)
		@container.mouseup(@_onMouseUp)
		@bind 'mousemove', (destination, source) =>
			@position2D.x = ((destination.x) / @container.innerWidth() ) * 2 - 1
			@position2D.y = -(( destination.y) / @container.innerHeight() ) * 2 + 1
		
	destroy: ->
		@container.unbind('mousedown', @_onMouseDown)
		@container.unbind('mouseup', @_onMouseUp)
		@container.unbind('mousemove', @_onMouseMove)
		
	_setPosition: (event) =>
		@position = 
			x: event.offsetX
			y: event.offsetY
	
	_onMouseMove: (event) =>
		oldPosition = @position
		@_setPosition(event)
		
		@trigger 'mousemove', @position, oldPosition, event
		
	_onMouseDown: (event) => @_onMouseChange(event, true)
	_onMouseUp: (event) => @_onMouseChange(event, false)
	_onMouseChange: (event, pressed) ->
		event.preventDefault()
		@_setPosition(event)
		
		#console.log event
		button = Collab.MouseState.buttons[event.which]
		
		@pressedState[button] = pressed
		
		#console.log @drag
		@trigger 'mousechange', button, pressed, event
		
		#console.log @pressed()
		#console.log @pressed('left')
		#console.log @pressed(1)
	
		
	pressed: (button = null) ->
		if button == null
			return (button for button, state of @pressedState when state)
		else
			if button in @pressedState
				return @pressedState[button]
			else
				key = Collab.MouseState.buttons[button]
				if key in @pressedState
					return @pressedState[key]
		
		return false
		
	dragend: (fn) ->
		@bind('dragend', fn )

Collab.MouseState.buttons =
	1: 'left'
	2: 'middle'
	3: 'right'