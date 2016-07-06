package demoapp.clients;

import static org.joox.JOOX.$;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joox.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vmware.vcloud.sdk.constants.VMStatus;

import demoapp.DemoException;
import demoapp.model.Organization;
import demoapp.model.ReferenceType;
import demoapp.model.ReplicationGroup;
import demoapp.model.Task;
import demoapp.model.Vdc;
import demoapp.model.VMInfo;
import demoapp.model.Vapp;

public class ApacheClientParser {

    final static Logger logger = LoggerFactory.getLogger(ApacheClientParser.class);

	 // InputStream enables unit testing with flat files containing XML
    /*
   public static ArrayList<ReferenceType> parseVdcVappRefs(InputStream istream) throws Exception {
   	
   	        ArrayList<ReferenceType> vapprefs = new ArrayList<>();
   	        BufferedInputStream bis  = null;
   	  
   	        try {
   		
				bis = new BufferedInputStream(istream);
		
				// Parse response with JOOX
				
				 
				logger.debug("\nMade Apache http call, parsing result");
					
				 
		
				 Match $vdc= $(bis);
				
	 
				 $vdc.find("ResourceEntity").forEach(resource -> {
			    	 
			            
	             if ($(resource).attr("type").equals("application/vnd.vmware.vcloud.vApp+xml")) {
	            	  String name = $(resource).attr("name");           // attribute
			          String type = $(resource).attr("type");           // attribute
			          String href = $(resource).attr("href");           // attribute
			         //String type = $(org).find("type").text();    // find for child elements
			            
	             	System.out.format("%s : - %s - %s %n",  name, type, href);
	             
	             	ReferenceType vappref = new ReferenceType(name, href, type);
	             	vapprefs.add(vappref);
	             }
	                 
			        });
				 
   	        } catch (IOException e) {
   	        	logger.debug("Error Parsing http response");
   	        	
   				e.printStackTrace();
   				throw new DemoException("Error parsing http response ", e);
   			} finally {
   				try {
   					if (bis != null)
   						bis.close();
   				} catch (IOException ex) {
   					logger.error("IOException close InputStream.");
   					ex.printStackTrace();
   				}
   			}
			 
			 
			 return vapprefs;
   	
   }
 */
	
	 // InputStream enables unit testing with flat files containing XML
   /*
  public static ArrayList<ReferenceType> parseOrgVdcs(InputStream istream) throws Exception {
  	
  	   ArrayList<ReferenceType> vdcList = new ArrayList<>();
  	   BufferedInputStream bis = null;
  	   
            try {
					// load into BufferedInputSteam so we can reset and read again
					 bis = new BufferedInputStream(istream);
					bis.mark(0);
					
				
					// Get-Capture Complete application/xml body response
					BufferedReader br = new BufferedReader(new InputStreamReader(bis));
					String output;
					logger.debug("============VDC OrgList Output:============");
		
					// Simply iterate through XML response and show on console.
					while ((output = br.readLine()) != null) {
						logger.debug(output);
					}
					
					// reset the stream to the beginning for JOOX to process
					bis.reset();
					
					// Parse response with JOOX
					
					 
					logger.debug("\nMade Apache http call, parsing result");
						
					 
			//		 Match $orgs = $(response.getEntity().getContent());
					 Match $vdcs = $(bis);
					
		 
					 $vdcs.find("Link").forEach(link -> {
				    	   String name = $(link).attr("name");           // attribute
				            String type = $(link).attr("type");           // attribute
				            String href = $(link).attr("href");           // attribute
				         //   String type = $(org).find("type").text();    // find for child elements
				           
		             if ($(link).attr("type").equals("application/vnd.vmware.vcloud.vdc+xml")) {
		             	System.out.format("%s : - %s - %s %n",  name, type, href);
		                 ReferenceType vdcRef = new ReferenceType(name, href, type);
		                 vdcList.add(vdcRef);
		             }
		                 
				        });
            } catch (IOException e) {
   	        	logger.debug("Error Parsing http response");
   	        	
   				e.printStackTrace();
   				throw new DemoException("Error parsing http response ", e);
   			} finally {
   				try {
   					if (bis != null)
   						bis.close();
   				} catch (IOException ex) {
   					logger.error("IOException close InputStream.");
   					ex.printStackTrace();
   				}
   			}
			 
		     
			 return vdcList;
			 
	
  }
  
  */
  
  
  // InputStream enables unit testing with flat files containing XML
  public static  ArrayList<ReferenceType> parseOrgList(InputStream istream) throws Exception {
	    ArrayList<ReferenceType> orgList = new ArrayList<ReferenceType>();
	    
	    BufferedInputStream bis = null;
        try { 
				// load into BufferedInputSteam so we can reset and read again
				 bis = new BufferedInputStream(istream);
			
				// Parse response with JOOX
				
				 
				logger.debug("\nMade Apache http call, parsing result");
					
				 
		//		 Match $orgs = $(response.getEntity().getContent());
				 Match $orgs = $(bis);
				 
				 // get replication href down link if present
		      //   Match $link = $orgs.xpath("//FullName");
		         // /Link[@rel='down:replications']
		      //   System.out.println("down link is " + $link.text());
			     
		    //     String data = $orgs.find("Org").text();
		  //       System.out.println("data "  + data);
		         
			     $orgs.find("Org").forEach(org -> {
			    	 String name = $(org).attr("name");
			         String href = $(org).attr("href");
			         String type = $(org).attr("type");
			     
			  
			     
			    		 
		
			         ReferenceType orgzn = new ReferenceType(name, href, type);
			         logger.debug("See Org Data: " );;
			         System.out.format("%s %s %s: %n", name, href, type);
			         orgList.add(orgzn);
			     });
        } catch (IOException e) {
	        	logger.debug("Error Parsing http response");
	        	
				e.printStackTrace();
				throw new DemoException("Error parsing http response ", e);
			} finally {
				try {
					if (bis != null)
						bis.close();
				} catch (IOException ex) {
					logger.error("IOException close InputStream.");
					ex.printStackTrace();
				}
			}
		 
	     
	     return orgList;
  }
  
  

