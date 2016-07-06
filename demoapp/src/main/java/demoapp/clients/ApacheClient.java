package demoapp.clients;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.joox.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vmware.vcloud.sdk.VcloudClient;

import demoapp.App;
import demoapp.VcdClient;
import demoapp.model.Organization;
import demoapp.model.ReferenceType;
import demoapp.model.ReplicationGroup;
import demoapp.model.Task;
import demoapp.model.Vdc;
import demoapp.model.VMInfo;
import demoapp.model.Vapp;
import demoapp.DemoException;
import spark.ModelAndView;
import spark.Request;

/* ApacheClient is a concrete REST client for the DemoApp that utilizes the vCD Apache Http Client and 
 * JOOX for xml processing
 * 
 */
public class ApacheClient extends VcdClient{
	
	
	
	public ApacheClient(String vcloudUrl) {
		super(vcloudUrl);
	
		url = vcloudUrl;
	}


	String sessionId = null; 
	String url = null;
	HttpClient httpClient = null;
	String orgName = null;
	
    final static Logger logger = LoggerFactory.getLogger(ApacheClient.class);
	
    
 
	public void  login(String username, String password) throws DemoException {
			
		try {
			    httpClient = createHttpClient_AcceptsUntrustedCerts(true, username, password);
	       
			    logger.debug("Login url " + url + "/api/sessions");
	            HttpPost httpPost = new HttpPost(url + "/api/sessions");
	            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	            nvps.add(new BasicNameValuePair("username", username));
	            nvps.add(new BasicNameValuePair("password", password));
	        
	            
				// Add additional header to getRequest which accepts application/xml data
	            httpPost.addHeader("accept", "application/*+xml;version=9.0");
	 
				// Execute your request and catch response
	        
			    httpPost.setEntity(new UrlEncodedFormEntity(nvps));
	            HttpResponse response2 = httpClient.execute(httpPost);
	            
			
				// Check for HTTP response code: 200 = success
	            logger.debug("status code is " + response2.getStatusLine().getStatusCode());
	            if (response2.getStatusLine().getStatusCode() == 403) {
	            	 
	            	logger.error("Http call error: " + response2.getStatusLine().getStatusCode() );
					throw new DemoException("Unauthorized: Invalid Username / Password " + response2.getStatusLine().getStatusCode());
	            }
	            
				if (response2.getStatusLine().getStatusCode() != 200) {
					logger.error("Http call error: " + response2.getStatusLine().getStatusCode() );
					throw new DemoException("Failed : HTTP error code : " + response2.getStatusLine().getStatusCode());
			
				}
				
				sessionId  = response2.getFirstHeader("x-vcloud-authorization").getValue();
			    orgName = username.substring(username.indexOf('@') + 1);
	 
				// Get-Capture Complete application/xml body response
				BufferedReader br = new BufferedReader(new InputStreamReader((response2.getEntity().getContent())));
				String output;
				logger.debug("============Output:============");
				logger.debug("x-vcloud-authorization: " + String.valueOf(sessionId));
				// Simply iterate through XML response and show on console.
				while ((output = br.readLine()) != null) {
					logger.debug(output);
				}
	 
		} catch (Exception e) {
			logger.error("Apache http call exception " , e);
			throw new DemoException("Apache http call exception", e);
		}
			  
		}
		

	
	public void  logout() {
		
	    // Get the list of vdc vapp refs
			try {

				// Create new getRequest with below mentioned URL
				HttpDelete delRequest = new HttpDelete (url);
	 
				// Add additional header to getRequest which accepts application/xml data
				delRequest.addHeader("accept", "application/*+xml;version=9.0");
	 
				// Add vcd cloud session id
				delRequest.addHeader("x-vcloud-authorization", sessionId);
			     
				logger.debug("Calling logout " + url);
				// Execute your request and catch response
				HttpResponse response = httpClient.execute(delRequest);
	 
				// Check for HTTP response code: 200 = success
				if (response.getStatusLine().getStatusCode() != 200) {
					logger.error("Http call error: " + response.getStatusLine().getStatusCode() );
					throw new DemoException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
				}
				
		 
			} catch (Exception e) {
				logger.error("Apache http call exception ", e); // stacktrace
				
			}
			
		
	}
	
	public String getVMStatus(String vmHref) throws DemoException {
		
		if(vmHref == null || vmHref.length() == 0) {
    		logger.error("orgHref passed to getVMStatus is null");
    		throw new DemoException("vmHref passed to getVMStatus is null.");
    	}
		
		 String status="";
		 VMInfo vm = getVMInfo( vmHref);
		 
		   if(vm.getTaskLinkRef() != null ) {
			   status ="UNRESOLVED";
	        }
	        else
	        	status = vm.getStatus().toString();
		 
		 return  status;
	}
	
