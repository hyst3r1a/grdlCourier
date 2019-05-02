<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String sessionid = request.getAttribute("sessionid").toString(),
 type = request.getAttribute("type").toString();
 %>


<html>
It works!
<%if(sessionid.equals("0")){%>
    <p>Login failed!</p>
    </br>
    <a href="/mycontext" Try again!></a>
    <%}%>
    <%if(type.equals("manager")){%>


    <% if(!sessionid.equals("0") && type.equals("manager")){%>
        <p>Current role: Manager<%request.getAttribute("user").toString();%></p>
        </br>
        <p><%request.getAttribute("user").toString();%></p>

        <% }} %>
    <% if(!sessionid.equals("null") && type.equals("driver")){%>
        <p>Current role: Driver</p>
        <% } %>
</html>