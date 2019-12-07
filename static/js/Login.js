function login() {

    const data = {username: document.getElementById("username").value, password: document.getElementById('password').value};
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
