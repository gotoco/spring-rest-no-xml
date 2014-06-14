package org.finance.app.adapters.webservices.restful.root.loan;

import org.finance.app.bports.services.LoanServiceApi;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    LoanServiceApi loanServiceApi;

    //DEPRICATED SERVICE
/*    @POST
    @RequestMapping("/applyForLoan")
    public Response applyForLoan( @Context HttpServletRequest request, FormJSON form) {

        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        form.setApplyingIpAddress(ipAddress);
        DateTime applicationDate = new DateTime();

        loanServiceApi.applyForLoan(form, applicationDate);

        return Response.status(200)
                       .entity("Application for a loan was approved from ip address : "  + ipAddress).build();
    }*/

    @POST
    @RequestMapping("/postForExtendLoan")
    public Response postForExtendLoan(
            @Context HttpServletRequest request,
            @FormParam("loanId") Long loanId,
            @FormParam("userId") Long userId,
            @FormParam("date") String date) {

        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        DateTime newExpirationDate = new DateTime(date);
        loanServiceApi.extendTheLoan(loanId, userId, newExpirationDate);

        return Response
                .status(200)
                .entity("Request for extend the loan was called from :" + ipAddress).build();
    }

    @Autowired
    public LoanGrantRestWs(LoanServiceApi loanService) {
        this.loanServiceApi = loanService;
    }
}