  // InputStream enables unit testing with flat files containing XML
  public static Organization parseOrganization(InputStream istream) throws Exception {
	   
	  
	    Organization org = new Organization(); 
	    BufferedInputStream bis = null;
	    
	    try {

				// load into BufferedInputSteam so we can reset and read again
				 bis = new BufferedInputStream(istream);
			
				// Parse response with JOOX
				
				 
				logger.debug("\nMade Apache http call, parsing result");
					
		
				 Match $org = $(bis);   // <Org>
				      
		    	 String name = $org.attr("name");
		    	 org.setName(name);
		    	 
		         String href = $org.attr("href");
		         org.setHref(href);
	//TODO	     
		         // Note: better to iterate through links because find, filter, equals combinations can cause npe if elements do not exist
		         
		         Match links =  $org.find("Link"); 
		         
		  //       String replHref =  $org.find("Link")
		  //      		                .filter(ctx -> $(ctx).attr("rel").equals("down:replications"))
		  //      		                .attr("href");
		 //        org.setReplHref(replHref);
		         
		         // Get links to vdcsdown:replications
		  //       Match links =  $org.find("Link")
 		   //                            .filter(ctx -> $(ctx).attr("type")
 		   //                            .equals("application/vnd.vmware.vcloud.vdc+xml"));
		         
                 $(links).forEach(link -> {
                	   
    		                // Get href for down: replications link   for example <Link rel="down:replications" href=
                	        if(link.getAttribute("rel").equals("down:replications") )
                	        	 org.setReplHref(link.getAttribute("href"));
                	        
                	        // Get Org vdc refs
                	        if(link.getAttribute("type").equals("application/vnd.vmware.vcloud.vdc+xml")) {
                	        	
	                    	    ReferenceType vdcref = new ReferenceType();
	                            vdcref.setName( link.getAttribute("name"));
	                            vdcref.setHref(link.getAttribute("href"));
	                            vdcref.setType( link.getAttribute("type"));
	                            org.getVdcRefs().add(vdcref);
                	        }
                      });
                 
			     
		         bis.close();
	    } catch (IOException e) {
        	logger.debug("Error Parsing http response");
        	
			e.printStackTrace();
			throw new DemoException("Error parsing http response ", e);
		} finally {
			try {
				if (bis != null)
					bis.close();
			} catch (IOException ex) {
				logger.error("IOException close InputStream.");
				ex.printStackTrace();
			}
		}
         
	     return org;
  }
  


