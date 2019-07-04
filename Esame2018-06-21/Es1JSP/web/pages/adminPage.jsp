<%@ page session="true"%>
<%@ page import="beans.Prodotto"%>
<%@ page import="java.util.*"%>
<html>
    <head>
		<title>AdminPage</title>
        <link type="text/css" href="styles/default.css" rel="stylesheet"></link>
        <script>
    
        </script>
    </head>
    <body>
    <% 
        Set<HttpSession> sessions = (Set<HttpSession>) application.getAttribute("sessionSet");
    %>


        <h2>Carrelli</h2>
        <table id="userTable">
            <th>username</th>
            <th></th>            
            <% 
            for (HttpSession currSession : sessions){

                Hashtable<String, Integer> cart = (Hashtable<String, Integer>) currSession.getAttribute("cart");
                if (cart == null){
                    continue;
                }
                //html code:
                %>
                <tr>
                    <td><%= ((LoginInfo)currSession.getAttribute("loginInfo")).getUsername() %></td>
                    <td>
                        <form method="get" action="../carrello&sessionId=<%= currSession.getId() %>">
                            <input type="submit" value="Rimuovi"/>
                        </form>
                    </td>
                </tr>
                <%
            }
            %>
        </table><!--End userTable-->
        
    

    </body>
</html>