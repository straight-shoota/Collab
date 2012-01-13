class Collab.CubeDemo extends Collab
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
		
	initScene: ->
		#add grid plane
		@plane = new THREE.Mesh( new THREE.PlaneGeometry( 500, 500, 10, 10 ), new THREE.MeshBasicMaterial( { color: 0x555555, wireframe: !true } ) );
		@plane.rotation.x = -90 * Math.PI / 180
		@plane.name = "plane"
		@scene.add @plane
		
		@addCube 2, 2
		
	addCube: (x, y) ->
		cube = new THREE.Mesh( @data.geo.cube, @data.materials.cube )
		cube.position.x = -250 + x * 50 + 25;
		cube.position.z = 250 - y * 50 - 25;
		cube.position.y = 25
		cube.name = "cube"
		@scene.add cube;
		
		Collab.message("New Cube added at #{x}, #{y}.", 'info');