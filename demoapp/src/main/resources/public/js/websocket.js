/*
 * A browser side websocket client
 */
   
var webSocket = new WebSocket("ws://" + "localhost" + ":" + "4567" + "/chat/");
webSocket.onmessage = function (msg) { receiveMessage(msg); };
webSocket.onclose = function () { alert("WebSocket connection closed") };

		

//Send message if "Send" is clicked
//id("send").addEventListener("click", function () {
//  sendMessage(id("message").value);
//});

//Send message if enter is pressed in the input field
//id("message").addEventListener("keypress", function (e) {
//  if (e.keyCode === 13) { sendMessage(e.target.value); }
//});

//Send a message if it's not empty, then clear the input field
function sendMessage(message) {
  if (message !== "") {
	  console.log("websocket.js sent message " + message);
      webSocket.send(message);
  //    id("message").value = "";
  }
}

//Update the chat-panel, and the list of connected users
function receiveMessage(msg) {
	 
	 console.log("Browser Received WebSocket Message: " + msg.data);
	 var parsedJson = $.parseJSON(msg.data);
	 
	 console.log("vmhref " + parsedJson.vmhref);
	 console.log("status " + parsedJson.status);
	 console.log("state " + parsedJson.state);
	 
	 updateVMStatus(parsedJson.vmhref,  parsedJson.state);
}


//Helper function for inserting HTML as the first child of an element
function insert(targetId, message) {
  id(targetId).insertAdjacentHTML("afterbegin", message);
}

//Helper function for selecting element by id
function id(id) {
  return document.getElementById(id);
}


/* This function sends the username to the server over the websocket session so the server
 * can maintain a map of users and what websocket sessions they use for communication.
 */
function registerCallbackSession() {
	 console.log("username is  "+  $("#username").html());
	 sendMessage(  $("#username").html() );
	

	}
