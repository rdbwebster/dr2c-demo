package demoapp.clients;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vmware.vcloud.api.rest.schema.ReferenceType;
import com.vmware.vcloud.api.rest.schema.ResourceType;
import com.vmware.vcloud.api.rest.schema.TaskType;
import com.vmware.vcloud.api.rest.schema.TasksInProgressType;
import com.vmware.vcloud.api.rest.schema.VdcType;
import com.vmware.vcloud.sdk.Organization;
import com.vmware.vcloud.sdk.Task;
import com.vmware.vcloud.sdk.VCloudException;
import com.vmware.vcloud.sdk.VM;
import com.vmware.vcloud.sdk.Vapp;
import com.vmware.vcloud.sdk.VcloudClient;
import com.vmware.vcloud.sdk.Vdc;
import com.vmware.vcloud.sdk.constants.Version;
import com.vmware.vcloud.sdk.exception.UnauthorizedAccessException;

import demoapp.VcdClient;
import demoapp.model.VMInfo;
import demoapp.DemoException;
import demoapp.model.ReplicationGroup;
import demoapp.FakeSSLSocketFactory;
import spark.ModelAndView;

/* SDKClient is a concrete REST client for the DemoApp that utilizes the vCD Java SDK 
 * XML Response parsing is managed by types provied in the SDK.
 * 
 */

public class SDKClient extends VcdClient {

	String url;
	
	VcloudClient client;
	
	public SDKClient(String vcloudUrl) {
		super(vcloudUrl);
		url = vcloudUrl;
	}
	
	final static Logger logger = LoggerFactory.getLogger(SDKClient.class);
	
	
	public void  login(String username, String password) throws DemoException {
          
          client = new VcloudClient(url, Version.V9_0);
          
          // Set SDK Client log level
          VcloudClient.setLogLevel(Level.INFO);
          
       
          try {
            client.registerScheme("https", 443, FakeSSLSocketFactory.getInstance());
        
            client.login(username,  password);
          } 
          
          catch (UnauthorizedAccessException ex)
          {
            logger.error("Exception during login  ", ex);
        	 throw new DemoException("Unauthorized", ex);
          }
          
		  catch (VCloudException ex)
	      {
			  logger.error("Exception during login  ", ex);
	        	 throw new DemoException( "Invalid Username or Password",ex);   // "Invalid Username or Password"
	      }
          
    	  catch (Exception ex)
	      {
    		  logger.error("Exception during login  ", ex);
	        	 throw new DemoException("Unknown Login Error", ex);
	      }
          

          
	}
	
