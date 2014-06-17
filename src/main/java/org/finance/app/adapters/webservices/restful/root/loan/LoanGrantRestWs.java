package org.finance.app.adapters.webservices.restful.root.loan;

import org.finance.app.adapters.webservices.json.FormJSON;
import org.finance.app.bports.services.LoanServiceApi;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Controller
@Produces({MediaType.APPLICATION_JSON + "; charset=UTF-8"})
@Consumes({MediaType.APPLICATION_JSON + "; charset=UTF-8"})
public class LoanGrantRestWs {

    LoanServiceApi loanService;

    @POST
    @RequestMapping("/loan/applyForLoan")
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

    @POST
    @RequestMapping("/loan/{id}/postForExtendLoan")
    public Response postForExtendLoan(
            @Context HttpServletRequest request,
            @PathVariable final long id,
            @FormParam("loanId") Long loanId,
            @FormParam("userId") Long userId,
            @FormParam("date") String date) {

        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        DateTime newExpirationDate = new DateTime(date);
        loanService.extendTheLoan(loanId, userId, newExpirationDate);

        return Response
                .status(200)
                .entity("Request for extend the loan was called from :" + ipAddress).build();
    }

    @Autowired
    public LoanGrantRestWs(LoanServiceApi loanService) {
        this.loanService = loanService;
    }
}
