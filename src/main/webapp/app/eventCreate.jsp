<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create event</title>
    <script src="../js/saveEvent.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://afeld.github.io/emoji-css/emoji.css" rel="stylesheet">
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../css/moodpages.css">
</head>
<body>
<form class="form2">
    <h2>Create new event...</h2>
    <p>
        <input class="text-input" name="eventName" id="eventName" placeholder="Input name for the Event">
    </p>
    <p>
        <input class="text-input" type="date" name="date" id="date">
    </p>
    <p>
        <input class="text-input" type="date" name="deadlineDate" id="deadlineDate">
    </p>
    <button class="back-button" type="button" onclick="back()">Go back!</button>
    <button class="sub-button" type="button" onclick="saveEvent()">Create Event</button>
</form>
</body>
</html>