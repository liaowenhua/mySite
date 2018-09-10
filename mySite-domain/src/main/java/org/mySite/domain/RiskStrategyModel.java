package org.mySite.domain;

public class RiskStrategyModel {
    //圆角分厘模式
    private double unit;
    //倍数
    private int price;
    //模式：0防守 1进攻
    private int mode;
    //资金风险比例
    private double riskRate;

    //账户总金额
    private double totalAmount;

    //订单总数
    private int orderCount;

    //总权重
    private int totalWeight = 1;

    public int getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(int totalWeight) {
        this.totalWeight = totalWeight;
    }

    public int getPriceWithWeight(int weight) {
        //(int)Math.round((analyseResult.getCurrentAmount() * riskStrategyModel.getRiskRate()) / (riskStrategyModel.getUnit()*4) / orderCount);
        int price;
        if (totalWeight == 1) {
            price = (int)Math.round((totalAmount * riskRate) / (unit*4) / orderCount);
        }else {
            price = (int)Math.round((totalAmount * riskRate * (weight / totalWeight)) / (unit*4) / orderCount);
        }
        return price;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getUnit() {
        return unit;
    }

    public void setUnit(double unit) {
        this.unit = unit;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public double getRiskRate() {
        return riskRate;
    }

    public void setRiskRate(double riskRate) {
        this.riskRate = riskRate;
    }
}
