class Collab.Scene
	
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
		#@scene.add(new THREE.AmbientLight(0x606060))
		
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

	select: (object) ->
		if(object)
			@selected = object
		else
			@selected = false
		@highlight(object)
		
	highlight: (object) ->
		@container.css('pointer', if object then 'move' else 'auto')
	
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