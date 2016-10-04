<%@ page import="java.util.Hashtable" %><%--
  Created by IntelliJ IDEA.
  User: Teddy
  Date: 2016-10-04
  Time: 12:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table name="ordertable" align="right">
    <tr>
        <th>Item Id</th>
        <th>Quantity</th>
    </tr>
    <tr>
            <%
                Integer ordersize = (Integer)request.getSession().getAttribute("ordersize");
                Hashtable orderTable = (Hashtable)request.getSession().getAttribute("showorder");

                for (int j = 0 ; j < ordersize-2; j++) {
                    Hashtable orderItem = (Hashtable) orderTable.get("OrderItem" + j);
                    %>
    <tr>
        <td> <%= orderItem.get("itemId")%> </td>
        <td> <%= orderItem.get("quantity")%> </td>
       </tr>
    <% } %>
    <%
        if(ordersize > 0)
        {
            int totalprice = (int)orderTable.get("ordertotalprice");

        %> <td>Total Price: <%= totalprice%> pesos </td>
    <tr><td>
        <form method="post" action="/index.jsp"><input name="packetorder" value="deliver" type="submit"></form>
    </td></tr>
    <%}%>

</table>
