package demoapp;

import static spark.Spark.*;
import demoapp.model.NavBean;
import demoapp.model.Organization;
import demoapp.model.ReferenceType;
import demoapp.model.ReplicationGroup;
import demoapp.model.Task;
import demoapp.model.VMInfo;
import demoapp.model.Vdc;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import spark.ModelAndView;
import spark.Session;
import spark.Request;
import spark.template.freemarker.FreeMarkerEngine;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.log4j.PropertyConfigurator;


/*
 * This is the main for the dr2c demo UI application
 * 
 * Build Setup
 * -----------
 * 
 * Maven must be installed on the build workstation.
 * Note: Most libraries are available in the maven public repository and will be resolved for the build,
 * but two libs for the VMWare sdk client are not in the public repo.
 * rest-api-schemas-8.10.0.jar and 	vcloud-java-sdk-8.10.0.jar
 * These libs must be downloaded from vmware and installed in the local workstation maven repository.
 * See the pom.xml file for details including sample commands to install the files in the local repository.
 * Once the files are installed in the local repo, run the command ->      mvn install
 * From the folder containing the pom.xml To resolve the dependencies
 * 
 * Building the App
 * ----------------
 * Build the application using the following steps:
 * In a terminal window change to the source directory containing the pom.xml file
 * Then run the following command.

 * 
 * $ mvn package
 * 

 * 
 * Running the App
 * -----------------
 * Once the build is complete, a demoApp-10.jar is created in the target folder.
 * Run the application using the following command, also from the folder containing the pom.xml file
 * 
 * java -cp ./target -jar ./target/demoApp-1.0.jar
 * 
 * The UI application should then be available on http://localhost:4567
 * 
 * App configuration
 * ------------------
 * The demo.properties file and log4j.properties files are used to configure the application settings and logging levels.
 * 
 * A default url, username and password can be set in the demo.properties file and will be read at runtime and appear on the login screen.
 * 
 * A choice of http clients can also be specified in the demo.properties file.
 * The vCloud Director SDK Java Client or the Apache Http client are the current choices.
 * The vCloud Director SDK Java Client does not contain API extensions needed for accessing DR2C extensions on vCD,
 * the Apache client is therefore recommended.
 * 
 * Logging is managed by slf4j and log4j.
 * Log levels can be changed by modifying log levels in the log4j.properties file.
 * A restart of the application is required to activate changes.
 * Each class has its own logger so log granularity can be set from the total app all the way down to individual classes.
 * The default configuration outputs log data to the stdout console
 * 
 */

public class App {
	
	public static String sessionId;
	
	static ConcurrentHashMap<String, VcdClient> vClientMap = new ConcurrentHashMap<String, VcdClient>();
	
	static ConcurrentHashMap<String, org.eclipse.jetty.websocket.api.Session> wsSessionMap = new ConcurrentHashMap<>();
	
    private static final CopyOnWriteArrayList<DemoTask> demoTaskList = new CopyOnWriteArrayList<>();
	
    static final String LOG_PROPERTIES_FILE = "./target/classes/demoapp/log4j.properties";
    static final String DEMO_PROPERTIES_FILE = "./target/classes/demoapp/demo.properties";
    
    final static Logger logger = LoggerFactory.getLogger(App.class);
    
    static Properties demoProperties = null;
    
    static Class<VcdClient> vcdClientClass= null;
    
    static String vcd_url ="";
    static String vcd_username = "";
    static String vcd_password = "";
    
    
    
