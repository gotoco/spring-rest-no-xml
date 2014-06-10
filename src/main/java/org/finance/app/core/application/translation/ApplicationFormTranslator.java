package org.finance.app.core.application.translation;

import org.finance.app.sharedcore.objects.Form;
import org.springframework.stereotype.Component;

@Component("ApplicationFormTranslator")
public class ApplicationFormTranslator {

    public Form createFormFromRequest(){
        Form resultForm = new Form();
    }
}
