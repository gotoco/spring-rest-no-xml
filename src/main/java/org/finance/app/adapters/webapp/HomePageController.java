package org.finance.app.adapters.webapp;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomePageController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcomePageInJsp(Model model) {
        model.addAttribute("message", "This is Welcome page for Java Server Faces (JSF)");
        return "homepage";
    }

    @RequestMapping(value = "/konto", method = RequestMethod.GET)
    public String accountPageInJsp(Model model) {
        model.addAttribute("message", "This is Welcome page for Java Server Faces (JSF)");
        return "homepage";
    }
}
