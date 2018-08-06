package org.mySite.service.ssc.riskStrategy.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mySite.common.constant.SSCConstants;
import org.mySite.domain.ResultAnalyseModle;
import org.mySite.domain.RiskStrategyModel;
import org.mySite.service.ssc.riskStrategy.IRiskStrategy;

public class WinCountRiskStrategyImpl implements IRiskStrategy {
    private static Logger log = LogManager.getLogger(WinCountRiskStrategyImpl.class);
    public RiskStrategyModel getRiskRate(ResultAnalyseModle analyseResult, int orderCount) {
        RiskStrategyModel riskStrategyModel = new RiskStrategyModel();
        riskStrategyModel.setUnit(SSCConstants.min_unit);
        //每次的风险比例
        double riskRate = 0;
        if (analyseResult != null) {
            if (analyseResult.getContinueWinCount() >= SSCConstants.AutoStrategyConstant.most_continue_win_num || analyseResult.getWinCountRate() >= SSCConstants.AutoStrategyConstant.win_rate_threshold_up) {
                riskRate = SSCConstants.AutoStrategyConstant.risk_dowm;
                log.info("降低投入比例为：" + SSCConstants.AutoStrategyConstant.risk_dowm);
            }else if (analyseResult.getContinueLoseCount() >= SSCConstants.AutoStrategyConstant.most_continue_lose_num || analyseResult.getWinCountRate() <= SSCConstants.AutoStrategyConstant.win_rate_threshold_dowm) {
                riskRate = SSCConstants.AutoStrategyConstant.risk_up;
                log.info("增加投入比例为：" + SSCConstants.AutoStrategyConstant.risk_up);
            }else {
                riskRate = SSCConstants.AutoStrategyConstant.risk_normal;
            }
        }
        riskStrategyModel.setRiskRate(riskRate);
        int price = (int)Math.round((analyseResult.getCurrentAmount() * riskRate) / (riskStrategyModel.getUnit()*4) / orderCount);//4表示没注买4个号;
        riskStrategyModel.setPrice(price);
        return riskStrategyModel;
    }
}
