
 <li>
			        <input type="radio" name="tabs" id="tab3" checked />
			        
			        <label for="tab3">Protected Virtual Machines</label>
			        
			        <div id="tab-content3" class="tab-content">
			            
			      <div class="tabmenudiv">
			      <div class="tabmenu">
			        <ul>
			        
			          
			                 <li  class="v-icon-action-power-on">
			     
			                      <button id = "Test" >Test</button>
			                
			            </li>
			            
			               <li class="v-icon-action-power-off">
			          
			                        <button id = "Cleanup" >Cleanup</button>
			                
			            </li>
			            
			            <li class="v-icon-action-suspend">
			           
			                        <button id = "Recover" >Recover</button>
			                
			            </li>
			            
			            <li  class="v-icon-action-power-on">
			   
			                      <button id = "Remove" >Remove Replication</button>
			                
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
			
			         <#if (rgroups?size == 0) >
							     <div><h4>No Replication Groups</h4></div>
					<#else>
			        
				      <table id="normal" >
						        <col style="width:2%">
						        <col style="width:20%">
						        <col style="width:15%">
						           <col style="width:15%">
						              <col style="width:15%">
						       
							    <thead>
							      <tr>
							        <th></th>
							        <th>Name</th>
							        <th>RPO</th>
							        <th>Replication State</th>
							        <th>Recovery State</th>
							        <th>Replication Paused</th>
							        <th>Test Recovery State</th>
							      
							            
							      </tr>
							    </thead>
							    <tbody>
							   
								     <#list rgroups as replbean> 
								      <tr id="${replbean.href}"  title="REST CALL: getReplicationGroup ${replbean.href}" tabindex="${replbean?index}" isselected="false">
								        <td><span class="checkbox unchecked"></span></td>
								        <td>${replbean.name}</td>
								        <td>${replbean.rpo}</td>
								        <td>${replbean.replicationState}</td>
								        <td>${replbean.recoveryState}</td>
								        <td>${replbean.paused}</td>
								        <td>${replbean.testRecoveryState}</td>
								      </tr>
								     </#list>
					   </#if>
							    </tbody>
					   </table> 
				   </div>    
			    </li>
			    
	  <!-- tabbed page handlers 
      <script src="./js/tabbedpage.js"></script>
 -->