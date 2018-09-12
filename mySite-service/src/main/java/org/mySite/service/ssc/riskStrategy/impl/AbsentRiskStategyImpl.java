package org.mySite.service.ssc.riskStrategy.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mySite.domain.AbsentedNode;
import org.mySite.domain.ResultAnalyseModle;
import org.mySite.domain.RiskStrategyModel;
import org.mySite.domain.SSCOrder;
import org.mySite.service.ssc.riskStrategy.IRiskStrategy;

import java.util.Iterator;
import java.util.Set;

/**
 * 遗漏下单策略
 */
public class AbsentRiskStategyImpl implements IRiskStrategy {

    private static Logger log = LogManager.getLogger(AbsentRiskStategyImpl.class);

    private static double unit = 0.002;
    //资金风险比例
    private static double risk = 0.05;

    private static int maxAbsent = 7;

    private static int minAbsent = 3;
    @Override
    public RiskStrategyModel getRiskRate(ResultAnalyseModle analyseResult, SSCOrder order) {
        RiskStrategyModel riskStrategyModel = new RiskStrategyModel();
        riskStrategyModel.setRiskRate(risk);
        riskStrategyModel.setUnit(unit);
        riskStrategyModel.setOrderCount(order.getAvailableOrderCount());
        riskStrategyModel.setTotalAmount(analyseResult.getCurrentAmount());
        riskStrategyModel.setMaxAbsent(maxAbsent);
        int totalWeight = 0;
        Set<AbsentedNode> absentedNodeSet = order.getAbsentedNodeSet();
        Iterator<AbsentedNode> it = absentedNodeSet.iterator();
        while (it.hasNext()) {
            AbsentedNode node = it.next();
            if (node.getAbsent() > maxAbsent) {
                log.info("超过最大遗漏，剔除.info:" + node.toString());
                it.remove();
                continue;
            }
            if (node.getAbsent() >= minAbsent) {
                log.info("达到最小遗漏次数.info:" + node.toString());
                node.setAvaliable(true);
                totalWeight = totalWeight + node.getAbsent() + 1;
            }

        }

        riskStrategyModel.setTotalWeight(totalWeight == order.getAvailableOrderCount() ? 1 : totalWeight);
        return riskStrategyModel;
    }
}
