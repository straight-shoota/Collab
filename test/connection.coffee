connection = new Collab.Connection
connection.connect('ws://localhost:3141')
connection.message (event) =>
	
	