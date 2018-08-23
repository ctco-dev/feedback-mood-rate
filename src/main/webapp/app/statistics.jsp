<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <title>Statistics</title>
    <style>
        #text-feedback {
            width: 600px;
        }
    </style>
</head>
<body onload="showTextFeedback()">
<%--Start--%>
<%--Here should be statistics from database--%>
<div id="feedback-statistics">
    <%--Title should include statictics type--%>
    <h2>Feedback for Monday - 23/04/2018</h2>
    <table>
        <tr>
            <td>Happy</td>
            <td>Neutral</td>
            <td>Sad</td>
        </tr>
        <tr>
            <td>16</td>
            <td>5</td>
            <td>8</td>
        </tr>
    </table>
</div>
<%--END--%>
<input type="checkbox" id="show-text-feedback" onclick="showTextFeedback()"/>
<label for="show-text-feedback">Show text feedback</label>
<%--Start--%>
<%--Here will be show text feedbacks--%>
<div id="text-feedback">
    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque felis nisi, placerat ac vulputate viverra,
        imperdiet sed nulla. Curabitur at nunc at mi elementum commodo. Donec sed sodales eros. Nam dictum est vitae
        lobortis dapibus. Suspendisse mattis magna id massa aliquam, rhoncus molestie purus efficitur. Fusce ut urna ex.
        Pellentesque</p>
    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque felis nisi, placerat ac vulputate viverra,
        imperdiet sed nulla. Curabitur at nunc at mi elementum commodo. Donec sed sodales eros. Nam dictum est vitae
        lobortis dapibus. Suspendisse mattis magna id massa aliquam, rhoncus molestie purus efficitur. Fusce ut urna ex.
        Pellentesque </p>
    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque felis nisi, placerat ac vulputate viverra,
        imperdiet sed nulla. Curabitur at nunc at mi elementum commodo. Donec sed sodales eros. Nam dictum est vitae
        lobortis dapibus. Suspendisse mattis magna id massa aliquam, rhoncus molestie purus efficitur. Fusce ut urna ex.
        Pellentesque</p>
</div>
<%--END--%>
<button type="button" onclick="back()">Go back</button>
<script>
    function showTextFeedback() {
        var checkbox = document.getElementById("show-text-feedback");
        if (checkbox.checked) {
            document.getElementById("text-feedback").classList.remove("w3-hide");
        } else {
            document.getElementById("text-feedback").classList.add("w3-hide");
        }
    }

    function back() {
        location.href = "/app/option.jsp";
    }
</script>
</body>
</html>
