package org.finance.app.application.userstories;

import org.finance.app.core.domain.common.Form;
import org.finance.test.builders.FormBuilder;
import org.junit.Test;

/**
 * Created by maciek on 09.06.14.
 */
public class SubmissionForLoanTest {

    @Test
    public void newValidRequestLoanGranted(){

        Form validForm = new FormBuilder().withCorrectlyFilledForm().build();


    }
}
