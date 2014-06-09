package org.finance.test.builders;

import junit.framework.Assert;
import org.finance.app.sharedcore.objects.Client;
import org.finance.app.sharedcore.objects.Form;
import org.finance.app.sharedcore.objects.Money;
import org.joda.time.DateTime;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class FormBuilder {

    private Form form;

    public FormBuilder(){

    }

    public FormBuilder(Form form){
        this.form = form;
    }

    public FormBuilder withEmptyForm(){
        return new FormBuilder(new Form(null, null, null, null, null));
    }

    @Transactional
    public FormBuilder withCorrectlyFilledForm(Client client){
        Client personalData = client;
        Money applyingAmount = new Money(2000);
        InetAddress applyingIpAddress = null;
        try {
            applyingIpAddress = InetAddress.getByName("127.0.0.1");
        } catch(UnknownHostException ex){
            ex.printStackTrace();
            Assert.fail("Cannot produce simple IP object");
        }
        Integer maturityInDays = 30;
        DateTime submissionDate = new DateTime();
        Form form = new Form(personalData, applyingAmount, applyingIpAddress, maturityInDays, submissionDate);

        return new FormBuilder(form);
    }

    public Form build(){
        return this.form;
    }
}