	public Task getTaskStatus(String taskHref) throws DemoException {
		
		if(taskHref == null || taskHref.length() == 0) {
    		logger.error("taskHref passed to getTaskStatus is null");
    		throw new DemoException("taskHref passed to getTaskStatus is null.");
    	}
		
		Task task = new Task();
	  
			try {

				// Create new getRequest with below mentioned URL
				HttpGet getRequest = new HttpGet(taskHref);
				
	 
				// Add additional header to getRequest which accepts application/xml data
				getRequest.addHeader("accept", "application/*+xml;version=9.0");
	 
				// Add vcd cloud session id
				getRequest.addHeader("x-vcloud-authorization", sessionId);
			     
				logger.debug("Calling getVapp " + taskHref);
				// Execute your request and catch response
				HttpResponse response = httpClient.execute(getRequest);
	 
				// Check for HTTP response code: 200 = success
				if (response.getStatusLine().getStatusCode() != 200) {
					logger.error("Http call error: " + response.getStatusLine().getStatusCode() );
					throw new DemoException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
				}
				
				task = ApacheClientParser.parseTask(response.getEntity().getContent());
			    
	 
			} catch (Exception e) {
				logger.error("Apache http call exception ", e); // stacktrace
				throw new DemoException("Apache http call exception", e);
			}
				
		
		return  task;
	}


	
	public String getVcloudToken() throws DemoException {
		return sessionId;
	}
	
