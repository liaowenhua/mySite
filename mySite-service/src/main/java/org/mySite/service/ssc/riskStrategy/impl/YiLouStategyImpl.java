package org.mySite.service.ssc.riskStrategy.impl;

import org.mySite.domain.ResultAnalyseModle;
import org.mySite.domain.RiskStrategyModel;
import org.mySite.service.ssc.riskStrategy.IRiskStrategy;

/**
 * 遗漏下单策略。当某位达到最大遗漏数后，开始下单，并且已固定的资金风险比例下固定单数
 */
public class YiLouStategyImpl  implements IRiskStrategy {
    @Override
    public RiskStrategyModel getRiskRate(ResultAnalyseModle analyseResult, int orderCount) {
        return null;
    }
}