  // InputStream enables unit testing with flat files containing XML
  public static Vdc parseVdc(InputStream istream) throws Exception {
	   
	  
	    Vdc newVdc = new Vdc(); 
	    BufferedInputStream bis = null;
        try {
				// load into BufferedInputSteam so we can reset and read again
				 bis = new BufferedInputStream(istream);
			
				// Parse response with JOOX
				 
				logger.debug("\nMade Apache http call, parsing result");
					
				 Match vdc = $(bis);   // <Vdc>
				      
		    	 String name = vdc.attr("name");
		    	 newVdc.setName(name);
		    	 
		         String href = vdc.attr("href");
		         newVdc.setHref(href);
		         
		         // Get Description 
		         String desc =  vdc.find("Description").text();
		         
		         String cpuAllocated = vdc.find("ComputeCapacity").find("Cpu").find("Allocated").text();
		         newVdc.setCpuAllocated(String.valueOf(cpuAllocated));
		         
		         String cpuUnits = vdc.find("ComputeCapacity").find("Cpu").find("Units").text();
		         newVdc.setCpuUnits(String.valueOf(cpuUnits));
		         
		         String cpuUsed = vdc.find("ComputeCapacity").find("Cpu").find("Used").text();
		         newVdc.setCpuUsed(String.valueOf(cpuUsed));
		        		              
		         String memoryAllocated = vdc.find("ComputeCapacity").find("Memory").find("Allocated").text();
		         newVdc.setMemoryAllocated(String.valueOf(memoryAllocated));
		         
		         String memoryUnits = vdc.find("ComputeCapacity").find("Memory").find("Units").text();
		         newVdc.setMemoryUnits(String.valueOf(memoryUnits));
		         
		         String memoryUsed = vdc.find("ComputeCapacity").find("Memory").find("Used").text();
		         newVdc.setMemoryUsed(String.valueOf(memoryUsed));
		         
		         
		         newVdc.setDescription(desc);
		         
		    	 vdc.find("ResourceEntity").forEach(resource -> {
			    	 
			            
		             if ($(resource).attr("type").equals("application/vnd.vmware.vcloud.vApp+xml")) {
		            	
		            	  String vname = $(resource).attr("name");           // attribute
				          String vtype = $(resource).attr("type");           // attribute
				          String vhref = $(resource).attr("href");           // attribute
				         //String type = $(org).find("type").text();    // find for child elements
				            
		             	System.out.format("%s : - %s - %s %n",  vname, vtype, vhref);
		             
		             	ReferenceType vappref = new ReferenceType(vname, vhref, vtype);
		             	newVdc.getVappRefs().add(vappref);
		             }
		                 
				        });
		    	 
		    	 // process interesting Links
		    	 
		    	    Match links =  vdc.find("Link"); 
			         
		  		    $(links).forEach(link -> {
		  		    	 	        
            	        // Get Org vdc refs
            	        if(link.getAttribute("type").equals("application/vnd.vmware.hcs.vrRecoveryDetails+xml")) {
            	        	
            	        	// save the reference
                    	    ReferenceType recoveryref = new ReferenceType();
                    	    recoveryref.setName( "recoveryDetails");
                    	    recoveryref.setHref(link.getAttribute("href"));
                    	    recoveryref.setType( link.getAttribute("type"));
                            newVdc.setRecoveryRef(recoveryref);
                            
            	        }
            	        
                        if(link.getAttribute("type").equals("application/vnd.vmware.vcloud.org+xml")) {
            	        	
            	        	// save the reference
                    	    ReferenceType orgRef = new ReferenceType();
                    	    orgRef.setName( "orgRef");
                    	    orgRef.setHref(link.getAttribute("href"));
                    	    orgRef.setType( link.getAttribute("type"));
                            newVdc.setOrgRef(orgRef);
                            
            	        }
            	      
            	        
                  });
             
		     
	         bis.close();
		    	 
        } catch (IOException e) {
        	logger.debug("Error Parsing http response");
        	
			e.printStackTrace();
			throw new DemoException("Error parsing http response ", e);
		} finally {
			try {
				if (bis != null)
					bis.close();
			} catch (IOException ex) {
				logger.error("IOException close InputStream.");
				ex.printStackTrace();
			}
		}
			     
	     return newVdc;
  }
	  
  

