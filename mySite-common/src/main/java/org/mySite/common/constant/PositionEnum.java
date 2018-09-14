package org.mySite.common.constant;

public enum PositionEnum {
    W(0,"万"), Q(1,"千"), B(2,"百"), S(3,"十"), G(4,"个");
    private int position;
    private String dec;
    PositionEnum(int position, String dec) {
        this.position = position;
        this.dec = dec;
    }

    public int position() {
        return position;
    }

    public static String dec(int position) {
        for (PositionEnum positionEnum : values()) {
            if (positionEnum.position() == position) {
                return positionEnum.dec;
            }
        }
        return "";
    }
}
