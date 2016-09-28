<%--
  Created by IntelliJ IDEA.
  User: Teddy
  Date: 2016-09-28
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import= "BO.LookItems" %>
<%@page import= "java.util.Hashtable" %>
<%@ page import="BO.BOManager" %>
<%
    BOManager.init();
  String manufactor = request.getParameter("Gibson");
  LookItems look = new LookItems();
  Hashtable table =  look.getItemsWithManufactor("Gibson");
  int size = (int) table.get("size");
%>
<table>
  <%
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


