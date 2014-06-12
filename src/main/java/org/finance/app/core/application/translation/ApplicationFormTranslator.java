package org.finance.app.core.application.translation;

import org.finance.app.adapters.webservices.json.FormJSON;
import org.finance.app.sharedcore.objects.Client;
import org.finance.app.sharedcore.objects.Form;
import org.finance.app.sharedcore.objects.Money;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component("ApplicationFormTranslator")
public class ApplicationFormTranslator {

    public Form createFormFromRequest(FormJSON jsonForm, Client client, DateTime submissionDate){
        return validateForm(jsonForm, client, submissionDate);
    }

    public Form validateForm(FormJSON jsonForm, Client client, DateTime submissionDate){
        //Possible place to validate client request before domain space
        InetAddress requestIp;
        try {
            requestIp = InetAddress.getByName(jsonForm.getApplyingIpAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
        Money applyingAmount = new Money(jsonForm.getApplyingAmount());
        Integer maturityInDays = jsonForm.getMaturityInDays();

        return new Form(client, applyingAmount, requestIp, maturityInDays, submissionDate);
    }
}
