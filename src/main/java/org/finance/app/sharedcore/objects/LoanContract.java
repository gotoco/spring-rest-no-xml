package org.finance.app.sharedcore.objects;

import org.finance.app.core.ddd.annotation.AggregateRoot;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@AggregateRoot
@Entity
@Table(name = "LoanContract")
@XmlRootElement
public class LoanContract {

    @Id
    @GeneratedValue
    @Column(name="contract_id")
    private Long contractId;

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="loanContract")
    private List<Loan> loanPeriods = new ArrayList<Loan>();

    @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client contractHolder;

    public Long getContractId() {
        return contractId;
    }

    public List<Loan> getLoanPeriods() {
        return loanPeriods;
    }

    public Client getContractHolder() {
        return contractHolder;
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

    public LoanContract(List<Loan> loans, Client client){
        this.contractHolder = client;
        this.loanPeriods = loans;
    }

    public synchronized Loan getLatestGrantedLoan(){
        this.loanPeriods.sort(new LoanByDateComparator());
        return loanPeriods.get(loanPeriods.size()-1);
    }

    private class LoanByDateComparator implements Comparator<Loan> {
        public int compare(Loan firstL, Loan secondL) {
            if (firstL.getEffectiveDate().before(secondL.getEffectiveDate())) {
                return -1;
            } else if (firstL.getEffectiveDate().after(secondL.getEffectiveDate())) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
