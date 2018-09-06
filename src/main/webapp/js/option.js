var listOfUsers = [];
function checkDayStatus() {
    fetch('/api/vote/checkDay', {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        return response.json();
    }).then(function (day) {
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
    fetch('/api/vote/event', {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        return response.json();
    }).then(function (event) {
        if (Object.keys(event).length > 0) {
            document.getElementById("event-btn").disabled = false;
            document.getElementById("eventVote").classList.add("w3-hide");
        } else {
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

function goToAddEvent() {
    location.href = "../app/eventCreate.jsp";
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
    fetch('/api/user/role', {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        return response.json();
    }).then(function (status) {
        if (status.role === "USER") {
            document.getElementById("button-stat").classList.add("w3-hide");
        } else if (status.role === "ADMIN") {
            document.getElementById("button-stat").classList.remove("w3-hide");
        }
    });
}

function userRegiseredMessage() {
    var succes_reg = document.getElementById("userRegisteredMessage_h3");
    succes_reg.classList.remove("w3-hide");
    setTimeout(function () {
        succes_reg.classList.add("w3-hide");
    }, 1500);
}

function init() {
    document.getElementById("username-txt").value = " ";
    document.getElementById("password1-txt").value = "";
    if( document.getElementById("password2-txt")){
        document.getElementById("password2-txt").value = "";
    }
}
window.onload = init;

function userList() {
    html = "";
    fetch('/api/user/list', {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        return response.json();
    }).then(function (userList) {
        if(Object.keys(userList).length > 0) {
            for (i = 0; i < Object.keys(userList).length; i++) {
                listOfUsers[i] = userList[Object.keys(userList)[i]].username;
                html += "<option value=" + i + ">" + listOfUsers[i] + "</option>"
            }
            document.getElementById("deleteUsers").classList.remove("w3-hide");
            document.getElementById("deleteUsers").innerHTML = html;
        }
    });
}

function deleteUser() {
    var userToDelete = listOfUsers[document.getElementById("deleteUsers").value];
    var deleteUser = {
        "username": userToDelete
    };
    fetch('/api/auth/deleteUser', {
        "method": "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(deleteUser)
    }).then(function (response){
        if(response.status === 400){
            window.alert(userToDelete + " IS NOT IN DATABASE");
        } else if (response.status === 200) {
            window.alert(userToDelete + " SUCCESSFULLY DELETED");
            userList();
        } else {
            window.alert("ERROR");
        }
    });
}

function delete_btn() {
    document.getElementById('modal_deleteUser').style.display='block';
    userList();
}