	public String getOrgName() {
		return orgName;
		
	}
	
	
	public Vapp getVapp(String vappHref) throws DemoException {
		
		if(vappHref == null || vappHref.length() == 0) {
    		logger.error("vappHref passed to getVapp is null");
    		throw new DemoException("vappHref passed to getVapp is null.");
    	}
		
		Vapp vapp = new Vapp();
		
	    // Get the list of vdc vapp refs
		try {

			// Create new getRequest with below mentioned URL
			HttpGet getRequest = new HttpGet(vappHref);
			
 
			// Add additional header to getRequest which accepts application/xml data
			getRequest.addHeader("accept", "application/*+xml;version=9.0");
 
			// Add vcd cloud session id
			getRequest.addHeader("x-vcloud-authorization", sessionId);
		     
			logger.debug("Calling getVapp " + vappHref);
			// Execute your request and catch response
			HttpResponse response = httpClient.execute(getRequest);
 
			// Check for HTTP response code: 200 = success
			if (response.getStatusLine().getStatusCode() != 200) {
				logger.error("Http call error: " + response.getStatusLine().getStatusCode() );
				throw new DemoException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
			
			vapp = ApacheClientParser.parseVapp(response.getEntity().getContent());
		    
 
		} catch (Exception e) {
			logger.error("Apache http call exception ", e); // stacktrace
			throw new DemoException("Apache http call exception", e);
		}
		
		
	    return vapp;
	
	
	}
	
	
	/*
	public ArrayList<ReferenceType> getVappVmRefs(Vapp vapp) throws DemoException {
		

	    ArrayList<ReferenceType> vmrefs = new ArrayList<ReferenceType>();
			
		
				try {
					
					
			//	    HttpClient httpClient = createHttpClient_AcceptsUntrustedCerts(true);
				       
		
					// Create new getRequest with below mentioned URL
					HttpGet getRequest = new HttpGet(vapp.getHref());  // vApp href
		 
					// Add additional header to getRequest which accepts application/xml data
					getRequest.addHeader("accept", "application/*+xml;version=9.0");
		 
					// Add vcd cloud session id
					getRequest.addHeader("x-vcloud-authorization", sessionId);
				     
					logger.debug("Calling getVappVMs " + vapp.getHref());
					// Execute your request and catch response
					HttpResponse response = httpClient.execute(getRequest);
		 
					// Check for HTTP response code: 200 = success
					if (response.getStatusLine().getStatusCode() != 200) {
						logger.error("Http call error: " + response.getStatusLine().getStatusCode() );
						throw new DemoException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
					}
		 
				
					vmrefs = ApacheClientParser.parseVappVmRefs(response.getEntity().getContent());
					
				} catch (Exception e) {
					logger.error("Apache http call exception " + e.getMessage());
					throw new DemoException("Apache http call exception", e);
				}
				
	    return vmrefs;
	
	}
	
	*/

		public List<VMInfo> getVdcVMs(Vdc vdc) throws DemoException {
			
			 // get list of VDC vApp refs
			//ArrayList<Vapp> vapps =  getVdcVapps(vdcHref);
			List<ReferenceType> vappRefs = vdc.getVappRefs();

		   
			ArrayList<VMInfo> masterVmList = new ArrayList<VMInfo>();
			
			 // get each vApp 
			for(ReferenceType vappRef :  vappRefs) {
				
				
			    Vapp vapp = this.getVapp(vappRef.getHref());
			    
			    // Get refs to the vapps vms
			//	ArrayList<ReferenceType> vmRefList = getVappVmRefs(vapp);
				List<ReferenceType> vmRefList = vapp.getVms();
			
					
					// Get Full VMInfo for each VM  
					for(ReferenceType avm : vmRefList) {
								
							try {
								
								VMInfo newVmInfo = getVMInfo(avm.getHref());
								
								// Add the extra Vapp info to the VM child
							    newVmInfo.setvApp(String.valueOf(vapp.getName()));
							    newVmInfo.setOwner(String.valueOf(vapp.getOwner()));
							    
							    // add to to the vdc vm list
							    masterVmList.add(newVmInfo);
		
		
							} catch (Exception e) {
								logger.error("Apache http call exception ", e); // stacktrace
								throw new DemoException("Apache http call exception", e);
							}
					}
			
			
			}
			
		    return masterVmList;
		
		}
	
	/*
	public List<ReferenceType> getOrgVdcList(String orgHref)  throws DemoException{    // list of vdc names in the org
		

	    ArrayList<ReferenceType> vdcRefList = new ArrayList<ReferenceType>();
		
		try {
			
			
	//	    HttpClient httpClient = createHttpClient_AcceptsUntrustedCerts(true);
		       

			// Create new getRequest with below mentioned URL
			HttpGet getRequest = new HttpGet(orgHref);
 
			// Add additional header to getRequest which accepts application/xml data
			getRequest.addHeader("accept", "application/*+xml;version=9.0");
 
			// Add vcd cloud session id
			getRequest.addHeader("x-vcloud-authorization", sessionId);
		     
			logger.debug("Calling getOrgVdcList " + orgHref);
			// Execute your request and catch response
			HttpResponse response = httpClient.execute(getRequest);
 
			// Check for HTTP response code: 200 = success
			if (response.getStatusLine().getStatusCode() != 200) {
				logger.error("Http call error: " + response.getStatusLine().getStatusCode() );
				throw new DemoException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
 
		//	vdcRefList = ApacheClientParser.parseOrgVdcs(response.getEntity().getContent());
			
		
			
			
		} catch (Exception e) {
			logger.error("Apache http call exception " + e.getMessage());
			throw new DemoException("Apache http call exception", e);
		}
		
	    return vdcList;
	}
	
	*/
	
	public List<ReferenceType> getOrgRefList() throws DemoException {
		

	    ArrayList<ReferenceType> orgList = new ArrayList<ReferenceType>();
		
		try {
			
			
	//	    HttpClient httpClient = createHttpClient_AcceptsUntrustedCerts(true);
		       

			// Create new getRequest with below mentioned URL
			HttpGet getRequest = new HttpGet(this.url + "/api/org/");
 
			// Add additional header to getRequest which accepts application/xml data
			getRequest.addHeader("accept", "application/*+xml;version=9.0");
 
			// Add vcd cloud session id
			getRequest.addHeader("x-vcloud-authorization", sessionId);
		     
			logger.debug("Calling getOrgList " + this.url + "/api/org/");
			// Execute your request and catch response
			HttpResponse response = httpClient.execute(getRequest);
 
			// Check for HTTP response code: 200 = success
			if (response.getStatusLine().getStatusCode() != 200) {
				logger.error("Http call error: " + response.getStatusLine().getStatusCode() );
				throw new DemoException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
 
		
			orgList = ApacheClientParser.parseOrgList(response.getEntity().getContent());
		     
			 
		} catch (Exception e) {
			logger.error("Apache http call exception ", e); // stacktrace
			throw new DemoException("Apache http call exception", e);
		}
		
	    return orgList;
	
	}
	
	
	 public Task powerOnVM(String vmHref)  {
		 
		 Task task = new Task();
		 
		
		 if(vmHref == null || vmHref.length() == 0) {
	    		logger.error("vmHref passed to powerOnVM is null");
				 task.setErrorDescription("Error: vmHref passed to powerOnVM is null " + vmHref);
	     }
			
		 
		 try {
			 VMInfo vm = getVMInfo( vmHref);
			 
			 // Call the poweron link if present
			 if(vm.getPowerOnHref() != null) {
				 // TODO
				 task = performPostAction(vm.getPowerOnHref(), null);
				 
			 } else
				 logger.error("VM " + vm.getName() + " in incorrect state for powerOn action.");
		          task.setErrorDescription("VM " + vm.getName() + " in incorrect state for powerOn action.");
		 }
		 
		 catch (DemoException ex) {
			 logger.error("Exception trying to retrieve VM using vmhref " + vmHref);
			 task.setErrorDescription("Exception trying to retrieve VM using vmhref " + vmHref);
		 }
			 
		 return task;
	 }
	
	 public Task powerOffVM(String vmHref)  {

		 
		 Task task = new Task();
		 
	
		 if(vmHref == null || vmHref.length() == 0) {
	    		logger.error("vmHref passed to powerOffVM is null");
				 task.setErrorDescription("Error: vmHref passed to powerOffVM is null " + vmHref);
	     }
		 
		 try {
		 
		 VMInfo vm = getVMInfo( vmHref);
		 
		 // Call the poweroff link if present
		 if(vm.getPowerOffhref() != null) {
			 // TODO
			 task = performPostAction(vm.getPowerOffhref(), null);
			 
		 } else
			 logger.error("VM " + vm.getName() + " in incorrect state for powerOff action.");
	         task.setErrorDescription("VM " + vm.getName() + " in incorrect state for poweOff action.");
		 }
		 
		 catch (DemoException ex) {
			 logger.error("Exception trying to retrieve VM using vmhref " + vmHref);
			 task.setErrorDescription("Exception trying to retrieve VM using vmhref " + vmHref);
		 }
		 
		
		 return task;
	 }
	
	 public Task suspendVM(String vmHref)  {
		 

		 Task task = new Task();
		 
		
		 if(vmHref == null || vmHref.length() == 0) {
	    		logger.error("vmHref passed to suspendVM is null");
				 task.setErrorDescription("Error: vmHref passed to suspendVM is null " + vmHref);
	     }
		 
		 try {
		 
		 VMInfo vm = getVMInfo( vmHref);
		 
		 // Call the poweron link if present
		 if(vm.getSuspendHref() != null) {
			 // TODO
			 performPostAction(vm.getSuspendHref(), null);
			 
		 } else
			 logger.error("VM " + vm.getName() + " in incorrect state for suspend action.");
		     task.setErrorDescription("VM " + vm.getName() + " in incorrect state for suspend action.");
        }
		 
		 catch (DemoException ex) {
			 logger.error("Exception trying to retrieve VM using vmhref " + vmHref);
			 task.setErrorDescription("Exception trying to retrieve VM using vmhref " + vmHref);
		 }
		 
		 return task;
	 }
	
	 public Task resumeVM(String vmHref) {
   
		 Task task = new Task();
		 
		 if(vmHref == null || vmHref.length() == 0) {
	    		logger.error("vmHref passed to resumeVM is null");
				 task.setErrorDescription("Error: vmHref passed to resumeVM is null " + vmHref);
	     }
		 
		 try { 
		 
		 VMInfo vm = getVMInfo( vmHref);
		 
		 // Call the poweron link if present
		 if(vm.getPowerOnHref() != null) {
			 // TODO
			 task = performPostAction(vm.getPowerOnHref(), null);
			 
		 } else
			 logger.error("VM " + vm.getName() + " in incorrect state for resume action.");
		      task.setErrorDescription("VM " + vm.getName() + " in incorrect state for resume action.");
			 }
			 
			 catch (DemoException ex) {
				 logger.error("Exception trying to retrieve VM using vmhref " + vmHref);
				 task.setErrorDescription("Exception trying to retrieve VM using vmhref " + vmHref);
			 }
		 
		 return task;
	 }
	 

	 public Task testReplication(String href)  {

		 
		 Task task = new Task();
		 
	
		 if(href == null || href.length() == 0) {
	    		logger.error("href passed to testReplication is null");
				 task.setErrorDescription("Error: href passed to testReplication is null " + href);
	     }
		 
		 try {
		 
		  ReplicationGroup rg = getReplicationGroup( href);
		 
			
		  List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		 
		  String testParm = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> " + 
				            "<ns2:TestFailoverParams xmlns=\"http://www.vmware.com/vcloud/v1.5\" " +
		 		            "xmlns:ns2=\"http://www.vmware.com/vr/v6.0\" xmlns:ns3=\"http://schemas.dmtf.org/ovf/envelope/1\" " +
				            "xmlns:ns4=\"http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_VirtualSystemSettingData\" " +
		 		            "xmlns:ns5=\"http://schemas.dmtf.org/wbem/wscim/1/common\" " +
				            "xmlns:ns6=\"http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_ResourceAllocationSettingData\" " +
		 		            "xmlns:ns7=\"http://schemas.dmtf.org/ovf/environment/1\"> " +
				            "<ns2:PowerOn>true</ns2:PowerOn> " +
                            "<ns2:Synchronize>false</ns2:Synchronize> " +
                            "</ns2:TestFailoverParams>";
		  System.out.println("testParm = " + testParm);
		 
		 // Call the test link if present
		 if(rg.getTestFailoverOpRef() != null && rg.getTestFailoverOpRef().getHref()!= null) {
			 // TODO
			 task = performPostAction(rg.getTestFailoverOpRef().getHref(), testParm);
			 logger.debug("Post call to test failover");
			 
		 } else
			 logger.error("ReplicationGroup " + rg.getName() + " in incorrect state for test action.");
	         task.setErrorDescription("ReplicationGroup " + rg.getName() + " in incorrect state for test action.");
		 }
		 	
		 catch (DemoException dex) {
			 logger.error("Exception trying to retrieve ReplicationGroup using href " + href);
			 task.setErrorDescription("Exception trying to retrieve ReplicationGroup using vmhref " + href);
		 }
		
		 return task;
	 }
	 


	 public Task cleanupTestReplication(String href)  {

		 Task task = new Task();
	
		 if(href == null || href.length() == 0) {
	    		logger.error("href passed to cleanupTestReplication is null");
				 task.setErrorDescription("Error: href passed to cleanupTestReplication is null " + href);
	     }
		 
		 try {
		 
		  ReplicationGroup rg = getReplicationGroup( href);
			
		  List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		 
		  String testParm = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> " + 
				            "<ns2:TestFailoverParams xmlns=\"http://www.vmware.com/vcloud/v1.5\" " +
		 		            "xmlns:ns2=\"http://www.vmware.com/vr/v6.0\" xmlns:ns3=\"http://schemas.dmtf.org/ovf/envelope/1\" " +
				            "xmlns:ns4=\"http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_VirtualSystemSettingData\" " +
		 		            "xmlns:ns5=\"http://schemas.dmtf.org/wbem/wscim/1/common\" " +
				            "xmlns:ns6=\"http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_ResourceAllocationSettingData\" " +
		 		            "xmlns:ns7=\"http://schemas.dmtf.org/ovf/environment/1\"> " +
				            "<ns2:PowerOn>true</ns2:PowerOn> " +
                            "<ns2:Synchronize>false</ns2:Synchronize> " +
                            "</ns2:TestFailoverParams>";
		//  System.out.println("testParm = " + testParm);
		 
		 // Check if the test cleanup link is present
		 if(rg.getTestCleanupFailoverOpRef() != null && rg.getTestCleanupFailoverOpRef().getHref()!= null) {	 
			 
			 task = performPostAction(rg.getTestCleanupFailoverOpRef().getHref(), testParm);
			 logger.debug("Post call to cleanup test failover");
			 
		 } else
			 logger.error("ReplicationGroup " + rg.getName() + " in incorrect state for cleanup test action.");
	         task.setErrorDescription("ReplicationGroup " + rg.getName() + " in incorrect state for cleanup test action.");
		 }
		 	
		 catch (DemoException dex) {
			 logger.error("Exception trying to retrieve ReplicationGroup using href " + href);
			 task.setErrorDescription("Exception trying to retrieve ReplicationGroup using vmhref " + href);
		 }
		
		 return task;
	 }
	 
	 

	 public Task removeReplication(String href)  {

		 
		 Task task = new Task();
		 
	
		 if(href == null || href.length() == 0) {
	    		logger.error("href passed to removeReplication is null");
				 task.setErrorDescription("Error: href passed to removeReplication is null " + href);
	     }
		 
		 try {
		 
		 ReplicationGroup rg = getReplicationGroup( href);
		 
		 // Call the test link if present
		 if(rg.getRemoveOpRef().getHref()!= null) {
			 // TODO
			 task = performDeleteAction(rg.getRemoveOpRef().getHref());
			 
		 } else
			 logger.error("ReplicationGroup " + rg.getName() + " in incorrect state for remove action.");
	         task.setErrorDescription("ReplicationGroup " + rg.getName() + " in incorrect state for remove action.");
		 }
		 	
		 catch (DemoException dex) {
			 logger.error("Exception trying to retrieve ReplicationGroup using href " + href);
			 task.setErrorDescription("Exception trying to retrieve ReplicationGroup using vmhref " + href);
		 }
		
		 return task;
	 }


	 
	// https://hc.apache.org/httpcomponents-client-4.3.x/quickstart.html
	 
	 public static HttpClient createHttpClient_AcceptsUntrustedCerts(boolean login, String username, String password) throws Exception  {
		 
		      
	    	    HttpClientBuilder b = HttpClientBuilder.create();;
	    	 
	    	  /*
	    	    
	    	    // set the connection timeout value to 30 seconds (30000 milliseconds)
	    	    final HttpParams httpParams = new BasicHttpParams();
	    	    HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
	    	    client = new DefaultHttpClient(httpParams);
	    	    
	    	    RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30 * 1000).build();
	    	    HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
	    	    
	    	 */
	    	    
	    	    // setup a Trust Strategy that allows all certificates.
	    	    //
	    	    SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
	    	        public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
	    	            return true;
	    	        }
	    	    }).build();
	    	    b.setSslcontext( sslContext);
	    	 
