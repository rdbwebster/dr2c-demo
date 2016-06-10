var selectedTableRowName = "";

var ajaxResponses = []

$(document).ready(function(){                     
    $(function(){
       $("#poweron").click(function(e){
    	   var postdata = getCheckedRows();
            
    	//   registerCallbackSession();
    	   
    	   makeAjaxCall("poweron", postdata);
    	   
    		   
    	   clearCheckedRows();
    	   
    	   updateHeaderMessages("getmessages");
    
        });
      });
    });

$(document).ready(function(){                     
    $(function(){
       $("#poweroff").click(function(e){
    	 
    	   var postdata = getCheckedRows();
    	   
    	//   registerCallbackSession();
   		
    	   makeAjaxCall("poweroff", postdata);
    	   
    	   
    	   clearCheckedRows();
    	   
    	   updateHeaderMessages("getmessages");
        });
      });
    });

$(document).ready(function(){                     
    $(function(){
       $("#suspend").click(function(e){
    	
    	   var postdata = getCheckedRows();
    	
    //	   registerCallbackSession();
    	   
    	   makeAjaxCall("suspend", postdata);
    	   
    	   
    	   clearCheckedRows();
    	   
    	   updateHeaderMessages("getmessages");
      });
    });
  });



$(document).ready(function(){                     
    $(function(){
       $("#resume").click(function(e){
    	
    	   var postdata = getCheckedRows();
    	
    	   
    //	   registerCallbackSession();
    	   
    	   makeAjaxCall("resume", postdata);
    	//   sendMessage(postdata);
    	   
     	   
    	   // change to in progress icon
    	//   .v-icon-status-UNRESOLVED
    	   
    	   
    	   clearCheckedRows();
    	   
    	   updateHeaderMessages("getmessages");
    	 
      });
    });
  });


$(document).ready(function(){                     
    $(function(){
      
    	  //  $("table#normal tr td.checkbox").click( function() {
    		   
    	  $("span.checkbox").click( function() {	   	    		
    		  console.log( "clicked on checkbox" );  
	    		  if ( $(this).hasClass("unchecked")) {	  
	    			  $(this).removeClass("unchecked");
	    		      $(this).addClass("checked");
	         		  $(this).parent().parent().attr("isselected", "true");
	         		
	    		  }
	    		  else {
	    			 
	    			  $(this).addClass("unchecked");
	    		      $(this).removeClass("checked");
	    			  $(this).parent().parent().attr("isselected", "false");
	    		  }
	     	      
// .prop("tagName");
	    		  
		    	  
		    	  setLegalMenuOptions();

    	   });

    });
});


function clearCheckedRows() {
	
	
	  $("span.checked").each(
			  function(index, element){
				  $(this).removeClass("checked");
			      $(this).addClass("unchecked");
	     		  $(this).parent().parent().attr("isselected", "false");
			  })
}


function updateVMStatus(id, state) {
	
	    var newState=""
		   
		// create new span for example <span class='v-icon-status-POWERED_ON' style='padding-left:16px;'>
	 	      
	    var sp1 = document.createElement("span");
	    var sp1_content = document.createTextNode(newState);
	    sp1.appendChild(sp1_content);
	    sp1.setAttribute("style", "padding-left:16px;");
	    
	    if(state == "POWERED_ON") { sp1.setAttribute("class", "v-icon-status-POWERED_ON"); }
	      else if(state == "POWERED_OFF") { sp1.setAttribute("class", "v-icon-status-POWERED_OFF"); }
	    	  else if(state == "SUSPENDED") {sp1.setAttribute("class", "v-icon-status-SUSPENDED"); } 
	    	     else if(state == "UNRESOLVED") {sp1.setAttribute("class", "v-icon-status-UNRESOLVED"); } 
	     
	     var oldChild = document.getElementById(id).children[1].firstElementChild.firstElementChild  // <span>
	     document.getElementById(id).children[1].firstElementChild.replaceChild(sp1, oldChild);
  
	     
	     // Note jQuery was not able to match id names such as urn:vcloud:vm:6a7712f7-2a9c-46a9-b905-5aa533c9d31b
	     // or href names like "https://10.134.3.58/api/vApp/vm-d8e3ffd4-8769-44b0-ad5a-3fde5236b1b5"
	     // Verified in Mozilla javascript console
	     // Had to do javascript impl above
	     
	 	 //	  alert( str = JSON.stringify($("#"+id),null, 4 ))
	     //  var bla = $("#"+id);
	     //	 jQuery(bla).find("td:eq(1) > div > span:eq(0)").replaceWith(newState);
         //  attempted most concise form below
	     //   $('tr["#"+href] > td:eq(1) > div > span:eq(0)').replaceWith(newState);		   

}

