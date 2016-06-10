# demo_dr2c

This is a UI interface demo for vmware vCloud Director 8.10

It allows the user to login to a specified remote vCD server and navigate Organizations, Virtual Data Centers and start/stop vApps and VMs.

### Frameworks and Prerequisites

Java 8 SE Development Kit

http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

Spark Microframework for Web Applications

http://sparkjava.com/

Apache Freemarker Template Engine

http://freemarker.org/

VMWare vCD SDK for Java

https://www.vmware.com/support/developer/vapi/index.html

Apache Http Client

https://hc.apache.org/



### Build Setup

  
  Maven must be installed on the build workstation.
  
  Note: Most libraries are available in the maven public repository and will be resolved for the build,
  but two libs for the VMWare sdk client are not in the public repo.
 rest-api-schemas-8.10.0.jar and 	vcloud-java-sdk-8.10.0.jar
 These libs must be downloaded from vmware and installed in the local workstation maven repository.
  See the pom.xml file for details including sample commands to install the files in the local repository.
  Once the files are installed in the local repo, run the command ->      mvn install
  From the folder containing the pom.xml To resolve the dependencies
  
###  Building the App
 Build the application using the following steps:
 In a terminal window change to the source directory containing the pom.xml file
 Then run the following command.

 
 $ mvn package
  

  
###  Running the App

  Once the build is complete, a demoApp-10.jar is created in the target folder.
  
  Run the application using the following command, also from the folder containing the pom.xml file
  
  java -cp ./target -jar ./target/demoApp-1.0.jar
  
  The UI application should then be available on http://localhost:4567
  
###   App configuration

  The demo.properties file and log4j.properties files are used to configure the application settings and logging levels.
  
  A default url, username and password can be set in the demo.properties file and will be read at runtime and appear on the login screen.
  
  A choice of http clients can also be specified in the demo.properties file.
  The vCloud Director SDK Java Client or the Apache Http client are the current choices.
  
  The vCloud Director SDK Java Client does not contain API extensions needed for accessing DR2C extensions on vCD,
  therefore the Apache client is recommended.
  
  Logging is managed by slf4j and log4j.
  Log levels can be changed by modifying log levels in the log4j.properties file.
  A restart of the application is required to activate changes.
  Each class has its own logger so log granularity can be set from the total app all the way down to individual classes.
  The default configuration outputs log data to the stdout console
  
  
####  vCD Simulator 
  
  A vCD simulator was created to support testing the UI without a connection to a live vCD 8.10 instance.
  The simulator is a jetty server that returns canned xml responses for api requests.
  See the git project name 'dr2c-demo-vcd'
 

