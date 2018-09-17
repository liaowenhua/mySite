package com.lwh.mySite.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mySite.common.constant.SSCConstants;
import org.mySite.common.util.SeasonIdUtils;
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
        String lastestCheckedSeasonId = "";//最近一次检查的期数
        while (true) {

            log.info("current order:" + SSCService.getCurrentOrders());
            SSCService sscService = new SSCService();
            SSCInfo sscInfo = sscService.getSSCInfo();
            //最新开奖的期数
            String lastestSeasonId = sscInfo.getLastestSeasonId();
            //当前开放下注的订单
            String currentOpenSeasonId = sscInfo.getCurrentOpenSeasonId();

            //当前投注期数比最新开奖期数大1
            if(SeasonIdUtils.isBiggerThanOne(currentOpenSeasonId, lastestSeasonId ) && !sscInfo.isInTrading()) {
                if (lastestCheckedSeasonId.equals(lastestSeasonId)) {
                    log.info("还未更新，进入休眠...");
                    Thread.currentThread().sleep(SSCConstants.interval_mill_second);
                    continue;
                }else {
                    lastestCheckedSeasonId = lastestSeasonId;
                    SSCOrder sscOrder;
                    if (isInit) {
                        sscOrder = sscService.initOrder(sscInfo);
                    }else {
                        sscOrder = sscService.mergeOrder(sscInfo);
                    }
                    String report = sscService.genAnalyseReport(sscInfo);
                    log.info("分析报告：" + report.replace("\\<br\\>", ";"));
                    RiskStrategyModel riskStrategyInfo = sscService.getRiskStrategyInfo(sscInfo, sscOrder);
                    sscService.submitOrders(sscOrder, sscInfo, riskStrategyInfo);
                    isInit = false;
                }
            }else if(!SeasonIdUtils.isBiggerThanOne(currentOpenSeasonId, lastestSeasonId )){
                log.info("正在开奖,进入休眠....");
                Thread.currentThread().sleep(SSCConstants.interval_mill_second);
                continue;
            }else {
                log.info("有未开奖订单....");
                Thread.currentThread().sleep(SSCConstants.interval_mill_second);
                continue;
            }
        }
    }
}
