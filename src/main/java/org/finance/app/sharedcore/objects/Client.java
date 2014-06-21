package org.finance.app.sharedcore.objects;

import org.finance.app.core.ddd.annotation.AggregateRoot;
import org.finance.app.core.domain.businessprocess.loangrant.LoanApplicationData;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AggregateRoot
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

    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.EAGER,  mappedBy="client")
    private List<LoanApplicationData> loanScheduler = new ArrayList<LoanApplicationData>();

    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.EAGER, mappedBy="contractHolder")
    private List<LoanContract> loanContracts = new ArrayList<LoanContract>();

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
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

    public Client(Long clientId, String firstName, String lastName, String address) {
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public Client( String firstName, String lastName, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public Client(){

    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", loanScheduler=" + loanScheduler +
                ", loanContracts=" + loanContracts +
                '}';
    }
}
