<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" type="text/css" href="../css/statisticPages.css">
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">google.charts.load('current', {'packages': ['corechart']});</script>
    <script src="../js/dayStatistics.js"></script>
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,300,300italic,400italic,600' rel='stylesheet' type='text/css'>
    <link href="//netdna.bootstrapcdn.com/font-awesome/3.1.1/css/font-awesome.css" rel="stylesheet">
    <title>Statistics</title>
</head>
<body onload="onLoadHandler()">
<div id="feedback-statistics" class="split left">
    <h1 class="h1-center">Feedback Statistics</h1>
    <div class="centered">
    <form class="form">
        <h3>Statistics menu</h3>
        <p>Chose statistics type</p>
        <input id="day-stats-radio" type="radio"
               name="statistics-type" value="day" checked="checked" onchange="changeStatsType()"> Daily statistics
        <input id="week-stats-radio" type="radio"
               name="statistics-type" value="week" onchange="changeStatsType()"> Weekly statistics
        <p></p>
        <div>
            <div id="day-stats-menu">
                <label for="date-select">Pick date: </label>
                <input class="text-input" type="date" id="date-select"/>
            </div>
            <div id="week-stats-menu">
                <label for="week-select">Pick week: </label>
                <input class="text-input" type="week" name="camp-week" id="week-select"
                       min="2018-W27" max="2018-W35" required/>
            </div>
        </div>
        <p>
        <input type="checkbox" id="show-text-feedback" onclick="showTextFeedback()"/>
        <label for="show-text-feedback">Show text feedback</label>
        </p>
        <p>
        <button class="get-stat-button" type="button" onclick="getStats()">Get Statistics</button>
        <button class="back-button" type="button" onclick="back()">Go back</button>
        </p>
    </form>
</div>
</div>
<div class="split right">
<div class="splitdiv bottom">
    <h2 class="h2-center">Feedback comments</h2>
    <div id="text-feedback" class="centered"></div>

</div>
<div class="splitdiv top">
    <div id="piechart" class="centered"></div>
</div>

</div>
</body>
</html>
