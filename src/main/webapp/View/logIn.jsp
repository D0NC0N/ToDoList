<%@ page import="Model.data.UserData" %>
<%@ page import="Model.data.ToDoListData" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Log in</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style/style.css" type="text/css">
</head>
<body>
<br>
<br>
<br>
<h1>Log in</h1>
<form class="myForm" method=post action="${pageContext.request.contextPath}/LogInServlet">
    <label>Login
        <input type=text name=login><br/>
    </label>
    <br/>
    <label>Password:
        <input type=text name=password><br/>
    </label>
    <br/>
    <button class="mySubmit" type="submit">Submit</button>
</form>
<br>
<br>
<h1>or register
    <a href="${pageContext.request.contextPath}/View/register.jsp">here</a>
</h1>
</body>
</html>