    public static void main(String[] args) {
    	
    	int maxThreads = 8;
    	int minThreads = 2;
    	int timeOutMillis = 30000;
    	threadPool(maxThreads, minThreads, timeOutMillis);

    	initializeLogger();
    	
    	System.out.println("Logger Level " + logger.ROOT_LOGGER_NAME + " Debug " + logger.isDebugEnabled());
    	staticFileLocation("/public");
    	
    	logger.info("App Started");
    	
        // Load the specified client
        
        loadDemoProperties();
        
        String clientName = demoProperties.getProperty("client");
        vcd_url =  demoProperties.getProperty("vcd_url");
        vcd_username =  demoProperties.getProperty("vcd_username");
        vcd_password =  demoProperties.getProperty("vcd_password");
        
        try {
        
        	vcdClientClass = (Class<VcdClient>) Class.forName(clientName);
        } 
      
        catch (ClassNotFoundException ex) {
        	logger.error("Failed to load vcdClient: " + clientName + " specified in demo.properties file");
        	halt();
        }
    	
    
    	
        FreeMarkerEngine engine = new FreeMarkerEngine();
        
        String layout = "spark/template/velocity/layout.vtl";
        
        // start Web Socket Handler
    //    webSocket("/chat/", WebSocketHandler.class);
        
        // start DemoTask Scheduler
   //     DemoTaskScheduler taskScheduler = new DemoTaskScheduler();
   //    try {
   //     taskScheduler.start();

     //  } catch (Exception ex) {
   // 	   logger.error("DemoTaskScheduler Exception " + ex);
   //    }
       
        get("/throwexception", (request, response) -> {
        	logger.error("/throwexcption Sees unhandled exception");
            throw new DemoException();
        });

    //    exception(DemoException.class, (exception, request, response) -> {
            // Handle the exception here
       // 	System.out.println("! ! ! ! !See uncaught exception " + exception);
       // 	logger.error("Uncaught Exception " + exception);
        	         
      	//       Session sess = request.session(true);      
      	  
       //        HashMap model = sess.attribute("model");
               
      //         addMessage(model, "Uncaught Exception: " +  exception.getMessage());
        	  
        	   
    //    });


    	   
  //     App.vClientMap = new ConcurrentHashMap<String, VcdClient>();
       
        get("/",(request, response) -> {
        	logger.debug("Route / called");
            response.redirect("/login");
            return "";
        });
            
     
        
        get("/login", (request, response) -> { 
        	logger.debug("Route /login called");
            HashMap model = new HashMap();   // model for login, no session yet
       
      //      model.put("template", "spark/template/velocity/login.vtl");
           model.put("vcloudurl", vcd_url);
        //    model.put("vcloudurl", "http://localhost:9000");
            model.put("username", vcd_username);
            model.put("password", vcd_password);
            return new ModelAndView(model, "login.ftl");
           
          },  engine);
        
        get("/logout", (request, res) -> {
        	logger.debug("Route /logout called");
        	
        	
        	 Session sess = request.session(true);        
        	  
             // get the client
        	 VcdClient client = vClientMap.get(sess.attribute("username"));
        	 client.logout();
        	 
        	
        	 String username = sess.attribute("username");
        	 if(username != null)
        		 vClientMap.remove(username);
        	 
        	 client = null;
        	 
        //	 sess.invalidate();
        	 
        	 
        	 HashMap model = new HashMap();
             model.put("vcloudurl", vcd_url);
               model.put("username", vcd_username);
               model.put("password", vcd_password);
               return new ModelAndView(model, "login.ftl");
                  
                 },  engine);
        
        

        post("/poweron", (request, response) -> {
         	logger.debug("Route /poweron called");
        	  return performAction("poweron", request);
        	  
          });

        post("/poweroff", (request, response) -> {
              logger.debug("Route /poweroff called");
        	  return performAction("poweroff", request);
        	  
          });
        
        post("/suspend", (request, response) -> {
        	logger.debug("Route /suspend called");
            
        	  return performAction("suspend", request);
        	  
        });
        
        post("/resume", (request, response) -> {
        	logger.debug("Route /resume called");
        	  return performAction("resume", request);
        	  
        });
        

        get("/getmessages", (request, response) -> {   
        	logger.debug("Route /getmessages called");
        	Session sess = request.session();
            HashMap model = sess.attribute("model");
            
            return (String) model.get("messages");
            
        });
      
     
      // handle task status request from polling browser client
        post("/getVMState", (request, response) -> { 
        	logger.debug("Route /getVMState called");
        	 
            Session sess = request.session(true)   ;                  

            String username = sess.attribute("username");
            if(username == null || username.length() == 0)
            		response.redirect("/login");
            				
            VcdClient client = vClientMap.get(username);
            
            String jsonData = request.queryParams("data");
            logger.debug("Entered geVMStatus: " + jsonData);
            
            JSONArray array = new JSONArray(jsonData);
            JSONObject object = array.getJSONObject(0);
            String vmHref = object.getString("value");
            
            String state="";
            
            response.header("Content-Type: ", "application/json");
            if(vmHref != null) {
	            try {
	            	
	            	state = client.getVMStatus(vmHref);
	            
	        
	            } catch(DemoException ex) {
	           	    
	            	 logger.error("Unable to retrieve VM status using href " + vmHref);
	            }
            }
            
            // json { vmhref: "https://10.134.3.58/api/vApp/vm-6a7712f7-2a9c-46a9-b905-5aa533c9d31b", state: "POWERED_ON"}
	    	   
        	logger.debug("getVMState State: " + state);  
            return state;
        });
       
        
        post("/dologin", (request, response) -> { 
        	logger.debug("Route /dologin called");
        	logger.debug("see username: " +  request.queryParams("username"));  // must be before any call to request.body()
        	logger.debug("see password: " +  request.queryParams("password"));  // must be before any call to request.body()
        	logger.debug("see vcloudurl: " +  request.queryParams("vcloudurl"));  // must be before any call to request.body()
            
            String vcloudurl = request.queryParams("vcloudurl");          
            String username  = request.queryParams("username");
            String password  = request.queryParams("password");
            
            if(vcloudurl != null ) vcloudurl=vcloudurl.trim();
            if(username != null ) username=username.trim();
            if(password != null ) password=password.trim();
            
           // Create Model 
            
            Map<String, Object> model = new HashMap<>();
            model.put("messages", "");  // add initial header messages string
          
           
            VcdClient vcdClient=null;
            
            try {
            	
            	Constructor<VcdClient> ctor = vcdClientClass.getDeclaredConstructor(String.class);
            	vcdClient = ctor.newInstance(vcloudurl);
            	
          //  	vcdClientClass.newInstance(vcloudurl);
            	vcdClient.login(username,  password);
            } catch (DemoException ex)
            {
            	 logger.warn("Login Error for user " + username);
            	 model.put("errorMessage", "Invalid Username or Password");
                 model.put("username", username);
                 model.put("password", password);
                 model.put("vcloudurl", vcloudurl);
            	 return new ModelAndView(model, "login.ftl");
            }
         
            addMessage(model, ""); // clear old messages
   
    
            // Login successful, save the client object
            
            vClientMap.put(username, vcdClient);
                        
            logger.debug("Session Token: " + vcdClient.getVcloudToken());
            logger.debug("Org: " + vcdClient.getOrgName());  
            

            
            // Create http Session
            
            Session sess = request.session(true)   ;                   // create and return session
            
            // Save the username
            sess.attribute("username", username);
            
            // Initialize NavBean and set Orglist
            
            NavBean navBean = NavBean.create();
            
            List<ReferenceType> orgList = (vcdClient.getOrgRefList());
            Collections.sort(orgList);
            navBean.setOrgList(orgList);
            System.out.println("Navbean selected name" + navBean.getSelectedVdcName());
            System.out.println("Navbean isselected " + navBean.getVdcEntrySelected());
            
            model.put("username", username);
            model.put("navBean",  navBean);

           // Place the model in Session
            
      //      sess.attribute("client", client);
            sess.attribute("model", model);
         
            	
            return new ModelAndView(sess.attribute("model"), "home.ftl");
           
          },  engine);
        

        /*
         * Get the selected Org Object and save it in the NavBean
         */
        get("/selectorg", (request, response) -> {
        	logger.debug("Route /selectorg called");
            Session sess = request.session(true)   ;                  

            String username = sess.attribute("username");
            if(username == null || username.length() == 0)
            		response.redirect("/login");
            
            if(vClientMap.get(sess.attribute("username")) == null)
            	response.redirect("/login");
            
         //   if( request.session().attribute("client") == null)
         //   	response.redirect("/login");
            
            String orgName = request.queryParams("name"); 
            
          	logger.debug("/selectorg " +orgName + " Requested");

            HashMap model = sess.attribute("model");
            addMessage(model, ""); // clear old messages
            NavBean navBean = (NavBean) model.get("navBean");
            
          	// get the client
            
         
            logger.debug("vClientMap is "  + String.valueOf(vClientMap));
            logger.debug("username is " + username);
            VcdClient client = vClientMap.get(username);
            
           // Lookup the orgname in the known list in navBean
          
            
            ReferenceType orgRef = navBean.getOrgRefByName(orgName);
            
            try {
            
		            // Get the Org object and save it in the navbean  
		            
		         	Organization org = client.getOrganization(orgRef);
		            
		            navBean.setSelectedOrg(org);
		            
		            
		            logger.debug("selected org is  " + navBean.getSelectedOrg().getName());
		            
		            // get the vdcList for the selected Org
		            List<ReferenceType> orgVdcList = org.getVdcRefs();
		     //       ArrayList<ReferenceType> orgVdcList = (ArrayList<ReferenceType>) client.getOrgVdcList(orgRef.getHref());
		            Collections.sort(orgVdcList);    
		    	    // Save the vdc list in the NavBean
		    	    navBean.setOrgVdcList(orgVdcList);
		    	   
		    	   // debug
		    	   if(logger.isDebugEnabled()) {
		    		   for (ReferenceType vdc : orgVdcList) {
		    			   logger.debug("See vdc named " + vdc.getName());
		    		   }
		    	   }
		            
		            // this must come from browser
		            // Send info to identify status callback channel for task status info
		      //      org.eclipse.jetty.websocket.api.Session wsSession = wsSessionMap.get(sess.attribute("username"));
		      //      wsSession.getRemote().sendString(sess.attribute("username"));
		      
		    	   logger.debug("orgSelected " + navBean.isOrgSelected());
            } catch (DemoException ex) {
            	  logger.error("Exception " + ex.getMessage());
            	  addMessage(model, "No Organization Data");
            }
            
            return new ModelAndView(sess.attribute("model"), "home.ftl");
        }, engine);
        
        
        
        
        get("/deselectvdc", (request, response) -> {
        	logger.debug("Route /deselectvdc called");
        	  Session sess = request.session(true)   ;                  

              String username = sess.attribute("username");
              if(username == null || username.length() == 0)
              		response.redirect("/login");
              
              HashMap model = sess.attribute("model");
              addMessage(model, ""); // clear old messages
              NavBean navBean = (NavBean) model.get("navBean");
              navBean.deSelectVdc();
           
              
        	  return new ModelAndView(sess.attribute("model"), "home.ftl");
        }, engine);
        
        
        /*
         * Get the selected Vdc object and save it in the NavBean
         */
        get("/selectvdc", (request, response) -> {
        	
        	logger.debug("Route /selectvdc called");
        	Session sess = request.session();          

             String username = sess.attribute("username");
             if(username == null || username.length() == 0)
             		response.redirect("/login");
        	 
            if(vClientMap.get(sess.attribute("username")) == null)
            	response.redirect("/login");
            
            String vdcName = request.queryParams("name"); 
            
        	logger.debug("/selectvdc " + vdcName + " Requested");
           
            HashMap model = sess.attribute("model");
            addMessage(model, ""); // clear old messages
            NavBean navBean = (NavBean) model.get("navBean");
            
        	// get the client
            
      
            logger.debug("vClientMap is "  + String.valueOf(vClientMap));
            logger.debug("username is " + username);
            VcdClient client = vClientMap.get(username);
            
            
            // Get the vdc ref from the known list of vdcs in NavBean
            // Get the Vdc object and save it in the NavBean
            
            ReferenceType ref = navBean.getVdcRefByName(vdcName);
            Vdc vdc = client.getVdc(ref);
            navBean.setSelectedVdc(vdc);
            navBean.setVdcEntrySelected(true);
             
            try {
        //    VMTabBean vmTabBean = VMTabBean.create(client, navBean.getSelectedVdcHref());
         //   vmTabBean.getVmBeanList();
            
             //TODO
	        	List<VMInfo> vmBeanList = client.getVdcVMs(navBean.getSelectedVdc());
	     //   	 navBean.setTabpage("vmlist");
	            model.put("vmlist", vmBeanList);
	        
              logger.debug("selected vdc is " + navBean.getSelectedVdc().getName());
              
            } catch (DemoException ex) {
          	  logger.error("Exception " + ex.getMessage());
          	  addMessage(model, "No Organization Data");
          }
                    
            return new ModelAndView(sess.attribute("model"), "home.ftl");
        }, engine);
 
        
    get("/selectallvdc", (request, response) -> {   
    	logger.debug("Route /selectallvdc called");
     	Session sess = request.session();
     	         

         String username = sess.attribute("username");
         if(username == null || username.length() == 0)
         		response.redirect("/login");
       
        HashMap model = sess.attribute("model");
        addMessage(model, ""); // clear old messages
        NavBean navBean = (NavBean) model.get("navBean");
        
        navBean.setSelectedVdc(null);
        navBean.setVdcEntrySelected(true);  // all vdc 
        
    	// get the client 
     
        logger.debug("vClientMap is "  + String.valueOf(vClientMap));
        logger.debug("username is " + username);
        VcdClient client = vClientMap.get(username);
   	
        try {
		   	 Organization org = navBean.getSelectedOrg();
		   	 
		   	 // Get replication groups for the org if they exist
		   	 if(org.getReplHref() != null && org.getReplHref().length() > 0) {
		   		 List<ReplicationGroup> rgroups = client.getReplicationGroups(org.getReplHref());
		   		 model.put("rgroups", rgroups );
		   	 }
		   	 else {
		//   	 navBean.setTabpage("replications");
		   		 model.put("rgroups", new ArrayList<ReplicationGroup>() );
		   	 }
	
	          	 
	        // Get VMs for replication groups 
	    //	List<VMInfo> vmBeanList = client.getVdcVMs(navBean.getSelectedVdc().getHref());
	   // 	 navBean.setTabpage("vmlist");
	    //    model.put("vmlist", vmBeanList);
	    
	       
	     
        } catch (DemoException ex) {
        	logger.error("Error retrieving replication data ", ex );
      	  addMessage(model, "No Replication Data");
      	  
        } catch (Exception ex) {
        	logger.error("Error retrieving replication data ", ex );
        	 addMessage(model, "Error retrieving replication data");
        }
                
        return new ModelAndView(sess.attribute("model"), "home.ftl");
    }, engine);
    
    
    }   // main

    

    
    
