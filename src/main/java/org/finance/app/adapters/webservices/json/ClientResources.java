package org.finance.app.adapters.webservices.json;

import org.finance.app.sharedcore.objects.Client;
import org.springframework.hateoas.ResourceSupport;


public class ClientResources extends ResourceSupport {

    private Long clientId;

    private String firstName;

    private String lastName;

    private String address;

    public ClientResources() {

    }

    public ClientResources(Client client) {
        this.clientId  = client.getClientId();
        this.firstName = client.getFirstName();
        this.lastName  = client.getLastName();
        this.address   = client.getAddress();
    }

}
