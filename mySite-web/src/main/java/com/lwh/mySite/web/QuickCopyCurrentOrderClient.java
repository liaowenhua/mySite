package com.lwh.mySite.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mySite.domain.RiskStrategyModel;
import org.mySite.domain.SSCInfo;
import org.mySite.domain.SSCOrder;
import org.mySite.service.ssc.SSCService;
import org.mySite.service.ssc.riskStrategy.impl.FixedRiskStrategyImpl;

/**
 * 快速复制当前订单，并且按照自定义的风险率重新下单
 */
public class QuickCopyCurrentOrderClient {
    private static double fixed_min_unit  = 0.002;//分模式
    private static double fixed_risk_rate = 1;//资金风险率。最大0.1
    private static double initAmount = 0;
    private static Logger log = LogManager.getLogger(QuickCopyCurrentOrderClient.class);
    public static void main( String[] args ) {
        SSCService sscService = new SSCService();
        SSCInfo sscInfo = sscService.getSSCInfo();
        SSCOrder copyedOrder = sscInfo.getCurrentOrder();
        RiskStrategyModel strategyModel = FixedRiskStrategyImpl.getStrategyModel(fixed_min_unit, fixed_risk_rate, sscInfo.getAmount(), sscInfo.getCurrentOrder().getAvailableOrderCount());
        if (strategyModel == null) {
            log.info("复制当前订单发生错误，请检查当前是否有订单，或者参数是否设置正确！");
            return;
        }
        sscService.submitOrders(copyedOrder, sscInfo, strategyModel);
    }
}
