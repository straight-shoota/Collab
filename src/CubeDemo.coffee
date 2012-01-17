class Collab.CubeDemo 
	constructor: ->
		@scene = new Collab.CubeDemo.Scene
		@scene.init($('#renderingContainer'));
		@scene.startLoop()

		@log = new Collab.Log()
		
		@connection = new Collab.Connection()
		@session = new Collab.Session(@connection)
		@chat = new Collab.Chat(@session, @log)
		
		@init()
		
	init: ->
		@connection.connect('ws://localhost:3141')

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
	
	update: ->
		$('#stat').html("#{@mouseState.position2D.x} | #{@mouseState.position2D.y}<br/>#{@mouseState.pressed().join(', ')}")
		
	initScene: ->
		@renderer.shadowMapEnabled = true
	
		#add grid plane
		@plane = new THREE.Mesh( new THREE.CubeGeometry( 500, 500, 10 ), new THREE.MeshBasicMaterial( { color: 0x555555, wireframe: !true } ) );
		@plane.rotation.x = Math.PI / -2
		@plane.position.y = -5
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
			drag.offset.sub(drag.object.position, drag.intersect.point)´
			
			@zeroLayer.position.y = drag.intersect.point.y
			console.log "dragstart ", drag.intersect.point
			
			drag.object.material.color.setHex(0xFF0000);
			drag.object.position.y += 10
			drag.object.dynamic = true
			
			#console.log i.point
			@select(drag.object)
			
		@mouseState.bind "dragend", (drag) => 
			@zeroLayer.position.y = 0
			drag.object.position.y -= 10
			@select(false)
			
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
			drag.object.position.x = pos.x
			drag.object.position.z = pos.z
		
		@addCube 2, 2
	
	pickObjects: (flag = false) ->
		ray = @projector.pickingRay( new THREE.Vector3(@mouseState.position2D.x,@mouseState.position2D.y,0), @camera )
		intersects = ray.intersectObjects( @objects )
		
		return intersects if not flag
		
		i for i in intersects when i.object[flag]
		
	addCube: (x, y) ->
		cube = new THREE.Mesh( @data.geo.cube, @data.materials.cube )
		cube.position.set(-250 + x * 50 + 25, 25, 250 - y * 50 - 25);
		cube.name = "cube"
		cube.receiveShadow = true
		cube.castShadow = true
		cube.draggable = true
		@scene.add cube
		@objects.push cube
		
		Collab.message("New Cube added at #{x}, #{y}.", 'info');