package org.finance.test.builders.external;

import org.finance.app.adapters.webservices.json.FormJSON;
import org.joda.time.DateTime;

import java.math.BigDecimal;

public class FormJSONBuilder {

    private String firstName;

    private String lastName;

    private BigDecimal applyingAmount;

    private String applyingIpAddress;

    private Integer maturityInDays;

    private DateTime submissionDate;

    private Long clientId;

    private String address;

    public FormJSON build(){
        return new FormJSON(firstName, lastName, applyingAmount);
    }

    public FormJSONBuilder withCorrectlyFilledData(){
        this.firstName = "Maciej";
        this.lastName  = "Grochowski";
        this.applyingAmount = new BigDecimal(2000);
        this.applyingIpAddress  = "127.0.0.1";
        this.maturityInDays = 30;
        this.submissionDate = new DateTime().withTimeAtStartOfDay().plusHours(8);

        return this;
    }
}
