function login() {
    var myusername = document.getElementById('username').value;
    var mypassword = document.getElementById('password').value;
    console.log(username + password);
    fetch('/login', {
        method: 'post',
        body: ('username=' + myusername + '&password=' + mypassword)
    }).then(res => res.json())
        .then(data => {
        console.log(data);
        document.getElementById("message").innerHTML = data.message;
    }).catch(err => {
        console.log('error happen: ');
        console.log(err);
    });
}