	    	    // don't check Hostnames, either.
	    	    //      -- use SSLConnectionSocketFactory.getDefaultHostnameVerifier(), if you don't want to weaken
	    	    HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
	    	 
	    	    // here's the special part:
	    	    //      -- need to create an SSL Socket Factory, to use our weakened "trust strategy";
	    	    //      -- and create a Registry, to register it.
	    	    //
	    	    SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
	    	    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
	    	            .register("http", PlainConnectionSocketFactory.getSocketFactory())
	    	            .register("https", sslSocketFactory)
	    	            .build();
	    	 
	    	    // now, we create connection-manager using our Registry.
	    	    //      -- allows multi-threaded use
	    	    PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager( socketFactoryRegistry);
	    	    b.setConnectionManager( connMgr);
	    	    
	    	    // https://hc.apache.org/httpcomponents-client-4.3.x/tutorial/html/connmgmt.html#d5e380
	    	    // Increase max total connection to 200
	    	    connMgr.setMaxTotal(200);
	    	    // Increase default max connection per route to 20
	    	    connMgr.setDefaultMaxPerRoute(20);
	    	 
	    	    HttpClient client = null;
	    	    
	    	    if (login) {
	    	           CredentialsProvider provider = new BasicCredentialsProvider();
	    	            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
	    	           provider.setCredentials(AuthScope.ANY, credentials);
	    	           RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3 * 1000).setConnectTimeout(3 * 1000).build();
	   	    	    
