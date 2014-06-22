package org.finance.app.core.application.translation;

import junit.framework.Assert;
import org.finance.app.adapters.webservices.json.FormJSON;
import org.finance.app.annotations.UnitTest;
import org.finance.app.sharedcore.objects.Client;
import org.finance.app.sharedcore.objects.Form;
import org.finance.app.sharedcore.objects.Money;
import org.finance.test.builders.PersonalDataBuilder;
import org.finance.test.builders.external.FormJSONBuilder;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class ApplicationFormTranslatorTest {

    @Test
    public void shouldTranslateJSONToForm(){
        //Given
        Client client = new PersonalDataBuilder().withDefaultData().build();
        DateTime submissionDate = new DateTime();
        FormJSON externalForm = new FormJSONBuilder().withCorrectlyFilledData()
                                                     .withClient(client)
                                                     .withSubmissionDate(submissionDate)
                                                     .build();

        //When
        Form domainFormTranslated = new ApplicationFormTranslator().validateForm(externalForm, client, submissionDate);

        //Then
        assertForms(domainFormTranslated, externalForm);
    }

    private void assertForms(Form domainForm, FormJSON formExternal){

        Boolean applyingAmountEqual = domainForm.getApplyingAmount()
                                                .equals(new Money(formExternal.getApplyingAmount()));

        Boolean ipAddressesEqual = domainForm.getApplyingIpAddress()
                                             .getHostAddress()
                                             .equals(formExternal.getApplyingIpAddress());

        Boolean maturityEqual = domainForm.getMaturityInDays()
                                          .equals(formExternal.getMaturityInDays());

        Client client = domainForm.getPersonalData();

        Boolean clientDataEqual = client.getLastName().equals(formExternal.getLastName()) &&
                                  client.getFirstName().equals(formExternal.getFirstName()) &&
                                  client.getAddress().equals(formExternal.getAddress()) ;

        Boolean submissionDatesEquals = domainForm.getSubmissionDate()
                                                  .equals(formExternal.getSubmissionDate());

        Boolean result = applyingAmountEqual && ipAddressesEqual && maturityEqual && submissionDatesEquals && clientDataEqual;

        Assert.assertTrue(result);
    }

}