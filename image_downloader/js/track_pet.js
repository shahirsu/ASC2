$(document).ready(function(){

    // Add JWT to every request
    $.ajaxSetup({ beforeSend: function(xhr) {
            xhr.setRequestHeader('x-auth-token', localStorage.jwt);
        }});
        
    function getLocation(pet) {
        $.ajax({
            type: "POST",
            url: "http://3.81.199.215:5432/location",
            data: JSON.stringify({pet: pet}),
            dataType: "json",
            contentType: "application/json",
            success: function(result, status, xhr) {
                $("#pet-output").html(result["locations"][1]);
            },
            error: function(req, status, error) {
                $("#pet-output").html(req + " " + status + " " + error);
            }
        });
    }

    function getPet() {
        var username = localStorage.username;
        console.log("Found this username "+username);
        $.ajax({
            type: "POST",
            url: "http://3.81.199.215:5432/fetchPet",
            data: JSON.stringify({username: username}),
            dataType: "json",
            contentType: "application/json",
            success: function(result, status, xhr) {
                getLocation(result["pet"]);
            }
        });
    }

    $('#signout').click(function(){
        alert("Thank You, See You Again!");
        localStorage.jwt = '';
        localStorage.username = '';
        window.location.replace("login.html")
    });

    // Initialize
    if (localStorage.getItem("jwt")){
        var username = localStorage.username;
        if(username == "admin") {
            window.location.replace("tools.html");
        }
        else {
            getPet(username);
        }
    } else{
        window.location.replace("login.html");
    }
});
