<%@ page import="java.util.Hashtable" %><%--
  Created by IntelliJ IDEA.
  User: Santos
  Date: 2016-09-29
  Time: 23:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Search for items</title>
</head>
<body>
    <form action="" method="post">
        <input type="text" name="searchfield">
        <input type="submit">
    </form>
    <br>
    <table>
        <%
            Integer size = (Integer)request.getSession().getAttribute("size");
            Hashtable table = (Hashtable)request.getSession().getAttribute("table");
            for (int i = 0 ; i < size ; i++) {
                Hashtable item = (Hashtable) table.get("Item"+i);
        %>
        <tr>
            <td> manufactor: </td> <td> <%= item.get("manufactor")%> </td>
            <td> model: </td> <td> <%= item.get("model")%> </td>
            <td> price: </td> <td> <%= item.get("price")%> </td>
            <td> amount: </td> <td> <%= item.get("quantity")%> </td>

        </tr>
        <% } %>
    </table>
</body>
</html>
