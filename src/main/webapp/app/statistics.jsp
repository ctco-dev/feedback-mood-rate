<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" type="text/css" href="../styles.css">
    <script src="../js/statistics.js"></script>
    <title>Statistics</title>
</head>
<body onload="onLoadHandler()">
<div id="feedback-statistics">
    <h2>Feedback Statistics</h2>
    <h3>Statistics menu</h3>
    <p>Chose statistics type</p>
    <button type="button" onclick="">Day</button>
    <button type="button" onclick="">Week</button>
    <button type="button" onclick="">Event</button>
    <div>
        <div>
            <label for="date-select">Pick date: </label>
            <input type="date" id="date-select"/>
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
</div>
<input type="checkbox" id="show-text-feedback" onclick="showTextFeedback()"/>
<label for="show-text-feedback">Show text feedback</label>
<%--Start--%>
<%--Here will be show text feedbacks--%>
<div id="text-feedback">
    <ul>
        <li>Mood - sad</li>
        <ul>
            <li>I lost my dog</li>
        </ul>

        <li>Mood - Happy</li>
        <ul>
            <li>I have new dog</li>
        </ul>
    </ul>
</div>
<%--END--%>
<button type="button" onclick="back()">Go back</button>
</body>
</html>
