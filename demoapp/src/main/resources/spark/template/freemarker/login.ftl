<!DOCTYPE html>
<html>
  <head>
    <title>DR2C Demo</title>
      <meta charset="utf-8">
     <link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css'>
  </head>
  <body>
      <div class="container">

<!--  login color vchs #4679B9 -->

<div class="container">
    <div class="row"> 
     <div class="col-sm-6 col-md-4 col-md-offset-4">
        <div class="center-block"  >
          <h4 class="text-center login-title"> <#if errorMessage??> ${errorMessage} </#if> </h4>
         

            <div class="account-wall">
         
                <img class="profile-img" src="./img/VMWare.jpg" alt="">
            <!--       <h3 class="text-center login-title">Sign in to continue </h3> -->
                <form class="form-signin" method="post" action="/dologin">
                <input id="vcloudurl" name="vcloudurl" type="text" class="form-control" placeholder="vCloud url" required autofocus 
                                      value="<#if vcloudurl??> ${vcloudurl} </#if>">
                <input id="username" name="username" type="text" class="form-control" placeholder="Email" required autofocus 
                                       value="<#if username??> ${username} </#if>">
                <input id="password" name="password" type="password" class="form-control" placeholder="Password" required 
                                        value="<#if password??> ${password} </#if>">
                <button class="btn btn-lg btn-primary btn-block" type="submit">
                    Sign in</button>
                    <!--
                <label class="checkbox pull-left">
                    <input type="checkbox" value="remember-me">
                    Remember me
                </label>
               
                <a href="#" class="pull-right need-help">Need help? </a><span class="clearfix"></span>
                 -->
                </form>
            </div>
       
        </div>
    </div>
</div>

  </body>
</html>