	public void  logout() {
	
		   try {
			   client.logout();
		   }
			   catch (VCloudException ex) {
				   logger.error("Exception during logout. Message=" + ex.getMessage());
			   }
	}
	
	
	public String getVMStatus(String vmHref) throws DemoException {
		
		   ReferenceType vmRef = new ReferenceType();
		   vmRef.setHref(vmHref);
	 	 
		   try { 
			   String state;
		 	   VM vm = VM.getVMByReference(client, vmRef);
		 	   
			    if(vm.getTasks().size() != 0 ) {
		        	state ="UNRESOLVED";
		        }
		        else
		        	state = vm.getVMStatus().toString();
			    
				   
		    	return "{\"vmhref\": \"" + vmHref + "\"," +
		    	   	   " \"state\":  \"" +  state + "\"}";
		   }
		   catch (VCloudException ex) {
			   logger.error("Exception during getVMstate  ", ex);
			   throw new DemoException("Unable to get VM by href", ex);
		   }
		
	}
	
	
	public demoapp.model.Task getTaskStatus(String taskHref) throws DemoException {
		
		demoapp.model.Task info=null;
		
		try {
 	    		 
		   ReferenceType taskRef = new ReferenceType();
	 	   taskRef.setHref(taskHref);
		 		
  	       Task task = Task.getTaskByReference(client, taskRef);
  	       TaskType ttype = task.getResource();
  	      
  	        info = createDemoTask(task.getResource());
  	   
	  	    } catch (VCloudException ex) {
	  	    	logger.error("DemoTaskScheduler: Exception retrieving Task from vCD Server. "  + ex.getMessage());
	  	    	throw new DemoException(ex);
	  	    }    
		 
		    return info;
	}
	
	
	public VMInfo getVMInfo(String vmHref) throws DemoException {
		 
		   ReferenceType vmRef = new ReferenceType();
		   VMInfo vmBean = null;
           vmRef.setHref(vmHref);
           
           try {
	        	   VM vm = VM.getVMByReference(client, vmRef);
		           if(vm == null) {
		        	   logger.error("Unable to retrieve vm by vmRef using vmHref: " + vmHref);
		        	   throw new DemoException("Unable to retrieve VM using href " + vmHref);
		           } 	  
         	 
		           ReferenceType vAppref = vm.getParentVappReference();
		           
		           Vapp vapp = Vapp.getVappByReference(client, vAppref);
		           
		   		   vmBean = new VMInfo(vmRef.getName());
		   		  
				  
				   vmBean.setCpu(Integer.toString(vm.getCpu().getNoOfCpus()));
				   vmBean.setMemory(vm.getMemory().getMemorySize().toString());
		           vmBean.setOs(vm.getOperatingSystemSection().getDescription().getValue());	
		           vmBean.setStatus(vm.getVMStatus().toString());
		           vmBean.setHref(vm.getReference().getHref());
		           	                    
		           // not available without vApp 
		           vmBean.setOwner(vapp.getOwner().getName());
		           vmBean.setvApp(vAppref.getName());
		           vmBean.setRecoveryStatus("");
		           vmBean.setRecoveryTime("");
           }
           catch (VCloudException ex) {
        	   
        	   logger.error("Unable to retrieve VM using vmHref  ", ex);
        	   throw new DemoException("Unable to retrieve VM using vmHref " + vmHref, ex);
           }
    	   
           return vmBean;
		
	}
	
		
	public String getVcloudToken() throws DemoException {
		
		String token=null;
		try {
			
			 token = client.getVcloudToken();
		} catch (VCloudException ex) {
			logger.error("VCloudException retrieving cloud token ", ex );
			throw new DemoException(ex);
		}
		return token;
	}
	
    public String getOrgName() {  // the org named in the login
	      return client.getOrgName();
	}
	
