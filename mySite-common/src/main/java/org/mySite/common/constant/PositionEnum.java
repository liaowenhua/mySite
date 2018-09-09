package org.mySite.common.constant;

public enum PositionEnum {
    W(0), Q(1), B(2), S(3), G(4);
    private int position;
    PositionEnum(int position) {
        this.position = position;
    }

    public int position() {
        return position;
    }
}
