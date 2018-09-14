package org.mySite.domain;

import com.alibaba.fastjson.JSONArray;

import java.util.Arrays;

public class SSCInfo {
    private float amount;//账户余额
    private String lastestSeasonId;//最近一次开奖期数
    private String currentOpenSeasonId;//正在投注的期数
    private String[] lastestCode;//最近一期开奖结果
    private boolean inTrading;//是否有还未开奖的订单
    private SSCOrder currentOrder = new SSCOrder();//当前未开奖的订单
    private JSONArray opensArray;//近期开奖结果

    public JSONArray getOpensArray() {
        return opensArray;
    }

    public void setOpensArray(JSONArray opensArray) {
        this.opensArray = opensArray;
    }

    public SSCOrder getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(SSCOrder currentOrder) {
        this.currentOrder = currentOrder;
    }

    public boolean isInTrading() {
        return inTrading;
    }

    public void setInTrading(boolean inTrading) {
        this.inTrading = inTrading;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getLastestSeasonId() {
        return lastestSeasonId;
    }

    public void setLastestSeasonId(String lastestSeasonId) {
        this.lastestSeasonId = lastestSeasonId;
    }

    public String[] getLastestCode() {
        return lastestCode;
    }

    public void setLastestCode(String[] lastestCode) {
        this.lastestCode = lastestCode;
    }

    public String getCurrentOpenSeasonId() {
        return currentOpenSeasonId;
    }

    public void setCurrentOpenSeasonId(String currentOpenSeasonId) {
        this.currentOpenSeasonId = currentOpenSeasonId;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("账户余额:").append(amount)
                .append(",最近开奖期数:").append(lastestSeasonId)
                .append(",最近开奖号码:").append(Arrays.toString(lastestCode))
                .append(",,正在开放投注的期数:").append(currentOpenSeasonId);
        if (!currentOrder.isEmpty()) {
            sb.append(",还未开奖订单:").append(currentOrder.toString());
        }
        return sb.toString();
    }
}
