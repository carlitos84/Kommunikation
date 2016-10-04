<%@ page import="java.util.Hashtable" %><%--
  Created by IntelliJ IDEA.
  User: Teddy
  Date: 2016-10-03
  Time: 19:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<form method="post" action="index.jsp"> <input name="showallorders" value="show order" type="submit"></form>

<table name="allOrders">
    <%
        Integer size = (int)request.getSession().getAttribute("allorderlistsize");
        Hashtable table = (Hashtable)request.getSession().getAttribute("orderlist");
        for (int i = 0 ; i < size-1 ; i++) {
            Hashtable item = (Hashtable) table.get("Order"+i);
    %>
    <tr>
        <td> Order Id: </td> <td> <%= item.get("orderId")%> </td>
        <td> Customer Id: </td> <td> <%= item.get("customerId")%> </td>
        <td><form method="post" action="index.jsp"><input type="hidden" name="<%="idfillednumber"+i%>" value="<%=item.get("orderId")%>"> <input id="<%=item.get("orderId")%>" value="Show Order" type="submit"  name="<%="showorderbutton"+i%>"></form></td>
    </tr>
    <% } %>
</table>
<body>

</body>
</html>
