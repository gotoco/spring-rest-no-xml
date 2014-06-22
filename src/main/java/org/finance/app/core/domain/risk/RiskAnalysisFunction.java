package org.finance.app.core.domain.risk;

import org.finance.app.core.domain.events.saga.DoRiskAnalysisRequest;

import java.util.List;

public interface RiskAnalysisFunction {

    public List<Risk> analyze(DoRiskAnalysisRequest request, List<Risk> allRisks);

}
