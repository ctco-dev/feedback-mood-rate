function createEventData() {
    var event = {};
    event["eventName"] = document.getElementById('eventName').value;
    event["date"] = document.getElementById('date').value;
    event["deadlineDate"] = document.getElementById('deadlineDate').value;
    console.log(event);
    return event;
}
function saveEvent() {
    var event = createEventData();
    if(event.eventName===""||event.date===""||event.deadlineDate===""){
        alert("You must fill out all fields to create an event!")
        return;
    }
    fetch('/api/vote/createEvent', {
        "method": "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }, body: JSON.stringify(event)
    }).then(function (response) {
        if (response.status === 400 ) {
            alert("Can't create this event");
        }
        else {
            console.log("DONE");
            alert("Event has been created!");
            back();
        }
    });
}
function back() {
    location.href = '../app/option.jsp';
}