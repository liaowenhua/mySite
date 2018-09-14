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

    public static String searchDic(String request) {
        if (StringUtils.isNotEmpty(request)) {
            return dic.get(request);
        }
        return "";
    }
}