    public	List<VMInfo> getVdcVMs(demoapp.model.Vdc vdc) throws DemoException {
    	
    	
		List <VMInfo> vmBeanList = new ArrayList<VMInfo>();
		
    //	ReferenceType vdcRef = new ReferenceType();
 	//	vdcRef.setHref(vdcHref);
    	
 	//	Vdc vdc = null;
 		
 		try {
 	//	    vdc = Vdc.getVdcByReference(client, vdcRef); 
 		
		
		//	VdcType vdcParams = vdc.getResource();
		//	logger.debug("vdc -- " + vdcParams.getName());
		
			
			// get vApp Refs from selected Vdc
 			List<demoapp.model.ReferenceType> vAppRefs = vdc.getVappRefs();
 			
		//	HashMap<String, ReferenceType> vAppReferencesByName = vdc.getVappRefsByName();
			
			if (!vAppRefs.isEmpty()) {
				for (demoapp.model.ReferenceType vAppRef : vAppRefs)  {
					logger.debug("vApp - " + vAppRef.getName());
					
					logger.debug("link - "+ vAppRef.getHref());
								
					// create a vcloud ReferenceType
					ReferenceType vcrt = new ReferenceType();
					vcrt.setHref(vAppRef.getHref());
					vcrt.setName(vAppRef.getName());
					vcrt.setType(vAppRef.getType());
					
					// get the vApp
					
					Vapp vapp = Vapp.getVappByReference(client, vcrt);
					logger.debug("Status is " + vapp.getVappStatus().value());
				
					// get vApp VMs
					
					List<VM> vmList = vapp.getChildrenVms();
					
					for (VM vm : vmList) {
						
					    	ReferenceType vmRef =  vm.getReference();

							VMInfo vmBean = new VMInfo(vmRef.getName());
							vmBean.setOwner(vapp.getOwner().getName());
							vmBean.setCpu(Integer.toString(vm.getCpu().getNoOfCpus()));
							vmBean.setMemory(vm.getMemory().getMemorySize().toString());
		                    vmBean.setOs(vm.getOperatingSystemSection().getDescription().getValue());	
		                    vmBean.setvApp(vAppRef.getName());
		                    // Check for running tasks against VM
		                    if(vm.getTasks().size() != 0 ) {
		                    	vmBean.setStatus("UNRESOLVED");
		                    }
		                    else
		                    	vmBean.setStatus(vm.getVMStatus().toString());
		                    
		                    logger.debug(vmRef.getName() + " STATUS "  + vm.getVMStatus().toString());
		                    
		                    vmBean.setHref(vm.getReference().getHref());
		                    	                    
		                    // not available
		                    vmBean.setRecoveryStatus("");
		                    vmBean.setRecoveryTime("");
	
							vmBeanList.add(vmBean);
							logger.debug("vm name: " +  vmBean.getName());
						
					}
					
				}
			} else
				logger.debug("			No vApp's Found");
 		}
 		catch (VCloudException ex) {
 			  logger.error("Unable to retrieve vdc vms  ", ex);
 			throw new DemoException(ex);
 		}
		return vmBeanList;
	}
		
    
/*
	
    public	List<demoapp.model.ReferenceType> getOrgVdcList(String orgHref) throws DemoException {   // list of vdc names in the org
	
 	 
 	   ArrayList<demoapp.model.ReferenceType> vdcList = new ArrayList<>();    // vdcRef 
		
 	   try {
 		   
 		 ReferenceType orgRef = new ReferenceType();
 		 orgRef.setHref(orgHref);
 		 Organization organization = Organization.getOrganizationByReference(client, orgRef);
 		    Collection<ReferenceType> vdcLinks = organization.getVdcRefs();
 			if (!vdcLinks.isEmpty()) {
 				for (ReferenceType vdcLink : vdcLinks) {	
 					demoapp.model.ReferenceType vdcRef = new demoapp.model.ReferenceType(vdcLink.getName(), vdcLink.getHref(), vdcLink.getType());
 					vdcList.add(vdcRef);
 				}
 			}
 	   } catch (VCloudException ex) {
 		   throw new DemoException(ex);
 	   }
 	   
 	   return vdcList;
	}
    */
	
    public	ArrayList<demoapp.model.ReferenceType> getOrgRefList() throws DemoException {
		
		HashMap<String, ReferenceType> organizationsMap = null;
		ArrayList<demoapp.model.ReferenceType> orgRefList = new ArrayList<demoapp.model.ReferenceType>();
		
		try {
	      	organizationsMap = client.getOrgRefsByName();
		}
		catch (VCloudException ex) {
			logger.error("Unable to obtain Org List");
			throw new DemoException(ex);
		}
			
	    if (!organizationsMap.isEmpty()) {
	    	for (String organizationName : organizationsMap.keySet()) {
	    	   demoapp.model.ReferenceType orgRef = new demoapp.model.ReferenceType(organizationName, 
	    			                                                             organizationsMap.get(organizationName).getHref(),
	    			                                                             organizationsMap.get(organizationName).getType());
	    	   orgRefList.add(orgRef);
	    	}
	    }
        	
	    return orgRefList;
		
	}
    

    public	demoapp.model.Organization getOrganization(demoapp.model.ReferenceType orgHref) throws DemoException {
		
    	
    	ReferenceType ort = new ReferenceType();
    	ort.setHref(orgHref.getHref());
    	
    	demoapp.model.Organization demoOrg = new demoapp.model.Organization();
    	
    	try { 
    	
	    	Organization org = Organization.getOrganizationByReference(client, ort);
	    	
	    	
	    	demoOrg.setHref(org.getReference().getHref());
	    	demoOrg.setName(org.getReference().getName());
	    	
	    	//TODO Test vCD instances today do not return a downlink for replication groups
	    	demoOrg.setReplHref("");   
	    	
	    	for( com.vmware.vcloud.api.rest.schema.ReferenceType vrt : org.getVdcRefs()) {
	    		demoapp.model.ReferenceType rt = new demoapp.model.ReferenceType();
	    		rt.setHref(vrt.getHref());
	    		rt.setName(vrt.getName());
	    		rt.setType(vrt.getType());
	    		demoOrg.getVdcRefs().add(rt);
	    	}
	    	
	    	
    	} catch (Exception ex) {
    		logger.error("Exception retrieving organization ", ex);
    		throw new DemoException("Exception retrieving organization ", ex);
    	}

	    return demoOrg;
		
	}
    

