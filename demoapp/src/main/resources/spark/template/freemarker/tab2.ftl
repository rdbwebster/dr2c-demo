<!--  NOT USED -->
 <li>
			        <input type="radio" name="tabs" id="tab2" checked />
			        
			        <label for="tab2">Virtual Machines</label>
			        
			        <div id="tab-content2" class="tab-content">
			            
			      <div class="tabmenudiv">
			      <div class="tabmenu">
			        <ul>
			        
			          
			            <li  class="v-icon-action-power-on">
			       <!--                <a id="poweronbutton" href="poweron">Power On</a> --> 
			                      <button id = "poweron" >Test</button>
			                
			            </li>
			            
			               <li class="v-icon-action-power-off">
			               <!--          <a id="poweroffbutton"  href="#">Power Off</a> -->
			                        <button id = "poweroff" >Cleanup</button>
			                
			            </li>
			            
			            <li class="v-icon-action-suspend">
			               <!--          <a id="suspendbutton"  href="#">Suspend</a> -->
			                        <button id = "suspend" >Recovery</button>
			                
			            </li>
			            
			           
			            
			            
			            
			           
			            <!-- 
			             <li><a href="#">Actions</a>
			             <ul>
			                    <li><a href="#">Action1</a>
			                    </li>
			                    <li><a href="#">Action2</a>
			         
			
			                </ul>
			         
			             </li>
			             -->
			      
			        </ul>
			    
			    </div>
			</div>
			
			 <#if vmlist?? >
							   
			        
				      <table id="normal" >
						        <col style="width:2%">
						        <col style="width:5%">
						        <col style="width:30%">
						       
							    <thead>
							      <tr>
							        <th></th>
							        <th>Status</th>
							        <th>Name</th>
							        <th>Owner</th>
							        <th>CPU</th>
							        <th>Memory</th>
							        <th>OS</th>
							        <th>vApp</th>
							            
							      </tr>
							    </thead>
							    
							   
								 
							             <tbody>

									     <#list vmlist as vmbean> 
									      <tr id="${vmbean.href}"  title="REST CALL: getVM ${vmbean.href}" tabindex="${vmbean?index}" isselected="false">
									        <td>
									        <span class="checkbox unchecked"></span></td>
									        <td>
									          <div style="text-align:center;">
									            <#if vmbean.status == "SUSPENDED" >
			                                      <span style="padding-left:16px;" class="v-icon-status-SUSPENDED"></span>
			                                   
			                                    <#elseif vmbean.status == "POWERED_ON" >
			                                      <span style="padding-left:16px;" class="v-icon-status-POWERED_ON"></span>
			                                    
			                                    <#elseif vmbean.status == "POWERED_OFF" >
			                                      <span style="padding-left:16px;" class="v-icon-status-POWERED_OFF"></span>
			                                  
			                                    <#elseif vmbean.status == "FAILED_CREATION" >
			                                      <span style="padding-left:16px;" class="v-icon-status-FAILED_CREATION"></span>
			                            
			                                    <#elseif vmbean.status == "UNRESOLVED" >
			                                      <span style="padding-left:16px;" class="v-icon-status-UNRESOLVED"></span>
			                             
			                                    <#elseif vmbean.status == "EXPIRED" >
			                                      <span style="padding-left:16px;" class="v-icon-status-EXPIRED"></span>
			                                                                      
			                                 
			                                    </#if>
			                                    <span style="display:none;">${vmbean.status}</span>
			                                    </div>
			                                  
		                                    </td>
									     
									        <td>${vmbean.name}</td>
									        <td>${vmbean.owner}</td>
									        <td>${vmbean.cpu}</td>
									        <td>${vmbean.memory}</td>
									        <td>${vmbean.os}</td>
									        <td>${vmbean.vApp}</td>
									           
									      </tr>
									     </#list>
								
							
							    </tbody>
					       </table> 
					        <#else>
					         <div><h4>No Virtual Machines</h4></div>
								
					   	</#if>
				   </div>    
			    </li>
			    
	  <!-- tabbed page handlers 
      <script src="./js/tabbedpage.js"></script>
 -->