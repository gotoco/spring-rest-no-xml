define(['jquery',
    './message',
    './LoanApplicationContext',
    './../angular/app',
    './controller'
], function($, message, loanContext, app, controller) {

    $(function() {
        $('#register-button').onclick;
    });

    $(document).ready(function () {
        $('#register-button').click(function () {
            loanContext.submitForm() ;
        });
    });

    $(function() {
        alert(controller.sayHello)
    });

/*    $(function() { //shorthand document.ready function
        $('#apply-form').on('submit', function(e) { //use on if jQuery 1.7+
            e.preventDefault();  //prevent form from submitting
            var data = $('#loan-value').val();
            $('#output').html("APPLAYED FOR: " + data);
            console.log(data); //use the console for debugging, F12 in Chrome, not alerts
            return false;
        });
    });*/

});

