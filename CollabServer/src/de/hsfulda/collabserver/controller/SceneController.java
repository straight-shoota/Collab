package de.hsfulda.collabserver.controller;

import javax.vecmath.Point3d;

import org.json.JSONException;
import org.json.JSONObject;

import de.hsfulda.collabserver.CollabClient;
import de.hsfulda.collabserver.Message;
import de.hsfulda.collabserver.scene.Object3D;
import de.hsfulda.collabserver.scene.Scene;
import de.hsfulda.collabserver.scene.Translation;

public class SceneController extends ActionDelegateController {

	@Override
	protected void initActions(){
		bind("drag", new CollabMessageListener() {
			@Override
			public void doAction(Message message, CollabClient client) throws Exception {
				Object3D target = client.getSession().getScene().getObject(message.getContent().getInt("target"));
				Point3d source = readPointFromJSON(message.getContent().getJSONObject("source"));				
				Point3d destination = readPointFromJSON(message.getContent().getJSONObject("destination"));
				
				if(! source.equals(target.getPosition())){
					throw new UnsyncedException(target, target.getPosition(), source, "position");
				}
				
				Scene scene = client.getSession().getScene();
				Translation translation = new Translation(target, source, destination);
				scene.registerUID(translation);
				
				translation.getDestination().setY(0);
				
				translation.apply();
				client.getSession().send("scene.transformation", translation);
			}
		});
		bind("get", new CollabMessageListener() {
			@Override
			public void doAction(Message message, CollabClient client) throws Exception {
				client.getSession().send("scene.content", client.getSession().getScene());
			}
		});
	}
	
	public Point3d readPointFromJSON(JSONObject j) throws JSONException {
		return new Point3d(
				j.getDouble("x"),
				j.getDouble("y"),
				j.getDouble("z"));
	}
}
