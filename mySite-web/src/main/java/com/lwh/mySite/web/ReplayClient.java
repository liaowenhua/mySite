package com.lwh.mySite.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mySite.common.bean.ReplayCookie;
import org.mySite.common.bean.RequestHeader;
import org.mySite.domain.Recorder;
import org.mySite.domain.SSCOrder;
import org.mySite.service.ssc.ReplayService;
import sun.misc.Request;

import java.util.List;

public class ReplayClient {
    private static Logger log = LogManager.getLogger(ReplayClient.class);
    public static void main(String[] args) throws Exception {
        ReplayService replayService = new ReplayService();
        ReplayCookie cookie = buildCookie();
        RequestHeader header = builderHeader();
        List<String> dataList = replayService.getResultOfDates(20190916, 20190916, cookie, header);
        SSCOrder sscOrder = new SSCOrder();
        replayService.replay(sscOrder, dataList);
        Recorder recorder = sscOrder.getRecorder();
        log.info(recorder.toString());
        log.info("total profile is " + recorder.getProfile());
    }

    private static ReplayCookie buildCookie() {
        ReplayCookie cookie = new ReplayCookie();
        cookie.setCLICKSTRN_ID("61.48.36.89-1537022842.774494::1B0EB7CEBEE11A4F8E903B0B3571FE0A");
        cookie.setHm_lpvt_4f816d475bb0b9ed640ae412d6b42cab("1537062852");
        cookie.setHm_lvt_4f816d475bb0b9ed640ae412d6b42cab("1537022811,1537060080,1537062852");
        cookie.setWT_FPC("id=undefined:lv=1537062851532:ss=1537062851532");
        cookie.set__utma("63332592.1169242678.1537062853.1537062853.1537062853.1");
        cookie.set__utmb("63332592.1.10.1537062853");
        cookie.set__utmc("63332592");
        cookie.set__utmt("1");
        cookie.set__utmz("63332592.1537062853.1.1.utmcsr=baidu|utmccn=(organic)|utmcmd=organic");
        cookie.setBdshare_firstime("1537060080092");
        cookie.setMotion_id("1537062868974_0.475365109450562");
        cookie.setSdc_session("1537062851534");
        cookie.setSdc_userflag("1537062851535::1537062851535::1");
        return cookie;
    }

    private static RequestHeader builderHeader() {
        RequestHeader header = new RequestHeader();
        header.setHost("kaijiang.500.com");
        header.setReferer("http://kaijiang.500.com/ssc.shtml");
        header.setUserAgent("Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Mobile Safari/537.36");
        return header;
    }
}
