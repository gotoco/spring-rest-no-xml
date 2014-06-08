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
    private String ip;

    @Column(name="date_of_application")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateOfApplication;

    @AttributeOverrides({
            @AttributeOverride(name = "denomination", column = @Column(name = "totalCost_denomination")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "totalCost_currencyCode")) })
    private Money totalCost;

    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "requestId"))})
    private AggregateId requestId;

    @Column(name="newExpirationDate")
    private Date newExpirationDate;

    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="loan", referencedColumnName="loan_id")
    private Loan loan;

    @Column(name="loanId")
    private Long loanId;

    public Date getNewExpirationDate(){
        return this.newExpirationDate;
    }

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

    public Loan getLoan() {
        return loan;
    }

    public Long getLoanId(){
        return this.loanId;
    }

    public Date getDateOfApplication() {
        return dateOfApplication;
    }

    public String getIp() {
        return ip;
    }
}
