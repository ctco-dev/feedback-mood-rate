<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Feedback Day Mood page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://afeld.github.io/emoji-css/emoji.css" rel="stylesheet">
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../styles.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="../js/mood.js"></script>
    <script src="../js/date.js"></script>
</head>
<body onload="displayDate()">
<h3 id="date"></h3>
<form name="mood_form" id="formId" method="post">
    <p>There will be event name</p>
    <ul>
        <li> Choose your mood!</li>
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
        <button type="button" onclick="back()">Go back!</button>
    </div>
    <div id="Submit">
        <button type="button" onclick="submitDailyVote()">Submit</button>
    </div>
</form>
</body>
</html>
