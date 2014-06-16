package org.finance.app.adapters.webservices.restful.root.user;

import com.google.gson.Gson;
import org.finance.app.adapters.webservices.json.FormJSON;
import org.finance.app.bports.crudes.ClientReaderService;
import org.finance.app.bports.crudes.ContractSchedulerPort;
import org.finance.app.bports.services.LoanServiceApi;
import org.finance.app.sharedcore.objects.Client;
import org.finance.app.sharedcore.objects.LoanContract;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@Produces({MediaType.APPLICATION_JSON + "; charset=UTF-8"})
@Consumes({MediaType.APPLICATION_JSON + "; charset=UTF-8"})
public class UserRestWs {

    private LoanServiceApi loanService;

    private ContractSchedulerPort contractScheduler;

    private ClientReaderService clientFinder;

    @Autowired
    public UserRestWs(LoanServiceApi service, ContractSchedulerPort contractScheduler, ClientReaderService clientReaderService){
        this.loanService = service;
        this.contractScheduler = contractScheduler;
        this.clientFinder = clientReaderService;
    }

    @POST
    @RequestMapping("/user/{id}/applyForLoan")
    public Response applyForLoan( @Context HttpServletRequest request, FormJSON form) {

        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        form.setApplyingIpAddress(ipAddress);
        DateTime applicationDate = new DateTime();

        loanService.applyForLoan(form, applicationDate);

        return Response.status(201)
                .entity("Application for a loan was created from ip address : "  + ipAddress).build();
    }

    @GET
    @RequestMapping("/user/{id}/loanHistory")
    public Response getHistoryOfUser(
            @Context HttpServletRequest request,
            @PathVariable final long id ) {

        Client client = clientFinder.findClientById(id);

        List<LoanContract> allContracts = contractScheduler.getAllContractsOfUser(client);

        if(allContracts.isEmpty()){
            return Response
                    .status(404)
                    .entity("No Contracts found for userId : " + id).build();
        }

        return Response
                .status(200)
                .entity(allContracts).build();
    }


    @GET
    @RequestMapping("/user/{id}")
    public ResponseEntity getUser(
            @Context HttpServletRequest request,
            @PathVariable final long id ) {

        Collection<Link> links = new ArrayList<Link>();
        Client client = null;

        try{
            client = clientFinder.findClientById(id);
        } catch(NoResultException exception) {

            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

//            return Response
//                    .status(404)
//                    .entity("No Contracts found for userId : " + id).build();
        }
        Gson gson = new Gson();
        String json = gson.toJson(client, Client.class);

        return new ResponseEntity<String>(json, HttpStatus.OK);
    }

}
