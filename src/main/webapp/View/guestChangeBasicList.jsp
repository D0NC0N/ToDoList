<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Change task</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style/style.css" type="text/css">
</head>
<body>
<jsp:useBean id="basicList" scope="request" type="Model.data.BasicList"/>
<div class="main2">
    <form id="formId" class="myForm2" method="post" action="${pageContext.request.contextPath}/GuestChangeBasicListServlet">
        <label>
            <input type="hidden" name="basicId" value="${basicList.basicId}">

            <input type="text" name="basicName" value="${basicList.basicName}">
            <br>
            <br>
            <textarea class="myInputText" name="comment">${basicList.comment}</textarea>
            <br>
            <br>
            <input type="text" name="priority" oninput="changeHandler(this)" value="${basicList.priority}">
            <br>
            <br>
            <input class="myDeadLine" type="time" name="deadLine" value="${basicList.deadline}">
            <br>
            <br>
            <button class="mySubmit" type="submit">Submit</button>
        </label>
    </form>
</div>

<script>
    function validListNumber(number) {
        if (number > 10 || number < 1) {
            const error = "Priority: " + number + " entered. Possible range: 1 to 10.";
            alert(error);
            throw new Error(error)
        }
    }

    document.addEventListener("DOMContentLoaded", () => {
        console.log("ready");

        document.getElementById('formId').addEventListener("submit", function (e) {
            e.preventDefault();
            let priority = e.target.priority.value;
            let basicName = e.target.basicName.value;

            validListNumber(priority)

            if (basicName.length > 20) {
                alert("Number of characters in a line: " + basicName.length + ". Maximum possible number: 20");
                throw new Error("The number of characters in a line is more than 20")
            }
            if (basicName.length === 0) {
                alert("Number of characters must be at least 1");
                throw new Error("Number of characters must be at least 1")
            }

            e.target.submit();
        });
    });
</script>

<script>
    const changeHandler = e => {
        const value = e.value;
        e.value = value.replace(/\D/g, '');
    }
</script>
</body>
</html>
