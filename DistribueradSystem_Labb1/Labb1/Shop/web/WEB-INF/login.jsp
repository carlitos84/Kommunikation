<%--
  Created by IntelliJ IDEA.
  User: Teddy
  Date: 2016-10-03
  Time: 12:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<form method="post" action="index.jsp">
<%
    boolean validation = (boolean)request.getSession().getAttribute("validation");
    if(validation)
    {
        String username = (String) request.getSession().getAttribute("username");
%>
    <input name="logoutbutton" value="logout" type="submit">
        Welcome <%=username%>! Enjoy a happy shopping!
    <%}
    else
    {%>
        User name:<br>
        <input type="text" name="usrname">
        <br>
        User password:<br>
        <input type="password" name="psw">
        <select name="usertype">
            <option value="customer">Customer</option>
            <option value="staff">Staff</option>
            <option value="admin">Admin</option>
        </select>
        <input value="login" type="submit">
    <%}%>
</form>

