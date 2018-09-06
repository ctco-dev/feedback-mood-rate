<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" type="text/css" href="../css/index.css">
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">google.charts.load('current', {'packages': ['corechart']});</script>
    <script src="../js/eventStatistics.js"></script>
    <title>Event Statistics</title>
</head>
<body onload="onLoadHandler()">
<div id="feedback-statistics">
    <h2>Feedback Statistics</h2>
    <h3>Statistics menu</h3>
    <p>Chose statistics type</p>
    <div>
        <select name="eventList" id="eventList"></select>
        <h3 id="data"></h3>
    </div>
    <button type="button" onclick="getStats()">Get Statistics</button>
    <table>
        <tr>
            <td>Happy</td>
            <td>Neutral</td>
            <td>Sad</td>
            <td>Empty</td>
        </tr>
        <tr>
            <td id="happy-vote-count"></td>
            <td id="neutral-vote-count"></td>
            <td id="sad-vote-count"></td>
            <td id="empty-vote-count"></td>
        </tr>
    </table>
    <div id="piechart"></div>
</div>
<input type="checkbox" id="show-text-feedback" onclick="showTextFeedback()"/>
<label for="show-text-feedback">Show text feedback</label>
<div id="text-feedback">
</div>
<div>
    <button type="button" onclick="back()">Go back</button>
</div>
</body>
</html>
