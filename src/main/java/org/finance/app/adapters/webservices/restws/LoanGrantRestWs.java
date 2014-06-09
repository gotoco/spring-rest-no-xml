package org.finance.app.adapters.webservices.restws;


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
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class LoanGrantRestWs {

    @POST
    @RequestMapping("/postLoan")
    public Response postForLoan(
            @Context HttpServletRequest request,
            @FormParam("name") String name,
            @FormParam("age") int age) {

        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

    return Response
            .status(200)
            .entity("getUsers is called, from ").build();
    }

 /*   @POST
    @RequestMapping("/")
    public*/
}