  // InputStream enables unit testing with flat files containing XML
  public static List<String> parseReplEnabledVdcs(InputStream istream) throws Exception {
	   
	    List<String> vdcs = new ArrayList<String>(); 
	    BufferedInputStream bis = null;
        try {
				// load into BufferedInputSteam so we can reset and read again
				 bis = new BufferedInputStream(istream);
			
				// Parse response with JOOX
				 
				logger.debug("\nMade Apache http call, parsing result");
					
				 Match refs = $(bis);   // <References>
				      
		    	 refs.find("Reference").forEach(ref -> {
			    		            
		             if ($(ref).attr("type").equals("application/vnd.vmware.vcloud.vdc+xml")) {
		            
				        String href = $(ref).attr("href");           // attribute     
		             	vdcs.add(href);
		             }               
				        });
	     
	         bis.close();
		    	 
        } catch (IOException e) {
        	logger.debug("Error Parsing http response");
        	
			e.printStackTrace();
			throw new DemoException("Error parsing http response ", e);
		} finally {
			try {
				if (bis != null)
					bis.close();
			} catch (IOException ex) {
				logger.error("IOException close InputStream.");
				ex.printStackTrace();
			}
		}
			     
	     return vdcs;
  }
	  
  

  // InputStream enables unit testing with flat files containing XML
  public static void parseVdcRecoveryDetails(InputStream istream, Vdc vdc) throws Exception {
	   
	    BufferedInputStream bis = null;
        try {
				// load into BufferedInputSteam so we can reset and read again
				 bis = new BufferedInputStream(istream);
			
				// Parse response with JOOX
				
				 
				logger.debug("\nMade Apache http call, parsing result");
					
				 Match $rd = $(bis).namespace("ns2", "http://www.vmware.com/vr/v6.0");  // <ns2:RecoveryDetails>
				
				      		       
		         String pme =  $rd.find("IsPlannedMigrationEnabled").text();
		         vdc.setPlannedMigrationEnabled(pme);

		         String foe =  $rd.find("IsFailoverEnabled").text();
		         vdc.setFailoverEnabled(foe);
		         
		         String tfe =  $rd.find("IsTestFailoverEnabled").text();
		         vdc.setTestFailoverEnabled(tfe);
             
	             bis.close();
		    	 
        } catch (IOException e) {
        	logger.debug("Error Parsing http response");
        	
			e.printStackTrace();
			throw new DemoException("Error parsing http response ", e);
		} finally {
			try {
				if (bis != null)
					bis.close();
			} catch (IOException ex) {
				logger.error("IOException close InputStream.");
				ex.printStackTrace();
			}
		}
			     
	     return;
  }
	  
	
  
  // InputStream enables unit testing with flat files containing XML
  public static  Vapp parseVapp(InputStream istream) throws Exception {
  
	  Vapp vapp = new Vapp();

	  BufferedInputStream bis = null;
       try {
			
			// load into BufferedInputSteam so we can reset and read again
			 bis = new BufferedInputStream(istream);
		
			// Parse response with JOOX
			
			 
			logger.debug("\nMade Apache http call, parsing result");
			
			 Match $vapp = $(bis);  // <VApp>
			 
			 String name = $vapp.attr("name");
	    	 vapp.setName(name);
	    	 
	         String href = $vapp.attr("href");
	         vapp.setHref(href);
			
	         String owner = $vapp.attr("owner");
	         vapp.setHref(owner);
	         
	         String vappstatus =  $vapp.attr("status");
	         vappstatus = VMStatus.fromValue(Integer.parseInt(vappstatus)).toString();
			 vapp.setStatus(vappstatus);
	         
	         
			 ReferenceType vmref = new ReferenceType();
				
			 // Get VM hrefs
			 $vapp.find("Vm").forEach(vm -> {
	    	    String vmname = $(vm).attr("name");           // attribute
	    	    vmref.setName(vmname);
	            String vmhref = $(vm).attr("href");           // attribute
	            vmref.setHref(vmhref);
		            
		        vapp.getVms().add(vmref);
	              
		        });
				
			 
       } catch (IOException e) {
       	logger.debug("Error Parsing http response");
       	
			e.printStackTrace();
			throw new DemoException("Error parsing http response ", e);
		} finally {
			try {
				if (bis != null)
					bis.close();
			} catch (IOException ex) {
				logger.error("IOException close InputStream.");
				ex.printStackTrace();
			}
		}
	     
	     return vapp;
		 
  }
  
  

