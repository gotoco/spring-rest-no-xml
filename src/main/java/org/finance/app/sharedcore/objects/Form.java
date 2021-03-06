package org.finance.app.sharedcore.objects;

import org.finance.app.core.ddd.annotation.ValueObject;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.net.InetAddress;

@ValueObject
public class Form implements Serializable {

    private Client personalData;

    private Money applyingAmount;

    private InetAddress applyingIpAddress;

    private Integer maturityInDays;

    private DateTime submissionDate;

    public Form(Client personalData, Money applyingAmount,
                    InetAddress applyingIpAddress, Integer maturityInDays, DateTime submissionDate) {
        this.personalData = personalData;
        this.applyingAmount = applyingAmount;
        this.applyingIpAddress = applyingIpAddress;
        this.maturityInDays = maturityInDays;
        this.submissionDate = submissionDate;
    }

    public boolean isFormEmpty(){
        return      this.personalData       == null &&
                    this.applyingAmount     == null &&
                    this.applyingIpAddress  == null &&
                    this.maturityInDays     == null &&
                    this.submissionDate     == null;
    }

    public Client getPersonalData() {
        return personalData;
    }

    public Integer getMaturityInDays() {
        return maturityInDays;
    }

    public Money getApplyingAmount() {
        return applyingAmount;
    }

    public DateTime getSubmissionDate() {
        return submissionDate;
    }

    public InetAddress getApplyingIpAddress() {
        return applyingIpAddress;
    }
}
