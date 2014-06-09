package org.finance.app.adapters.webapp;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomePageController {

    @RequestMapping(value = "/jsfdemo", method = RequestMethod.GET)
    public String welcomePageForJSF(Model model) {
        model.addAttribute("message", "This is Welcome page for Java Server Faces (JSF)");
        return "indexJSF";
    }
}
