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

function collectData(){
    var usernameTxt = document.getElementById("username-txt");
    var password1Txt = document.getElementById("password1-txt");
    var password2Txt = document.getElementById("password2-txt");
    var pwd1 = password1Txt.value;
    var pwd2 = password2Txt.value;
    if (pwd1 !== pwd2) {
        showError("Passwords doesn't match!");
        return null;
    }
    var dto = {
        "username": usernameTxt.value,
        "password": pwd1
    }
    return dto;
}

function collectDataAndRegister(){
    hideError();
    var data = collectData();
    if (data) {
        register(data);
    }
}

function register(data) {
    hideError();
    console.log("sending registration data");
    fetch('/api/auth/register', {
        "method": "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(function (response) {
        if (response.status === 200) {
            console.log("registration success");
            userRegiseredMessage();
            init();
        } else if (response.status === 401) {
            showError("Something is wrong!");
        } else {
            response.json().then(function(json) {
                switch(json.errorCode) {
                    case "CONFLICT":
                        showError("A user with the same username already exists!");
                        break;
                    case "BAD_USERNAME":
                        showError("Username is invalid!");
                        break;
                    case "BAD_PASSWORD":
                        showError("Password is invalid!");
                        break;
                    default:
                        showError("Something is wrong!");
                }
            })
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