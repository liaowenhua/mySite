package org.mySite.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mySite.common.constant.CodeDic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recorder {
    private static Logger log = LogManager.getLogger(Recorder.class);
    private Map<Integer, Integer> absentedMap = new HashMap<Integer, Integer>();
    private static double initMoney = 650.0;
    private static List<Double> moneyChangeList = new ArrayList<Double>();

    static {
        moneyChangeList.add(initMoney);
    }

    public void add(Integer i) {
        if (absentedMap.containsKey(i)) {
            absentedMap.put(i, absentedMap.get(i) + 1);
        }else {
            absentedMap.put(i, 1);
        }

        Double lastProfile = moneyChangeList.get(moneyChangeList.size() - 1);
        if (i >= CodeDic.min && i <= CodeDic.max) {
            moneyChangeList.add(lastProfile + CodeDic.getProfileMap().get(i));
        }else if (i > CodeDic.max) {
            moneyChangeList.add(lastProfile + CodeDic.getProfileMap().get(CodeDic.max + 1));
        }
    }

    public double getProfile() {
        double totalProfile = 0.0;
        for (Integer i : absentedMap.keySet()) {
            int absent = absentedMap.get(i);
            double profile = CodeDic.getProfile(i);

            totalProfile += profile * absent;
            if (profile < 0) {
                log.info("lost!current profile is " + totalProfile);
            }
        }
        return totalProfile;
    }

    public List<Double> getMoneyChangeList() {
        return moneyChangeList;
    }

    public void setMoneyChangeList(List<Double> moneyChangeList) {
        this.moneyChangeList = moneyChangeList;
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