  // InputStream enables unit testing with flat files containing XML
  public static  ArrayList<ReferenceType> parseVM(InputStream istream) throws Exception {
  
	  ArrayList<ReferenceType>  vmlist = new ArrayList<ReferenceType>() ;
	  BufferedInputStream bis = null;
       try {
			
			// load into BufferedInputSteam so we can reset and read again
			 bis = new BufferedInputStream(istream);
		
			// Parse response with JOOX
			
			 
			logger.debug("\nMade Apache http call, parsing result");
				
			
			
			
			 Match $vapp = $(bis);  // <VApp>
			
			 ReferenceType vmref = new ReferenceType();
				
				 // Get Some Vapp Info used in the VMInfo
	//		    String vappOwner = $vapp.find("Owner").find("User").attr("name");
		//		vminfo.setOwner(String.valueOf(vappOwner));	
			    
		//		String vappName = $vapp.attr("name");
		//		vminfo.setvApp(String.valueOf(vappName));
			
			
				 // Get VM Info
				$vapp.find("Vm").forEach(vm -> {
		    	   String name = $(vm).attr("name");           // attribute
		    	   vmref.setName(name);
		            String href = $(vm).attr("href");           // attribute
		            vmref.setHref(href);
		            
		         //   String type = $(org).find("type").text();    // find for child elements
		           
	          if ($(vm).attr("type").equals("application/vnd.vmware.vcloud.vm+xml")) {
	          	System.out.format("%s : - - %s %n",  name, href);
	          	
	          	vmlist.add(vmref);
	          }
	              
		        });
       } catch (IOException e) {
       	logger.debug("Error Parsing http response");
       	
			e.printStackTrace();
			throw new DemoException("Error parsing http response ", e);
		} finally {
			try {
				if (bis != null)
					bis.close();
			} catch (IOException ex) {
				logger.error("IOException close InputStream.");
				ex.printStackTrace();
			}
		}
	     
	     return vmlist;
		 
  }
  
  public static ArrayList<String> parseReplicationGroupRefs(InputStream istream) throws Exception {
	  

	    ArrayList<String>  rgRefList = new ArrayList<String>() ;
	    BufferedInputStream bis = null;
	    
        try { 
				// load into BufferedInputSteam so we can reset and read again
				 bis = new BufferedInputStream(istream);
			
				// Parse response with JOOX
				
				logger.debug("\n parseReplicationGroupRefs");
				
			
					
				 Match $refs = $(bis);  // <References>
				
						
				 // Get Refs Info
			     $refs.find("Reference").forEach(ref -> {
		            String href = $(ref).attr("href");           // attribute
		           	logger.debug("Replication group href: " + href);
		         	rgRefList.add(href);
		          
		        });
			  
          
			     
			     
        } catch (IOException e) {
           	logger.debug("Error Parsing http response");
           	
    			e.printStackTrace();
    			throw new DemoException("Error parsing http response ", e);
    		} finally {
    			try {
    				if (bis != null)
    					bis.close();
    			} catch (IOException ex) {
    				logger.error("IOException close InputStream.");
    				ex.printStackTrace();
    			}
    		}
		
	     
	     return rgRefList;
		 
	  
  }
  

