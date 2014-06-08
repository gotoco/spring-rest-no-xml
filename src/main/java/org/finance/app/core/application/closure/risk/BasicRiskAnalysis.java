package org.finance.app.core.application.closure.risk;

import org.finance.app.core.domain.events.impl.saga.DoRiskAnalysisRequest;
import org.finance.app.core.domain.risk.Decision;
import org.finance.app.core.domain.risk.RiskAnalysisFunction;
import org.finance.app.ddd.annotation.Function;

/**
 * Created by maciek on 08.06.14.
 */
public class BasicRiskAnalysis implements RiskAnalysisFunction {

    @Function
    @Override
    public Decision analyze(DoRiskAnalysisRequest request) {
        return null;
    }
}
