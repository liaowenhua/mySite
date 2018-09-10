package org.mySite.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * 记录遗漏
 */
public class Drop {
    //key:位置 map：key-code value-遗漏次数
    private Map<Integer, Map<String, Integer>> drop = new HashMap<Integer, Map<String, Integer>>();

    //最后检查的期数
    private String lastestCheckSeason;

    public String getLastestCheckSeason() {
        return lastestCheckSeason;
    }

    public void setLastestCheckSeason(String lastestCheckSeason) {
        this.lastestCheckSeason = lastestCheckSeason;
    }

    public Map<Integer, Map<String, Integer>> getDrop() {
        return drop;
    }

    public void setDrop(Map<Integer, Map<String, Integer>> drop) {
        this.drop = drop;
    }

    public Map<String, Integer> getWDrop() {
        return drop.get(0);
    }

    public Map<String, Integer> getQDrop() {
        return drop.get(1);
    }

    public Map<String, Integer> getBDrop() {
        return drop.get(2);
    }

    public Map<String, Integer> getSDrop() {
        return drop.get(3);
    }

    public Map<String, Integer> getGDrop() {
        return drop.get(4);
    }

    private Map<String, Integer> getDrop(Integer order) {
        Map<String, Integer> map = drop.get(order);
        if (map == null) {
            return new HashMap<String, Integer>();
        }
        return map;
    }
}
