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
        if(eventType === "DAY") {
            location.href = "../app/dayMood.jsp";
        } else if (eventType === "EVENT") {
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
        if (status.role === "USER") {
            document.getElementById("button-stat").classList.add("w3-hide");
        } else if (status.role === "ADMIN") {
            document.getElementById("button-stat").classList.remove("w3-hide");
        }
    });
}