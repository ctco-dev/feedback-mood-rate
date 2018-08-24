<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Option</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body onload="onloadHandler()">
<h2>You mood in:</h2>
<p>
    <button type="button" onclick="handlerEvent('DAY')">Day</button>
    <span id="date"></span><br/><br/>
    <button type="button" onclick="handlerEvent('EVENT')">Event</button>
    <br/><br/><br/><br/><br/>
</p>
<p id="button-stat">
    <button type="button" onclick="goStatistics()">Statistics</button>
    <br/><br/>
</p>
<button type="button" onclick="logout()">Log out</button>
<script>
    function handlerEvent(eventType) {
        console.log(JSON.stringify(eventType));
        fetch("<c:url value='/api/vote/start'/>", {
            "method": "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(eventType)
        }).then(function (response) {
            location.href = "/app/mood.jsp";
        });
    }

    function logout() {
        fetch("<c:url value='/api/auth/logout'/>", {"method": "POST"})
            .then(function (response) {
                location.href = "/";
            });
    }

    function goStatistics() {
        location.href = "/app/statistics.jsp";
    }

    function onloadHandler() {
        getTodayDate();
        getUserRole();
        getEvents();
    }

    function getTodayDate() {
        todaysDate = new Date();
        year = todaysDate.getFullYear();
        month = todaysDate.getMonth() + 1;
        day = todaysDate.getDate();
        document.getElementById("date").innerHTML = day + "." + month + "." + year;
    }

    function getUserRole() {
        console.log("role");
        fetch("<c:url value='/api/user/role'/>", {
            "method": "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            return response.json();
        }).then(function (status) {
            console.log(JSON.stringify(status));
            if (status.role == "USER") {
                document.getElementById("button-stat").classList.add("w3-hide");
            } else if (status.role == "ADMIN") {
                document.getElementById("button-stat").classList.remove("w3-hide");
            }
        });
    }

    function getEvents() {
        console.log("event");
        fetch("<c:url value='/api/vote/event'/>", {
            "method": "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            return response.json();
        }).then(function (event) {
            console.log(JSON.stringify(event));
        });
    }
</script>
</body>
</html>