    public static String performAction(String action, Request request) {
    	
    	 String response ="";
    	 
    	 Session sess = request.session(true)   ;                   // create and return session

         // sparkJava bug, avoid calling - request.body() before queryParams or parms will be lost 
         String jsonData = request.queryParams("data");
         
         logger.debug("App received Resume request with parms " + jsonData);
         
         
         // TODO: Add iteration for > 1 VM requested
         JSONArray array = new JSONArray(jsonData);
         for (int i = 0; i < array.length(); i++) {
             JSONObject object = array.getJSONObject(i);
             logger.debug("the name is " + object.getString("name"));
             logger.debug("the value is " + object.getString("value"));
         }
         
   
         HashMap model = sess.attribute("model");
         addMessage(model, ""); // clear old messages
         NavBean navBean = (NavBean) model.get("navBean");      
                     
         // get vm id
         JSONObject object = array.getJSONObject(0);
         String vmHref = object.getString("value");
        
         
         Task task = null;
         
         VcdClient client = vClientMap.get(sess.attribute("username"));
         
	        	 
    	 if(action.equals("poweron"))   task = client.powerOnVM(vmHref);
           else if(action.equals("poweroff"))  task = client.powerOffVM(vmHref);
             else if(action.equals("suspend")) task = client.suspendVM(vmHref);
              else if(action.equals("resume")) task = client.resumeVM(vmHref);;    
              	
         if(task.getErrorDescription().length() != 0) {
        	   logger.error("Illegal task action or no task returned by server. vmHref: " + 
                             vmHref+ " Action: " + action);
        	   response = "Error Unable to start task.";
        	   addMessage(model, "Error: Task Not Started on VM " + task.getErrorDescription()); 
         }
         logger.debug("Task Started: " + task.getHref());
     	
         addMessage(model, "Task Started: " + task.getHref()); 

         response = task.getHref();
         
         return response;
    }
    
