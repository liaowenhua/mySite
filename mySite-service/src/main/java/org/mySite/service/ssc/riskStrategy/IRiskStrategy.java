package org.mySite.service.ssc.riskStrategy;

import org.mySite.domain.ResultAnalyseModle;
import org.mySite.domain.RiskStrategyModel;
import org.mySite.domain.SSCOrder;

public interface IRiskStrategy {
    RiskStrategyModel getRiskRate(ResultAnalyseModle analyseResult, SSCOrder order);
}
