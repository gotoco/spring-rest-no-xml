package org.finance.app.bports.services;


import org.finance.app.adapters.webservices.json.FormJSON;
import org.joda.time.DateTime;

public interface LoanServiceApi {

    public void applyForLoan(FormJSON jsonForm, DateTime submissionDate);

    public void extendTheLoan(Long loanId, Long clientId, DateTime newExpirationDate);
}
