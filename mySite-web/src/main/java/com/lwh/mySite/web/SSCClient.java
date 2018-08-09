package com.lwh.mySite.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mySite.common.constant.SSCConstants;
import org.mySite.domain.RiskStrategyModel;
import org.mySite.domain.SSCInfo;
import org.mySite.domain.SSCOrder;
import org.mySite.service.ssc.SSCService;

/**
 * Hello world!
 *
 */

public class SSCClient {
    private static Logger log = LogManager.getLogger(SSCClient.class);
    public static void main( String[] args ) throws InterruptedException {
         boolean isInit = true;
        while (true) {
            SSCService sscService = new SSCService();
            SSCInfo sscInfo = sscService.getSSCInfo();
            SSCOrder sscOrder = sscService.mergeOrder(sscInfo, isInit);
            String report = sscService.genAnalyseReport(sscInfo);
            log.info("分析报告：" + report.replace("\\<br\\>", ";"));
            RiskStrategyModel riskStrategyInfo = sscService.getRiskStrategyInfo(sscInfo, sscOrder.getOrderCount());
            sscService.submitOrders(sscOrder, sscInfo, riskStrategyInfo);
            isInit = false;
            log.info("进入休眠...");
            Thread.sleep(SSCConstants.interval_mill_second);
            log.info("休眠结束");
        }
    }
}
