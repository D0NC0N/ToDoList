<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add new task</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style/style.css" type="text/css">
</head>
<body>
<div class="main">
    <form class="myForm" method="get" action="${pageContext.request.contextPath}/AddNewBasicServlet">
        <label>
            <input type="text" name="basicName">
            <br>
            <br>
            <input type="text" name="comment">
            <br>
            <br>
            <input type="text" name="priority" oninput="changeHandler(this)">
            <br>
            <br>
            <input class="myDeadLine" type="time" name="deadLine">
            <br>
            <br>
            <button class="mySubmit" type="submit">Submit</button>
        </label>
    </form>
</div>

<script>
    const changeHandler = e => {
        const value = e.value;
        e.value = value.replace(/\D/g, '');
    }
</script>
</body>
</html>
