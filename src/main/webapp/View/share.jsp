<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Share</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style/style.css" type="text/css">
</head>
<body>
<jsp:useBean id="usersInfo" scope="request" type="java.util.List"/>
<button class="buttonLog" type="button" onclick="window.location.href='BackFromGuestServlet'">Back</button>

<div class="main3">
    <form id="formId" class="myForm3" method="post" action="${pageContext.request.contextPath}/ShareServlet">
        <c:forEach var="user" items="${usersInfo}" varStatus="status">
            <h2><c:out value="${user.second} - ${user.third}"/></h2>
            <label>
                X
                <input type="radio" name="${user.first}" value="-1" checked>
                R
                <input type="radio" name="${user.first}" value="0">
                C
                <input type="radio" name="${user.first}" value="1">
            </label>
            <br>
        </c:forEach>
        <br>
        <button class="mySubmit" type="submit">Submit</button>
    </form>
</div>

</body>
</html>
