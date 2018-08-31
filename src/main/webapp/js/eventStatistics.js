function showTextFeedback() {
    var checkbox = document.getElementById("show-text-feedback");
    if (checkbox.checked) {
        document.getElementById("text-feedback").classList.remove("w3-hide");
    } else {
        document.getElementById("text-feedback").classList.add("w3-hide");
    }
}

function onLoadHandler() {
    showTextFeedback();
    // getStats();
}

function getStats() {
    var date = {
        "date": document.getElementById("date-select").value,
        "week": document.getElementById("week-select").value
    };
    var week = document.getElementById("week-select").value;
    var happy = 0;
    var neutral = 0;
    var sad = 0;
    var empty = 0;
    console.log(date);
    console.log(week);
    console.log("Collecting Statistics");
    fetch('/api/vote/getStatistics', {
        "method": "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(date)
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
    });
}

function back() {
    location.href = "../app/option.jsp";
}