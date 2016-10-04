<%@ page import="java.util.Hashtable" %><%--
  Created by IntelliJ IDEA.
  User: Santos
  Date: 2016-09-29
  Time: 23:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <form action="../index.jsp" method="post">
        Search:
        <input type="text" name="searchfield">
        <select name="searchBy">
            <option value="manufactor">Manufactor</option>
            <option value="model">Model</option>
        </select>
        <input value="search" type="submit">
    </form>
    <br>
    <table name="result">
        <%
            Integer size = (Integer)request.getSession().getAttribute("resultsize");

            Hashtable table = (Hashtable)request.getSession().getAttribute("resulttable");
            for (int i = 0 ; i < size ; i++) {
                Hashtable item = (Hashtable) table.get("Item"+i);
        %>
        <tr>
            <td> manufactor: </td> <td> <%= item.get("manufactor")%> </td>
            <td> model: </td> <td> <%= item.get("model")%> </td>
            <td> price: </td> <td> <%= item.get("price")%> </td>
            <td> amount: </td> <td> <%= item.get("quantity")%> </td>
            <td><form method="post" action="../index.jsp"><input name="<%="amountfield"+i%>" type="text" maxlength="2" value="1" size="2"><input value="add" type="submit" name="<%="button"+i%>"></form> </td>
        </tr>
        <% } %>
    </table>
