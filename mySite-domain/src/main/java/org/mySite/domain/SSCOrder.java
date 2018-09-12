package org.mySite.domain;

import java.util.HashSet;
import java.util.Set;

public class SSCOrder {
    private String orderId;
    private String seasonId;//当前订单的期数
    private Set<AbsentedNode> absentedNodeSet = new HashSet<AbsentedNode>();

    public Set<AbsentedNode> getAbsentedNodeSet() {
        return absentedNodeSet;
    }

    public void setAbsentedNodeSet(Set<AbsentedNode> absentedNodeSet) {
        this.absentedNodeSet = absentedNodeSet;
    }

    public void putAbsentedNode(AbsentedNode node) {
        absentedNodeSet.add(node);
    }

    public boolean isEmpty() {
        return absentedNodeSet.isEmpty();
    }

    public int getAvailableOrderCount() {
        int orderCount = 0;
        for (AbsentedNode node : absentedNodeSet) {
            if (node.isAvaliable()) orderCount = orderCount + 1;
        }
        return orderCount;
    }
    @Override
    public String toString() {
        return absentedNodeSet.toString();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(String seasonId) {
        this.seasonId = seasonId;
    }

}
