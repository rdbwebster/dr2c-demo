package demoapp;

import java.util.ArrayList;
import java.util.List;
import demoapp.model.Organization;
import demoapp.model.ReferenceType;
import demoapp.model.ReplicationGroup;
import demoapp.model.Task;
import demoapp.model.Vdc;
import demoapp.model.VMInfo;
import demoapp.model.Vapp;

/*
 * Client is an interface to support different concrete client types.
 * It abstracts the libraries used to make the rest calls to vCD and the response parsing technology.
 * The client returns UI Value beans that the UI can use to present screens without a dependence on any 
 * particular client object types.
 * 
 * For example the Java SDK provided with vCD which parses XML responses into schema based types.
 * vs the Apache http Client with parses the xml without relying on schema based types.
 * One use of this feature is during testing.  The Apache client will run against a moch vCD instance
 * which returns the correct data.  This is not easy with the SDK client as it pulls schema information for 
 * each response object from the server.
 */

public abstract class VcdClient {
	
	
	public VcdClient(String vcloudUrl) {
		
	}

	abstract public void  login(String username, String password) throws DemoException;
	
	abstract public void  logout();
		
	abstract public String getVcloudToken() throws DemoException;
	
	abstract public String getOrgName();  // the org named in the login
	
	abstract public Task getTaskStatus(String taskHref) throws DemoException; 
	
	abstract public String getVMStatus(String vmHref) throws DemoException;
	
	abstract public List<VMInfo> getVdcVMs(Vdc vdc) throws DemoException; 
	
	// need a getReplGroupVms() gets vapp by id and vms note can derive href from id almost copy of above
	
//	abstract public List<ReferenceType> getOrgVdcList(String orgHref) throws DemoException;    // list of vdc names in the org
	
	abstract public List<ReferenceType> getOrgRefList() throws DemoException;     
	
	abstract public Task powerOnVM(String vmHref);
	
	abstract public Task powerOffVM(String vmHref);
	
	abstract public Task suspendVM(String vmHref);
	
	abstract public Task resumeVM(String vmHref);
	
    abstract public VMInfo getVMInfo(String vmHref) throws DemoException;
    
    abstract public List<ReplicationGroup> getReplicationGroups(String orgHref) throws DemoException;
	
    abstract public Organization getOrganization(ReferenceType orgRef) throws DemoException;
    
    abstract public Vdc getVdc(ReferenceType vdcRef) throws DemoException;
    
}


// each client needs to have own representation of org, vapp, vm.
// client should get entity and marshall / return VMBean, OrgBean, VMBean