	    	           client = b.setDefaultCredentialsProvider(provider).setDefaultRequestConfig(requestConfig).build();
	    	      /*
	    	       
	    	       // http://stackoverflow.com/questions/9925113/httpclient-stuck-without-any-exception
	    	             RequestConfig defaultRequestConfig = RequestConfig.custom()
	    	            		                                           .setCookieSpec(CookieSpecs.BEST_MATCH)
	    	            		                                           .setExpectContinueEnabled(true)
	    	            		                                           .setStaleConnectionCheckEnabled(true)
	    	            		                                           .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
	    	            		                                           .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
	    	            		                                           .build();

	    	             HttpGet httpGet = new HttpGet(url);    
	    	             RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig)
	    	                                                        .setSocketTimeout(5000)
	    	                                                        .setConnectTimeout(5000)
	    	                                                        .setConnectionRequestTimeout(5000)
	    	                                                        .build();
	    	             httpGet.setConfig(requestConfig);
	    	      */
	    	    }
	    	    else {
	    	    // finally, build the HttpClient;  set connection and wait for data timeouts
	    	    //      -- done!
	    	    	 RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3 * 1000).setSocketTimeout(3 * 1000).build();
	    	    	
	    	    	 
	    	     client = b.setDefaultRequestConfig(requestConfig).build(); 
	    	    }
	    	    
	    	    return client;
	    	}
	 

	    public VMInfo getVMInfo(String vmHref)  {

	    	// get the VM
	    	
	    	VMInfo vminfo = new VMInfo();
			
			try {
				
				
		//	    HttpClient httpClient = createHttpClient_AcceptsUntrustedCerts(true);
			       
				logger.debug("Calling getVM " + vmHref);
				// Create new getRequest with below mentioned URL
				HttpGet getRequest = new HttpGet(vmHref);  // vApp href
	 
				// Add additional header to getRequest which accepts application/xml data
				getRequest.addHeader("accept", "application/*+xml;version=9.0");
	 
				// Add vcd cloud session id
				getRequest.addHeader("x-vcloud-authorization", sessionId);
			     
				
				// Execute your request and catch response
				HttpResponse response = httpClient.execute(getRequest);
	 
				// Check for HTTP response code: 200 = success
				if (response.getStatusLine().getStatusCode() != 200) {
					logger.error("Http call error: " + response.getStatusLine().getStatusCode() );
					throw new DemoException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
				}
	 
			    vminfo = ApacheClientParser.parseVMInfo(new BufferedInputStream(response.getEntity().getContent()));


			} catch (Exception e) {
				logger.error("Apache http call exception retrieving Task Status ", e); // stacktrace
				
			}
	    	
	    	
	    	
	    	return vminfo;
	    }
	    
	    
	     public List<ReplicationGroup> getReplicationGroups(String orgHref) throws DemoException {
	    					
			
	    	if(orgHref == null || orgHref.length() == 0) {
	    		logger.error("orgHref passed to getReplicationGroups is null");
	    		throw new DemoException("orgHref is null.");
	    	}
	    	
		    ArrayList<ReplicationGroup> replgroup = new ArrayList<ReplicationGroup>();
	
			 try {
				
				// Create new getRequest with below mentioned URL
				HttpGet getRequest = new HttpGet(orgHref);
	 
				// Add additional header to getRequest which accepts application/xml data
				getRequest.addHeader("accept", "application/*+xml;version=9.0");
	 
				// Add vcd cloud session id
				getRequest.addHeader("x-vcloud-authorization", sessionId);
			     
				logger.debug("Calling to retrieve Org Replication Group Reference List " + orgHref);
				
				// Execute your request and catch response
				HttpResponse response = httpClient.execute(getRequest);
				
				// Check for HTTP response code: 200 = success
				if (response.getStatusLine().getStatusCode() != 200) {
					logger.error("Http call error: " + response.getStatusLine().getStatusCode() );
					throw new DemoException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
				}
	 
				ArrayList<String> rgRefList = ApacheClientParser.parseReplicationGroupRefs(response.getEntity().getContent());
			
					if(rgRefList.size() == 0) {
						logger.warn("No replication group references returned for org " + orgHref );
				
				}
				
				// for each reference returned get replication group
				
				for (String rgRef : rgRefList) {
					
					ReplicationGroup rg = getReplicationGroup(rgRef); 
			
					replgroup.add(rg);
				}
				
			} catch (Exception e) {
				logger.error("Apache http call exception ", e); // stacktrace
				throw new DemoException("Apache http call exception", e);
			}
			
		    return replgroup;
	     }
	     
	     public ReplicationGroup getReplicationGroup(String rgRef) throws DemoException {
	    	 
				// Create new getRequest with below mentioned URL
				HttpGet rgRequest = new HttpGet(rgRef);
	 
				// Add additional header to getRequest which accepts application/xml data
				rgRequest.addHeader("accept", "application/*+xml;version=9.0");
	 
				// Add vcd cloud session id
				rgRequest.addHeader("x-vcloud-authorization", sessionId);
			     
				logger.debug("Calling to retrieve Replication Group " + rgRef);
				HttpResponse  rgResponse = null;
				ReplicationGroup rg = null;
				
				try {
				
						// Execute your request and catch response
						rgResponse = httpClient.execute(rgRequest);		
	 
						// Check for HTTP response code: 200 = success
						if (rgResponse.getStatusLine().getStatusCode() != 200) {
							logger.error("Http call error: " + rgResponse.getStatusLine().getStatusCode() );
							throw new DemoException("Failed : HTTP error code : " + rgResponse.getStatusLine().getStatusCode());
						}
								 
						
						rg = ApacheClientParser.parseReplicationGroup(rgResponse.getEntity().getContent());
				
				} catch (Exception ex) {
					logger.error("Apache http call exception retrieving ReplicationGroup ", ex); // stacktrace
					throw new DemoException("Apache http call exception retrieving ReplicationGroup", ex);
				}
				
				logger.debug("Returned replication group " + rg.getName());
				return rg;
	     }


	     public Organization getOrganization(ReferenceType orgref) throws DemoException {
	    	
		    Organization org = new Organization(); 
		
			try {
				
				if (!orgref.getType().equals("application/vnd.vmware.vcloud.org+xml")) {
					throw new DemoException("Cannot retrieve Organization object using ReferenceType of type " + orgref.getType());
				}
				if (orgref.getHref() == null || orgref.getHref().length() == 0) {
					throw new DemoException("Cannot retrieve Organization object using ReferenceType will null Href");
				}
					
				// Create new getRequest with below mentioned URL
				HttpGet getRequest = new HttpGet(orgref.getHref());
	 
				// Add additional header to getRequest which accepts application/xml data
				getRequest.addHeader("accept", "application/*+xml;version=9.0");
	 
				// Add vcd cloud session id
				getRequest.addHeader("x-vcloud-authorization", sessionId);
			     
				logger.debug("Calling to retrieve Organization " + orgref);
				
				// Execute your request and catch response
				HttpResponse response = httpClient.execute(getRequest);
	 
				// Check for HTTP response code: 200 = success
				if (response.getStatusLine().getStatusCode() != 200) {
					logger.error("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
				} 
				else
				{
					org = ApacheClientParser.parseOrganization(response.getEntity().getContent());
				}
				
	 
			} catch (Exception e) {
				logger.error("Apache http call exception ", e); // stacktrace
				throw new DemoException("Apache http call exception", e);
			}
			
		    return org;
	     }
	     
	     

	     public List<String> getReplEnabledVdcs(ReferenceType orgref) throws DemoException {
	    	
		    List<String> vdcRefList = new ArrayList<String>(); 
		
			try {
				
				if (!orgref.getType().equals("application/vnd.vmware.vcloud.org+xml")) {
					throw new DemoException("Cannot retrieve Organization object using ReferenceType of type " + orgref.getType());
				}
				if (orgref.getHref() == null || orgref.getHref().length() == 0) {
					throw new DemoException("Cannot retrieve Organization object using ReferenceType will null Href");
				}
					
				// Create new getRequest with below mentioned URL
				HttpGet getRequest = new HttpGet(orgref.getHref() + "/enabledForReplicationVdcs");
	 
				// Add additional header to getRequest which accepts application/xml data
				getRequest.addHeader("accept", "application/*+xml;version=9.0");
	 
				// Add vcd cloud session id
				getRequest.addHeader("x-vcloud-authorization", sessionId);
			     
				logger.debug("Calling to retrieve list of replication enabled VDCs in Organization " + orgref);
				
				// Execute your request and catch response
				HttpResponse response = httpClient.execute(getRequest);
	 
				// Check for HTTP response code: 200 = success
				
				if (response.getStatusLine().getStatusCode() == 404) {
					logger.debug("vCloud Director environment does not have vSphere Availability installed");
				}
					else if (response.getStatusLine().getStatusCode() != 200) {
						logger.error("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
					} 
						else
						{
							vdcRefList = ApacheClientParser.parseReplEnabledVdcs(response.getEntity().getContent());
						}
				
	 
			} catch (Exception e) {
				logger.error("Apache http call exception ", e); // stacktrace
				throw new DemoException("Apache http call exception", e);
			}
			
		    return vdcRefList;
	     }


	     public Task performPostAction(String href, String data ) throws DemoException {
	    	
		    Task task = new Task();
		
			try {

				
				// Create new Request with below mentioned URL
				HttpPost postRequest = new HttpPost(href);
	 
				// Add additional header to getRequest which accepts application/xml data
				postRequest.addHeader("accept", "application/*+xml;version=9.0");
	 
				// Add post parms if any
				if(data != null) {
			    //	postRequest.setEntity(new UrlEncodedFormEntity(parms));
			    	postRequest.addHeader("Content-Type", "application/vnd.vmware.hcs.failoverParams+xml");
					HttpEntity entity = new ByteArrayEntity(data.getBytes("UTF-8"));
					postRequest.setEntity(entity);
				}
				
				// Add vcd cloud session id
				postRequest.addHeader("x-vcloud-authorization", sessionId);
			     
				logger.debug("Calling to perform action on VM " + href);
				
				// Execute your request and catch response
				HttpResponse response = httpClient.execute(postRequest);
	 
				// Check for HTTP response code: 202 = success
				if (response.getStatusLine().getStatusCode() != 202 && response.getStatusLine().getStatusCode() != 200) {
					logger.error("Failed : HTTP Post error code : " + response.getStatusLine().getStatusCode());
					task.setErrorDescription( "HTTP Post error code : " + response.getStatusLine().getStatusCode());
				} 
				else
				{
				//	org = ApacheClientParser.parseOrganization(response.getEntity().getContent());
					
					BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
				//	String output;
				//	logger.debug("============Output:============");
				//	logger.debug("x-vcloud-authorization: " + String.valueOf(sessionId));
					// Simply iterate through XML response and show on console.
				//	while ((output = br.readLine()) != null) {
				//		logger.debug(output);
				//	}
					
					task = ApacheClientParser.parseTask(response.getEntity().getContent());
					
				}
				
	 
			} catch (Exception e) {
				logger.error("Apache http post call exception ", e); // stacktrace
				throw new DemoException("Apache http  post call exception", e);
			}
			
		    return task;
	     }
		
	     

	     public Task performDeleteAction(String href) throws DemoException {
	    	
		    Task task = new Task();
		
			try {
				
						
				// Create new Request with below mentioned URL
				HttpDelete deleteRequest = new HttpDelete(href);
	 
				// Add additional header to deleteRequest which accepts application/xml data
				deleteRequest.addHeader("accept", "application/*+xml;version=9.0");
	 
				// Add vcd cloud session id
				deleteRequest.addHeader("x-vcloud-authorization", sessionId);
			     
				logger.debug("Calling to perform action on VM " + href);
				
				// Execute your request and catch response
				HttpResponse response = httpClient.execute(deleteRequest);
	 
				// Check for HTTP response code: 200 = success
				if (response.getStatusLine().getStatusCode() != 200) {
					logger.error("Failed : HTTP Delete error code : " + response.getStatusLine().getStatusCode());
					task.setErrorDescription( "HTTP Delete error code : " + response.getStatusLine().getStatusCode());
				} 
				else
				{
				//	org = ApacheClientParser.parseOrganization(response.getEntity().getContent());
					
					BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
				//	String output;
				//	logger.debug("============Output:============");
				//	logger.debug("x-vcloud-authorization: " + String.valueOf(sessionId));
					// Simply iterate through XML response and show on console.
				//	while ((output = br.readLine()) != null) {
				//		logger.debug(output);
				//	}
					
					task = ApacheClientParser.parseTask(response.getEntity().getContent());
					
				}
				
	 
			} catch (Exception e) {
				logger.error("Apache http Delete call exception ", e); // stacktrace
				throw new DemoException("Apache http Delete call exception", e);
			}
			
		    return task;
	     }
		
	   
	     public Vdc getVdc(ReferenceType vdcref) throws DemoException {
	    	
		    Vdc vdc = new Vdc(); 
		
			try {
				
				if (!vdcref.getType().equals("application/vnd.vmware.vcloud.vdc+xml")) {
					throw new DemoException("Cannot retrieve VDC object using ReferenceType of type " + vdcref.getType());
				}
				if (vdcref.getHref() == null || vdcref.getHref().length() == 0) {
					throw new DemoException("Cannot retrieve VDC object using ReferenceType will null Href");
				}
					
				// Create new getRequest with below mentioned URL
				HttpGet getRequest = new HttpGet(vdcref.getHref());
	 
				// Add additional header to getRequest which accepts application/xml data
				getRequest.addHeader("accept", "application/*+xml;version=9.0");
	 
				// Add vcd cloud session id
				getRequest.addHeader("x-vcloud-authorization", sessionId);
			     
				logger.debug("Calling to retrieve VDC " + vdcref);
				
				// Execute your request and catch response
				HttpResponse response = httpClient.execute(getRequest);
	 
				// Check for HTTP response code: 200 = success
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new DemoException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
				}
	 
			    vdc = ApacheClientParser.parseVdc(response.getEntity().getContent());
				
			    // Call the vdcs Org to retrieve a list of Replication enabled vdcs.
			    List<String> replEnabledVdcs = getReplEnabledVdcs(vdc.getOrgRef());
			    
			    boolean vdcEnabled = false;
	            for (String vdcHref : replEnabledVdcs) {
					if(vdcHref.equals(vdcref.getHref()))
						vdcEnabled = true;
				}
			    
			    // Note if VDC not enabled for replication, a link for recoveryDetails still exists if vCD extended with DR2C
			    // But if not enabled then url returns 404
			    // It also has links for both disableReplication and enableReplication

			    	

			    // if the vdc has replication enabled get the details and add to vdc object
			    if (vdcEnabled) {
			       getVdcRecoveryDetails(vdc.getRecoveryRef(), vdc);
			    }
			       
			       // https://10.158.12.128/api/vdc/e76b8f93-ebcd-4fc0-8fd6-3a9500de4911/replications
				
	 
			} catch (Exception e) {
				logger.error("Apache http call exception ", e); // stacktrace
				throw new DemoException("Apache http call exception", e);
			}
			
			
		    return vdc;
	     }
	     

	     public Vdc getVdcRecoveryDetails(ReferenceType vdcRecoveryRef, Vdc vdc) throws DemoException {
	    	
		    
		
			try {
				
				if (!vdcRecoveryRef.getType().equals("application/vnd.vmware.hcs.vrRecoveryDetails+xml")) {
					throw new DemoException("Cannot retrieve VDC recovery details using ReferenceType of type " + vdcRecoveryRef.getType());
				}
				if (vdcRecoveryRef.getHref() == null || vdcRecoveryRef.getHref().length() == 0) {
					throw new DemoException("Cannot retrieve VDC recovery details  using ReferenceType with null Href");
				}
					
				// Create new getRequest with below mentioned URL
				HttpGet getRequest = new HttpGet(vdcRecoveryRef.getHref());
	 
				// Add additional header to getRequest which accepts application/xml data
				getRequest.addHeader("accept", "application/*+xml;version=9.0");
	 
				// Add vcd cloud session id
				getRequest.addHeader("x-vcloud-authorization", sessionId);
			     
				logger.debug("Calling to retrieve VDC Recovery Details " + vdcRecoveryRef);
				
				// Execute your request and catch response
				HttpResponse response = httpClient.execute(getRequest);
	 
				// Check for HTTP response code: 200 = success
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new DemoException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
				}
	 
			    ApacheClientParser.parseVdcRecoveryDetails(response.getEntity().getContent(), vdc);
				
		
			  
				
	 
			} catch (Exception e) {
				logger.error("Apache http call exception ", e); // stacktrace
				throw new DemoException("Apache http call exception", e);
			}
			
			
		    return vdc;
	     }
	  
	  

}


