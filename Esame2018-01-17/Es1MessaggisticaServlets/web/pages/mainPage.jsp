<%@ page session="true"%>
<%@ page import="beans.MessageList"%>
<%@ page import="beans.Message"%>
<%@ page import="beans.LoginInfo"%>
<%@ page import="java.util.*"%>
<html>
    <head>
		<title>Start</title>
        <link type="text/css" href="styles/default.css" rel="stylesheet"></link>
        <script type="text/javascript" src="../scripts/ajax.js"></script>
        <script>
            function setRefresh(){
                setInterval(checkForNewMessages, 30000);
            }
        </script>
    </head>
    <body onload="setRefresh()">
    <div id="messages">
        <%  
            HashMap<String, MessageList> messageMap = (HashMap<String, MessageList>)application.getAttribute("msgLists");
			String groupName = (String)session.getAttribute("groupName");
            MessageList groupChat = messageMap.get(groupName);
            for(Message m : groupChat.getMessages()){
            	%>
                <%= m.getMessage()%><br class="brMessage"/>
                <%
            }
        %>
    </div>
    <div>
    	<h4 id="newMessages">0 nuovi messaggi disponibili! Fai il refresh!</h4>
    	<form method="get" action="../chatagent?refresh=true">
    		<input type="submit" value="Refresh" />
    	</form>
    </div>
    <div id="messageInput">
    	<form method="post" action="../chatagent">
    		<input type="text" name="message" value="" />
    		<input type="submit" value="Submit" />
    	</form>
    </div>

    </body>
</html>