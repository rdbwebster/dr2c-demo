
 <li>
			        <input type="radio" name="tabs" id="tab0" checked />
			        
			        <label for="tab0">Virtual DataCenter</label>
			        
			        <div id="tab-content0" class="tab-content">
			
			        

			
		            <h2> ${navBean.selectedVdc.name}</h2>
		   
		             <p class="spacer">&nbsp;</p>
		             
				    <table class="simple">
						       <col style="width:20%">
						        <col style="width:10%">
						        <col style="width:30%">
						          <col style="width:30%">
  
							         <thead>
							         <tr>
							             <th><h2>Capacity</h2></th>
							             <th></th>
							             <th><h2>Recovery Details</h2></th>
							             </tr>
							         </thead>
							         
						             <tbody>
                                           <tr></tr>
                                            <tr></tr>
								        <tr>
										        <td>
										           <h3>CPU</h3>
			                                    </td>
			                                    <td></td>
			                                    <td><span class="title">Planned Migration Enabled</span>
	                                            <span>${navBean.selectedVdc.plannedMigrationEnabled}</span>
	                                            </td>
			                                  
	                                    </tr>
	                                    <tr>
			                                    <td>
			                                     
			                                      <div id="progressbarcpu"></div>

			                                    </td>
			                                    <td>
			                                    </td>
			                                     <td><span class="title">Fail Over Enabled</span>  
	                                             <span>${navBean.selectedVdc.failoverEnabled}</span>
	                                             </td>
	                                    </tr>
	                                    <tr></tr>
	                                    
	                                    <tr>
			                                     <td>
										           <h3>Memory</h3>
			                                    </td>
			                                    <td></td>
			                                         <td><span class="title">Test Fail Over Enabled</span> 
			                                         <span>${navBean.selectedVdc.testFailoverEnabled}</span>
			                                         </td>
	                                   
	                                    </tr>
	                                     <tr>
			                                    <td>
			                                     
			                                      <div id="progressbarmem"></div>
			                                      
			                                    </td>
			                                   
			                            <tr>
			                               <td>  <p class="spacer">&nbsp;</p> </td>
	                                    </tr>
	                            
	                                    
	                                 	
	                            
	                                               
	                                              
	                                       
	                                               
	                                  

							    </tbody>
					       </table> 
					       
					       

					  
				   </div>    
			    </li>
			    
	  <!-- tabbed page handlers 
      <script src="./js/tabbedpage.js"></script>
 -->
 
   
 <script>
  $(function() {
    $( "#progressbarcpu" ).progressbar({
      value: 37,
      max:100
    }); 
  });
  </script>
  
   <script>
  $(function() {
    $( "#progressbarmem" ).progressbar({
      value: 10,
      max: 100
    });
  });
  </script>