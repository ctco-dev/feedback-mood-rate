<%--
  Created by IntelliJ IDEA.
  User: evita.ritina
  Date: 8/21/2018
  Time: 17:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Feedback Mood page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://afeld.github.io/emoji-css/emoji.css" rel="stylesheet">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>

    </style>
</head>
<body>
<p id="date"></p>
<form name="submit" id="submit" action="#" method="post">
    <p>There will be event name</p>
    <ul>
        <li> Choose your mood!</li>
        <li>
            <i class="em em-slightly_smiling_face" style="font-size:60px;"></i>
            <input type="radio" id="radiobuttonone" name="first_item" value="1"  title="happy"/>
        </li>
        <li>
            <i class="em em-neutral_face" style="font-size:60px;"></i>
            <input type="radio" id="radiobuttontwo" name="first_item" value="2" title="neutral"/>
        </li>
        <li>
            <i class="em em-white_frowning_face" style="font-size:60px;"></i>
            <input type="radio" id="radiobuttonthree" name="first_item" value="3" title="unhappy"/>
        </li>
    </ul>
    <div class="form-group">
        <label for="comment">Comment:</label>
        <textarea class="form-control" rows="5" id="comment"></textarea>
    </div>
    <div style="float: left; width: 130px" id="Go back" >
        <button type="button" onclick="back()">Go back!</button>
    </div>
    <div style="float: right; width: 225px" id="Submit" >
        <button type="button" onclick="submit()">Submit</button>
    </div>
</form>
<script>
    n =  new Date();
    y = n.getFullYear();
    m = n.getMonth() + 1;
    d = n.getDate();
    document.getElementById("date").innerHTML = m + "/" + d + "/" + y;
</script>
</body>
</html>
