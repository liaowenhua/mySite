package org.mySite.domain;

public class SSCOrderNode {
    private String playId = "ssc_star1_dwd";
    private String content;//-,-,-,0378,-
    private int betCount = 4;//固定4
    private int price;//倍数
    private double unit;//最小单位金额，如分模式下 为0.02

    transient private int weight = 1;

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getPlayId() {
        return playId;
    }

    public void setPlayId(String playId) {
        this.playId = playId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getBetCount() {
        return betCount;
    }

    public void setBetCount(int betCount) {
        this.betCount = betCount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getUnit() {
        return unit;
    }

    public void setUnit(double unit) {
        this.unit = unit;
    }
}
