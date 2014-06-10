package org.finance.app.adapters.webservices.json;

import org.finance.app.sharedcore.objects.Client;
import org.finance.app.sharedcore.objects.Money;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.net.InetAddress;


@XmlRootElement
public class FormJSON {

    private String firstName;

    private String lastName;

    private BigDecimal applyingAmount;

    private InetAddress applyingIpAddress;

    private Integer maturityInDays;

    private DateTime submissionDate;

    private Long clientId;

    public FormJSON(String firstName, String lastName, BigDecimal applyingAmount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.applyingAmount = applyingAmount;
    }

    public FormJSON() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getApplyingAmount() {
        return applyingAmount;
    }

    public void setApplyingAmount(BigDecimal applyingAmount) {
        this.applyingAmount = applyingAmount;
    }

    public InetAddress getApplyingIpAddress() {
        return applyingIpAddress;
    }

    public void setApplyingIpAddress(InetAddress applyingIpAddress) {
        this.applyingIpAddress = applyingIpAddress;
    }

    public Integer getMaturityInDays() {
        return maturityInDays;
    }

    public void setMaturityInDays(Integer maturityInDays) {
        this.maturityInDays = maturityInDays;
    }

    public DateTime getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(DateTime submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
