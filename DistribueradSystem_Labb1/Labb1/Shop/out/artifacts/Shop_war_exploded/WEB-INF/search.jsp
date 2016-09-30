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
    <table name="shoppingcart" align="right">
        <tr>
            <th>Manufactor</th>
            <th>Model</th>
            <th>Price</th>
            <th>Quantity</th>
        </tr>
        <tr>
            <%
                Integer shoppingcartSize = (Integer)request.getSession().getAttribute("shoppingcartsize");
                Hashtable shoppinCartTable = (Hashtable)request.getSession().getAttribute("shoppingcarttable");

                for (int j = 0 ; j < shoppingcartSize ; j++) {
                    Hashtable shoppingCartItem = (Hashtable) shoppinCartTable.get("Item" + j);
                    System.out.println(shoppingCartItem);
                    %>
            <tr>
                <td> <%= shoppingCartItem.get("manufactor")%> </td>
                <td> <%= shoppingCartItem.get("model")%> </td>
                <td> <%= shoppingCartItem.get("price")%> </td>
                <td> <%= shoppingCartItem.get("quantity")%> </td>
                </tr>
        <% } %>
            %>
        </tr>
    </table>

    <form action="" method="post">
        Search:
        <input type="text" name="searchfield">
        <select name="searchBy">
            <option value="manufactor">Manufactor</option>
            <option value="model">Model</option>
        </select>
        <input type="submit">
    </form>
    <br>
    <table name="result">
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
            <td><form method="post"><input name="<%="amountfield"+i%>" type="text" maxlength="2" value="1" size="2"><input type="submit"  name="<%="button"+i%>"></form> </td>
        </tr>
        <% } %>
    </table>
</body>
</html>