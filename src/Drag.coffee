class Collab.MouseState.Drag extends Collab.MouseState
	drag: false
	
	constructor: (container) ->
		super(container)
				
		@bind 'mousemove', (destination, source) =>
			if @pressedState.left
				if @drag
					@drag.position = destination
					@trigger 'dragmove', @drag, source, destination
				#else
					#@startDrag(oldPosition)
					#@drag.position = @position
				
		@bind 'mousechange', (button, pressed) =>
			if button == 'left'
				if pressed
					if intersect = @isDraggable(@position)
						@startDrag(@position, intersect)
				if !pressed and @drag
					@drag.endPosition = @position
					#@drag.endTime = event.timeStamp
					@trigger 'dragend', @drag
					console.log 'dragend'
					@drag = false
	
	isDraggable: (position) -> false
	startDrag: (position, intersect) ->
		@drag = 
			startPosition: position
			position: @position
			button: 'left'
			intersect: intersect
			object: intersect.object
		@trigger 'dragstart', @drag
		