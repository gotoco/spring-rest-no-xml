package org.finance.app.core.domain.businessprocess.loangrant;

import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.sharedcore.objects.Client;
import org.finance.app.sharedcore.objects.Form;
import org.finance.app.sharedcore.objects.Money;
import org.finance.app.sharedcore.objects.Loan;
import org.finance.app.core.domain.events.impl.customerservice.ExtendTheLoanRequest;
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

    @Column(name="has_valid_ip")
    private Boolean hasValidIp;

    @Column(name="has_risk")
    private Boolean hasRisk;

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

    @Column(name="new_expiration_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date newExpirationDate;

    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="loan", referencedColumnName="loan_id")
    private Loan loan;

    @Column(name="loan_id")
    private Long loanId;

    @ManyToOne //(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="client_id", referencedColumnName = "client_id")
    private Client client;

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

    public void setDateOfApplication(DateTime dateOfApplication) {
        this.dateOfApplication = dateOfApplication.toDate();
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

    public Boolean hasValidIp() {
        return hasValidIp;
    }

    public void setValidIp(Boolean hasValidIp) {
        this.hasValidIp = hasValidIp;
    }

    public Boolean hasRisk() {
        return hasRisk;
    }

    public void setRisk(Boolean hasRisk) {
        this.hasRisk = hasRisk;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void fillDataFromForm(Form form){
        this.setIp(form.getApplyingIpAddress().getHostAddress());
        this.setDateOfApplication(form.getSubmissionDate());
        this.setLoan(null);
        this.setLoanId(null);
        this.setValidIp(null);
        this.setRisk(null);
        this.setTotalCost(form.getApplyingAmount());
        this.setNewExpirationDate(form.getSubmissionDate().plusDays(form.getMaturityInDays()));
        this.setClient(form.getPersonalData());
    }

    public void fillDataFromRequest(ExtendTheLoanRequest requestEvent) {
        this.setLoan(requestEvent.getBaseLoan());
        this.setLoanId(requestEvent.getLoanId());
    }

    public GrantingOfLoanData(){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GrantingOfLoanData)) return false;

        GrantingOfLoanData that = (GrantingOfLoanData) o;

        if (client != null ? !client.equals(that.client) : that.client != null) return false;
        if (!dateOfApplication.equals(that.dateOfApplication)) return false;
        if (hasRisk != null ? !hasRisk.equals(that.hasRisk) : that.hasRisk != null) return false;
        if (hasValidIp != null ? !hasValidIp.equals(that.hasValidIp) : that.hasValidIp != null) return false;
        if (!id.equals(that.id)) return false;
        if (ip != null ? !ip.equals(that.ip) : that.ip != null) return false;
        if (loan != null ? !loan.equals(that.loan) : that.loan != null) return false;
        if (loanId != null ? !loanId.equals(that.loanId) : that.loanId != null) return false;
        if (newExpirationDate != null ? !newExpirationDate.equals(that.newExpirationDate) : that.newExpirationDate != null)
            return false;
        if (requestId != null ? !requestId.equals(that.requestId) : that.requestId != null) return false;
        if (!totalCost.equals(that.totalCost)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (hasValidIp != null ? hasValidIp.hashCode() : 0);
        result = 31 * result + (hasRisk != null ? hasRisk.hashCode() : 0);
        result = 31 * result + dateOfApplication.hashCode();
        result = 31 * result + totalCost.hashCode();
        result = 31 * result + (requestId != null ? requestId.hashCode() : 0);
        result = 31 * result + (newExpirationDate != null ? newExpirationDate.hashCode() : 0);
        result = 31 * result + (loan != null ? loan.hashCode() : 0);
        result = 31 * result + (loanId != null ? loanId.hashCode() : 0);
        result = 31 * result + (client != null ? client.hashCode() : 0);
        return result;
    }
}
