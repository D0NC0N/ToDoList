<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Choose-watch</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style/style.css" type="text/css">
</head>
<body>
<jsp:useBean id="lists" scope="request" type="java.util.List"/>
<button class="buttonLog" type="button" onclick="window.location.href='BackFromGuestServlet'">Back</button>

<div class="main3">
    <form id="formId" class="myForm4" method="post" action="${pageContext.request.contextPath}/WhichWatchServlet">
        <c:forEach var="list" items="${lists}" varStatus="status">
            <label>
            <h2><c:out value="${list.group.userName}:   ${list.group.day.date}.${list.group.day.month+1}.${list.group.day.year + 1900}"/></h2>

            <h2><c:out value="${list.subgroup}"/></h2>

            <input type="hidden" name="toDoListId" value="${list.group.toDoListId}">
            <input type="hidden" name="which${list.group.toDoListId}" value="${list.subgroup}">

            <button name="subIt" value="${list.group.toDoListId}:${list.subgroup}" class="button2" type="submit">Watch</button>
            </label>
            <br>
            <br>
            <br>
        </c:forEach>
    </form>
</div>
</body>
</html>
