class Collab.MouseState
	pressedState:
		left: false
		middle: false
		right: false
	position:
		x: 0
		y: 0
	drag: false
	
	constructor: (container = window.document) ->
		@container = $(container)
		@container.mousemove(@_onMouseMove)
		#@container.click((event) => @_click(event))
		@container.mousedown(@_onMouseDown)
		@container.mouseup(@_onMouseUp)
		@mouseDown = false
		
		@dragend (drag) -> alert "It works"
		
		for name in ['dragstart', 'dragend', 'dragmove']
			this[name] = (data, fn) ->
				if ( fn == null ) 
					fn = data;
					data = null;

				return if arguments.length > 0 then this.on( name, null, data, fn ) else this.trigger( name )
		
	
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
		
		console.log 
		if @pressedState.left
			@drag = 
				startPosition: @oldPosition
				position: @position
				startTime: event.timeStamp
				button: 'left'
			@trigger('dragstart', @drag)
		
		if @drag
			@drag.position = @position
		
	_onMouseDown: (event) => @_onMouseChange(event, true)
	_onMouseUp: (event) => @_onMouseChange(event, false)
	_onMouseChange: (event, pressed) ->
		event.preventDefault()
		@_setPosition(event)
		
		#console.log event
		button = Collab.MouseState.buttons[event.which]
		
		@pressedState[button] = pressed
		
		#console.log @drag
		if button == 'left' and !pressed and @drag
			@drag.endPosition = @position
			@drag.endTime = event.timeStamp
			@trigger('dragend', @drag)
			console.log 'dragend'
			@drag = false
		
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
	
Collab.MouseState.prototype	 = $.extend(Collab.MouseState.prototype, $.fn);

Collab.MouseState.buttons =
	1: 'left'
	2: 'middle'
	3: 'right'