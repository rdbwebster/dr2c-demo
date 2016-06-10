package demoapp;

import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vmware.vcloud.api.rest.schema.ReferenceType;
import com.vmware.vcloud.sdk.Task;
import com.vmware.vcloud.sdk.VM;
import com.vmware.vcloud.sdk.VcloudClient;

import demoapp.model.NavBean;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

@WebSocket
public class WebSocketHandler {
	
    final static Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

    // Store sessions if you want to, for example, broadcast a message to all users
   // private static final Queue<Session> sessions = new ConcurrentLinkedQueue<>();

    @OnWebSocketConnect
    public void connected(Session session) {
    	 logger.debug("WebSocket Got websocket connection request");  
    //    sessions.add(session);
    }

    @OnWebSocketClose
    public void closed(Session session, int statusCode, String reason) {
    	 logger.debug("WebSocket Got websocket disconnect request");  
    //    sessions.remove(session);
    }

    // In current App implementation we are not expecting inbound messages from client, echo back
    @OnWebSocketMessage
    public void message(Session session, String message) throws IOException {
        logger.debug("WebSocket Got: " + message);   // Print message
   
        // Parse message for action
        
        // Save the session indexed by username
        App.wsSessionMap.put(message, session);
        
   //     session.getRemote().sendString(message); // and send it back
    }

}

