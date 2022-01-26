<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Watch-read</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style/style.css" type="text/css">
</head>
<body>
<jsp:useBean id="lists" scope="request" type="java.util.List"/>

<button class="buttonLog" type="button" onclick="window.location.href='BackFromGuestServlet'">Back</button>

<div class="main">
    <h1>
        <c:out value="${lists.get(0).userName}"/>
        <br>
        <c:out value="${lists.get(0).day.date}.${lists.get(0).day.month+1}.${lists.get(0).day.year + 1900}"/>
    </h1>
    <ol>
        <c:forEach var="list" items="${lists}" varStatus="status">
            <c:forEach var="listOfBasics" items="${list.listOfBasics}" varStatus="stat">

                <div class="group" id="${stat.current}">
                    <li>
                        <c:out value="${listOfBasics.basicName}"/>

                        <button class="button" id="${listOfBasics.basicName}" type="button"
                                onclick="show('${stat.index}', '${listOfBasics.basicName}')">+
                        </button>

                        <button class="button" id="pop${listOfBasics.basicId}" type="button"
                                onclick="showPopup('myPop${listOfBasics.basicId}', 'pop${listOfBasics.basicId}')">:
                        </button>
                    </li>

                    <div class="popup" id="myPop${listOfBasics.basicId}">
                        <div class="popup_body">
                            <div class="popup_content">

                                <button class="popup_close" id="close${listOfBasics.basicId}" type="button"
                                        onclick="showPopup('myPop${listOfBasics.basicId}', 'close${listOfBasics.basicId}')">
                                    X
                                </button>

                                <div class="popup_title">
                                    <br>${listOfBasics.basicName} <br><br>
                                    Priority ${listOfBasics.priority} <br>
                                    Deadline ${listOfBasics.deadline}<br>
                                    Comment:<br>${listOfBasics.comment}<br>
                                </div>
                                <br>
                                <div class="popup_text">
                                    <c:forEach var="valin" items="${listOfBasics.subTasks}" varStatus="subin">
                                        <div class="subgroup" id="${subin.current}">
                                            <c:out value="${valin}"/>
                                            <br>
                                        </div>
                                    </c:forEach>
                                </div>
                                <br>
                            </div>
                        </div>
                    </div>

                    <div class="div" id="${stat.index}">
                        <c:forEach var="val" items="${listOfBasics.subTasks}" varStatus="sub">
                            <div class="subgroup" id="${sub.current}">
                                <ul>
                                    <li>
                                        <c:out value="${val}"/>
                                    </li>
                                </ul>
                            </div>
                        </c:forEach>
                    </div>

                </div>
                <br>
            </c:forEach>
        </c:forEach>
    </ol>
</div>

<script>
    function show(elementId, buttonId) {
        let elem = document.getElementById(elementId);
        let button = document.getElementById(buttonId);
        if (elem.style.display === "block") {
            elem.style.display = "none";
            button.innerHTML = "+";
        } else {
            elem.style.display = "block";
            button.innerHTML = "-";
        }
    }
</script>

<script>
    function showPopup(elementId, buttonId) {
        let elem = document.getElementById(elementId);
        let button = document.getElementById(buttonId);
        if (elem.style.display === "block") {
            elem.style.display = "none";
        } else {
            elem.style.display = "block";
        }
    }
</script>

<script>
    const changeHandler = e => {
        const value = e.value;
        e.value = value.replace(/\D/g, '');
    }
</script>
</body>
</html>
