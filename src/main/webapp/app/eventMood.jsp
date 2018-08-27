<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Feedback Event Mood page</title>
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
<h3 id="date"></h3>
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
    var feedback = {};

    function getDate(date) {
        var monthNames = [
            "January", "February", "March",
            "April", "May", "June", "July",
            "August", "September", "October",
            "November", "December"
        ];
        var day = date.getDate();
        var monthIndex = date.getMonth();
        var year = date.getFullYear();
        return day + ' ' + monthNames[monthIndex] + ' ' + year;
    }

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
            if (status.dayStatus) {
                document.getElementById("date").innerHTML = getDate(new Date());
            } else if (status.eventStatus) {
                document.getElementById("date").innerHTML = "Event name";
            }
        });
    }

    function submitVote() {
        console.log("Collecting feedback data");
        var comment = document.getElementById("comment").value;
        if (document.getElementById('radio-button-one').checked) {
            console.log("Mood - Happy");
            feedback = {"mood": 1, "comment": comment};
            submitData();
        } else if (document.getElementById('radio-button-two').checked) {
            console.log("Mood - Neutral");
            feedback = {"mood": 2, "comment": comment};
            submitData();
        } else if (document.getElementById('radio-button-three').checked) {
            console.log("Mood - Sad");
            feedback = {"mood": 3, "comment": comment};
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
            alert("Thanks for vote!");
            back();
        });
    }

    function back() {
        location.href = "<c:url value='/app/option.jsp'/>";
    }
</script>
</body>
</html>
