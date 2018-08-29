function checkDayStatus() {
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

function dayMood() {
    fetch('/api/vote/start', {
        "method": "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        location.href = "../app/dayMood.jsp";
    });
}

function handlerEvent(eventType) {
    console.log(JSON.stringify(eventType));
    fetch('/api/vote/start', {
        "method": "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(eventType)
    }).then(function (response) {
        if(eventType == "DAY") {
            location.href = "../app/dayMood.jsp";
        } else if (eventType == "EVENT") {
            location.href = "../app/eventMood.jsp";
        }
    });
}

function logout() {
    fetch('/api/auth/logout', {"method": "POST"})
        .then(function (response) {
            location.href = "../";
        });
}

function goStatistics() {
    location.href = "../app/statistics.jsp";
}

function onloadHandler() {
    getTodayDate();
    getUserRole();
    getEvents();
    checkDayStatus();
}

function getTodayDate() {
    todaysDate = new Date();
    year = todaysDate.getFullYear();
    month = todaysDate.getMonth() + 1;
    day = todaysDate.getDate();
    document.getElementById("date").innerHTML = day + "." + month + "." + year;
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
        if (status.role == "USER") {
            document.getElementById("button-stat").classList.add("w3-hide");
        } else if (status.role == "ADMIN") {
            document.getElementById("button-stat").classList.remove("w3-hide");
        }
    });
}

function getEvents() {
    // console.log("event");
    // fetch('/api/vote/event', {
    //     "method": "GET",
    //     headers: {
    //         'Accept': 'application/json',
    //         'Content-Type': 'application/json'
    //     }
    // }).then(function (response) {
    //     return response.json();
    // }).then(function (event) {
    //     console.log(JSON.stringify(event));
    // });
}