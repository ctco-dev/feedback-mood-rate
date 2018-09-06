<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" type="text/css" href="../css/index.css">
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">google.charts.load('current', {'packages': ['corechart']});</script>
    <script src="../js/dayStatistics.js"></script>
    <title>Statistics</title>
</head>
<body onload="onLoadHandler()">
<div id="feedback-statistics">
    <h1 class="h1-center">Feedback Statistics</h1>
    <h3>Statistics menu</h3>
    <p>Chose statistics type</p>

    <form action="select stats">
        <input id="day-stats-radio" type="radio"
               name="statistics-type" value="day" checked="checked" onchange="changeStatsType()"> Daily statistics
        <input id="week-stats-radio" type="radio"
               name="statistics-type" value="week" onchange="changeStatsType()"> Weekly statistics
    </form>
    <div>
        <div id="day-stats-menu">
            <label for="date-select">Pick date: </label>
            <input type="date" id="date-select"/>
        </div>
        <div id="week-stats-menu">
            <label for="week-select">Pick week: </label>
            <input type="week" name="camp-week" id="week-select"
                   min="2018-W27" max="2018-W35" required/>
        </div>
    </div>
    <button type="button" onclick="getStats()">Get Statistics</button>
    <table>
        <tr>
            <td>Happy</td>
            <td>Neutral</td>
            <td>Sad</td>
        </tr>
        <tr>
            <td id="happy-vote-count"></td>
            <td id="neutral-vote-count"></td>
            <td id="sad-vote-count"></td>
        </tr>
    </table>
    <div id="piechart"></div>
</div>
<input type="checkbox" id="show-text-feedback" onclick="showTextFeedback()"/>
<label for="show-text-feedback">Show text feedback</label>
<div id="text-feedback"></div>
<button type="button" onclick="back()">Go back</button>
</body>
</html>
