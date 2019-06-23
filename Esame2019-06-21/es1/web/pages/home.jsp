<%@ page session="true"%>
<%@ page import="beans.MsgList"%>
<%@ page import="java.util.*"%>
<html>
    <head>
		<title>Home</title>
        <link type="text/css" href="styles/default.css" rel="stylesheet"></link>
        <script>
            function sendMsg(input){
                var value = this.value;
                if (value.charAt(value.length - 1) != '@'){
                    return;
                } 
                this.value = value.toLowerCase();
                input.form.submit();
            }
        </script>
        <jsp:useBean id="msgToReturn" class="beans.MsgList" scope="session"/>
    </head>
    <body>

    <div id="msgBoard">
        <% 
            ArrayList<String> messages = msgToReturn.getMsgList();
            for (String msg : messages ){
                //html code:
                %><p><%= msg%></p><%
            }
        %>

    </div>

    <form method="post" action="../msgmod">
        New message (press @ to send):<br/>
        <input type="text" name="msgInput" value="" onkeyup="sendMsg(this)" />
    </form>

    </body>
</html>

