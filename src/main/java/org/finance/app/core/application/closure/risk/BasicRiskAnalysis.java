package org.finance.app.core.application.closure.risk;

import org.finance.app.core.domain.common.Money;
import org.finance.app.core.domain.events.impl.saga.DoRiskAnalysisRequest;
import org.finance.app.core.domain.risk.Risk;
import org.finance.app.core.domain.risk.RiskAnalysisFunction;
import org.finance.app.ddd.annotation.Function;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;


@Component
public class BasicRiskAnalysis implements RiskAnalysisFunction {

    private static final Integer beginOfRiskyHours = 0;
    private static final Integer endOfRiskyHours = 6;
    private static final Money maxValueOfLoan = new Money(3000);
    private static final String basicRejectCase = "External conditions of the loan are worrying";

    @Function
    @Override
    public Risk analyze(DoRiskAnalysisRequest request) {
        Boolean risk;
        DateTime requestTime = request.getApplicationTime();
        DateTime riskyHoursStart = new DateTime().withTimeAtStartOfDay().plusHours(beginOfRiskyHours);
        DateTime riskyHoursEnd = new DateTime().withTimeAtStartOfDay().plusHours(endOfRiskyHours);
        Boolean isInRiskyHours = requestTime.isAfter(riskyHoursStart) && requestTime.isBefore(riskyHoursEnd);
        Boolean isLoanWithMaxValue = request.getLoanValue().equals(maxValueOfLoan) || request.getLoanValue().greaterThan(maxValueOfLoan);

        risk = isInRiskyHours && isLoanWithMaxValue;

        return risk ? new Risk(risk, basicRejectCase) : new Risk(risk);
    }
}