    private static void addMessage(Map model, String message) {
    	
    	String messages = (String) model.get("messages");
    	
    	// want to clear old message?
    	if (message == null || message.length() == 0){
    		if(!messages.endsWith("&#13;&#10;"))
    			messages.concat("&#13;&#10;");
    	} else {
	    	
	    	messages =  getCurrentTimeStamp() + "  " + message +  "&#13;&#10;" + messages;	
	    	
	    	model.put("messages",  messages);
    	}
    }
    
    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
    
    private static void initializeLogger()
    {
      Properties logProperties = new Properties();
   
      try
      {
        // load our log4j properties / configuration file
        logProperties.load(new FileInputStream(LOG_PROPERTIES_FILE));
        PropertyConfigurator.configure(logProperties);
        logger.info("Logging initialized.");
      }
      catch(IOException e)
      {
    	  logger.error("Error loading log4j.properties ", e );
        throw new RuntimeException("Unable to load logging property " + LOG_PROPERTIES_FILE);
      }
    }
    
    private static void loadDemoProperties()
    {
    	demoProperties = new Properties();
   
      try
      {
        // load our log4j properties / configuration file
        demoProperties.load(new FileInputStream(DEMO_PROPERTIES_FILE));
        logger.info("Demo properties read.");
      }
      catch(IOException e)
      {
    	  logger.error("Error loading demo.properties ", e );
        throw new RuntimeException("Unable to load demo property file " + DEMO_PROPERTIES_FILE);
      }
    }
    
	public static CopyOnWriteArrayList<DemoTask> getDemoTaskQueue() {
	
		return demoTaskList;
	}
}