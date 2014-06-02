package org.finance.app.adapters;

/**
 * Author: Mazeryt Freager
 * Contact: skirki@o2.pl
 * Date: 6/1/14
 * Time: 11:11 AM
 */
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloWorldController {

    @RequestMapping("/hello")
    public String hello(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "helloworld";
    }

}
