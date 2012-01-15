class Collab.CubeDemo 
	constructor: ->
		@scene = new Collab.CubeDemo.Scene
		@scene.init($('#renderingContainer'));
		@scene.startLoop()

		@connection = new Collab.Connection()
		@session = new Collab.Session(@connection)
		@chat = new Collab.Chat()
		
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
	
	update: ->
		$('#stat').html("#{@mouseState.position2D.x} | #{@mouseState.position2D.y}<br/>#{@mouseState.pressed().join(', ')}")
		
	initScene: ->
		#add grid plane
		@plane = new THREE.Mesh( new THREE.PlaneGeometry( 500, 500, 10, 10 ), new THREE.MeshBasicMaterial( { color: 0x555555, wireframe: !true } ) );
		@plane.rotation.x = -90 * Math.PI / 180
		@plane.name = "plane"
		@scene.add @plane
		
		@zeroLayer = new THREE.Mesh( new THREE.PlaneGeometry( 10000, 10000, 10, 10 ), new THREE.MeshBasicMaterial(
			opacity: 0
		))
		@zeroLayer.rotation.x = -90 * Math.PI / 180
		@zeroLayer.name = "zeroLayer"
		@scene.add @zeroLayer
			
		@mouseState = new Collab.MouseState.Drag($('#renderingContainer'))
		@mouseState.bind 'dragend', (drag) -> 
			$('#drag').html("#{drag.startPosition.x} | #{drag.startPosition.y} => #{drag.endPosition.x} | #{drag.endPosition.y}")		
			
		@mouseState.isDraggable = (position) =>
			ray = @projector.pickingRay( new THREE.Vector3(@mouseState.position2D.x,@mouseState.position2D.y,0), @camera );
			intersects = ray.intersectScene( @scene )
			
			for i in intersects
				if i.object.name == "cube"
					i.object.material.color.setHex(0xFF0000);
					i.object.dynamic = true
					
					#console.log i.point
					@select(i.object)
					return i
				else if i.object.name == "plane"
					@positionOnPlane = i.point
		
		@mouseState.bind "dragstart", (drag) => 
			#@mouseSate.drag.offset = drag.object.position - drag.intersect.point
			#console.log drag.intersect.point.sub(drag.object.position)7
			drag.offset = new THREE.Vector3()
			drag.offset.sub(drag.object.position, drag.intersect.point)
			console.log "dragstart ", drag.object.position, drag.intersect.point
			
		@mouseState.bind "dragmove", (drag, source, destination) =>
			#console.log destination
			#drag.object.position.x += destination.x - source.x
			#drag.object.position.z += destination.y - source.y
			#s@positionOnPlane = i.point
			ray = @projector.pickingRay( new THREE.Vector3(@mouseState.position2D.x,@mouseState.position2D.y,0), @camera );
			intersects = ray.intersectObject( @zeroLayer )[0]
			#console.log intersects
			pos = new THREE.Vector3()
			pos.add(intersects.point, drag.offset)
			drag.object.position.x = pos.x
			drag.object.position.z = pos.z
		
		@addCube 2, 2
		
	addCube: (x, y) ->
		cube = new THREE.Mesh( @data.geo.cube, @data.materials.cube )
		cube.position.x = -250 + x * 50 + 25;
		cube.position.z = 250 - y * 50 - 25;
		cube.position.y = 25
		cube.name = "cube"
		@scene.add cube;
		
		Collab.message("New Cube added at #{x}, #{y}.", 'info');