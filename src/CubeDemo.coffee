class Collab.CubeDemo 
	constructor: ->
		@scene = new Collab.CubeDemo.Scene
		@scene.init($('#renderingContainer'));
		@scene.triggerAction = (action, data) =>
			@connection.send 'scene.' + action, data
		@scene.startLoop()

		@log = new Collab.Log()
		
		@connection = new Collab.Connection()
		@scene.initConnectionHandlers(@connection)
		
		@connection.bind 'close', =>
			@showOverlay(if @connection.initialized then 'lost connection :(' else 'server not responding :(')
		
		@session = new Collab.Session(@connection)
		@chat = new Collab.Chat(@session, @log)
		
		$(=> @createOverlay())
		
		@init()
		
	init: ->
		@connection.connect('ws://localhost:3141')
		
	createOverlay: ->
		$overlay = $('<div id="overlay" />')
		$overlayMessage = $('<div id="overlayMessage" />')
		$area = $('#contentArea')
		$('body').append($overlay).append($overlayMessage)
		$overlay.css(
			top: $area.offset().top
			left: $area.offset().left
			width: $area.innerWidth()
			height: $area.innerHeight()
		)
		$overlayMessage.css(
			top: $area.offset().top
			left: $area.offset().left
		)
		
		@$overlay = $overlay
		@$overlayMessage = $overlayMessage
		
		@connection.bind 'server.info', => @hideOverlay()
		@showOverlay('connecting ...')
		
	hideOverlay: ->
		@$overlay.fadeOut()
		@$overlayMessage.fadeOut()
		
	showOverlay: (message = false) ->
	
		if(message)
			@$overlayMessage.html(message)
		else
			@$overlayMessage.html('')
		
		@$overlayMessage.css(
			marginTop: (@$overlay.innerHeight() - @$overlayMessage.height()) / 2 
			marginLeft: (@$overlay.innerWidth() - @$overlayMessage.width()) / 2 
		)
		@$overlay.fadeIn()
		@$overlayMessage.fadeIn()

