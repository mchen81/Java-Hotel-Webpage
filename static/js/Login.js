function login() {

    let name = document.getElementById("username").value;
    let pass = document.getElementById('password').value;


    const regex = /(?=.*\d)(?=.*[a-z])(?=.*[@#$%.\/]){5,10}/gm;
    if (regex.exec(pass) == null) {
        alert("Password does not follow the rule")
        return;
    }

    const data = {username: name, password: pass};
    console.log(data)
    fetch('/login', {
        method: 'post',
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json",
        },
    }).then(res => res.json())
        .then(data => {

            if(data.success == true){
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
