package com.lwh.mySite.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mySite.domain.Recorder;
import org.mySite.domain.SSCOrder;
import org.mySite.service.ssc.ReplayService;

import java.util.List;

public class ReplayClient {
    private static Logger log = LogManager.getLogger(ReplayClient.class);
    public static void main(String[] args) throws Exception {
        ReplayService replayService = new ReplayService();
        List<String> dataList = replayService.getResultOfDates(20170915, 20160915);
        SSCOrder sscOrder = new SSCOrder();
        replayService.replay(sscOrder, dataList);
        Recorder recorder = sscOrder.getRecorder();
        log.info(recorder.toString());
        log.info("total profile is " + recorder.getProfile());
    }
}