  public static ReplicationGroup parseReplicationGroup(InputStream istream) throws Exception {
	  

	    ReplicationGroup  repGroup = new ReplicationGroup();
	  
	    BufferedInputStream bis = null;
	    try {
	
			// load into BufferedInputSteam so we can reset and read again
			 bis = new BufferedInputStream(istream);
		
			// Parse response with JOOX
			
			logger.debug("\nMade Apache http call, parsing result");
						
			 Match $rg = $(bis).namespace("ns2", "http://www.vmware.com/vr/v6.0");  // <ns2:ReplicationGroup>			
				
			 String href = $rg.attr("href");           // attribute
			 repGroup.setHref(href);
			 
			 String name = $rg.attr("name");           // attribute
			 repGroup.setName(String.valueOf(name));  // valueOf guard against null
			 
			 // Get Refs Info
		     repGroup.setRpo(String.valueOf($rg.find("Rpo").text()));
		     repGroup.setQuiesceGuestEnabled(String.valueOf($rg.find("QuiesceGuestEnabled").text()));
		     repGroup.setNetworkCompressionEnabled(String.valueOf($rg.find("NetworkCompressionEnabled").text()));
		     repGroup.setPlaceholderVappId(String.valueOf($rg.find("PlaceholderVappId").text()));
		     repGroup.setEventPartitionId(String.valueOf($rg.find("EventPartitionId").text()));
		     repGroup.setReplicationState(String.valueOf($rg.find("ReplicationState").text()));
		     if(repGroup.getReplicationState().equals("null")) repGroup.setReplicationState("not applicable");
		     repGroup.setVrServerInfo(String.valueOf($rg.find("VrServerInfo").find("Uuid").text()));
		     repGroup.setPaused(String.valueOf($rg.find("Paused").text()));
		     repGroup.setCurrentRpoViolation(String.valueOf($rg.find("CurrentRpoViolation").text()));
		     repGroup.setTestRecoveryState(String.valueOf($rg.find("TestRecoveryState").text()));
		     if(repGroup.getTestRecoveryState().equals("null")) repGroup.setTestRecoveryState("not applicable");
		     repGroup.setRecoveryState(String.valueOf($rg.find("RecoveryState").text()));
		     if(repGroup.getRecoveryState().equals("null")) repGroup.setRecoveryState("not applicable");
		     repGroup.setRecoveryCompletionTime(String.valueOf($rg.find("RecoveryCompletionTime").text()));
		     repGroup.setTransferStartTime(String.valueOf($rg.find("ReplicationGroupInstance").find("TransferStartTime").text()));
		     repGroup.setTransferSeconds(String.valueOf($rg.find("ReplicationGroupInstance").find("TransferSeconds").text()));
		     repGroup.setTransferBytes(String.valueOf($rg.find("ReplicationGroupInstance").find("TransferBytes").text()));
		     
		  	 // process Links of interest 
	    	 
	    	 Match links =  $rg.find("Link"); 
		         
	  		 $(links).forEach(link -> {
	  		    	 	        
	     	     // Get failover operation 
	  			 // Check rel vs type since type not unique in response
	     	     if(link.getAttribute("rel").equals("operation:failover")) {
	     	        	
	     	        	 ReferenceType ref = new ReferenceType();
	     	        	 ref.setName(link.getAttribute("rel"));
	                     ref.setHref(link.getAttribute("href"));
	                     ref.setType( link.getAttribute("type"));
	                     repGroup.setFailoverOpRef(ref);    
	     	     }
	     	     
		 	        
	     	     // Get test failover operation 
	     	     if(link.getAttribute("type").equals("application/vnd.vmware.hcs.testFailoverParams+xml")) {
	     	        	
	     	        	 ReferenceType ref = new ReferenceType();
	     	        	 ref.setName(link.getAttribute("rel"));
	                     ref.setHref(link.getAttribute("href"));
	                     ref.setType( link.getAttribute("type"));
	                     repGroup.setTestFailoverOpRef(ref);    
	     	     }
	     	     
	     	     // Get test remove operation 
	     	     if(link.getAttribute("type").equals("application/vnd.vmware.hcs.replicationGroup+xml")) {
	     	        	
	     	        	 ReferenceType ref = new ReferenceType();
	     	        	 ref.setName(link.getAttribute("rel"));
	                     ref.setHref(link.getAttribute("href"));
	                     ref.setType( link.getAttribute("type"));
	                     repGroup.setRemoveOpRef(ref);    
	     	     }
	     	     
	     	     // Get planned migration operation
	     	     // Check rel vs type since type not unique in response
	     	    if(link.getAttribute("rel").equals("operation:plannedMigration")) {
     	        	
    	        	    ReferenceType ref = new ReferenceType();
    	                ref.setName(link.getAttribute("rel"));
                        ref.setHref(link.getAttribute("href"));
                        ref.setType( link.getAttribute("type"));
                        repGroup.setPlannedMigrationOpRef(ref);    
    	     }
     	        
             });
	  		 
	  		 
		     
	    } catch (IOException e) {
           	logger.debug("Error Parsing http response");
           	
    			e.printStackTrace();
    			throw new DemoException("Error parsing http response ", e);
    		} finally {
    			try {
    				if (bis != null)
    					bis.close();
    			} catch (IOException ex) {
    				logger.error("IOException close InputStream.");
    				ex.printStackTrace();
    			}
    		}
		
	     
	     return repGroup;
		 
	  
  }
  
