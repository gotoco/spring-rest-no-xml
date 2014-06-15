define(['jquery'], function($) {

    function LoanApplicationContext(  ) {

    }

    LoanApplicationContext.prototype = new LoanApplicationContext();

    LoanApplicationContext.prototype.context = undefined;

    LoanApplicationContext.prototype.submitForm = function() {

        var form = this.validateForm();

        if(form === false){
            alert("Proszę wypełnić wszystkie dane w formularzu!");
        }


    };

    LoanApplicationContext.prototype.validateForm = function() {

        var loanValue = $('#submission-loanvalue').val();
            if(loanValue.length < 1) return false;
        var loanDays = $('#submission-days').val();
            if(loanValue.length < 1) return false;
        var clientName = $('#submission-firstname').val();
            if(loanValue.length < 1) return false;
        var clientLastName = $('#submission-lastname').val();
            if(loanValue.length < 1) return false;
        var clientPesel = $('#submission-pesel').val();
            if(loanValue.length < 1) return false;
        var clientAddress = $('#submission-address').val();
            if(loanValue.length < 1) return false;

        return [loanValue, loanDays, clientName, clientLastName, clientPesel, clientAddress];
    };

    return new LoanApplicationContext();
});