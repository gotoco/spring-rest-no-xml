package org.finance.test.builders;

import org.finance.app.sharedcore.objects.Client;
import org.joda.time.DateTime;

public class PersonalDataBuilder {

    private Long clientId;

    private String firstName;

    private String lastName;

    private String address;

    private DateTime dateOfBirth;

    public PersonalDataBuilder withClientId(Long id){
        this.clientId = id;
        return this;
    }

    public PersonalDataBuilder withFirstName(String firstName){
        this.firstName = firstName;
        return this;
    }

    public PersonalDataBuilder withCorrectlyFilledData(){
        return new PersonalDataBuilder(10000L, "Jan", "Kowalski", "Lesnej polanki 1", new DateTime().minusYears(30));
    }

    public Client build(){
        return new Client(clientId, firstName, lastName, address);
    }

    public PersonalDataBuilder(Long clientId, String firstName, String lastName, String address, DateTime dateOfBirth) {
        //this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
    }

    public PersonalDataBuilder(){

    }
}
