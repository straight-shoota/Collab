package de.hsfulda.collabserver.controller;

import de.hsfulda.collabserver.CollabClient;
import de.hsfulda.collabserver.Message;

public class UserController extends ActionDelegateController {
	
	@Override
	protected void initActions() {
		bind("update", new CollabMessageListener() {

			@Override
			public void doAction(Message message, CollabClient client) {
				// TODO Auto-generated method stub
				
			}
		});
	}

		/*if(command.length == 1){
			throw new InvalidControllerActionException(command);
		}
		
		if(command[1].equals("info")){
			//client.sendMessage(client.getClientInfo());
		}else if(command[1].equals("update")){
			/*try {
				/*JSONObject object = client.readJSONObject();
				if(object != null){
					for(String key : JSONObject.getNames(object)){
						client.updateClientInfo(key, object.get(key));
					}
				}
			}catch(JSONException e){
				e.printStackTrace();
			}
		}else
			throw new InvalidControllerActionException(command);
		}*/
}
