$(document).ready(function(){
    // Add JWT to every request
    $.ajaxSetup({ beforeSend: function(xhr) {
        xhr.setRequestHeader('x-auth-token', localStorage.jwt);
    }});

    //Location Lookup Event Handler
    $('#cat').click(function(){
        makeAjax("cat"); 
    });

    $('#dog').click(function(){
        makeAjax("dog"); 
    });

    $('#bird').click(function(){
        makeAjax("bird"); 
    });

    $('#sheep').click(function(){
        makeAjax("sheep"); 
    });

    $('#personsearch').click(function(){
        let input = document.getElementById('searchbar').value;
        input = String(input);
        makeAjax(input); 
    });

    function makeAjax(pet) {
        $.ajax({
            type: "POST",
            url: "http://3.81.199.215:5432/location",
            data: JSON.stringify({pet: pet}),
            dataType: "json",
            contentType: "application/json",
            success: function(result, status, xhr) {
                $("#location-output").html(result["locations"][1]);
            },
            error: function(req, status, error) {
                $("#location-output").html(req + " " + status + " " + error);
            }
        });
    }

    $('#signout').click(function(){
        alert("Thank You, See You Again!");
        localStorage.jwt = '';
        localStorage.username = '';
        window.location.replace("login.html")
    });

    if (localStorage.getItem("jwt")){
        var username = localStorage.username;
        if(username != "admin") {
            window.location.replace("track_pet.html");
        }
    } else{
        window.location.replace("login.html");
    }
    
});