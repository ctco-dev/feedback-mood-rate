<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Feedback Event Mood page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://afeld.github.io/emoji-css/emoji.css" rel="stylesheet">
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../styles.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="../js/mood.js"></script>
</head>
<body onload="getStatus()">
<h3>Choose your mood!</h3>
<form name="mood_form" id="formId" method="post">
    <div>
        <select name="eventList" id="eventList" required></select>
        <h3 id="data"></h3>
    </div>
    <br/>
    <ul>
        <li>
            <i class="em em-slightly_smiling_face"></i>
            <input type="radio" id="radio-button-mood-happy" name="first_item" value="happy" title="happy"/>
        </li>
        <li>
            <i class="em em-neutral_face"></i>
            <input type="radio" id="radio-button-mood-neutral" name="first_item" value="neutral" title="neutral"/>
        </li>
        <li>
            <i class="em em-white_frowning_face"></i>
            <input type="radio" id="radio-button-mood-sad" name="first_item" value="sad" title="unhappy"/>
        </li>
    </ul>
    <div class="form-group">
        <label for="comment">Comment:</label>
        <textarea class="form-control" rows="5" id="comment"></textarea>
    </div>
    <div id="Go_back">
        <button type="button" class="w3-btn w3-teal w3-ripple" onclick="back()">Go back!</button>
    </div>
    <div id="Submit">
        <button type="button" id="submit-btn" class="w3-btn w3-teal w3-ripple" onclick="submitEventVote()">Submit
        </button>
    </div>
</form>
</body>
</html>
