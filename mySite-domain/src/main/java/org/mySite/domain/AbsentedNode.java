package org.mySite.domain;

public class AbsentedNode {
    private String code;
    private int absent = 0;
    private int position;
    private boolean avaliable = true;

    public boolean isAvaliable() {
        return avaliable;
    }

    public void setAvaliable(boolean avaliable) {
        this.avaliable = avaliable;
    }

    public AbsentedNode() {}

    public AbsentedNode(String code,  int position) {
        this.code = code;
        this.position = position;
        this.avaliable = false;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getAbsent() {
        return absent;
    }

    public void setAbsent(int absent) {
        this.absent = absent;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof AbsentedNode) {
            AbsentedNode node = (AbsentedNode)obj;
            if (node.code.equals(this.code) && node.position == this.position) return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return code.hashCode() + position;
    }

    @Override
    public String toString() {
        return "position:" + position + "#code:" + code + "#absent:" + absent;
    }
}
