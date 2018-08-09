package org.mySite.service.ssc.riskStrategy.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mySite.common.util.MailUtil;
import org.mySite.domain.ResultAnalyseModle;
import org.mySite.domain.RiskStrategyModel;
import org.mySite.service.ssc.riskStrategy.IRiskStrategy;

public class RecentWinRateRistStrategyImpl implements IRiskStrategy {

    private static Logger log = LogManager.getLogger(RecentWinRateRistStrategyImpl.class);
    //防守模式，使用小的risk_rate
    private static int mode_defend = 0;
    //进攻模式，使用较大的risk_rate
    private static int mode_fighting = 1;
    //初始的模式，默认为防守模式
    private static int mode_current = mode_defend;
    private static double unit_defend = 0.002;
    private static double unit_fighting = 0.02;
    //当盈利率小于等于该值时，mode_current模式调整为 mode_fighting
    private static double win_rate_threshold_dowm = -0.04;
    //当盈利率大于等于该值时，模式调整为 mode_defend
    private static double win_rate_threshold_up = 0.05;
    //防守模式下的资金风险比例
    private static double risk_defend = 0.002;
    //进攻模式下的资金风险比例，为防守模式下的10倍
    private static double risk_fighting = 25*risk_defend;

    private static double recent_WinCountOrder_Rate_Threshold  =  0.43;
    private static double recent_WinCountOrder_Rate_Threshold_Up  =  1;
    @Override
    public RiskStrategyModel getRiskRate(ResultAnalyseModle analyseResult, int orderCount) {
        RiskStrategyModel riskStrategyModel = new RiskStrategyModel();
        if (analyseResult != null) {
            log.info("current mode is " + mode_current);
            log.info("RecentWinCountOrderRate is " + analyseResult.getRecentWinCountOrderRate());
            log.info("recent_WinCountOrder_Rate_Threshold is " + recent_WinCountOrder_Rate_Threshold);
            if (mode_current == mode_defend) {
                if (analyseResult.getRecentWinCountOrderRate() <= recent_WinCountOrder_Rate_Threshold) {
                    mode_current = mode_fighting;
                    riskStrategyModel.setUnit(unit_fighting);
                    riskStrategyModel.setRiskRate(risk_fighting);
                    riskStrategyModel.setMode(mode_fighting);
                    log.info("mode_defend change to mode_fighting。当前余额为:" + analyseResult.getCurrentAmount());

                    MailUtil.sendSSCAcountMail("mode_defend change to mode_fighting。当前余额为:" + analyseResult.getCurrentAmount());
                }else {
                    log.info("无需切换模式");
                    riskStrategyModel.setUnit(unit_defend);
                    riskStrategyModel.setRiskRate(risk_defend);
                    riskStrategyModel.setMode(mode_defend);
                }
//                if (analyseResult.getCurrentAmount() > ResultAnalyseModle.getInitAmount()) {
//                    log.info("当前余额" + analyseResult.getCurrentAmount() + "大于初始资金" + ResultAnalyseModle.getInitAmount() + ".重新初始化资金为" + analyseResult.getCurrentAmount());
//                    ResultAnalyseModle.setInitAmount(analyseResult.getCurrentAmount());
//                }
            }else if (mode_current == mode_fighting) {
                if (analyseResult.getRecentWinCountOrderRate() >= recent_WinCountOrder_Rate_Threshold_Up
                        && analyseResult.getCurrentAmount() - ResultAnalyseModle.getInitAmount() > 0) {
                    mode_current = mode_defend;
                    riskStrategyModel.setUnit(unit_defend);
                    riskStrategyModel.setRiskRate(risk_defend);
                    riskStrategyModel.setMode(mode_defend);
                   // ResultAnalyseModle.setInitAmount(analyseResult.getCurrentAmount());
                    log.info("mode_fighting change to mode_defend。当前余额为:" + analyseResult.getCurrentAmount());
                    MailUtil.sendSSCAcountMail("mode_fighting change to mode_defend。当前余额为:" + analyseResult.getCurrentAmount());
                }else {
                    log.info("无需切换模式");
                    riskStrategyModel.setUnit(unit_fighting);
                    riskStrategyModel.setRiskRate(risk_fighting);
                    riskStrategyModel.setMode(mode_fighting);
                }
                if (analyseResult.getWinRate() <= -0.5) {
                    log.error("注意：已经亏损50%！");
                    MailUtil.sendSSCAcountMail("注意：已经亏损50%！当前余额为:" + analyseResult.getCurrentAmount());
                }
            }
            int price = (int)Math.round((analyseResult.getCurrentAmount() * riskStrategyModel.getRiskRate()) / (riskStrategyModel.getUnit()*4) / orderCount);
            riskStrategyModel.setPrice(price);
        }
        return riskStrategyModel;
    }
}
