<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html class="home-page">
<head>
    <title>W3.CSS Template</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
    <link rel="stylesheet" type="text/css" href="css/index.css">
</head>
<body class="home-page">
<div class="bgimg w3-display-container w3-animate-opacity w3-text-white">
    <div class="w3-display-topleft w3-padding-large w3-xlarge">
        <img id="myImg" src="https://intranet.ctco.lv/app/uploads/2018/01/Logo.png" alt="The Pulpit Rock" width="220"
             height="54">
    </div>
    <div class="w3-display-middle w3-text-dark-grey">
        <br>
        <h1 class="w3-jumbo w3-center w3-text-black"><b>What is your mood today?</b></h1>
        <br>
        <hr class="w3-border-black" style="margin:auto;width:40%">
        <br>
        <h2 class="w3-center">C.T.CO FEEDBACK MOOD RATE</h2>
    </div>
</div>
<div class="w3-display-bottommiddle w3-padding w3-hide-small w3-text-indigo">
    <h1><b>
        <a href="<c:url value='/login.jsp'/>">Log in</a>
    </b></h1>
</div>
</body>
</html>
