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

function goStatistics() {
    location.href = "../app/statistics.jsp";
}

function onloadHandler() {
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