function getCheckedRows() {
	
	
	   // An action has been request (suspend, resume etc) change icon of marked rows to spinning wheel.
	   var retval = []
	   $("tr[isselected='true']").each(  
			   function(index, element){
				   retval.push($(this).attr('id'))
				   
				   // get status column and update icon
				   jQuery(this).find("td:eq(1) > div > span:eq(0)").replaceWith("<span class='v-icon-status-UNRESOLVED' style='padding-left:16px;'> </span");
				 
	   })
	   
	   alert("see " + retval.length + " marked rows, first row " + retval[0]);
	   
	
	   $.each( retval, function( key, value ) {
		   console.log( value );  
		});
   
	   
	   // build json array with data
	   
	   var postdata="[ ";  // init json array
	   
	   postdata.concat("{ name: \"username \", value: \"" + getUsername() + "\"}");
	   
	   $.each( retval, function( key, value ) {
		    console.log("concat");
		   
		       
		       if (key == retval.length-1) {
		    	   postdata = postdata.concat("{ name: \"p" , key , "\", value: \"" + value +"\"}");  // last entry
		       } else
		       {
		    	   postdata = postdata.concat("{ name: \"p" , key , "\", value: \"" + value +"\"},");;  
		       }

	    			   // entries like this  { name: "first", value: "Rick" },
			   		
	   });
	   postdata = postdata.concat(" ];");
	   
	//   $.each(parmArray, function(i,val) { console.log("parm " + val)});
	 console.log("postdata = " + postdata);
	 
	 return postdata;
	
}


function getCheckedRowsJson(action) {
	
	   var retval = []
	   $("tr[isselected='true']").each(  
			   function(index, element){
				   retval.push($(this).attr('id'))
				   
				   // get status column and update icon
				   jQuery(this).find("td:eq(1) > div > span:eq(0)").replaceWith("<span class='v-icon-status-UNRESOLVED' style='padding-left:16px;'> </span");
				 
	   })
	   
	   alert("see " + retval.length + " marked rows, first row " + retval[0]);
	   
	
	   $.each( retval, function( key, value ) {
		   console.log( value );  
		});
   
	   
	   // build json array with data
	   
	   var jsondata="[ ";  // init json array
	   $.each( retval, function( key, value ) {
		    console.log("concat");
		   
		    //  [ { name: "p0", value: "https://10.134.3.58/api/vApp/vm-6a7712f7-2a9c-46a9-b905-5aa533c9d31b"} ];
		       
		       if (key == retval.length-1) {
		    	   jsondata = jsondata.concat("{ action: \"" ,  action , "\", value: \"" + value +"\"}");  // last entry
		       } else
		       {
		    	   jsondata = jsondata.concat("{ action: \"p" , action , "\", value: \"" + value +"\"},");
		       }

	    			   // entries like this  { name: "first", value: "Rick" },
			   		
	   });
	   jsondata = jsondata.concat(" ];");
	   
	//   $.each(parmArray, function(i,val) { console.log("parm " + val)});
	 console.log("jsondata = " + jsondata);
	 
	 return jsondata;
	
}



/*
 *  this works   
*/
 setInterval(function(){
	
    //  [ { name: "href", value: "https://10.134.3.58/api/vApp/vm-6a7712f7-2a9c-46a9-b905-5aa533c9d31b"} ];
	   console.log("check for tasks");
    $("span[class='v-icon-status-UNRESOLVED']").parent().parent().parent().each(   // b
    		  function(index, element){     //b
    		       var postdata="[";
    	    	   postdata = postdata.concat("{ name: \"href\" , value: \"" + element.id +"\"}]"); 
    	   		
					    $.ajax({  type: "POST",
					    	      url: "http://localhost:4567/getVMState", 
					    	      data: { data : postdata } ,       
					    	      success: function(msg){
					    	    	  console.log("setInterval: ajax call returned " + msg);
					    	  if(msg != null ) {
					    		  console.log("getVMState returned " + msg)
						          var parsedJson = $.parseJSON(msg);
							 
							      console.log("vmhref: " + parsedJson.vmhref);
		
							      console.log("state: " + parsedJson.state);
							      //  { response: "https://10.134.3.58/api/vApp/vm-6a7712f7-2a9c-46a9-b905-5aa533c9d31b", status: "success"}
							   
							      if(parsedJson.state != "UNRESOLVED")
							          updateVMStatus(parsedJson.vmhref,  parsedJson.state);
							      
					    	      } else console.log("checkVMState returned null");
					       console.log("Poll for task state");
					   //     }, dataType: "text json"}); 
					    	      }}); 
    		           }); // b
      }, 10000);
 

