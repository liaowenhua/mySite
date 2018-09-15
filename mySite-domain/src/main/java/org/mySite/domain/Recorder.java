package org.mySite.domain;

import org.mySite.common.constant.CodeDic;

import java.util.HashMap;
import java.util.Map;

public class Recorder {
    private Map<Integer, Integer> absentedMap = new HashMap<Integer, Integer>();

    public void add(Integer i) {
        if (absentedMap.containsKey(i)) {
            absentedMap.put(i, absentedMap.get(i) + 1);
        }else {
            absentedMap.put(i, 1);
        }
    }

    public double getProfile() {
        double totalProfile = 0.0;
        for (Integer i : absentedMap.keySet()) {
            int absent = absentedMap.get(i);
            totalProfile += CodeDic.getProfile(i) * absent;
        }
        return totalProfile;
    }

    @Override
    public String toString() {
        int totalCount = 0;
        for (Integer i : absentedMap.keySet()) {
            totalCount += absentedMap.get(i);
        }
        return absentedMap.toString() + ", totalCount=" + totalCount;
    }
}
