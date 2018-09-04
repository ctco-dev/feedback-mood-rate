function login() {
    hideError();
    console.log("start login");
    var usernameTxt = document.getElementById("username-txt");
    var passwordTxt = document.getElementById("password1-txt");
    var dto = {
        "username": usernameTxt.value,
        "password": passwordTxt.value
    };
    console.log("sending login data");
    fetch('/api/auth/login', {
        "method": "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dto)
    }).then(function (response) {
        if (response.status === 200) {
            console.log("login success");
            location.href = "../app/option.jsp";
        } else {
            showError("Username or Password is incorrect!");
        }
    })
}

function hideError() {
    var errorPanel = document.getElementById("error-panel");
    errorPanel.classList.add("w3-hide");
}
function showError(msg) {
    var errorPanel = document.getElementById("error-panel");
    errorPanel.classList.remove("w3-hide");
    w3DisplayData("error-panel", {"message": msg});
}