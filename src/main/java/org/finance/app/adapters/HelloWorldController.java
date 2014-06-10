package org.finance.app.adapters;

/**
 * Author: Mazeryt Freager
 * Contact: skirki@o2.pl
 * Date: 6/1/14
 * Time: 11:11 AM
 */
import org.finance.app.core.domain.businessprocess.loangrant.GrantingOfLoanData;
import org.finance.app.core.domain.businessprocess.loangrant.GrantingOfLoanSagaManager;
import org.finance.app.core.domain.common.*;
import org.finance.app.sharedcore.objects.Loan;
import org.finance.app.core.domain.events.impl.customerservice.ExtendTheLoanRequest;
import org.finance.app.core.domain.events.impl.customerservice.RequestWasSubmitted;
import org.finance.app.sharedcore.objects.Client;
import org.finance.app.sharedcore.objects.Form;
import org.finance.app.sharedcore.objects.Money;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloWorldController {
/*
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
        sagaData.setDateOfApplication(new DateTime(Calendar.getInstance().getTime()) );
        sagaData.setRequestId(AggregateId.generate());
        sagaData.setIp(ipAddress);
        entityManager.persist(sagaData);

        for(int i=0; i<5; i++) {
            Client personalData = null;
            Money applyingAmount = new Money(2000);
            InetAddress applyingIpAddress = null;
            try {
                applyingIpAddress = InetAddress.getByName("127.0.0.1");
            } catch(UnknownHostException ex){
                ex.printStackTrace();
            }
            Integer maturityInDays = 30;
            DateTime submissionDate = new DateTime();
            Form form = new Form(personalData, applyingAmount, applyingIpAddress, maturityInDays, submissionDate);
            RequestWasSubmitted requestWasSubmitted = new RequestWasSubmitted(form, AggregateId.generate());
            sagaManager.handleRequestWasSubmitted(requestWasSubmitted);
        }

        DateTime currentDate = new DateTime();
        DateTime startDate = currentDate.minusDays(1);
        DateTime endDateDate = currentDate;


        Query query = entityManager.createQuery("from GrantingOfLoanData g WHERE g.dateOfApplication > :startDate AND g.ip = :ipAddress AND g.hasValidIp IS NOT NULL")
                                        .setParameter("startDate", startDate.toDate(), TemporalType.DATE)
                                        .setParameter("ipAddress", ipAddress );

        List<GrantingOfLoanData> result =  query.getResultList();

        return "helloworld" + result.size();
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
            sagaData.setDateOfApplication(new DateTime(Calendar.getInstance().getTime()) );
            sagaData.setRequestId(AggregateId.generate());
            sagaData.setIp(ipAddress);
        }
        entityManager.persist(sagaData);

        return "helloworld";
    }*/
}