class Collab.CubeDemo.Scene extends Collab.Scene
	settings:
		renderer:
			antialias: true
		camera:
			angle:	45
			near:	.1
			far:	10000
	
	data:
		materials:
			cube: new THREE.MeshLambertMaterial(
				color: 0x00ff80
				ambient: 0x00ff80
				shading: THREE.FlatShading )
			cubeHighlight: new THREE.MeshLambertMaterial(
				color: 0x0000FF
				ambient: 0x00ff80
				shading: THREE.FlatShading )
		geo:
			cube: new THREE.CubeGeometry( 50, 50, 50 )
	
	objects: []
	uidObjectDirectory: {}
	
	update: ->
		$('#stat').html("#{@mouseState.position2D.x} | #{@mouseState.position2D.y}<br/>#{@mouseState.pressed().join(', ')}")
		
	initScene: ->
		@renderer.shadowMapEnabled = true
	
		#add grid plane
		@plane = new THREE.Mesh( new THREE.CubeGeometry( 500, 500, 10 ), new THREE.MeshBasicMaterial( { color: 0x555555, wireframe: !true } ) );
		@plane.rotation.x = Math.PI / -2
		@plane.position.y = -30
		@plane.name = "plane"
		@plane.receiveShadow = true
		@plane.castShadow = true
		@scene.add @plane
		
		@zeroLayer = new THREE.Mesh( new THREE.PlaneGeometry( 10000, 10000, 10, 10 ), new THREE.MeshBasicMaterial(
			opacity: 0
		))
		@zeroLayer.rotation.x = -90 * Math.PI / 180
		@zeroLayer.name = "zeroLayer"
		@scene.add @zeroLayer
		
		lamp = new THREE.SpotLight
		lamp.position.set(0, 300, 50)
		lamp.castShadow = true
		@scene.add lamp
		
		@mouseState = new Collab.MouseState.Drag($('#renderingContainer'))
		@mouseState.bind 'dragend', (drag) -> 
			$('#drag').html("#{drag.startPosition.x} | #{drag.startPosition.y} => #{drag.endPosition.x} | #{drag.endPosition.y}")		
			
		@mouseState.isDraggable = (position) =>
			return @pickObjects('draggable')[0]
			
		@mouseState.bind "mousemove", =>
			@container.css('cursor', if @pickObjects('draggable').length then 'pointer' else 'auto')
		
		@mouseState.bind "dragstart", (drag) => 
			drag.offset = new THREE.Vector3()
			drag.offset.sub(drag.object.position, drag.intersect.point)
			
			@zeroLayer.position.y = drag.intersect.point.y
			console.log "dragstart ", drag.intersect.point
			
			#console.log "hovering " + drag.object.uid
			#drag.object.position.y += 10
			drag.object.dynamic = true
			
			#console.log i.point
			@select(drag.object)
			
		@mouseState.bind "dragend", (drag) => 
			@zeroLayer.position.y = 0
			#console.log "surfacing " + drag.object.uid
			#drag.object.position.y -= 10
			@select(false)
			
			@triggerAction 'drag',
				target: drag.object.uid
				source: drag.objectStartPosition
				destination: drag.object.position
			
		@mouseState.bind "dragmove", (drag, source, destination) =>
			#console.log destination
			#drag.object.position.x += destination.x - source.x
			#drag.object.position.z += destination.y - source.y
			#s@positionOnPlane = i.point
			ray = @projector.pickingRay( new THREE.Vector3(@mouseState.position2D.x,@mouseState.position2D.y,0), @camera );
			intersects = ray.intersectObject( @zeroLayer )[0]
			#console.log intersects
			pos = new THREE.Vector3()
			#pos = intersects.point
			pos.add(intersects.point, drag.offset)
			drag.object.position.set(Math.round(pos.x), 0, Math.round(pos.z))
		
		#@addCube 2, 2
	
	initConnectionHandlers: (connection) ->
		connection.bind 'server.info', =>
			connection.send "scene.get"
		connection.bind 'scene.content', (message) =>
			@removeObjects()
			#console.log message.content
			for o in message.content.objects
				@createCube(o)
				
		connection.bind 'scene.transformation', (message) =>
			console.log "transformation: ", message.content
			object = @getObject(message.content.target)
			if(message.content.type == 'translation')
				console.log "apply", message.content.destination
				object.position.copy(message.content.destination)
	
	pickObjects: (flag = false) ->
		ray = @projector.pickingRay( new THREE.Vector3(@mouseState.position2D.x,@mouseState.position2D.y,0), @camera )
		intersects = ray.intersectObjects( @objects )
		
		return intersects if not flag
		
		i for i in intersects when i.object[flag]
		
	addCube: (x, y) ->
		cube = new THREE.Mesh( @data.geo.cube, @data.materials.cube )
		cube.position.set(-250 + x * 50 + 25, 25, 250 - y * 50 - 25);
		cube.name = "cube"
		cube.uid = 1
		cube.receiveShadow = true
		cube.castShadow = true
		cube.draggable = true
		@scene.add cube
		@objects.push cube
		
		Collab.message("New Cube added at #{x}, #{y}.", 'info');
		
	createCube: (object) ->
		cube = new THREE.Mesh( @data.geo.cube, @data.materials.cube )
		#cube.position.set(-250 + object.position.x * 50 + 25, object.position.z + 25, 250 - object.position.z * 50 - 25);
		cube.position.copy object.position
		cube.name = "cube"
		cube.uid = object.uid
		cube.receiveShadow = true
		cube.castShadow = true
		cube.draggable = true
		@scene.add cube
		@addObject(cube)
		
	getObject: (uid) ->
		@uidObjectDirectory[uid]
	addObject: (object) ->
		@uidObjectDirectory[object.uid] = object
		@objects.push(object)
	removeObjects: ->
		for o in @objects
			@scene.remove(o)
		@objects = []
		@uidObjectDirectory = {}
	removeObject: (o) ->
		@scene.remove(o)
		i = @objects.indexOf(o)
		@objects.splice(i, 1) if i != -1
		@uidObjectDirectory[o.uid] = null
	