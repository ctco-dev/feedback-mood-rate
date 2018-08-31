<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Option</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" type="text/css" href="../styles.css">
    <script src="../js/option.js"></script>
    <script src="../js/date.js"></script>
</head>
<body onload="onloadHandler()">
<h2>You mood in:</h2>
<p>
    <button id="buttonDay" onclick="handlerEvent('DAY')">Day</button>
    <span id="voteDone">Come back tomorrow!</span><span id="date"></span><br/><br/>
    <button type="button" onclick="handlerEvent('EVENT')">Event</button>
    <br/><br/><br/><br/><br/>
</p>
<p id="button-stat">
    <button type="button" onclick="goStatistics()">Daily Statistics</button>
    <br/><br/>
    <button type="button" onclick="goEventStatistics()">Event Statistics</button>
    <br/><br/>
</p>
<button type="button" onclick="logout()">Log out</button>
</body>
</html>
