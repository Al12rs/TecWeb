<%@ page session="true"%>
<%@ page import="beans.Prodotto"%>
<%@ page import="java.util.*"%>
<html>
    <head>
		<title>Catalogo</title>
        <link type="text/css" href="styles/default.css" rel="stylesheet"></link>
        <script>
            function validate(value){
                if(value.length != 1 && !( (value > '0' && value < '9') || (value > 'a' && value < 'z')) ){
                    return false;
                }
                return true;
            }
        </script>
    </head>
    <body>
    <% 
        Hashtable<String, Prodotto> catalogo = (Hashtable<String, Prodotto> catalogo) application.getAttribute("catalogo");
    %>


        <h2>Catalogo</h2>
        <table id="userTable">
            <th>NomeProdotto</th>
            <th>Quantita</th>
            <th></th>            
            <% 
            for (Prodotto currProd : catalogo.values()){

                //html code:
                %>
                <tr>
                    <td><%= currProd.getName() %></td>
                    <td><%= currProd.getQuantita %></td>
                    <td>
                        <form method="post" action="../carrello">                            
                            <input type="text" name="prodotto" style="display:none;" value="<%=currProd.getName() %>">
                            <input type="submit" value="Aggiungi"/>
                        </form>
                    </td>
                </tr>
                <%
            }
            %>
        </table><!--End userTable-->
        
        
        

    <div id="result">
        <%  
           boolean finalizable = (Boolean) session.getAttribute("finalizzabile");
           if(finalizable){
                %>
                <form action="../carrello" method="post">
                    <input type="submit" value="Finalizza ordine"/>
                </form>
                <%
           }
        %>

    </div>

    </body>
</html>