package org.finance.app.core.domain.common.loan;

import org.finance.app.core.domain.common.Client;
import org.finance.app.core.domain.common.Money;
import org.finance.app.core.ddd.annotation.ValueObject;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Date;

@ValueObject
@Entity
public class Loan {

    @Transient
    private Loan basedOnLoan;

    @Transient
    private ExtendTheLoanFunction extendTheLoanFunction;

    @Id
    @GeneratedValue
    @Column(name="loan_id")
    private Long loan_id;

    public Long getLoan_id() {
        return loan_id;
    }

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

    @ManyToOne
    @JoinColumn(name="client_id")
    private Client loanHolder;

    public Loan(Loan basedOnLoan, Money value, Money interest, DateTime expirationDate, DateTime effectiveDate) {
        this.basedOnLoan = basedOnLoan;
        this.value = value;
        this.interest = interest;
        this.expirationDate = expirationDate.toDate();
        this.effectiveDate = effectiveDate.toDate();
    }

    public Loan extendLoan(DateTime changeDate){

        if (changeDate.isBefore(new DateTime(effectiveDate) ) || changeDate.isAfter(new DateTime(expirationDate) ) ) {
            throw new IllegalArgumentException("Time sequence fault. Incorrect data");
        }

        this.expirationDate = changeDate.toDate();

        return extendTheLoanFunction.extend(changeDate);
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

    @Autowired
    public void setExtendTheLoanFunction(ExtendTheLoanFunction extendTheLoanFunction) {
        this.extendTheLoanFunction = extendTheLoanFunction;
    }

    public void setLoan_id(Long loan_id) {
        this.loan_id = loan_id;
    }

    public Loan getBasedOnLoan() {
        return basedOnLoan;
    }

    public void setBasedOnLoan(Loan basedOnLoan) {
        this.basedOnLoan = basedOnLoan;
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
}
