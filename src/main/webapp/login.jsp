<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>W3.CSS</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" type="text/css" href="styles.css">
    <script src="http://www.w3schools.com/lib/w3data.js"></script>
    <script src="./js/login.js"></script>
</head>
<body>
<header class="w3-container w3-teal">
    <h1>Login</h1>
</header>
<div class="w3-container w3-half w3-margin-top">
    <form class="w3-container w3-card-4">
        <div id="error-panel" class="w3-panel w3-red w3-hide">
            <h3>Error!</h3>
            <p>{{message}}</p>
        </div>
        <p>
            <input id="username-txt" class="w3-input input-field" type="text" required>
            <label for="username-txt">Name</label>
        </p>
        <p>
            <input id="password1-txt" class="w3-input input-field" type="password" required>
            <label for="password1-txt">Password</label>
        </p>
        <p>
            <button id="login-btn" type="button" class="w3-button w3-section w3-teal w3-ripple" onclick="login()">Log
                in
            </button>
        </p>
    </form>
</div>
</body>
</html>
