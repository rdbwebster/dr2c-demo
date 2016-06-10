<!DOCTYPE html>
<html>
  <head>
    <title>DR2C Demo</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <link href="./css/admin.css" rel="stylesheet"> 
    <link href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
    <link href="./bower_components/bootstrap/dist/css/bootstrap.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>	
    <script type="text/javascript" src="./js/admin.js"></script>
    <link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css'>
 
   <!-- menu -->
   <link  rel="stylesheet" href="./css/menu.css">
   <script src="./js/menu.js"></script>
   
  </head>
  
  
  <body>



<div class="container">
<nav class="navbar navbar-default" role="navigation">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">Portal</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
     <ul class="nav navbar-nav">
        <li class="active"><a href="#">Link</a></li>
        <li><a href="#">Link</a></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <b class="caret"></b></a>
          <ul class="dropdown-menu">
            <li><a href="#">Action</a></li>
            <li><a href="#">Another action</a></li>
            <li><a href="#">Something else here</a></li>
            <li class="divider"></li>
            <li><a href="#">Separated link</a></li>
            <li class="divider"></li>
            <li><a href="#">One more separated link</a></li>
          </ul>
        </li>
      </ul> 
          <ul class="nav navbar-nav navbar-right">
          <li><a href="/logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
      
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">Settings <b class="caret"></b></a>
          <ul class="dropdown-menu">
            <li><a href="#">Action</a></li>
            <li><a href="#">Another action</a></li>
            <li><a href="#">Something else here</a></li>
            <li class="divider"></li>
            <li><a href="#">Separated link</a></li>
          </ul>
        </li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

    
    <!-- side menu -->
    
<div class="container">

<div class="row">

  <div class="col-sm-3">
  
  
<div id='cssmenu'>

<ul>

  <#if !navBean.isOrgSelected() >
      <li class='active'><a href='#'><span>${navBean.navTitle}</span></a></li>
	  <#list navBean.orgList as orgName> 
		   <li class='last'><a href='/selectorg?name=${orgName}'><span>${orgName}</span></a></li>
	  </#list> 
			   
  <#else>
           <li class="active"><a href="/deselectorg"><span class="glyphicon glyphicon-chevron-left"> ${navBean.navTitle}</a></li>
    
           <#list navBean.orgVdcList as vdcName> 
                <#if vdcName == navBean.selectedVdcName >
                      
                       <li class='last'><a href='/selectvdc?name=${vdcName}'><span>${vdcName}</span></a></li>
                <#else>
                       <li class='last'><a href="/selectvdc?name=${vdcName}"><span>${vdcName}</span></a></li>
                </#if>
           </#list> 
   		
   </#if>        

</ul>
</div>

 <!--  
    <div class="sidebar-nav">
      <div class="navbar navbar-default" role="navigation">
	        <div class="navbar-header">
	          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-navbar-collapse">
	            <span class="sr-only">Toggle navigation</span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	          </button>
	          <span class="visible-xs navbar-brand">Sidebar menu</span>
	        </div>
	        <div class="navbar-collapse collapse sidebar-navbar-collapse">
	          <ul class="nav navbar-nav">
	          
	      
	           
	           <#if !navBean.isOrgSelected() >
	                   <li class="active"><a href="#"> </span> ${navBean.navTitle}</a></li>
			           <#list navBean.orgList as orgName> 
			                <li><a href="/selectorg?name=${orgName}">${orgName}</a></li>
			           </#list> 
	           <#else>
	                   <li class="active"><a href="/deselectorg"><span class="glyphicon glyphicon-chevron-left"> ${navBean.navTitle}</a></li>
	                   <#list navBean.orgVdcList as vdcName> 
	                        <#if vdcName == navBean.selectedVdcName >
			                       <li class="active"><a href="/selectvdc?name=${vdcName}">${vdcName}</a></li>
			                <#else>
			                       <li><a href="/selectvdc?name=${vdcName}">${vdcName}</a></li>
			                </#if>
			           </#list> 
	           		
	           </#if>
	          </ul>
	        </div>
      </div>
    </div>
    
    -->
  </div>    <!-- col-sm-3 -->

    <!-- end side menu -->
    
    
    <!-- Main Content on right  same row-->
    
  <div class="col-sm-9">

  <#if navBean.isVdcSelected() >
			  
			<ul class="nav nav-tabs">
			  <li class="active"><a data-toggle="tab" href="#home">Virtual Machines</a></li>
			  <li><a data-toggle="tab" href="#menu1">Replication Status</a></li>
			  <li><a data-toggle="tab" href="#menu2">Menu 2</a></li>
			</ul>
			
			<div class="tab-content">
			
			  <div id="home" class="tab-pane fade in active">
			  <table class="table table-striped">
			    <thead>
			      <tr>
			        <th>Status</th>
			        <th>Name</th>
			        <th>Recovery Status</th>
			      </tr>
			    </thead>
			    <tbody>
			     <#list vmlist as vmbean> 
			      <tr>
			        <td>Status</td>
			        <td>${vmbean.name}</td>
			        <td>john@example.com</td>
			      </tr>
			     </#list>
			
			    </tbody>
			  </table> 
			  </div>
			  <div id="menu2" class="tab-pane fade">
			    
			  
			  <table class="table table-striped">
			    <thead>
			      <tr>
			        <th>Status</th>
			        <th>Name</th>
			        <th>Recovery Status</th>
			      </tr>
			    </thead>
			    <tbody>
			    
			    </tbody>
			  </table>    
			  </div>
			  <div id="menu3" class="tab-pane fade">
			    <h3>Menu 2</h3>
			    <p>Some content in menu 2.</p>
			  </div>
			</div>
</#if>		   
    
   </div>  <!-- end row -->

  </div>  <!-- end container -->
      
  </body>
</html>