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
    <link rel="stylesheet" type="text/css" href="../css/moodpages.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="../js/mood.js"></script>
</head>
<body onload="getStatus()">
<form class="form2" name="mood_form" id="formId" method="post">
    <h2>Choose your mood for event!</h2>
    <p>
        <select class="text-input" name="eventList" id="eventList" required></select>
    <h3 id="data"></h3>
    </p>
    <p>
        <i class="em em-slightly_smiling_face"></i>
        <input type="radio" id="radio-button-mood-happy" name="first_item" value="happy" title="happy"/>
        <i class="em em-neutral_face"></i>
        <input type="radio" id="radio-button-mood-neutral" name="first_item" value="neutral" title="neutral"/>
        <i class="em em-white_frowning_face"></i>
        <input type="radio" id="radio-button-mood-sad" name="first_item" value="sad" title="unhappy"/>
    </p>
    <p>
        <input id="comment" class="text-input" placeholder="Input comment about your mood...">
    </p>
    <button class="back-button" type="button" onclick="back()">Go back!</button>
    <button class="sub-button" id="submit-btn" type="button" onclick="submitEventVote()">Submit</button>
</form>
</body>
</html>
