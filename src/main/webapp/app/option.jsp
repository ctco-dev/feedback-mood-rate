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
<h1 class="h1-center">Feedback Mood Rate Menu</h1>
<div class="inline-buttons">
    <p>
        <button class="buttons ph-button ph-btn-blue" onclick="goToAddEvent()">Create New Event</button>
        <button id="buttonDay" onclick="handlerEvent('DAY')" class="buttons ph-button ph-btn-blue">Today's Mood</button>
        <button id="event-btn" onclick="handlerEvent('EVENT')" class="buttons ph-button ph-btn-blue">Event Mood</button>
        <button id="button-stat-day" type="button" onclick="goDailyStatistics()" class="buttons ph-button ph-btn-blue">Day statistics</button>
        <button id="button-stat-event" type="button" onclick="goEventStatistics()" class="buttons ph-button ph-btn-blue">Event statistics</button>
        <button type="button" onclick="logout()" class="buttons ph-button ph-btn-red">Log Out</button>
    </p>
</div>
<h3 class = "span-center" id="voteDone">You have submitted your mood for today!</h3>
<h3 class = "span-center" id="date"></h3>
<h3 class = "span-center" id="eventVote">There are no events to vote!</h3>
</body>
</html>
