package org.mySite.common.constant;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class CodeDic {
    private static Map<String, String> dic = new HashMap<String, String>();
    static {
        dic.put("0",SSCConstants.code_map_0);
        dic.put("2",SSCConstants.code_map_2);
        dic.put("5",SSCConstants.code_map_5);
        dic.put("9",SSCConstants.code_map_9);

        dic.put(SSCConstants.code_map_0,"0");
        dic.put(SSCConstants.code_map_2,"2");
        dic.put(SSCConstants.code_map_5,"5");
        dic.put(SSCConstants.code_map_9,"9");
    }

    private static Map<Integer, Double> profileMap = new HashMap<Integer, Double>();
    static {
        profileMap.put(11, 11.52);
        profileMap.put(12, 15.04);
        profileMap.put(13, 33.6);
        profileMap.put(14, 62.72);
        profileMap.put(15, 20.8);
        profileMap.put(16, 27.52);
        profileMap.put(17, 49.92);
        profileMap.put(18, 85.12);
        profileMap.put(19, -1496.00);
    }

    public static double getProfile(int absent) {
        if (profileMap.containsKey(absent)) {
            return profileMap.get(absent);
        }
        return 0;
    }

    public static String searchDic(String request) {
        if (StringUtils.isNotEmpty(request)) {
            return dic.get(request);
        }
        return "";
    }
}
