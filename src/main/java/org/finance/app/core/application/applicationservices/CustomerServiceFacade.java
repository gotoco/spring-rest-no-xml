package org.finance.app.core.application.applicationservices;

import org.finance.app.adapters.webservices.json.FormJSON;
import org.finance.app.core.domain.CustomerService;
import org.finance.app.ports.crudes.LoanReaderService;
import org.finance.app.sharedcore.objects.Form;
import org.finance.app.sharedcore.objects.Loan;
import org.finance.app.core.ddd.annotation.ApplicationService;
import org.finance.app.ports.services.LoanServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("CustomerServiceFacade")
@ApplicationService
public class CustomerServiceFacade implements LoanServiceApi {

    private CustomerService customerService;

    private LoanReaderService loanReaderService;

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Autowired
    public void setLoanReaderService(LoanReaderService loanReaderService) {
        this.loanReaderService = loanReaderService;
    }

    public void applyForLoan(FormJSON jsonForm){

        customerService.applyForaLoan(form);
    }

    public void extendTheLoan(Long loanId, Long clientId){

        Loan loan = loanReaderService.getLoanByLoanIdAndUserId(loanId, clientId);

        customerService.extendTheLoan(loan);
    }

}
