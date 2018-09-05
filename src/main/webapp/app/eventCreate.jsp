<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create event</title>
    <script src="../js/saveEvent.js"></script>
</head>
<body>
<h3>Create Events</h3>
<div>
    <p><b>Event name:</b></p>
    <textarea name="eventName" id="eventName" ></textarea>
</div>
<div>
    <p><b>Event start date:</b></p>
    <input type="date" name="date" id="date" >
</div>
<div>
    <p><b>Event deadline date:</b></p>
    <input type="date" name="deadlineDate" id="deadlineDate" >
</div>
<button type="button" onclick="saveEvent()">Save</button>
</body>
</html>