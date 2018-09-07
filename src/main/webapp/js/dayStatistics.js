function drawChart(happyCount, sadCount, neutralCount, emptyCount) {
    var data = google.visualization.arrayToDataTable([
        ['Mood state', 'Count'],
        ['Happy', happyCount],
        ['Sad', sadCount],
        ['Neutral', neutralCount],
        ['Empty', emptyCount],
    ]);
    var options = {'title': 'Mood rate day statistics', 'width': 550, 'height': 400};
    var chart = new google.visualization.PieChart(document.getElementById('piechart'));
    chart.draw(data, options);
}

function showTextFeedback() {
    var checkbox = document.getElementById("show-text-feedback");
    if (checkbox.checked) {
        document.getElementById("text-feedback").classList.remove("w3-hide");
    } else {
        document.getElementById("text-feedback").classList.add("w3-hide");
    }
}
function changeStatsType() {
    var dayStats = document.getElementById("day-stats-radio");
    var weekStats = document.getElementById("week-stats-radio");
    if (dayStats.checked) {
        document.getElementById("day-stats-menu").classList.remove("w3-hide");
        document.getElementById("week-stats-menu").classList.add("w3-hide");
    } else if (weekStats.checked) {
        document.getElementById("week-stats-menu").classList.remove("w3-hide");
        document.getElementById("day-stats-menu").classList.add("w3-hide");
    }
}

function onLoadHandler() {
    showTextFeedback();
    changeStatsType();
}

function getStats() {
    document.getElementById('text-feedback').innerHTML = '';
    var happy = 0;
    var neutral = 0;
    var sad = 0;
    var empty = 0;
    var commentsArr = [];
    var statsDto = {};
    var dayStats = document.getElementById("day-stats-radio");
    var weekStats = document.getElementById("week-stats-radio");
    if (dayStats.checked) {
        statsDto = {"date": document.getElementById("date-select").value};
    }
    else if (weekStats.checked) {
        statsDto = {
            "week": document.getElementById("week-select").value
        };
    }
    fetch('/api/vote/getDailyStatistics', {
        "method": "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(statsDto)
    }).then(function (response) {
        return response.json();
    }).then(function (stats) {
        for (var k in stats) {
            switch (stats[k].mood) {
                case "HAPPY":
                    happy++;
                    break;
                case "NEUTRAL":
                    neutral++;
                    break;
                case "SAD":
                    sad++;
                    break;
                default:
                    empty++;
                    break;
            }
            commentsArr.push(stats[k]);
        }
        if (document.getElementById('user_Feedback')) {
            var previousFeedback = document.getElementsByClassName('user_Feedback');
            previousFeedback.parentNode.removeChild(previousFeedback);
        }
        commentsArr.forEach(function (element) {
            var feedback = document.createElement('p');
            feedback.setAttribute("class", "user_Feedback");
            console.log(element.comment);
            if(element.comment != "") {
                feedback.innerHTML = "User mood: " + element.mood + "<br>"
                    + "User comment: " + element.comment + "<br>";

                document.getElementById('text-feedback').appendChild(feedback);
            }
        });
        drawChart(happy, sad, neutral, empty);
    });
}
function back() {
    location.href = "../app/option.jsp";
}