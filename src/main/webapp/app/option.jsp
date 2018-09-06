<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Option</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" type="text/css" href="../styles.css">
    <script src="http://www.w3schools.com/lib/w3data.js"></script>
    <script src="../js/option.js"></script>
    <script src="../js/date.js"></script>
    <script src="../js/login.js"></script>
</head>
<body onload="onloadHandler()">
<h2>You mood in:</h2>
<p>
    <button id="buttonDay" onclick="handlerEvent('DAY')">Day</button>
    <span id="voteDone">Come back tomorrow!</span><span id="date"></span><br/><br/>
    <button id="event-btn" onclick="handlerEvent('EVENT')">Event</button>
    <span id="eventVote">There are no events to vote!</span><br/>
    <br/><br/><br/><br/><br/>
</p>
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
<p id="button-stat">
    <button id="registerNewUser-btn" type="button" onclick="document.getElementById('modal_addUser').style.display='block'">Create new user</button>
    <br/><br/>
    <button id="deleteUser-btn" type="button" onclick="delete_btn()">Delete user</button>
    <br/><br/>
    <button type="button" onclick="goDailyStatistics()">Daily Statistics</button>
    <br/><br/>
    <button type="button" onclick="goEventStatistics()">Event Statistics</button>
    <br/><br/>
</p>
<button type="button" onclick="goToAddEvent()">Add event</button>
<button type="button" onclick="logout()">Log out</button>
</body>
</html>