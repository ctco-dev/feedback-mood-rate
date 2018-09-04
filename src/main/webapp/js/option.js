function checkDayStatus() {
    console.log("Checking day button status");
    fetch('/api/vote/checkDay', {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        return response.json();
    }).then(function (day) {
        console.log(JSON.stringify(day));
        if (day) {
            buttonDay.disabled = true;
            document.getElementById("date").classList.add("w3-hide");
            document.getElementById("voteDone").classList.remove("w3-hide");
        } else {
            buttonDay.disabled = false;
            document.getElementById("date").classList.remove("w3-hide");
            document.getElementById("voteDone").classList.add("w3-hide");
        }
    });
}

function checkEventStatus() {
    console.log("Check event status");
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
            console.log("checking event staus");
            document.getElementById("event-btn").disabled = false;
            document.getElementById("eventVote").classList.add("w3-hide");
        } else {
            console.log("Checked status");
            document.getElementById("event-btn").disabled = true;
            document.getElementById("eventVote").classList.remove("w3-hide");
        }
    });
}

function handlerEvent(eventType) {
    if (eventType === "DAY") {
        location.href = "../app/dayMood.jsp";
    } else if (eventType === "EVENT") {
        location.href = "../app/eventMood.jsp";
    }
}

function logout() {
    fetch('/api/auth/logout', {"method": "POST"})
        .then(function (response) {
            location.href = "../";
        });
}

function goDailyStatistics() {
    location.href = "../app/dayStatistics.jsp";
}


function goEventStatistics() {
    location.href = "../app/eventStatistics.jsp";
}

function onloadHandler() {
    checkDayStatus();
    checkEventStatus();
    document.getElementById("date").innerHTML = getDate(new Date());
    getUserRole();
}

function getUserRole() {
    console.log("role");
    fetch('/api/user/role', {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        return response.json();
    }).then(function (status) {
        console.log(JSON.stringify(status));
        if (status.role === "USER") {
            document.getElementById("button-stat").classList.add("w3-hide");
        } else if (status.role === "ADMIN") {
            document.getElementById("button-stat").classList.remove("w3-hide");
        }
    });
}