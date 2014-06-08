package org.finance.app.core.domain.risk;

import org.finance.app.core.domain.events.impl.saga.DoRiskAnalysisRequest;

/**
 * Created by maciek on 08.06.14.
 */
public interface RiskAnalysisFunction {

    public Risk analyze(DoRiskAnalysisRequest request);

}
