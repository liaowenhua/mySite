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
