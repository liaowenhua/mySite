package org.mySite.domain;

import java.util.List;
import java.util.Map;

//参数：
//        isTrace:0
//        traceWinStop:0
//        bounsType:0
//        order[0].playId:ssc_star1_dwd
//        order[0].content:-,-,-,0378,-
//        order[0].betCount:4
//        order[0].price:3
//        order[0].unit:0.2
//        traceOrders[0].seasonId:20180729-070
//        amount:2.4
//        count:4
//        force:0
public class SSCOrderParam {
    private String isTrace = "0";
    private String traceWinStop = "0";
    private String bounsType = "0";
    private SSCOrderNode[] order;
    private List<Map<String, String>> traceOrders;
    private double amount;
    private int count;
    private String force = "0";

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("SSCOrderParam info:");
        if (order == null) {
            sb.append("order is null").append("\n");
            return  sb.toString();
        }else {
            sb.append("order is:");
            for (SSCOrderNode node : order) {
                sb.append(node.getContent()).append("|");
            }
            sb = sb.delete(sb.length() - 1 , sb.length()).append("\n");
            sb.append("amount is:").append(amount).append("\n");
            sb.append("traceOrders[0] is:").append(traceOrders.get(0).toString()).append("\n");
            return sb.toString();
        }
    }


    public String getIsTrace() {
        return isTrace;
    }

    public void setIsTrace(String isTrace) {
        this.isTrace = isTrace;
    }


    public String getTraceWinStop() {
        return traceWinStop;
    }

    public void setTraceWinStop(String traceWinStop) {
        this.traceWinStop = traceWinStop;
    }

    public String getBounsType() {
        return bounsType;
    }

    public void setBounsType(String bounsType) {
        this.bounsType = bounsType;
    }

    public String getForce() {
        return force;
    }

    public void setForce(String force) {
        this.force = force;
    }

    public SSCOrderNode[] getOrder() {
        return order;
    }

    public void setOrder(SSCOrderNode[] order) {
        this.order = order;
    }

    public List<Map<String, String>> getTraceOrders() {
        return traceOrders;
    }

    public void setTraceOrders(List<Map<String, String>> traceOrders) {
        this.traceOrders = traceOrders;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