    public	demoapp.model.Vdc getVdc(demoapp.model.ReferenceType vdcHref) throws DemoException {
		
    	
    	ReferenceType rt = new ReferenceType();
    	rt.setHref(vdcHref.getHref());
    	
    	demoapp.model.Vdc demoVdc = new demoapp.model.Vdc();
    	
    	try { 
    	
    		com.vmware.vcloud.sdk.Vdc vdc = Vdc.getVdcByReference(client, rt);
	    	
	    	
    		demoVdc.setHref(vdc.getReference().getHref());
    		demoVdc.setName(vdc.getReference().getName());
    		demoVdc.setDescription(vdc.getResource().getDescription());
    		
    		// vapp Refs
    		for(ReferenceType ref :vdc.getVappRefs()) {
    			demoapp.model.ReferenceType  mref = new demoapp.model.ReferenceType();
    			mref.setName(ref.getName());
    			mref.setType(ref.getType());
    			mref.setHref(ref.getHref());
    			demoVdc.getVappRefs().add(mref);
    			
    		}
    		
    		// set org link
    		ReferenceType ref = vdc.getOrgReference();
    		demoapp.model.ReferenceType  oref = new demoapp.model.ReferenceType();
    		oref.setName(ref.getName());
    		oref.setType(ref.getType());
    		oref.setHref(ref.getHref());
			demoVdc.setOrgRef(oref);
    		
	    	 
    	} catch (Exception ex) {
    		logger.error("Exception retrieving Vdc ", ex);
    		throw new DemoException(ex);
    	}

	    return demoVdc;
		
	}
    
     private demoapp.model.Task createDemoTask(TaskType ttype) {
    	 
    	 demoapp.model.Task demoTask = new demoapp.model.Task();
    	 demoTask.setName(ttype.getName());
    	 demoTask.setHref(ttype.getHref());
    	 demoTask.setStatus(ttype.getStatus());
    	 demoTask.setStartTime(ttype.getStartTime().toString());
    	 demoTask.setExpiryTime(ttype.getExpiryTime().toString());
    	 demoTask.setOperation(ttype.getOperation());
    	 demoTask.setOperationName(ttype.getOperation());
    	 demoTask.setOwnerHref(ttype.getOwner().getHref());
    	 demoTask.setOwnerName(ttype.getOwner().getName());
    	 demoTask.setUserHref(ttype.getUser().getHref());
    	 demoTask.setUserName(ttype.getUser().getName());
    	 demoTask.setOrgHref(ttype.getOrganization().getHref());
    	 demoTask.setOrgName(ttype.getOrganization().getName());

		 return demoTask;
     }
     

 


	 public demoapp.model.Task powerOnVM(String vmHref) {
		 
		 demoapp.model.Task demoTask = new demoapp.model.Task();
		 
		 try {
			 VM vm = getVMbyHRef(vmHref);
			 com.vmware.vcloud.sdk.Task task = vm.powerOn();
			 
			 demoTask = createDemoTask(task.getResource());
			  
		 } catch (VCloudException ex) {
			logger.error("PoweronVm Failed: ", ex);
			demoTask.setErrorDescription("PowerOnVm Failed " + ex.getMessage());
		 }
		 return demoTask;
	
	 }
	
	 public demoapp.model.Task powerOffVM(String vmHref)  {
		 
          demoapp.model.Task demoTask = new demoapp.model.Task();
		 
		 try {
			 VM vm = getVMbyHRef(vmHref);
			 com.vmware.vcloud.sdk.Task task = vm.powerOff();
			 
			 demoTask = createDemoTask(task.getResource());
			  
		 } catch (VCloudException ex) {
			logger.error("PoweroffVm Failed: ", ex);
			demoTask.setErrorDescription("PowerOffVm Failed " + ex.getMessage());
		 }
		 return demoTask;
		 
	 }
	
