package org.finance.app.sharedcore.objects;

import org.finance.app.core.ddd.annotation.AggregateRoot;
import org.finance.app.core.domain.common.loan.ExtendTheLoanFunction;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Date;

@AggregateRoot
@Entity
@XmlRootElement
public class Loan {

    @Transient
    private ExtendTheLoanFunction extendTheLoanFunction;

    @Id
    @GeneratedValue
    @Column(name="loan_id")
    private Long loanId;

    @AttributeOverrides({
            @AttributeOverride(name = "denomination",
                    column = @Column(name = "valuedenomination")),
            @AttributeOverride(name = "currencyCode",
                    column = @Column(name = "valuecurrencycode")) })
    private Money value;

    @AttributeOverrides({
            @AttributeOverride(name = "denomination",
                    column = @Column(name = "interestdenomination")),
            @AttributeOverride(name = "currencyCode",
                    column = @Column(name = "interestcurrencycode")) })
    private Money interest;

    @Column(name="expiration_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date expirationDate;

    @Column(name="effective_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date effectiveDate;

    @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client loanHolder;

    @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="contract_id", referencedColumnName = "contract_id")
    private LoanContract loanContract;

    private Long basedOnLoanId;

    public Loan(Long basedOnLoan, Money value, Money interest, DateTime expirationDate, DateTime effectiveDate, Client loanHolder) {
        this.basedOnLoanId = basedOnLoan;
        this.value = value;
        this.interest = interest;
        this.expirationDate = expirationDate.toDate();
        this.effectiveDate = effectiveDate.toDate();
        this.loanHolder = loanHolder;
    }

    public Loan(){

    }

    public Long getLoanId() {
        return loanId;
    }

    public Loan extendLoan(DateTime changeDate){

        if (changeDate.isBefore(new DateTime(effectiveDate) ) || changeDate.isAfter(new DateTime(expirationDate) ) ) {
            throw new IllegalArgumentException("Time sequence fault. Incorrect data");
        }

        this.expirationDate = changeDate.toDate();

        return extendTheLoanFunction.extend(this, changeDate);
    }

    @Autowired
    public void setExtendTheLoanFunction(ExtendTheLoanFunction extendTheLoanFunction) {
        this.extendTheLoanFunction = extendTheLoanFunction;
    }

    public void increaseLoanValue(Money money){
        this.value.add(money);
    }

    public void subtractLoanValue(Money money){
        this.value.subtract(money);
    }

    public void increaseInterestValue(Money money){
        this.interest.add(money);
    }

    public void subtractInterestValue(Money money){
        this.interest.subtract(money);
    }

    public Money showLoanValue() {
        return this.value;
    }

    public Money showInterestValue() {
        return this.interest;
    }

    public ExtendTheLoanFunction getExtendTheLoanFunction() {
        return extendTheLoanFunction;
    }

    public Long getBasedOnLoanId() {
        return basedOnLoanId;
    }

    public void setBasedOnLoan(Long basedOnLoan) {
        this.basedOnLoanId = basedOnLoan;
    }

    public Money getValue() {
        return value;
    }

    public void setValue(Money value) {
        this.value = value;
    }

    public Money getInterest() {
        return interest;
    }

    public void setInterest(Money interest) {
        this.interest = interest;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Client getLoanHolder() {
        return loanHolder;
    }

    public void setLoanHolder(Client loanHolder) {
        this.loanHolder = loanHolder;
    }

    public void setBasedOnLoanId(Long basedOnLoanId) {
        this.basedOnLoanId = basedOnLoanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public LoanContract getContract() {
        return loanContract;
    }

    public void setContract(LoanContract contract) {
        this.loanContract = contract;
    }
}
