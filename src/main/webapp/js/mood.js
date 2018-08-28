var feedback = {};

function getDate(date) {
    var monthNames = [
        "January", "February", "March",
        "April", "May", "June", "July",
        "August", "September", "October",
        "November", "December"
    ];
    var day = date.getDate();
    var monthIndex = date.getMonth();
    var year = date.getFullYear();
    return day + ' ' + monthNames[monthIndex] + ' ' + year;
}

function displayDate() {
    document.getElementById("date").innerHTML = getDate(new Date());
}

function submitDailyVote() {
    console.log("Collecting feedback data");
    var comment = document.getElementById("comment").value;
    if (document.getElementById('radio-button-one').checked) {
        console.log("Mood - Happy");
        feedback = {"mood": "HAPPY", "comment": comment};
        submitDailyData();
    } else if (document.getElementById('radio-button-two').checked) {
        console.log("Mood - Neutral");
        feedback = {"mood": "NEUTRAL", "comment": comment};
        submitDailyData();
    } else if (document.getElementById('radio-button-three').checked) {
        console.log("Mood - Sad");
        feedback = {"mood": "SAD", "comment": comment};
        submitDailyData();
    } else {
        console.log("Error - mood not selected!");
        alert("Please select your mood");
    }
}

function submitDailyData() {
    console.log("Submitting data");
    console.log(JSON.stringify(feedback));
    fetch('/api/vote/submit-daily-vote', {
        "method": "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(feedback)
    }).then(function (response) {
        console.log("DONE");
        alert("Thanks for vote!");
        back();
    });
}

function back() {
    location.href = '../app/option.jsp';
}