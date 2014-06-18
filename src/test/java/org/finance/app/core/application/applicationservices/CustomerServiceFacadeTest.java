package org.finance.app.core.application.applicationservices;

import org.finance.app.adapters.webservices.json.FormJSON;
import org.finance.app.annotations.IntegrationTest;
import org.finance.app.bports.services.LoanServiceApi;
import org.finance.test.builders.external.FormJSONBuilder;
import org.finance.test.ConfigTest;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = ConfigTest.class)
public class CustomerServiceFacadeTest {

    @Autowired
    LoanServiceApi customerServiceFacade;

    @Test
    public void whenCorrectSubmissionNewLoanIsCreated(){
        //Given
        FormJSON form = new FormJSONBuilder().withCorrectlyFilledData().build();

        //When
        customerServiceFacade.applyForLoan(form, new DateTime());

        //Then
    }
}