	 public demoapp.model.Task suspendVM(String vmHref)  {
		 
        demoapp.model.Task demoTask = new demoapp.model.Task();
		 
		 try {
			 VM vm = getVMbyHRef(vmHref);
			 com.vmware.vcloud.sdk.Task task = vm.suspend();
			 
			 demoTask = createDemoTask(task.getResource());
			  
		 } catch (VCloudException ex) {
			logger.error("SuspendVm Failed: ", ex);
			demoTask.setErrorDescription("Suspend VM Failed " + ex.getMessage());
		 }
		 return demoTask;
		 
	 }
	
	 public demoapp.model.Task resumeVM(String vmHref)  {
		 
         demoapp.model.Task demoTask = new demoapp.model.Task();
		 
		 try {
			 VM vm = getVMbyHRef(vmHref);
			 com.vmware.vcloud.sdk.Task task = vm.powerOn();
			 
			 demoTask = createDemoTask(task.getResource());
			  
		 } catch (VCloudException ex) {
			logger.error("Resume Vm Failed: ", ex);
			demoTask.setErrorDescription("Resume VM Failed " + ex.getMessage());
		 }
		 return demoTask;
		 
	 }
	 
     public VM getVMbyHRef(String vmHref) throws VCloudException  {
    	 
    	 ReferenceType ref = new ReferenceType();
    	 ref.setHref(vmHref);
    	 
    	 return VM.getVMByReference( client, ref);
    	 
     }
    
    /**
     * Finding the vm using the org name and the vapp name
     *
     * @param orgName
     * @param vAppName
     * @param vmName
     * @return
     * @throws VCloudException
     */
    private static VM findVM(VcloudClient client, String orgName, String vAppName, String vmName) throws VCloudException {
        Organization org =
                Organization.getOrganizationByReference(client, client.getOrgRefsByName().get(
                        orgName));
        for (ReferenceType vdcRef : org.getVdcRefs()) {
            Vdc vdc = Vdc.getVdcByReference(client, vdcRef);
            if (vdc.getVappRefsByName().containsKey(vAppName)) {
                Vapp vapp = Vapp.getVappByReference(client, vdc.getVappRefByName(vAppName));
                for (VM vm : vapp.getChildrenVms()) {
                    if (vm.getResource().getName().equals(vmName)) {
                    	
                        return vm;
                    }
                }
            }
        }
      
        System.exit(0);
        return null;
    }
    
    /* test replication, not supported in vCD Java API 
     * 
     */
    public demoapp.model.Task  testReplication(String s) {
    	logger.warn("testReplication not implemented by vCD Java API");
    	return null;
    }
    
    /* cleanup test replication, not supported in vCD Java API 
     * 
     */
    public demoapp.model.Task  cleanupTestReplication(String s) {
    	logger.warn("testReplication not implemented by vCD Java API");
    	return null;
    }
    
    /* test replication, not supported in vCD Java API 
     * 
     */
    public demoapp.model.Task  removeReplication(String s) {
    	logger.warn("removeReplication not implemented by vCD Java API");
    	return null;
    }

    
    /**
  	 * Check for tasks if any
  	 * 
  	 * @param vm
  	 * @return {@link Task}
  	 * @throws VCloudException
  	 
  	public static Task returnTask(VcloudClient client, VM vm) throws VCloudException {
  		TasksInProgressType tasksInProgress = vm.getResource().getTasks();
  		if (tasksInProgress != null)
  			for (TaskType task : tasksInProgress.getTask()) {
  				return new Task(client, task);
  			}
  		return null;
  	}
  	*/
  	
