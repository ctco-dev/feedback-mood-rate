<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Option</title>
</head>
<body>
<button type="button" onclick="handlerEvent('DAY')">Day</button>
<button type="button" onclick="handlerEvent('EVENT')">Event</button>
<button type="button" onclick="logout()">Log out</button>
<button type="button" onclick="goStatistics()">Statistics</button>

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
</script>
</body>
</html>
