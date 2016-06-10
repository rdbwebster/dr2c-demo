package demoapp;


import org.eclipse.jetty.websocket.api.Session;

import com.vmware.vcloud.api.rest.schema.ReferenceType;

/*
 *  DemoTask is a value object that contains information about outstanding Async Tasks that have been submitted to vCD. 
 */

public class DemoTask {

	String vmHref;
	String action;              // ?
	String username;        
	spark.Session requestorSession;  // remove instead add username and use as index to clientMap
	
	String taskHref;
	
	public spark.Session getRequestorSession() {
		return requestorSession;
	}
	
	public void setRequestorSession(spark.Session requestorSession) {
		this.requestorSession = requestorSession;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}

	public String getVmHref() {
		return vmHref;
	}
	public void setVmHref(String vmHref) {
		this.vmHref = vmHref;
	}
	public String getTaskHref() {
		return taskHref;
	}
	public void setTaskHref(String taskHref) {
		this.taskHref = taskHref;
	}

	
}