  // InputStream enables unit testing with flat files containing XML
  public static VMInfo parseVMInfo(InputStream istream) throws Exception {
  	
	  VMInfo vminfo = new VMInfo();
	  BufferedInputStream bis = null;
	   try {
	    
				// load into BufferedInputSteam so we can reset and read again
				 bis = new BufferedInputStream(istream);
				
				// Parse response with JOOX
				
				logger.debug("\nMade Apache http call, parsing result");
					
				 Match $vm = $(bis);  // <VM>
				 
				 String vmhref =  $vm.attr("href");
				 System.out.println("vmhref " + vmhref);
				 vminfo.setHref(vmhref);
				 
				 String vmstatus =  $vm.attr("status");
				 vmstatus = VMStatus.fromValue(Integer.parseInt(vmstatus)).toString();
				 
				 System.out.println("status " + vmstatus);
				 vminfo.setStatus(vmstatus);
				 
				 
				  // Get links if any 
		         Match taskLink =  $vm.find("Link");   // npe if next two lines and no task link
 		                            //   .filter(ctx -> $(ctx).attr("type")
 		                             //  .equals("application/vnd.vmware.vcloud.tasksList+xml"));
		         
                 $(taskLink).forEach(link -> {
                	        // task link
                	        if(link.getAttribute("type").equals("application/vnd.vmware.vcloud.tasksList+xml")) {
	                    	    ReferenceType vdcref = new ReferenceType();
	                            vdcref.setHref(link.getAttribute("href"));
	                            vdcref.setType( link.getAttribute("type"));
	                            vminfo.setTaskLinkRef(vdcref);
                	        }
                	        
                	        // powerOn link
                	        if(link.getAttribute("rel").equals("power:powerOn")) {
                	        	  vminfo.setPowerOnHref(link.getAttribute("href"));
                	        }
                	        
                	        // powerOff link
                	        if(link.getAttribute("rel").equals("power:powerOff")) {
                	        	  vminfo.setPowerOffHref(link.getAttribute("href"));
                	        }
                	        
                	        // power:suspend
                	        if(link.getAttribute("rel").equals("power:suspend")) {
              	        	  vminfo.setSuspendHref(link.getAttribute("href"));
              	        }
                       
                      });
                 
                
		         

				
				 Match $osSection =  $vm.namespace("ovf", "http://schemas.dmtf.org/ovf/envelope/1")
						               .namespace("vmw", "http://www.vmware.com/schema/ovf")
						        
						               .xpath("//ovf:OperatingSystemSection/ovf:Description");
				 
			
				 
				 System.out.println("OS Type is " + $osSection.text());
				 vminfo.setOs($osSection.text());
				 
				//a[starts-with(@href, "buy.php/")]
						 
				 Match $items =  $vm.namespace("ovf", "http://schemas.dmtf.org/ovf/envelope/1")
				     .namespace("vcloud", "http://www.vmware.com/vcloud/v1.5")
			//	     .xpath("//ovf:Item[@vcloud:href='https://10.134.3.58/api/vApp/vm-6a7712f7-2a9c-46a9-b905-5aa533c9d31b/virtualHardwareSection/cpu']").forEach(vm-> {
				     .xpath("//ovf:Item");
				 
				 String vmname = $vm.attr("name");           // attribute
				 System.out.println("VM Name " +  vmname + " # Items " + $vm.children().size());
				 vminfo.setName(vmname);
				 
			//	 $items.attr("https://10.134.3.58/api/vApp/vm-6a7712f7-2a9c-46a9-b905-5aa533c9d31b/virtualHardwareSection/cpu")
			
				 
				 $items.forEach(item-> {
				
				     
			//	 $vm.namespace("ovf", "http://schemas.dmtf.org/ovf/envelope/1");
		
			//	 $vm.namespace("ovf", "http://schemas.dmtf.org/ovf/envelope/1").find("ovf:Item").forEach(vm -> {
			//		  System.out.println($(vm.getLocalName()));
			    	   String name = $(item).attr("name");           // attribute
			            String type = $(item).attr("type");           // attribute
			            String href = $(item).attr("href");           // attribute
			         
			           
		      
		    //      System.out.println("name " + name + " href " + href);
		          
		          // "//input[@id[ends-with(.,'register')]]"
		   //  System.out.println( "See element" +      $vm.namespace("rasd", "http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_ResourceAllocationSettingData")
		      //                                            .xpath("//rasd:ElementName").text());  
		  
		       
			          // cpus
			            
		              if (href != null && href.endsWith("/cpu")) {  
		       //     	  System.out.println("Item = " + $item.text());
		          	  String cpuCount = $(item).namespace("rasd", "http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_ResourceAllocationSettingData")
		          			                 .xpath("rasd:InstanceID").text();  
		          	  System.out.println("Cpu Count " + String.valueOf(cpuCount));
		              vminfo.setCpu(String.valueOf(cpuCount));
		              }
		              
		              // memory
		              if (href != null && href.endsWith("/memory")) {  
		                  //     	  System.out.println("Item = " + $vm.text());
		                     	  String memory = $(item).namespace("rasd", "http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_ResourceAllocationSettingData")
		                     			                 .xpath("rasd:VirtualQuantity").text();  
		                     	  System.out.println("Memory " + String.valueOf(memory));
		                     	  vminfo.setMemory(String.valueOf(memory));
		              }
		          
		       });
		 
	   } catch (IOException e) {
          	logger.debug("Error Parsing http response");
          	
   			e.printStackTrace();
   			throw new DemoException("Error parsing http response ", e);
   		} finally {
   			try {
   				if (bis != null)
   					bis.close();
   			} catch (IOException ex) {
   				logger.error("IOException close InputStream.");
   				ex.printStackTrace();
   			}
   		}
		
       	
    
		    
  	   return vminfo;
  }
  

