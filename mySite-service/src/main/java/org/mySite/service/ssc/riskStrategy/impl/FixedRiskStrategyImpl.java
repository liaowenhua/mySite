package org.mySite.service.ssc.riskStrategy.impl;

import org.mySite.domain.ResultAnalyseModle;
import org.mySite.domain.RiskStrategyModel;
import org.mySite.service.ssc.riskStrategy.IRiskStrategy;

public class FixedRiskStrategyImpl implements IRiskStrategy {

    @Override
    public RiskStrategyModel getRiskRate(ResultAnalyseModle analyseResult, int orderCount) {

        throw new UnsupportedOperationException("FixedRiskStrategyImpl不支持getRiskRate方法。请直接调用静态方法getStrategyModel");
    }

    public static RiskStrategyModel getStrategyModel(double unit, double riskRate, double amount, int orderCount) {
        if (orderCount == 0 || amount <= 0 || unit == 0 || riskRate >= 0.1) return null;
        RiskStrategyModel riskStrategyModel = new RiskStrategyModel();
        riskStrategyModel.setUnit(unit);
        riskStrategyModel.setRiskRate(riskRate);
        int price = (int)Math.round((amount * riskStrategyModel.getRiskRate()) / (riskStrategyModel.getUnit()*4) / orderCount);
        riskStrategyModel.setPrice(price);
        return riskStrategyModel;
    }
}
