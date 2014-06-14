package org.finance.app.core.application.closure.risk;

import org.finance.app.sharedcore.objects.Money;
import org.finance.app.core.domain.events.saga.DoRiskAnalysisRequest;
import org.finance.app.core.domain.risk.Risk;
import org.finance.app.core.domain.risk.RiskAnalysisFunction;
import org.finance.app.core.ddd.annotation.Function;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class BasicRiskAnalysis implements RiskAnalysisFunction {

    private static final Integer beginOfRiskyHours = 0;
    private static final Integer endOfRiskyHours = 6;
    private static final Money maxValueOfLoan = new Money(3000);
    private static final String basicRejectCase = "Application was submitted in risky night hours with max possible value";

    @Function
    @Override
    public List<Risk> analyze(DoRiskAnalysisRequest request, List<Risk> allRisks) {
        List<Risk> risks = new ArrayList<>();
        DateTime requestTime = request.getApplicationTime();
        DateTime riskyHoursStart = new DateTime().withTimeAtStartOfDay().plusHours(beginOfRiskyHours);
        DateTime riskyHoursEnd = new DateTime().withTimeAtStartOfDay().plusHours(endOfRiskyHours);
        Boolean isInRiskyHours = requestTime.isAfter(riskyHoursStart) && requestTime.isBefore(riskyHoursEnd);
        Boolean isLoanWithMaxValue = request.getLoanValue().equals(maxValueOfLoan) || request.getLoanValue().greaterThan(maxValueOfLoan);

        if(isInRiskyHours && isLoanWithMaxValue){

            for(Risk risk : allRisks){
                if(risk.getCause().compareToIgnoreCase(basicRejectCase) == 0){
                    risks.add(risk);
                }
            }
        }

       return risks;
    }
}
