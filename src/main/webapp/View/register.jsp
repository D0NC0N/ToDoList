<%@ page import="Model.data.UserData" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style/style.css" type="text/css">
</head>
<body>
<br>
<br>
<br>
<h1>Register</h1>
<form class="myForm" method=post action="${pageContext.request.contextPath}/RegisterServlet">
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
</body>
</html>

