function drawChart(h, s, n, e) {
    var data = google.visualization.arrayToDataTable([
        ['Mood state', 'Count'],
        ['Happy', h],
        ['Sad', s],
        ['Neutral', n],
        ['Empty', e],
    ]);
    var options = {'title':'Mood rate statistics', 'width':550, 'height':400};
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
    var happy = 0;
    var neutral = 0;
    var sad = 0;
    var empty = 0;
    console.log("Checking selected statistics type");
    var statsDto = {};


    var dayStats = document.getElementById("day-stats-radio");
    var weekStats = document.getElementById("week-stats-radio");
    if (dayStats.checked) {
        console.log("Statistics type - Day");
        statsDto = {"date": document.getElementById("date-select").value};
    }
    else if (weekStats.checked) {
        console.log("Statistics type - Week");
        statsDto = {
            "week": document.getElementById("week-select").value
        };
    }

    console.log(statsDto);
    console.log("Collecting Statistics from Database");
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
            console.log(k, stats[k]);
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
        }
        document.getElementById("happy-vote-count").innerHTML = happy.toString();
        document.getElementById("neutral-vote-count").innerHTML = neutral.toString();
        document.getElementById("sad-vote-count").innerHTML = sad.toString();
        console.log("DONE")
        drawChart(happy, sad, neutral, empty);
    });
}
function back() {
    location.href = "../app/option.jsp";
}