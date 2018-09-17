package org.mySite.common.constant;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class CodeDic {
    public static Map<String, String> dic = new HashMap<String, String>();
//    public static int min = 13;
//    private static int n2 = 14;
//    private static int n3 = 15;
//    private static int n4 = 16;
//    private static int n5 = 17;
//    private static int n6 = 18;
//    private static int n7 = 19;
//    public static int max = 20;

    public static int min = 6;
    private static int n2 = 7;
    private static int n3 = 8;
    private static int n4 = 9;
    private static int n5 = 10;
    private static int n6 = 11;
    private static int n7 = 12;
    public static int max = 13;
    public static double maxLost = -3048.00;

    //对应规则
    public static final String code_map_0 = "0369";
    public static final String code_map_2 = "0578";
    public static final String code_map_5 = "3467";
    public static final String code_map_9 = "0378";
    static {
        dic.put("0",code_map_0);
        dic.put("2",code_map_2);
        dic.put("5",code_map_5);
        dic.put("9",code_map_9);

        dic.put(code_map_0,"0");
        dic.put(code_map_2,"2");
        dic.put(code_map_5,"5");
        dic.put(code_map_9,"9");
    }

    //key 遗漏期数 value 累计收益
    private static Map<Integer, Double> profileMap = new HashMap<Integer, Double>();
    static {
        profileMap.put(min, 23.04);
        profileMap.put(n2, 30.08);
        profileMap.put(n3, 67.2);
        profileMap.put(n4, 171.52);
        profileMap.put(n5, 21.12);
        profileMap.put(n6, 38.08);
        profileMap.put(n7, 55.36);
        profileMap.put(max, 94.72);
        profileMap.put(max + 1, maxLost);
    }

    //key 遗漏期数  value 倍数
    public static Map<Integer, Integer> priceMap = new HashMap<Integer, Integer>();
    static {
        priceMap.put(min, 2);
        priceMap.put(n2, 4);
        priceMap.put(n3, 10);
        priceMap.put(n4, 26);
        priceMap.put(n5, 31);
        priceMap.put(n6, 54);
        priceMap.put(n7, 93);
        priceMap.put(max, 161);
    }

    public static double getProfile(int absent) {
        if (absent < min) return 0;
        if (absent > max) return maxLost;
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
