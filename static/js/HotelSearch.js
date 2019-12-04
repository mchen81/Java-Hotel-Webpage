function searchHotel(){


    var xhttp = new XMLHttpRequest();

    var city = document.getElementById("city").value;
    var hotelName = document.getElementById("hotelName").value;

    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("hotelInfo").innerHTML = this.responseText;
        }
    };
    xhttp.open("GET", "search?city=" + city + "&hotelName=" + hotelName, true);
    xhttp.send();
}
