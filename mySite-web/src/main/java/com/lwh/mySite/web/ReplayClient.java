package com.lwh.mySite.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mySite.common.bean.ReplayCookie;
import org.mySite.common.bean.RequestHeader;
import org.mySite.domain.Recorder;
import org.mySite.domain.SSCOrder;
import org.mySite.service.ssc.ReplayService;

import java.util.List;

public class ReplayClient extends BaseClient{
    private static Logger log = LogManager.getLogger(ReplayClient.class);
    public static void main(String[] args) throws Exception {
        ReplayService replayService = new ReplayService();
        ReplayCookie cookie = buildCookie();
        RequestHeader header = builderHeader();
        List<String> dataList = replayService.getResultOfDates(20180916, 20180916, null, header);
        SSCOrder sscOrder = new SSCOrder();
        replayService.replay(sscOrder, dataList);
        Recorder recorder = sscOrder.getRecorder();
        log.info(recorder.toString());
        log.info("total profile is " + recorder.getProfile());
    }


}
