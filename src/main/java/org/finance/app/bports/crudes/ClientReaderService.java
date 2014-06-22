package org.finance.app.bports.crudes;


import org.finance.app.sharedcore.objects.Client;

public interface ClientReaderService {

    public Client findClientById(Long clientId);

}


