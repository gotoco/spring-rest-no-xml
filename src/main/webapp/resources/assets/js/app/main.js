define(['jquery','./message'], function($, message) {

    $(function() {
        $('#output').html(message + ' ');
    });

    $(function() { //shorthand document.ready function
        $('#form-submit').on('submit', function(e) { //use on if jQuery 1.7+
            e.preventDefault();  //prevent form from submitting
            var data = $("#login_form :input").serializeArray();
            console.log(data); //use the console for debugging, F12 in Chrome, not alerts
        });
    });

});

/*
apply-button

$('#login_form').submit(function() {
    var data = $("#login_form :input").serializeArray();

    alert('Handler for .submit() called.');
});*/
