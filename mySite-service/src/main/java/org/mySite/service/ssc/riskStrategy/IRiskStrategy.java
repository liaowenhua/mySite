package org.mySite.service.ssc.riskStrategy;

import org.mySite.domain.ResultAnalyseModle;
import org.mySite.domain.RiskStrategyModel;

public interface IRiskStrategy {
    RiskStrategyModel getRiskRate(ResultAnalyseModle analyseResult, int orderCount);
}
