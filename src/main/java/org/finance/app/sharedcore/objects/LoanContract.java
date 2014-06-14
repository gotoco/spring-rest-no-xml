package org.finance.app.sharedcore.objects;

import org.finance.app.core.ddd.annotation.AggregateRoot;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AggregateRoot
@Entity
@Table(name = "LoanContract")
public class LoanContract {

    @Id
    @GeneratedValue
    @Column(name="contract_id")
    private Long contractId;

    @OneToMany(cascade={CascadeType.ALL}, mappedBy="loanContract")
    private List<Loan> loanPeriods = new ArrayList<Loan>();

    @ManyToOne
    @JoinColumn(name="client_id")
    private Client contractHolder;

    public Long getContractId() {
        return contractId;
    }

    public List<Loan> getLoanPeriods() {
        return loanPeriods;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public void setLoanPeriods(List<Loan> loanPeriods) {
        this.loanPeriods = loanPeriods;
    }

    public Client getContractHolder() {
        return contractHolder;
    }

    public void setContractHolder(Client contractHolder) {
        this.contractHolder = contractHolder;
    }

    public void addToLoansPeriods(Loan loan){
       loanPeriods.add(loan);
    }

    public LoanContract(){

    }

    public LoanContract(Loan loan, Client client){
        this.contractHolder = client;
        this.loanPeriods.add(loan);
    }
}
