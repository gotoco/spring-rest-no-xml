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
        return new FormJSON(firstName, lastName, applyingAmount, address);
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

    public FormJSONBuilder withSpecifiedData(String firstName,
                                             String lastName,
                                             BigDecimal applyingAmount,
                                             String applyingIpAddress,
                                             Integer maturityInDays,
                                             DateTime submissionDate,
                                             String address){
        this.firstName = firstName;
        this.lastName  = lastName;
        this.applyingAmount = applyingAmount;
        this.applyingIpAddress  = applyingIpAddress;
        this.maturityInDays = maturityInDays;
        this.submissionDate = submissionDate;
        this.address = address;

        return this;
    }
}
