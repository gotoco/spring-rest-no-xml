package org.finance.app.adapters.webservices.restful.root.user;

import com.google.gson.Gson;
import org.finance.app.bports.crudes.ClientReaderService;
import org.finance.app.bports.crudes.ContractSchedulerPort;
import org.finance.app.bports.services.LoanService;
import org.finance.app.sharedcore.objects.Client;
import org.finance.app.sharedcore.objects.LoanContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static org.springframework.hateoas.jaxrs.JaxRsLinkBuilder.linkTo;

@Controller
@Produces({MediaType.APPLICATION_JSON + "; charset=UTF-8"})
@Consumes({MediaType.APPLICATION_JSON + "; charset=UTF-8"})
public class UserRestWs {

    private LoanService loanService;

    private ContractSchedulerPort contractScheduler;

    private ClientReaderService clientFinder;

    @Autowired
    public UserRestWs(LoanService service, ContractSchedulerPort contractScheduler, ClientReaderService clientReaderService){
        this.loanService = service;
        this.contractScheduler = contractScheduler;
        this.clientFinder = clientReaderService;
    }

    @GET
    @RequestMapping("/user/{id}/loanHistory")
    public ResponseEntity getHistoryOfUser(
            @Context HttpServletRequest request,
            @PathVariable final long id ) {

        Client client = clientFinder.findClientById(id);

        List<LoanContract> allContracts = contractScheduler.getAllContractsOfUser(client);

        if(allContracts.isEmpty()){
            return new ResponseEntity<>("No Contracts found for userId : " + id, HttpStatus.NOT_FOUND);
        }

        Gson gson = new Gson();
        String json = gson.toJson(allContracts, LoanContract[].class);

        return new ResponseEntity<>(json, HttpStatus.OK);

    }


    @GET
    @RequestMapping("/user/{id}")
    public ResponseEntity getUser(
            @Context HttpServletRequest request,
            @PathVariable final long id ) {

        Client client;

        try{
            client = clientFinder.findClientById(id);
        } catch(NoResultException exception) {
            return new ResponseEntity<>("Client not found", HttpStatus.BAD_REQUEST);
        }
        Gson gson = new Gson();
        String json = gson.toJson(client, Client.class);

        return new ResponseEntity<>(json, HttpStatus.OK);
    }

}
