function drawChart(happyCount, sadCount, neutralCount, emptyCount) {
    var data = google.visualization.arrayToDataTable([
        ['Mood state', 'Count'],
        ['Happy', happyCount],
        ['Sad', sadCount],
        ['Neutral', neutralCount],
        ['Empty', emptyCount],
    ]);
    var options = {'title':'Mood rate for event: statistics', 'width':550, 'height':400};
    var chart = new google.visualization.PieChart(document.getElementById('piechart'));
    chart.draw(data, options);
}

var eventName = [];
var html = "";
var eventVotes = [];

function showTextFeedback() {
    var checkbox = document.getElementById("show-text-feedback");
    if (checkbox.checked) {
        document.getElementById("text-feedback").classList.remove("w3-hide");
    } else {
        document.getElementById("text-feedback").classList.add("w3-hide");
    }
}

function onLoadHandler() {
    showTextFeedback();
    getAllEvents();
    getAllEventVotes();
}

function getAllEvents() {
    fetch('/api/vote/allEvent', {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        return response.json();
    }).then(function (event) {
        for (i = 0; i < Object.keys(event).length; i++) {
            eventName[i] = event[Object.keys(event)[i]].eventName;
            html += "<option value=" + i + ">" + eventName[i] + "</option>";
        }
        document.getElementById("eventList").innerHTML = html;
    });
}

function getAllEventVotes() {
    fetch('/api/vote/allEventVote', {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        return response.json();
    }).then(function (eventVote) {
        for (i = 0; i < Object.keys(eventVote).length; i++) {
            eventVotes[i] = eventVote[Object.keys(eventVote)[i]];
        }
    });
}

function getStats() {
    var commentsHtml = "";
    var happy = 0;
    var neutral = 0;
    var sad = 0;
    var empty = 0;
    var event = eventName[document.getElementById("eventList").value];
    for (i = 0; i < eventVotes.length; i++) {
        if (eventVotes[i].eventName === event) {
            switch (eventVotes[i].mood) {
                case "HAPPY":
                    happy++;
                    break;
                case "NEUTRAL":
                    neutral++;
                    break;
                case "SAD":
                    sad++;
                    break;
                case "EMPTY":
                    empty++;
                    break;
            }
            if (eventVotes[i].comment !== undefined && eventVotes[i].comment.length > 0) {
                commentsHtml += "<p>" + eventVotes[i].comment + "</p>";
            }
            document.getElementById("text-feedback").innerHTML = commentsHtml;
        }
    }
    document.getElementById("happy-vote-count").innerHTML = happy.toString();
    document.getElementById("neutral-vote-count").innerHTML = neutral.toString();
    document.getElementById("sad-vote-count").innerHTML = sad.toString();
    document.getElementById("empty-vote-count").innerHTML = empty.toString();
    drawChart(happy, sad, neutral, empty);
}

function back() {
    location.href = "../app/option.jsp";
}