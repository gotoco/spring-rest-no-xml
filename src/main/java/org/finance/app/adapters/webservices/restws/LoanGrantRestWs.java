package org.finance.app.adapters.webservices.restws;


import org.finance.app.ports.services.LoanServiceApi;
import org.finance.app.sharedcore.objects.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @POST
    @RequestMapping("/applyForLoan")
    public Response applyForLoan(
            @Context HttpServletRequest request,
            Form form) {

        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

    return Response
            .status(200)
            .entity("getUsers is called, from " + ipAddress).build();
    }

    @POST
    @RequestMapping("/postForExtendLoan")
    public Response postForExtendLoan(
            @Context HttpServletRequest request,
            @FormParam("loanId") Long loanId,
            @FormParam("userId") Long userId) {

        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        loanServiceApi.extendTheLoan(loanId, userId);


        return Response
                .status(200)
                .entity("getUsers is called, from " + ipAddress).build();
    }

    @Autowired
    public void setLoanServiceApi(LoanServiceApi loanServiceApi) {
        this.loanServiceApi = loanServiceApi;
    }
}
