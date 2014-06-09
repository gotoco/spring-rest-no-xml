package org.finance.app.core.domain.common;

import org.finance.app.core.domain.businessprocess.loangrant.GrantingOfLoanData;
import org.finance.app.core.domain.common.loan.Loan;
import org.finance.app.ddd.annotation.ValueObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by maciek on 02.06.14.
 */
@ValueObject
@Entity
public class Client {

    @Id
    @GeneratedValue
    @Column(name="client_id")
    private Long clientId;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="address")
    private String address;

    @OneToMany(cascade={CascadeType.ALL}, mappedBy="client")
    private List<GrantingOfLoanData> loanScheduler = new ArrayList<GrantingOfLoanData>();

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public List<GrantingOfLoanData> getLoanScheduler() {
        return loanScheduler;
    }

    public void setLoanScheduler(List<GrantingOfLoanData> loanScheduler) {
        this.loanScheduler = loanScheduler;
    }

    public Client(Long clientId, String firstName, String lastName, String address) {
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }
}