    /*
  	
  	public TaskInfo checkForVmTask(String vmHref) throws DemoException {
  		ReferenceType vmRef = new ReferenceType();
  		vmRef.setHref(vmHref);
  		try {
  			VM vm = VM.getVMByReference(client, vmRef);
  			
  			TasksInProgressType tasksInProgress = vm.getResource().getTasks();
  	  		if (tasksInProgress != null)
  	  			for (TaskType tasktype : tasksInProgress.getTask()) {
  	  				TaskInfo tinfo = createTaskInfo(tasktype);
  	  				return tinfo;
  	  			}
  	  		return null;
  			
  		} catch (VCloudException ex) {
  			throw new DemoException("Could not retrieve VM using href ", ex);
  		}
  		
  	}
  	*/
        /*
public static List<demoapp.model.VDC> getVdcList(VcloudClient client, ReferenceType orgRef) throws VCloudException {
	
	List<demoapp.model.VDC> vdcList = new ArrayList<>();    // vdcRef 

     Organization organization = Organization.getOrganizationByReference(client, orgRef);
	    Collection<ReferenceType> vdcLinks = organization.getVdcRefs();
		if (!vdcLinks.isEmpty()) {
			for (ReferenceType vdcLink : vdcLinks) {
				demoapp.model.VDC demoVdc = new demoapp.model.VDC(vdcLink.getName(), vdcLink.getHref());
				vdcList.add(demoVdc);
			}
		}
			
	   // debug		
	   for(demoapp.model.VDC vdcRef: vdcList ) {
	     	logger.debug("Vdc: " + vdcRef.getName());
	   }
	
	
	   return vdcList;
}


public static List<ReferenceType> getOrgRefList(VcloudClient client) throws VCloudException {
	
    // get Orgs
    
	    List<ReferenceType> orgList = new ArrayList<>();    // orgRef 

	    // TODO  is this needed,  I can user organizationsMap
	    
	    HashMap<String, ReferenceType> organizationsMap = client.getOrgRefsByName();
        logger.debug("Found " + organizationsMap.size() + " Organizations");
        
        if (!organizationsMap.isEmpty()) {
        	
			for (String organizationName : organizationsMap.keySet()) {
				orgList.add(organizationsMap.get(organizationName));    // add ReferenceType
						
            }
        }
		
		// debug		
		for(ReferenceType orgRef: orgList ) {
			logger.debug("Org: " + orgRef.getName());
			logger.debug("Org Id: " + orgRef.getId());
			
		}

        return orgList;
}

*/
    
public List<ReplicationGroup> getReplicationGroups(String vmHref) throws DemoException {
	
	return new ArrayList<ReplicationGroup>();
}

/*
    
public List<VMBean> getVdcDetails(String vdcHref)  throws VCloudException {


	    ReferenceType ref = new ReferenceType();
	    ref.setHref(vdcHref);
		Vdc vdc = Vdc.getVdcByReference(client, ref);
		
		VdcType vdcParams = vdc.getResource();
		logger.debug("vdc -- " + vdcParams.getName());
	
		List <VMBean> vmBeanList = new ArrayList<VMBean>();
		
		// get vApps
		HashMap<String, ReferenceType> vAppReferencesByName = vdc
				.getVappRefsByName();
		if (!vAppReferencesByName.isEmpty()) {
			for (String vAppName : vAppReferencesByName
					.keySet()) {
				logger.debug("vApp -" + vAppName);
				
				logger.debug("link -		"
						+ vAppReferencesByName.get(vAppName)
								.getHref());
				
				Vapp vapp = Vapp.getVappByReference(client, vAppReferencesByName.get(vAppName));
				logger.debug("Status is " + vapp.getVappStatus().value());
			
				// get vApp VMs
				
				List<VM> vmList = vapp.getChildrenVms();
				
				for (VM vm : vmList) {
						ReferenceType vmRef =  vm.getReference();
						
						VMBean vmBean = new VMBean(vmRef.getName());
						vmBean.setOwner(vapp.getOwner().getName());
						vmBean.setCpu(Integer.toString(vm.getCpu().getNoOfCpus()));
						vmBean.setMemory(vm.getMemory().getMemorySize().toString());
	                    vmBean.setOs(vm.getOperatingSystemSection().getDescription().getValue());	
	                    vmBean.setvApp(vAppName);
	                    vmBean.setStatus(vm.getVMStatus().toString());
	                    logger.debug(vmRef.getName() + " STATUS "  + vm.getVMStatus().toString());
	                    
	                    vmBean.setHref(vm.getReference().getHref()); 
	                    	                    
	                    // not available
	                    vmBean.setRecoveryStatus("");
	                    vmBean.setRecoveryTime("");

						vmBeanList.add(vmBean);
						logger.debug("vm name: " +  vmBean.getName());
					
				}
				
			}
		} else
			logger.debug("			No vApp's Found");
		return vmBeanList;
	}
	*/

}
