
 <li>
			        <input type="radio" name="tabs" id="tab0" checked />
			        
			        <label for="tab0">Virtual DataCenter</label>
			        
			        <div id="tab-content0" class="tab-content">
			
			        

			
		            <h2> ${navBean.selectedVdc.name}</h2>
		   
		             <p class="spacer">&nbsp;</p>
		             
				     <table >
						       
  
							         <thead>
							         </thead>
							         
						             <tbody>
                                           <tr></tr>
								        <tr>
										        <td style="border: none;">
										           <h3>CPU</h3>
			                                    </td>
	                                    </tr>
	                                    <tr>
			                                    <td style="border: none;">
			                                     
			                                      <div id="progressbarcpu"></div>
			                                      
			                                    </td>
	                                    </tr>
	                                    <tr></tr>
	                                    
	                                    <tr>
			                                     <td style="border: none;">
										           <h3>Memory</h3>
			                                    </td>
	                                    </tr>
	                                     <tr>
			                                    <td style="border: none;">
			                                     
			                                      <div id="progressbarmem"></div>
			                                      
			                                    </td>
	                                    </tr>
	                                      <tr></tr>
	                                  

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