package org.finance.app.core.domain.common;

import org.finance.app.ddd.annotation.ValueObject;
import org.joda.time.DateTime;

import java.io.Serializable;

@ValueObject
public class PersonalData implements Serializable {

    private String firstName;

    private String LastName;

    private String address;

    private DateTime dateOfBirth;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
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


}
