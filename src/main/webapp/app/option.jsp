<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Option</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" type="text/css" href="../css/option.css">
    <script src="http://www.w3schools.com/lib/w3data.js"></script>
    <script src="../js/option.js"></script>
    <script src="../js/date.js"></script>
    <script src="../js/login.js"></script>
</head>
<body class="bgimg" onload="onloadHandler()">
<h1 class="h1-center">Feedback Mood Rate Menu</h1>
<div class="inline-buttons">
    <p>
        <button id="buttonDay" onclick="handlerEvent('DAY')" class="buttons ph-button ph-btn-blue">Today's Mood</button>
        <button id="event-btn" onclick="handlerEvent('EVENT')" class="buttons ph-button ph-btn-blue">Event Mood</button>
        <button type="button" onclick="logout()" class="buttons ph-button ph-btn-red">Log Out</button>
    </p>
</div>
<h3 class = "span-center w3-hide" id="voteDone">You have submitted your mood for today!</h3>
<h3 class = "span-center" id="date"></h3>
<h3 class = "span-center w3-hide" id="eventVote">There are no events to vote!</h3>
<div id="admin-panel" class="footer-adm w3-hide" >
    <h4 class = "span-center">Admin Panel:</h4>
    <div class="inline-buttons">
        <p>
            <button id="button-create-ev" type="button" class="buttons ph-button ph-btn-blue" onclick="goToAddEvent()">
                Create New Event
            </button>
            <button type="button" onclick="goDailyStatistics()" class="buttons ph-button ph-btn-blue">Day statistics
            </button>
            <button type="button" onclick="goEventStatistics()" class="buttons ph-button ph-btn-blue">Event statistics
            </button>
            <button id="registerNewUser-btn" type="button" onclick="document.getElementById('modal_addUser').style.display='block'">Create new user</button>
            <button id="deleteUser-btn" type="button" onclick="delete_btn()">Delete user</button>
        </p>
    </div>
</div>
<div id="modal_addUser" class="w3-modal">
    <div class="w3-modal-content">
        <div class="w3-container">
            <header>
                <div id="error-panel" class="error-panel w3-panel w3-red w3-hide">
                    <h3>Error!</h3>
                    <p>{{message}}</p>
                </div>
                <span onclick="document.getElementById('modal_addUser').style.display='none'"
                      class="w3-button w3-display-topright">&times;</span>
                <h5>NEW USER REGISTRATION</h5>
                <h3 id="userRegisteredMessage_h3" class="w3-hide">USER SUCCESSFULLY REGISTERED</h3>
            </header>
            <div>
                <p>
                    <input id="username-txt" class="w3-input" type="text" style="width:90%" required>
                    <label for="username-txt">Name</label>
                </p>
                <p>
                    <input id="password1-txt" class="w3-input" type="password" style="width:90%" required>
                    <label for="password1-txt">Password</label>
                </p>
                <p id="password2-grp">
                    <input id="password2-txt" class="w3-input input-field" type="password" required>
                    <label for="password2-txt">Repeat Password</label>
                </p>
            </div>
            <footer>
                <p>
                    <button id="register-btn" type="button" class="w3-button w3-section w3-indigo w3-ripple" onclick="collectDataAndRegister()">Register</button>
                </p>
            </footer>
        </div>
    </div>
</div>
<div id="modal_deleteUser" class="w3-modal">
    <div class="w3-modal-content">
        <div class="w3-container">
            <header>
                <span onclick="document.getElementById('modal_deleteUser').style.display='none'"
                      class="w3-button w3-display-topright">&times;</span>
                <h3>DELETE USER</h3>
            </header>
            <div>
                <select name="deleteUsers" id="deleteUsers" required></select>
            </div>
            <footer>
                <p>
                    <button id="delete-btn" type="button" class="w3-button w3-section w3-indigo w3-ripple" onclick="deleteUser()">DELETE</button>
                </p>
            </footer>
        </div>
    </div>
</div>
</body>
</html>