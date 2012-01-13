
class Collab.3D
	
	mouse2D: new THREE.Vector3(0,0,0)
	inLoop: false
	selected: false
	
	constructor: ->
	
	init: (container) ->
		@container = $(container)
		@renderer	= new THREE.WebGLRenderer @settings.renderer
		@renderer.setSize @container.innerWidth(), @container.innerHeight()
		@container.append(@renderer.domElement);
		
		#setup scene
		@scene		= new THREE.Scene
		@scene.add(new THREE.AmbientLight(0xB0B0B0))
		
		# setup camera
		aspect = @settings.camera.aspect || @container.innerWidth() / @container.innerHeight()
		@camera		= new THREE.PerspectiveCamera(	@settings.camera.angle, aspect, 
													@settings.camera.near, @settings.camera.far)
		@camera.position.z = 550
		@camera.position.y = 350
		@cameraTarget = new THREE.Vector3( 0, 0, 0 )
		@camera.lookAt @cameraTarget
		@scene.add @camera
		
		#ray picking
		@projector = new THREE.Projector()
		
		@initScene()
		
	initScene: ->
	
	
	render: ->
		@renderer.render(@scene, @camera)
	
	update: ->
	
	startLoop: ->
		@inLoop = true
		@_loop();
	
	stopLoop: ->
		@inLoop = false
	
	_loop: ->
		@update();
		
		@render();
		
		requestAnimationFrame(=> @_loop()) if( @inLoop)
		
	_mouseMove: (event) ->
		@mouse2D.x = ((event.pageX - @container.offset().left) / @container.innerWidth() ) * 2 - 1
		@mouse2D.y = -(( event.pageY - @container.offset().top) / @container.innerHeight() ) * 2 + 1
		#console.log("mouse position: #{round(@mouse2D.x, 2)} #{round(@mouse2D.y, 2)} --- #{event.clientX} #{event.clientY}")
		
		ray = @projector.pickingRay( @mouse2D.clone(), @camera );
		intersects = ray.intersectScene( @scene )
		
		if(@selected)
			for i in intersects
				if i.object.name == "plane"
					#console.log i
					@selected.position.x += i.point.x - @positionOnPlane.x
					@selected.position.z += i.point.z - @positionOnPlane.z
					@positionOnPlane = i.point
					#console.log 
		else
			hover = false
			for i in intersects
				if i.object.name == "cube"
					hover = true
			
			@container.css('cursor', if hover then 'pointer' else 'auto')
		
		
	_click: (event) ->
	_mouseDown: (event) ->
		ray = @projector.pickingRay( @mouse2D.clone(), @camera );
		intersects = ray.intersectScene( @scene )
		
		for i in intersects
			if i.object.name == "cube"
				i.object.material.color.setHex(0xFF0000);
				i.object.dynamic = true
				#console.log i
				@select(i.object)
			else if i.object.name == "plane"
				@positionOnPlane = i.point
				
		
	_mouseUp: (event) ->
		@select(false)
		@positionOnPlane = null
	
	select: (object) ->
		if(object)
			@selected = object
		else
			@selected = false
		@container.css('cursor', if object then 'move' else 'auto');
	
Collab.message = (message, level = "info", classes = []) ->
	classes = classes.join(" ")
	log = console.log
	log = console[level] if typeof console[level] == "function"
	#log(message) if log && typeof log == "function"
	$message = $('<div class="message ' + classes + '">' + message + '</div>').hide();
	Collab.message.container.append($message)
	$message.slideDown().delay(5000).fadeOut();

$( ->
	Collab.message.container = $('#messages')
)


	
window.round = (n, digits = 0) ->
	power = Math.pow(10, digits)
	n = n * power
	n = Math.round(n)
	n = n / power