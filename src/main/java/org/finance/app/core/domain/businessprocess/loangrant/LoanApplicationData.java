package org.finance.app.core.domain.businessprocess.loangrant;

import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.events.saga.IpCheckedResponse;
import org.finance.app.core.domain.events.saga.RiskAnalyzedResponse;
import org.finance.app.core.domain.risk.Risk;
import org.finance.app.sharedcore.objects.Client;
import org.finance.app.sharedcore.objects.Form;
import org.finance.app.sharedcore.objects.Money;
import org.finance.app.sharedcore.objects.Loan;
import org.finance.app.core.domain.events.customerservice.ExtendTheLoanRequest;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class LoanApplicationData {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name="ip")
    private String ip;

    @Column(name="has_valid_ip")
    private Boolean hasValidIp;

    @Column(name="has_risk") //Deprecated!
    private Boolean hasRisk;

    @Column(name="date_of_application")
    @Temporal(TemporalType.TIMESTAMP)
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

    @ManyToOne
    @JoinColumn(name="client_id", referencedColumnName = "client_id")
    private Client client;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "application_risk",
            joinColumns = { @JoinColumn(name = "id") },
            inverseJoinColumns = { @JoinColumn(name = "risk_id") })
    private List<Risk> risks = new ArrayList<Risk>();

    public Date getNewExpirationDate(){
        return this.newExpirationDate;
    }

    public Money getTotalCost() {
        return totalCost;
    }

    public AggregateId getRequestId() {
        return requestId;
    }

    public void setNewExpirationDate(DateTime newDate){
        this.newExpirationDate = newDate.toDate();
    }

    public Long getFixedId(){
        return this.id;
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

    public Boolean hasRisk() {
        return hasRisk;
    }

    public Client getClient() {
        return client;
    }

    public void whenFormIsApplied(Form form){
        this.ip = form.getApplyingIpAddress().getHostAddress();
        this.dateOfApplication = form.getSubmissionDate().toDate();
        this.loan = null;
        this.loanId = null;
        this.hasValidIp = null;
        this.hasRisk = null;
        this.totalCost = form.getApplyingAmount();
        this.newExpirationDate = form.getSubmissionDate().plusDays(form.getMaturityInDays()).toDate();
        this.client = form.getPersonalData();
    }

    public void fillDataFromRequest(ExtendTheLoanRequest requestEvent) {
        this.loan = requestEvent.getBaseLoan();
        this.loanId = requestEvent.getLoanId();
    }

    public LoanApplicationData(){

    }

    public void whenIpValidIsChecked(IpCheckedResponse responseEvent){
        this.hasValidIp = responseEvent.getValidIpAddress();
    }

    public LoanApplicationData(AggregateId aggregateId){
        this.requestId = aggregateId;
    }

    public LoanApplicationData(AggregateId aggregateId, Loan loan){
        this.requestId = aggregateId;
        this.loan = loan;
    }

    public void updateLoanInformation(Loan loan){
        this.loan = loan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoanApplicationData)) return false;

        LoanApplicationData that = (LoanApplicationData) o;

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

    public void whenRiskWasAnalyzed(RiskAnalyzedResponse response) {
        if(response.hasRisk()){
            this.hasRisk = true;
            for(Risk risk : response.getRisks()){
                if(!this.risks.contains(risk)){
                    risks.add(risk);
                }
            }
        } else {
            this.hasRisk = false;
        }
    }

    public List<Risk> showRisks(){
        return this.risks;
    }
}
