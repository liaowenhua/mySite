package org.mySite.common.util;

import org.apache.commons.lang3.StringUtils;

public class SeasonIdUtils {

    public static Integer getSeasonIdSuffix(String seasonId) {
        if (StringUtils.isNotEmpty(seasonId)) {
            String[] split = seasonId.split("-");
            return Integer.parseInt(split[1]);
        }
        return -1;
    }

    /**
     * 判断是不是每天的第一期
     * @param currentOpenSeasonId
     * @return
     */
    public static boolean isFirstSeason(String currentOpenSeasonId) {
        if (StringUtils.isNotEmpty(currentOpenSeasonId)) {
            Integer seasonIdSuffix = getSeasonIdSuffix(currentOpenSeasonId);
            if (seasonIdSuffix != null && seasonIdSuffix.equals(1)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBiggerThanOne(String big, String small) {
        if (StringUtils.isEmpty(big) || StringUtils.isEmpty(small)) return false;

        Integer bigInt = getSeasonIdSuffix(big);
        Integer smallInt = getSeasonIdSuffix(small);
        if (bigInt == 1 && smallInt == 120) return true;

        if (bigInt - smallInt == 1) return true;

        return false;
    }
}
