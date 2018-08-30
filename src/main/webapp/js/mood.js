var eventName = [];
var html = "";

function displayDate() {
    hideSubmitButtonCheck();
    document.getElementById("date").innerHTML = getDate(new Date());
}

function submitDailyVote() {
    var feedback = {};
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
    var feedbackEvent = {};
    console.log("Collecting feedback data");
    var comment = document.getElementById("comment").value;
    var event = eventName[document.getElementById("eventList").value];
    if (document.getElementById('radio-button-mood-happy').checked) {
        feedbackEvent = {"eventName": event, "mood": "HAPPY", "comment": comment};
        submitEventData(feedbackEvent);
    } else if (document.getElementById('radio-button-mood-neutral').checked) {
        feedbackEvent = {"eventName": event, "mood": "NEUTRAL", "comment": comment};
        submitEventData(feedbackEvent);
    } else if (document.getElementById('radio-button-mood-sad').checked) {
        feedbackEvent = {"eventName": event, "mood": "SAD", "comment": comment};
        submitEventData(feedbackEvent);
    } else {
        console.log("Error - mood not selected!");
        alert("Please select your mood");
    }
}

function submitEventData(feedbackEvent) {
    console.log("Submitting data");
    console.log(JSON.stringify(feedbackEvent));
    fetch('/api/vote/submitEventVote', {
        "method": "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(feedbackEvent)
    }).then(function (response) {
        if (response.status === 400) {
            alert("No events for vote!");
        } else {
            console.log("DONE");
            alert("Thanks for vote!");
            back();
        }
    });
}

function submitDailyData(feedback) {
    console.log("Submitting data");
    console.log(JSON.stringify(feedback));
    fetch('/api/vote/checkSubmit', {
        "method": "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(feedback)
    }).then(function (response) {
        if(response.status === 405){
            console.log("Day vote registration already done!");
            alert("Already voted today!");
            back();
        } else if (response.status == 400) {
            console.log("Cannot get mood status");
            alert("Mood field error!");
        } else if (response.status == 201){
        console.log("DONE");
        alert("Thanks for vote!");
        back();
        }
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
            document.getElementById("data").classList.add("w3-hide");
            document.getElementById("eventList").classList.remove("w3-hide");
            document.getElementById("eventList").innerHTML = html;
        } else {
            document.getElementById("data").classList.remove("w3-hide");
            document.getElementById("eventList").classList.add("w3-hide");
            document.getElementById("data").innerHTML = "No events for vote";
            document.getElementById("submit-btn").disabled = true;
            document.getElementById("submit-btn").classList.add("w3-disabled");
        }
    });
}

function getEventVotes() {
    console.log("eventVote");
    fetch('/api/vote/eventVote', {
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

function hideSubmitButtonCheck() {
    console.log("Checking day button status");
    fetch('/api/vote/checkDay', {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function(response){
        return response.json();
    }).then(function (day) {
        console.log(JSON.stringify(day));
        if(day){
            buttonSubmit.disabled = true;
        } else {
            buttonSubmit.disabled = false;
        }
    });
}