package com.lwh.mySite.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mySite.common.constant.SSCConstants;
import org.mySite.common.constant.SSCConstants.AutoStrategy;
import org.mySite.common.util.MailUtil;
import org.mySite.domain.ResultAnalyseModle;
import org.mySite.domain.SSCInfo;
import org.mySite.service.ssc.SSCService;

public class SSCMonitor {

    private static Logger log = LogManager.getLogger(SSCMonitor.class);
    public static void main( String[] args ) throws InterruptedException {
        SSCService sscService = new SSCService();
        float initAmount = SSCConstants.ssc_monitor_init_amount;
        if (initAmount == 0) {
            SSCInfo sscInfo = sscService.getSSCInfo();
            initAmount = sscInfo.getAmount();
            sscInfo = null;
        }


        while (true) {
            log.info("开始执行监控任务...");
            SSCService sscServiceNew = new SSCService();
            SSCInfo sscInfoNew = sscServiceNew.getSSCInfo();
            ResultAnalyseModle analyseModle = sscServiceNew.analyseResult("", "", AutoStrategy.analyse_count);
            String analyseReport = sscServiceNew.getAnalyseInfo(analyseModle, sscInfoNew);
            MailUtil.sendSSCAcountMail(analyseReport);
            log.info("监控任务执行完成！");
            log.info("监控任务休眠....");
            //10分钟发送一封
            Thread.sleep(SSCConstants.monitor_interval_mill_second);
        }
    }
}