  public static Task parseTask(InputStream istream) throws Exception {
	  

	    ArrayList<String>  rgRefList = new ArrayList<String>() ;
	    BufferedInputStream bis = null;
	    
	    Task task = new Task();
	    
        try { 
				// load into BufferedInputSteam so we can reset and read again
				 bis = new BufferedInputStream(istream);
			
				// Parse response with JOOX
				
				logger.debug("\n parseTask called");
				
			
					
				Match $tsk = $(bis);  // <Task>
				 
				task.setName($tsk.attr("name"));
				task.setOperation($tsk.attr("operation"));
				task.setOperationName($tsk.attr("operationName"));
                task.setStartTime($tsk.attr("startTime"));
                task.setExpiryTime($tsk.attr("expiryTime"));
                task.setStatus($tsk.attr("status"));
                task.setHref($tsk.attr("href"));
				
						
				 // Get Owner Info
			     $tsk.find("Owner").forEach(owner-> {
		            String href = $(owner).attr("href");           
		           	String name = $(owner).attr("name");
                    task.setOwnerHref(href);
                    task.setOwnerName(name);  
		        });
			     
					
				 // Get User Info
			     $tsk.find("User").forEach(user-> {
		            String href = $(user).attr("href");           
		           	String name = $(user).attr("name");
                    task.setUserHref(href);
                    task.setUserName(name);  
		        });
			     
					
				 // Get Org Info
			     $tsk.find("Organization").forEach(org-> {
		            String href = $(org).attr("href");           
		           	String name = $(org).attr("name");
                    task.setOrgHref(href);
                    task.setOrgName(name);  
		        });
			     
			     
			     
        } catch (IOException e) {
           	logger.debug("Error Parsing http response");
           	
    			e.printStackTrace();
    			throw new DemoException("Error parsing http response ", e);
    		} finally {
    			try {
    				if (bis != null)
    					bis.close();
    			} catch (IOException ex) {
    				logger.error("IOException close InputStream.");
    				ex.printStackTrace();
    			}
    		}
		
	     
	     return task;
		 
	  
  }
  
}
