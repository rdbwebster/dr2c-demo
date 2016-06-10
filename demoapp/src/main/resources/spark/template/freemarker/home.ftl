<!DOCTYPE html>
<html>
  <head>
    <title>DR2C Demo</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

 <!--    <link href="./css/admin.css" rel="stylesheet"> --> 
  <!--  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>  -->   
      
  <!--     <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">  -->   
     <script src="//code.jquery.com/jquery-1.10.2.js"></script>
     <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>  
 
    <link href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">   
   <!-- <script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script> --> 
  
   
    <!--  page layout  -->
 	<link rel="stylesheet" type="text/css" href="./css/style.css" />
 	
 	<!--  tabs -->
 	   <link  rel="stylesheet" href="./css/tabs.css"> 
 	   
 	 <!- tab menu -->
 	   	   <link  rel="stylesheet" href="./css/tabmenu.css">
 	   	   
 <script src="./js/tabbedpage.js"></script>
 	   
   <!-- menu -->
   <link  rel="stylesheet" href="./css/menu.css">
   <script src="./js/menu.js"></script>
   
 <!--   <script src="./js/websocket.js"> </script> --> 


   
   <!--  colResizable -->
     <link rel="stylesheet" type="text/css" href="./css/col_resizable.css" />  
  <!-- <script  src="./js/jquery.js"></script> -->
  
     <script src="./js/blockui.js"></script> <!--  jquery extension -->
  <script  src="./js/colResizable-1.6.min.js"></script>
  



  <script type="text/javascript">
	$(function(){	

	
		$("#normal").colResizable({
			liveDrag:true, 
			gripInnerHtml:"<div class='grip'></div>", 
			draggingClass:"dragging", 
            resizeMode:'fit'
        });
        
        $("#flex").colResizable({
            liveDrag:true, 
            gripInnerHtml:"<div class='grip'></div>", 
            draggingClass:"dragging", 
            resizeMode:'flex'
        });


      $("#overflow").colResizable({
          liveDrag:true, 
          gripInnerHtml:"<div class='grip'></div>", 
          draggingClass:"dragging", 
          resizeMode:'overflow'
      });
        

      $("#disabled").colResizable({
          liveDrag:true, 
          gripInnerHtml:"<div class='grip'></div>", 
          draggingClass:"dragging", 
          resizeMode:'overflow',
          disabledColumns: [2]
      });

        
    });	
  </script>
   
  </head>
  
  
  <body>
  
  <#include "leftnav.ftl">

 <img src="./img/vmware_small_crop.png" alt="VMware" > 
 
        <ul class="menu-logout">
           
	       
		
		    <li id="username" title="bob webster">${username}</li>
		        <li class="options" ><a href="/logout"> Logout</a></li>
        </ul>
	
<div id="container">
<div id="header">
    
      <textarea id="messages" rows="1" cols="100" readonly style="margin-left:100px; border: none; overflow:hidden;  resize: none;" > ${messages} </textarea> 

  
</div>

<!-- 	<hr>   -->
<div id="greyarea">

<div id="content">

      <@leftnav/>

		
		<div id="main-content">
		 
			  <#if navBean.vdcEntrySelected > 
			  
				  
			  
			           <#if navBean.selectedVdcName != "" >   
			       
									<ul class="tabs">
									
									  <#include "tab0.ftl">
									  
								   </ul>
								   	<ul class="tabs">
									  <#include "tab1.ftl">
   				      
						           </ul>
			    	    <#else>
	
	                               <ul class="tabs">
											    		  	
									  <#include "tab2.ftl">
									   				      
						           </ul>
	
									<ul class="tabs">
											  	
									  <#include "tab3.ftl">
									   				      
						           </ul>
			             </#if>		
			           
		             
		    </#if>		
		
		
</div> <!--  content  -->

</div> 

</div>
    
    <!--  
  <script>
  $(function() {
    $( document ).tooltip();
  });
  </script>
  -->
  
  </body>
</html>