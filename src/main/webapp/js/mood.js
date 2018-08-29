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

function voteStatus() {
    console.log("checking status");
    fetch('/api/vote/status', {
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
    fetch('/api/vote/submit', {
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

function eventStatus() {
    voteStatus();
    getEvents();
}


