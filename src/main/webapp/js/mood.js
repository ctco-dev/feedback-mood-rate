function displayDate() {
    document.getElementById("date").innerHTML = getDate(new Date());
}

function submitDailyVote() {
    var feedback = {};
    console.log("Collecting feedback data");
    var comment = document.getElementById("comment").value;
    if (document.getElementById('radio-button-mood-happy').checked) {
        console.log("Mood - Happy");
        feedback = {"mood": "HAPPY", "comment": comment};
        submitDailyData(feedback);
    } else if (document.getElementById('radio-button-mood-neutral').checked) {
        console.log("Mood - Neutral");
        feedback = {"mood": "NEUTRAL", "comment": comment};
        submitDailyData(feedback);
    } else if (document.getElementById('radio-button-mood-sad').checked) {
        console.log("Mood - Sad");
        feedback = {"mood": "SAD", "comment": comment};
        submitDailyData(feedback);
    } else {
        console.log("Error - mood not selected!");
        alert("Please select your mood");
    }
}

function submitEventVote() {

}

function submitDailyData(feedback) {
    console.log("Submitting data");
    console.log(JSON.stringify(feedback));
    fetch('/api/vote/submitDailyVote', {
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