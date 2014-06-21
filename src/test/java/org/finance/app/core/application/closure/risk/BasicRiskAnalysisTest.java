package org.finance.app.core.application.closure.risk;


import junit.framework.Assert;
import org.finance.app.annotations.UnitTest;
import org.finance.app.core.application.closure.risk.BasicRiskAnalysis;
import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.risk.Risk;
import org.finance.app.sharedcore.objects.Money;
import org.finance.app.core.domain.events.saga.DoRiskAnalysisRequest;
import org.finance.test.builders.events.DoRiskAnalysisRequestBuilder;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.ArrayList;
import java.util.List;

@Category(UnitTest.class)
public class BasicRiskAnalysisTest {

    private static final String basicRejectCase = "Application was submitted in risky night hours with max possible value";

    @Test
    public void simpleShouldRejectLoan(){
        //Given
        BasicRiskAnalysis testedFunction = new BasicRiskAnalysis();
        DoRiskAnalysisRequest riskAnalysisRequest = new DoRiskAnalysisRequestBuilder().withRiskedData().build();
        List risks = prepareRisksToAnalyze();

        //When
        Boolean isRejected = !testedFunction.analyze(riskAnalysisRequest, risks).isEmpty();

        //Then
        Assert.assertTrue(isRejected);
    }

    @Test
    public void simpleShouldAcceptLoan(){
        //Given
        BasicRiskAnalysis testedFunction = new BasicRiskAnalysis();
        DoRiskAnalysisRequest riskAnalysisRequest = new DoRiskAnalysisRequestBuilder().withNoRiskedData().build();
        List risks = prepareRisksToAnalyze();

        //When
        Boolean isAccepted = testedFunction.analyze(riskAnalysisRequest, risks).isEmpty();

        //Then
        Assert.assertTrue(isAccepted);
    }

    private List<Risk> prepareRisksToAnalyze(){
        List risks = new ArrayList<Risk>();
        risks.add(new Risk(basicRejectCase));

        return risks;
    }
}
