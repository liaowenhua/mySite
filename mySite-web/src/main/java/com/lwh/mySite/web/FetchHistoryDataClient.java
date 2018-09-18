package com.lwh.mySite.web;

import org.mySite.service.ssc.ReplayService;

public class FetchHistoryDataClient extends BaseClient{
    public static void main(String[] args) throws Exception{
        ReplayService replayService = new ReplayService();
        //replayService.storeHistoryData(20160101, 20180917, buildCookie(), builderHeader());
        replayService.storeHistoryData(20180917, 20180918, buildCookie(), builderHeader());
    }
}
