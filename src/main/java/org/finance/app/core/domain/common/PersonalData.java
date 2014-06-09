package org.finance.app.core.domain.common;

import org.finance.app.ddd.annotation.ValueObject;
import org.joda.time.DateTime;

import java.io.Serializable;

@ValueObject
public class PersonalData implements Serializable {

    private Long clientId;

    private String firstName;

    private String lastName;

    private String address;

    private DateTime dateOfBirth;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public DateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(DateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public PersonalData(Long clientId, String firstName, String lastName, DateTime dateOfBirth, String address) {
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public PersonalData(){

    }
}
