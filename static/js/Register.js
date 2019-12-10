function register() {

    let name = document.getElementById("username").value;
    let pass = document.getElementById('password').value;
    let email = document.getElementById("email").value;

    const regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@!%*#?&\/])[A-Za-z\d$@$!%*#?&]{5,10}$/;
    if (regex.exec(pass) == null) {
        alert("Password does not follow the rule")
        return;
    }

    const data = {username: name, password: pass, email : email};
    console.log(data)
    fetch('/register', {
        method: 'post',
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json",
        },
    }).then(res => res.json())
        .then(data => {

            if(data.success == true){
                alert(data.message)
                window.location.replace("/");
            }else {
                console.log(data);
                document.getElementById("message").innerHTML = data.message;
            }

        }).catch(err => {
        console.log('error happen: ');
        console.log(err);
    });
}
