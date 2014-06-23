package org.finance.app.adapters.webservices.restful.root.loan;

import org.finance.app.adapters.webservices.json.FormJSON;
import org.finance.app.bports.services.LoanService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Controller
@Produces({MediaType.APPLICATION_JSON + "; charset=UTF-8"})
@Consumes({MediaType.APPLICATION_JSON + "; charset=UTF-8"})
public class LoanGrantRestWs {

    LoanService loanService;

    @POST
    @RequestMapping("/loan/applyForLoan")
    public ResponseEntity applyForLoan( @Context HttpServletRequest request, FormJSON form) {

        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        form.setApplyingIpAddress(ipAddress);
        DateTime applicationDate = new DateTime();

        loanService.applyForLoan(form, applicationDate);

        return new ResponseEntity<>("Application for a loan was created from ip address : "  + ipAddress, HttpStatus.OK);
    }

    @POST
    @RequestMapping("/loan/{id}/postForExtendLoan")
    public ResponseEntity postForExtendLoan(
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

        return new ResponseEntity<>("Request for extend the loan was called from :" + ipAddress, HttpStatus.OK);
    }

    @Autowired
    public LoanGrantRestWs(LoanService loanService) {
        this.loanService = loanService;
    }
}
