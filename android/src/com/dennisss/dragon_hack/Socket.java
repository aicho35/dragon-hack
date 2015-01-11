package com.dennisss.dragon_hack;

import java.net.MalformedURLException;

import org.json.*;

import io.socket.*;

public class Socket {

	public SocketIO socket;

	public Socket(){
		try {
			socket = new SocketIO("http://127.0.0.1:3001/");

			socket.connect(new IOCallback() {
				@Override
				public void onMessage(JSONObject json, IOAcknowledge ack) {
					try {
						System.out.println("Server said:" + json.toString(2));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onMessage(String data, IOAcknowledge ack) {
					System.out.println("Server said: " + data);
				}

				@Override
				public void onError(SocketIOException socketIOException) {
					System.out.println("an Error occured");
					socketIOException.printStackTrace();
				}

				@Override
				public void onDisconnect() {
					System.out.println("Connection terminated.");
				}

				@Override
				public void onConnect() {
					System.out.println("Connection established");
				}

				@Override
				public void on(String event, IOAcknowledge ack, Object... args) {
					System.out.println("Server triggered event '" + event + "'");
				}
			});

		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
    

	public void send(float angle, float throttle, float dx, float dy){
		
		JSONObject obj = new JSONObject();
		
		try {
			
			obj.put("orient", angle);
			obj.put("throt", throttle);
			obj.put("dx", dx);
			obj.put("dy", dy);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//json.add("key", new JsonPrimitive("value"));
		//json.add("key2", new JsonPrimitive("another value"));
		//socket.send(json);

		// Emits an event to the server.
		socket.emit("update", obj);
		
	}
	
	
}
