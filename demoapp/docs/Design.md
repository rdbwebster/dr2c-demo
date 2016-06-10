
// Maven build dependencies

// If project build path in eclipse lists libs as maven dependencies that you dont want
reset the repo using the command below

cd /Users/bwebster/apps/demo_dr2c/demoapp
mvn dependency:purge-local-repository -DactTransitively=false:wq



Callback Design

Actions such as VM poweron, poweroff are created as async Tasks on vCD.
The UI uses jQuery to update the status icon on the UI for any VMs that have a task in progress.

In the original design,
The UI uses ajax to send the poweron etc as a post request to the App on the Server.
The App extracts the users vCloud Client object from the session, connects to vCD and issues the task request.
A task identifier is returned to be used to check status of the task later on.
The App also creates a DemoTask object and places it in a CopyOnWriteArrayList named demoAppList which is stored at App scope.

A background process name 'DemoTaskScheduler' wakes up every 5 seconds and looks for entries in the demoAppList.
It gets the requestors vclient object from the DemoTask and the task ID and calls vCD to check the status of the task.
When the task is complete it calls the original requestor back using a websocket that was established when the user first connected to the server.

Issue: The issue is that DemoTaskScheduler does not know what webSocket session is used by the client.
So it cannot determine what websocket connection to send the completed status for the task info.


Solution Candidate 1: 
---------------------
Continue with http ajax requests to create tasks.
Have the App store the vClient object in a map instead of the http Session.
Store just the username in the session.
The map would be indexed by username as shown below.

username / vCloud client / websocket session

When the user first logs in, it sends a websocket 'registration' message containing the users name to the server over the websocket.
The Server's onMessage routine receives the sessionObject and the message and now has a mapping of user to websocket sessions for reply messages.  The websocket session gets added to the same map above indexed by user.
The DemoTaskScheduler can use the map to get both the clients vClient object to call vcd and check on the task, and ghe websocket session object to send notifications back to the cliet.



Solution Candidate 2:
---------------------

Have both task creation requests and reply's be sent

Have the DemoTaskScheduler use


Choice of connections
1) Use http post to task creation and websocket for reply notifications.
2) Use web socket for both task creation and completion notifications.

Either can be accommodated by design above.

Question: Should DemoTaskScheduler use the clients vClient object to check on Task status or use its own administrator account.

Own Connection; Pro - simple, no need to pass client vClient objects for each DemoTaskScheduler object to be processed.
                    - initial connection by app would be using admin acct so ensure vcd connects work before clients try to login.
Own Connectionl Con - security, would need to ensure clients owns objects in vcd that they wanted to create or check status on.
                    - Cannot run

Client Connection Pro -  Better security since all requests handed by vClient with correct permissions.
Client Connection Con - Need to provide each time.





-------                         -------------------------------                         --------------
  hidden element                httpSession - username 
  (username)                    vClientMap
                                WSSessionMap
       <....websocket..........>
                                              
    javascript can get
    username for http req                          \ access to vClient for vcd call   
    or web socket calls                             \ and user WSSession for reply
-------                       -------------------------------                          -------------- 
Browser                       App       App/DemoTaskScheduler                          vCloud Director

1) User logins on browser
2) App creates vClient for user and authenticates against vcd
3) App creates vClientMap and places vClient in map indexed by username, add username to http session.
3) App replies with page, embeds username as hidden field on page.
4) Javascript gets username and sends over web socket to App to register channel
5) App WebSocketHandler receives message and creates a WSSessionmap indexed by user and stores web socket session.

-) Any http post requests can get username from http sesssion and lookup vClient in Map.
-) DemoTaskScheduler can lookup both vClient and reply websocket given username using WSSessionmap and vClientMap, username in DemoTask

-) DemoTask objects dont need to contain vClient Session.  Just need username and taskRef.
-) Javascript can get username from page and send messages over websocket to Server if needed in design later.
