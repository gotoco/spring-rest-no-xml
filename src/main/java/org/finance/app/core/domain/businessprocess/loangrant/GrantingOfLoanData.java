package org.finance.app.core.domain.businessprocess.loangrant;

import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.common.Money;
import org.finance.app.core.domain.common.loan.Loan;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Date;

@Entity
public class GrantingOfLoanData {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name="ip")
    public String getIp() {
        return ip;
    }

    @Column(name="date_of_application")
    @Temporal(javax.persistence.TemporalType.DATE)
    public Date getDateOfApplication() {
        return dateOfApplication;
    }

    @AttributeOverrides({
            @AttributeOverride(name = "denomination", column = @Column(name = "totalCost_denomination")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "totalCost_currencyCode")) })
    private Money totalCost;

    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "requestId"))})
    private AggregateId requestId;

    @Column(name="loanId")
    public Long getLoanId(){
        return this.loanId;
    }

    @Column(name="newExpirationDate")
    public Date getNewExpirationDate(){
        return this.newExpirationDate;
    }

    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy = "GrantingOfLoanData")
    public Loan getLoan() {
        return loan;
    }

    private Long loanId;
    private String ip;
    private Date dateOfApplication;
    private Date newExpirationDate;
    private Loan loan;

    public Money getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Money totalCost) {
        this.totalCost = totalCost;
    }

    public AggregateId getRequestId() {
        return requestId;
    }

    public void setRequestId(AggregateId requestId) {
        this.requestId = requestId;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setDateOfApplication(Date dateOfApplication) {
        this.dateOfApplication = dateOfApplication;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public void setNewExpirationDate(DateTime newDate){
        this.newExpirationDate = newDate.toDate();
    }

    public Long getFixedId(){
        return this.id;
    }

    public void setLoan(Loan loan){
        this.loan = loan;
    }
}