/*
setInterval(function(){
    $.ajax({ url: "server", success: function(data){
        //Update your dashboard gauge
        salesGauge.setValue(data.value);
    }, dataType: "json"});
}, 30000);
*/

 function updateHeaderMessages(url) {
 $.ajax({
	  url: url,
	  data: {
	//    data: value
	  },
	  success: function( result ) {
	    $( "#messages" ).val(  result );
	  }
	});
 }

function makeAjaxCall(url, postdata) {
	

    // http://api.jquery.com/jquery.ajax/
    $.ajax({
       type: "POST",
       url: url,
   //    dataType: "json" 
       data: { data : postdata } ,            
       success: function(resultdata) {
      	 console.log("returned with " + resultdata);
      
         // place result in global list
         // rather than return from this method, since ajax is an async function, 
      	 // result data available after makeAjaxCall function returns 
         ajaxResponses.push(resultdata);
         
     
       }
   });
   
}

function getUsername() {
	return document.getElementById("username") 
}

/* 
 * set legal menu options based on rows checked
 * Function used to trigger on check / uncheck events but when last row unchecked, no state available
 * So function now searches all rows for any checked state, since once one row is checked, others checked must
 * be in the same state, menu options disable checking other types.
 */
function setLegalMenuOptions() {

	   // get the status from any checked row
	
	//   alert ( $( "tr[isselected='true'] > td:eq(1) > div > span:eq(1)").html() );
 
	   var state = $( "tr[isselected='true'] > td:eq(1) > div > span:eq(1)").html()
	
	   
	   if(state == "SUSPENDED") {
	 
		   // disable power on
		   $('#poweron').parent().addClass('darkClass').removeClass('tabmenu');   
	       
		   // disable power off
		   $('#poweroff').parent().addClass('darkClass').removeClass('tabmenu');   
	    
		   // disable suspend 
		   $('#suspend').parent().addClass('darkClass').removeClass('tabmenu');    
	   
		   // enable resume 
		   $('#resume').parent().removeClass('darkClass').addClass('tabmenu');   
	   
	   } 
	   
	   else if(state == "POWERED_ON") {
		   
		   // disable power on
		   $('#poweron').parent().addClass('darkClass').removeClass('tabmenu');   
	       
		   // disable resume 
		   $('#resume').parent().addClass('darkClass').removeClass('tabmenu');   
		   
		   // enable power off 
		   $('#poweroff').parent().removeClass('darkClass').addClass('tabmenu');   
		   
		   // enable supsend 
		   $('#suspend').parent().removeClass('darkClass').addClass('tabmenu');  
		   
	   }
	   
			 else if(state == "POWERED_OFF") {
					   
					   // disable power off
					   $('#poweroff').parent().addClass('darkClass').removeClass('tabmenu');   
				       
					   // disable suspend 
					   $('#suspend').parent().addClass('darkClass').removeClass('tabmenu');  
					   
					   // disable resume 
					   $('#resume').parent().addClass('darkClass').removeClass('tabmenu');   
					   
					   // enable poweron 
					   $('#poweron').parent().removeClass('darkClass').addClass('tabmenu');  
					   
				   }
	   
		          // default disable all
				 else {   
				 
					  // disable power on
					   $('#poweron').parent().addClass('darkClass').removeClass('tabmenu');   
					   
					   // disable power off
					   $('#poweroff').parent().addClass('darkClass').removeClass('tabmenu');   
				       
					   // disable suspend 
					   $('#suspend').parent().addClass('darkClass').removeClass('tabmenu');  
					   
					   // disable resume 
					   $('#resume').parent().addClass('darkClass').removeClass('tabmenu');   
				 }
}
		   

	

