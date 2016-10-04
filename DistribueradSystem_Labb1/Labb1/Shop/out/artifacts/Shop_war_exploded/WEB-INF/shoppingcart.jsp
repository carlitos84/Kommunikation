<%@ page import="java.util.Hashtable" %><%--
  Created by IntelliJ IDEA.
  User: Teddy
  Date: 2016-10-03
  Time: 12:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
                System.out.println("shoppingcartSize:" + shoppingcartSize);
                for (int j = 0 ; j < shoppingcartSize ; j++) {
                    Hashtable shoppingCartItem = (Hashtable) shoppinCartTable.get("Item" + j);

                    %>
        <tr>
            <td> <%= shoppingCartItem.get("manufactor")%> </td>
            <td> <%= shoppingCartItem.get("model")%> </td>
            <td> <%= shoppingCartItem.get("price")%> </td>
            <td> <%= shoppingCartItem.get("quantity")%> </td>
    <td><form method="post" action="../index.jsp"><input name="<%="amountremovefield"+j%>" type="text" maxlength="2" value="1" size="2"><input value="remove" type="submit" name="<%="removebutton"+j%>"></form> </td>
</tr>
        <% } %>
    </tr>
    <tr><td> Total price: <%int totalprice = (int)request.getSession().getAttribute("totalprice");%><%= totalprice%> pesos </td></tr>
    <tr><td><form method="post" action="../index.jsp"><input name="makeorderbutton" value="makeorder" type="submit"></form></td></tr>
</table>

