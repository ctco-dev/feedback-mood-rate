var feedback = {};
var eventName = [];
var html = "";

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

function displayDate() {
    document.getElementById("date").innerHTML = getDate(new Date());
}

function submitDailyVote() {
    console.log("Collecting feedback data");
    var comment = document.getElementById("comment").value;
    if (document.getElementById('radio-button-mood-happy').checked) {
        console.log("Mood - Happy");
        feedback = {"mood": "HAPPY", "comment": comment};
        submitDailyData(feedback);
    } else if (document.getElementById('radio-button-mood-neutral').checked) {
        console.log("Mood - Neutral");
        feedback = {"mood": "NEUTRAL", "comment": comment};
        submitDailyData(feedback);
    } else if (document.getElementById('radio-button-mood-sad').checked) {
        console.log("Mood - Sad");
        feedback = {"mood": "SAD", "comment": comment};
        submitDailyData(feedback);
    } else {
        console.log("Error - mood not selected!");
        alert("Please select your mood");
    }
}

function submitEventVote() {

}

function submitDailyData(feedback) {
    console.log("Submitting data");
    console.log(JSON.stringify(feedback));
    fetch('/api/vote/submitDailyVote', {
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
    location.href = '../app/option.jsp';
}

function getEvents() {
    console.log("event");
    fetch('/api/vote/event', {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        return response.json();
    }).then(function (event) {
        console.log(JSON.stringify(event));
        if (Object.keys(event).length > 0) {
            for (i = 0; i < Object.keys(event).length; i++) {
                eventName[i] = event[Object.keys(event)[i]].eventName;
                html += "<option value=" + i  + ">" +eventName[i] + "</option>"
            }
            document.getElementById("data0").classList.add("w3-hide");
            document.getElementById("datas").classList.remove("w3-hide");
            document.getElementById("datas").innerHTML = html;
        } else {
            document.getElementById("data0").classList.remove("w3-hide");
            document.getElementById("datas").classList.add("w3-hide");
            document.getElementById("data0").innerHTML = "No events for vote";
            document.getElementById("submit-btn").disabled = true;
            document.getElementById("submit-btn").classList.add("w3-disabled");
        }
    });
}

function getEventVotes() {
    console.log("eventVote");
    fetch('/api/vote/eventvote', {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        return response.json();
    }).then(function (eventVote) {
        console.log(JSON.stringify(eventVote));
    });
}

function getStatus() {
    getEvents();
    getEventVotes();
}