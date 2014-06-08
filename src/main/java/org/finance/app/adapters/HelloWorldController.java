package org.finance.app.adapters;

/**
 * Author: Mazeryt Freager
 * Contact: skirki@o2.pl
 * Date: 6/1/14
 * Time: 11:11 AM
 */
import org.finance.app.core.domain.businessprocess.loangrant.GrantingOfLoanData;
import org.finance.app.core.domain.businessprocess.loangrant.GrantingOfLoanSagaManager;
import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.common.Money;
import org.finance.app.core.domain.common.loan.Loan;
import org.finance.app.core.domain.events.impl.customerservice.ExtendTheLoanRequest;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloWorldController {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    GrantingOfLoanSagaManager sagaManager;

    @Transactional
    @RequestMapping("/hello")
    public String hello(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model, HttpServletRequest request) {
        model.addAttribute("name", name);

        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }


        GrantingOfLoanData sagaData = new GrantingOfLoanData();
        sagaData.setDateOfApplication(Calendar.getInstance().getTime());
        sagaData.setRequestId(AggregateId.generate());
        sagaData.setIp(ipAddress);
        entityManager.persist(sagaData);

        return "helloworld";
    }

    @Transactional
    @RequestMapping("/loan")
    public String loan(@RequestParam(value="name", required=false, defaultValue="World") String id, Model model, HttpServletRequest request) {

        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        Loan loan = new Loan(null, new Money(1000), new Money(10), DateTime.now(), DateTime.now().plusDays(30));
        loan.setLoan_id(Long.decode(id));
        ExtendTheLoanRequest resuest = new ExtendTheLoanRequest(loan, AggregateId.generate());

        GrantingOfLoanData sagaData = null;
       try {
            sagaData = sagaManager.loadSaga(resuest);
       }catch(Exception ex ){
           System.out.println("nie znalazłem");
       }


        if(sagaData == null) {
            sagaData = new GrantingOfLoanData();
            sagaData.setDateOfApplication(Calendar.getInstance().getTime());
            sagaData.setRequestId(AggregateId.generate());
            sagaData.setIp(ipAddress);
        }
        entityManager.persist(sagaData);

        return "helloworld";
    }

}
