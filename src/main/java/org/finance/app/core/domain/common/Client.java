package org.finance.app.core.domain.common;

import org.finance.app.core.domain.businessprocess.loangrant.GrantingOfLoanData;
import org.finance.app.core.ddd.annotation.ValueObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;

        Client client = (Client) o;

        if (address != null ? !address.equals(client.address) : client.address != null) return false;
        if (clientId != null ? !clientId.equals(client.clientId) : client.clientId != null) return false;
        if (firstName != null ? !firstName.equals(client.firstName) : client.firstName != null) return false;
        if (lastName != null ? !lastName.equals(client.lastName) : client.lastName != null) return false;
        if (loanScheduler != null ? !loanScheduler.equals(client.loanScheduler) : client.loanScheduler != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = clientId != null ? clientId.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (loanScheduler != null ? loanScheduler.hashCode() : 0);
        return result;
    }
}
