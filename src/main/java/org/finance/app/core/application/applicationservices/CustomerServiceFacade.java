package org.finance.app.core.application.applicationservices;

import org.finance.app.core.domain.CustomerService;
import org.finance.app.core.domain.common.Decision;
import org.finance.app.core.domain.common.Form;
import org.finance.app.ddd.annotation.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("CustomerServiceFacade")
@ApplicationService
public class CustomerServiceFacade {

    private CustomerService customerService;

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public Decision applyForLoan(Form form){
        customerService.applyForaLoan(form);

        return null;
    }

}
