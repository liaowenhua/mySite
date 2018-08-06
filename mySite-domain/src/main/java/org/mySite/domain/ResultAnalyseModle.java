package org.mySite.domain;

import com.alibaba.fastjson.JSONObject;
import org.mySite.common.constant.SSCConstants;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 结果分析数据
 */
public class ResultAnalyseModle {
    private static double initAmount = SSCConstants.ssc_monitor_init_amount;//初始金额
    private double currentAmount;//当前资金
    private int totalCount;//总单数
    private int winCount;//盈利总单数
    private int loseCount;//亏损总数量
    private double winRate;//盈利(亏损)金额占初始资金比例
    private int inTradingCount;//还未开奖的单数
    private double winCountRate;//盈利单比率
    private int continueWinCount;//连赢单数量
    private int continueLoseCount;//连续亏损单数量
    private String lastestSeason;//统计的最新期数
    private Date startTime;//统计的起始时间
    private Date endTime;//统计的结束时间
    private Map<String,Node> detailResult = new HashMap<String,Node>();//每种组合的详细结果。

    public ResultAnalyseModle() {
        detailResult.put(SSCConstants.code_map_0, new Node());
        detailResult.put(SSCConstants.code_map_2,new Node());
        detailResult.put(SSCConstants.code_map_5,new Node());
        detailResult.put(SSCConstants.code_map_9,new Node());
    }

    public class Node {
        private int totalCount = 0;
        private int winCount = 0;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getWinCount() {
            return winCount;
        }

        public void setWinCount(int winCount) {
            this.winCount = winCount;
        }
    }



    public double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public static double getInitAmount() {
        return initAmount;
    }

    public static void setInitAmount(double amount) {
        initAmount = amount;
    }

    public int getLoseCount() {
        return loseCount;
    }

    public void setLoseCount(int loseCount) {
        this.loseCount = loseCount;
    }

    public String getLastestSeason() {
        return lastestSeason;
    }

    public void setLastestSeason(String lastestSeason) {
        this.lastestSeason = lastestSeason;
    }

    public int getContinueLoseCount() {
        return continueLoseCount;
    }

    public void setContinueLoseCount(int continueLoseCount) {
        this.continueLoseCount = continueLoseCount;
    }

    public int getContinueWinCount() {
        return continueWinCount;
    }

    public void setContinueWinCount(int continueWinCount) {
        this.continueWinCount = continueWinCount;
    }

    public int getInTradingCount() {
        return inTradingCount;
    }

    public void setInTradingCount(int inTradingCount) {
        this.inTradingCount = inTradingCount;
    }

    public Map<String, Node> getDetailResult() {
        return detailResult;
    }

    public void setDetailResult(Map<String, Node> detailResult) {
        this.detailResult = detailResult;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public double getWinCountRate() {
        if (winCount == 0 || (totalCount - inTradingCount) == 0) return 0;
        //new BigDecimal(orderMoney).setScale(2, RoundingMode.DOWN);
        winCountRate = (double)winCount / (double)(totalCount - inTradingCount);
        return  winCountRate;
    }

    public void setWinCountRate(double winCountRate) {
        this.winCountRate = winCountRate;
    }

    public double getWinRate() {
        if (currentAmount > 0 && initAmount > 0) {
            this.winRate = (currentAmount - initAmount) / initAmount;
        }
        return winRate;
    }

    public void setWinRate(double winRate) {
        this.winRate = winRate;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
