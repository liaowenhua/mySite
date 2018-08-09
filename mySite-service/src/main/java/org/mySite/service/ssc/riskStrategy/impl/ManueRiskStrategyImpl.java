package org.mySite.service.ssc.riskStrategy.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mySite.common.constant.SSCConstants;
import org.mySite.common.util.MailUtil;
import org.mySite.domain.ResultAnalyseModle;
import org.mySite.domain.RiskStrategyModel;
import org.mySite.service.ssc.riskStrategy.IRiskStrategy;

public class ManueRiskStrategyImpl implements IRiskStrategy {
    private static Logger log = LogManager.getLogger(ManueRiskStrategyImpl.class);
    @Override
    public RiskStrategyModel getRiskRate(ResultAnalyseModle analyseResult, int orderCount) {
        log.info("当前使用手动风险模式。风险比例是" + SSCConstants.custom_price);
        RiskStrategyModel riskStrategyModel = new RiskStrategyModel();
        riskStrategyModel.setUnit(SSCConstants.getCustom_min_price);
        riskStrategyModel.setRiskRate(SSCConstants.custom_price);
        int price = (int)Math.round((analyseResult.getCurrentAmount() * riskStrategyModel.getRiskRate()) / (riskStrategyModel.getUnit()*4) / orderCount);
        riskStrategyModel.setPrice(price);
        if (analyseResult.getContinueLoseCount() >= 5 || analyseResult.getRecentWinCountOrderRate() <= 0.3) {
            MailUtil.sendSSCAcountMail("交易机会！当前最新统计期数为：" + analyseResult.getLastestSeason());
        }
        return riskStrategyModel;
    }
}
