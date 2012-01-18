package de.hsfulda.collabserver.controller;

import javax.vecmath.Point3d;

import org.json.JSONException;
import org.json.JSONObject;

import de.hsfulda.collabserver.CollabClient;
import de.hsfulda.collabserver.Message;
import de.hsfulda.collabserver.scene.Object3D;
import de.hsfulda.collabserver.scene.Transformation;
import de.hsfulda.collabserver.uid.UniqueEntityProvider;

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
				
				Transformation transformation = new Transformation(target, source, destination);
				
				transformation.apply();
				client.getSession().send("scene.transformation", transformation);
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
