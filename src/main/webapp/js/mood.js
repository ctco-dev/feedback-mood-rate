function displayDate() {
    hideSubmitButtonCheck();
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
    fetch('/api/vote/checkSubmit', {
        "method": "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(feedback)
    }).then(function (response) {
        if(response.status === 405){
            console.log("Day vote registration already done!");
            alert("Already voted today!");
            back();
        } else if (response.status == 400) {
            console.log("Cannot get mood status");
            alert("Mood field error!");
        } else if (response.status == 201){
        console.log("DONE");
        alert("Thanks for vote!");
        back();
        }
    });
}

function back() {
    location.href = '../app/option.jsp';
}

function hideSubmitButtonCheck() {
    console.log("Checking day button status");
    fetch('/api/vote/checkDay', {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function(response){
        return response.json();
    }).then(function (day) {
        console.log(JSON.stringify(day));
        if(day){
            buttonSubmit.disabled = true;
        } else {
            buttonSubmit.disabled = false;
        }
    });
}