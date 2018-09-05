function createEventData() {
    var event = {};
    event["eventName"] = document.getElementById('eventName').value;;
    event["date"] = document.getElementById('date').value;
    event["deadlineDate"] = document.getElementById('deadlineDate').value;
    console.log(event);
    return event;
}
function saveEvent() {
    var event = createEventData();
    fetch('/api/vote/createEvent', {
        "method": "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }, body: JSON.stringify(event)
    });
    back();
}
function back() {
    location.href = '../app/option.jsp';
} 