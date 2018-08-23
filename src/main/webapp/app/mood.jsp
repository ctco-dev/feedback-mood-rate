<%--
  Created by IntelliJ IDEA.
  User: evita.ritina
  Date: 8/21/2018
  Time: 17:48
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Feedback Mood page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://afeld.github.io/emoji-css/emoji.css" rel="stylesheet">
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        #Submit {
            float: right;
            width: 225px;
        }

        #Go_back {
            float: left;
            width: 130px;
        }

        i {
            font-size: 60px;
        }
    </style>
</head>
<body onload="voteStatus()">
<p id="date"></p>
<form name="mood_form" id="formId" method="post">
    <p>There will be event name</p>
    <ul>
        <li> Choose your mood!</li>
        <li>
            <i class="em em-slightly_smiling_face"></i>
            <input type="radio" id="radio-button-one" name="first_item" value="1" title="happy"/>
        </li>
        <li>
            <i class="em em-neutral_face"></i>
            <input type="radio" id="radio-button-two" name="first_item" value="2" title="neutral"/>
        </li>
        <li>
            <i class="em em-white_frowning_face"></i>
            <input type="radio" id="radio-button-three" name="first_item" value="3" title="unhappy"/>
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
        <button type="button" onclick="submitVote()">Submit</button>
    </div>
</form>
<script>
    todaysDate = new Date();
    year = todaysDate.getFullYear();
    month = todaysDate.getMonth() + 1;
    day = todaysDate.getDate();
    document.getElementById("date").innerHTML = month + "/" + day + "/" + year;

    var feedback = {};

    function voteStatus() {
        console.log("checking status");
        fetch("<c:url value='/api/vote/status'/>", {
            "method": "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            return response.json();
        }).then(function (status) {
            console.log(JSON.stringify(status));
        });
    }

    function back() {
        location.href = "<c:url value='/app/option.jsp'/>";
    }

    function submitVote() {
        console.log("Collecting feedback data");
        var mood;
        if (document.getElementById('radio-button-one').checked) {
            console.log("Mood - Happy");
            feedback = {"mood": 1, "comment": document.getElementById("comment").value};
            submitData();
        } else if (document.getElementById('radio-button-two').checked) {
            console.log("Mood - Neutral");
            feedback = {"mood": 2, "comment": document.getElementById("comment").value};
            submitData();
        } else if (document.getElementById('radio-button-three').checked) {
            console.log("Mood - Sad");
            feedback = {"mood": 3, "comment": document.getElementById("comment").value};
            submitData();
        } else {
            console.log("Error - mood not selected!");
            alert("Please select your mood");
        }
    }

    function submitData() {
        console.log("Submitting data");
        console.log(JSON.stringify(feedback));
        fetch("<c:url value='/api/vote/submit'/>", {
            "method": "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(feedback)
        }).then(function (response) {
            console.log("DONE");
        });
    }
</script>
</body>
</html>
