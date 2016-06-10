<#macro leftnav>

	<div id="sidebar">
		
			<div style="text-align: center;">
			
			  <#if !navBean.isOrgSelected() >
				<span  class="navtitle">${navBean.navTitle}</span>
			 <#else>

					<a style="font-size: 17px;"  href="/deselectvdc"> <span class="x-tool-collapse-left" style="padding-left: 25px;"></a>
					<span class="navtitle"> ${navBean.navTitle}</span>
				
				</span>
		    
		      </#if>
		      
		      
			</div>
			
				<div id='cssmenu'>

					<ul>
					  <!--  Display Org List -->
					  <#if !navBean.isOrgSelected() >
					<!--       <li class='active'><a href='#'><span>${navBean.navTitle}</span></a></li> --> 
						  <#list navBean.orgList as org> 
							   <li class='last' title="REST CALL: getOrganization ${org.href}"><a href='/selectorg?name=${org.name}'><span>${org.name}</span></a></li>
						  </#list> 
								   
					  <#else>
					  <!--  Display Vdc List -->
					
					    <!--       <li class="active"><a href="/deselectorg"><span class="glyphicon glyphicon-chevron-left"> ${navBean.navTitle}</a></li> -->
					           <!--  first menu item is fixed entry for 'All - replications' -->
					           
					           <li class='last' title="REST CALL: getOrganizationReplicationGroups ${navBean.selectedOrg.replHref}"><a href='/selectallvdc'><span>${navBean.allVdcTitle}</span></a></li>
					        
					           <#list navBean.orgVdcList as vdc> 
					                <#if vdc.name == navBean.selectedVdcName >
					                      
					                       <li class='last navselected' title="REST CALL: getVdc ${vdc.href}"><a href='/selectvdc?name=${vdc.name}'><span>${vdc.name}</span></a></li>
					                <#else>
					                       <li class='last' title="REST CALL: getVdc ${vdc.href}"><a href="/selectvdc?name=${vdc.name}"><span>${vdc.name}</span></a></li>
					                </#if>
					           </#list> 
					   		
					   </#if>        
					
					</ul>
				</div>
		</div>

</#macro>