package org.mySite.domain;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mySite.common.constant.CodeDic;
import org.mySite.common.constant.PositionEnum;
import org.mySite.common.constant.SSCConstants;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SSCOrder {
    private static Logger log = LogManager.getLogger(SSCOrder.class);
    private String orderId;
    private String seasonId;//当前订单的期数
    private Set<AbsentedNode> absentedNodeSet = new HashSet<AbsentedNode>();
    private static Recorder recorder = new Recorder();

    public Recorder getRecorder() {
        return recorder;
    }

    public void setRecorder(Recorder recorder) {
        SSCOrder.recorder = recorder;
    }

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

    public void addOrderNode(int position, String code, String seasonId) {
        AbsentedNode orderNode = new AbsentedNode(code, position);
        Iterator<AbsentedNode> it = absentedNodeSet.iterator();
        boolean positionHadNode = false;
        Set<AbsentedNode> temp = new HashSet<AbsentedNode>();
        while (it.hasNext()) {
            AbsentedNode node = it.next();
            if (node.getPosition() == position) {
                positionHadNode = true;
                //中奖
                if (CodeDic.searchDic(node.getCode()).contains(code)) {
                    recorder.add(node.getAbsent());
                    it.remove();
                    if (node.getAbsent() > CodeDic.max) {
                        log.info("超过最大遗漏中奖，遗漏次数:" + node.getAbsent() +" ,position:" + PositionEnum.dec(position) + " ,code:" + code + " ,当前期数为:" + seasonId);
                    }
                    if (StringUtils.isNotEmpty(CodeDic.searchDic(code))) {
                        temp.add(orderNode);
                    }
                }else {
                    node.setAbsent(node.getAbsent() + 1);
                }
            }
        }
        if (!positionHadNode && StringUtils.isNotEmpty(CodeDic.searchDic(code))) {
            absentedNodeSet.add(orderNode);
        }

        absentedNodeSet.addAll(temp);

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
