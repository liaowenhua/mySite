package org.mySite.domain;

import java.util.HashSet;
import java.util.Set;

public class SSCOrder {
    private String orderId;
    private String seasonId;//当前订单的期数
    private Set<String> w = new HashSet<>();//万位
    private Set<String> q = new HashSet<>();//千位
    private Set<String> b = new HashSet<>();//百位
    private Set<String> s = new HashSet<>();//十位
    private Set<String> g = new HashSet<>();//个位

    public boolean isEmpty() {
        return w.isEmpty() && q.isEmpty() && b.isEmpty() && s.isEmpty() && g.isEmpty();
    }

    public int getOrderCount() {
        return w.size() + q.size() + b.size() + s.size() + g.size();
    }
    @Override
    public String toString() {
        return "seasonId:" + seasonId + "," + w.toString() + "-" + q.toString() + "-"  + b.toString() + "-"  + s.toString() + "-"  + g.toString();
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

    public Set<String> getW() {
        return w;
    }

    public void setW(Set<String> w) {
        this.w = w;
    }

    public Set<String> getQ() {
        return q;
    }

    public void setQ(Set<String> q) {
        this.q = q;
    }

    public Set<String> getB() {
        return b;
    }

    public void setB(Set<String> b) {
        this.b = b;
    }

    public Set<String> getS() {
        return s;
    }

    public void setS(Set<String> s) {
        this.s = s;
    }

    public Set<String> getG() {
        return g;
    }

    public void setG(Set<String> g) {
        this.g = g;
    }
}
