package org.mySite.common.bean;

public class SSCCookie implements Cookie{
    private String JSESSIONID;
    private String guide1;
    private String openSwitching;
    private String __cfduid;

    public String get__cfduid() {
        return __cfduid;
    }

    public void set__cfduid(String __cfduid) {
        this.__cfduid = __cfduid;
    }

    public SSCCookie(){}
    public SSCCookie(String user, String jsessionId, String swtichOpen, String cfdUid){
        this.JSESSIONID = jsessionId;
        this.guide1 = user;
        this.openSwitching = swtichOpen;
        this.__cfduid = cfdUid;
    }

    public String getJSESSIONID() {
        return JSESSIONID;
    }

    public void setJSESSIONID(String JSESSIONID) {
        this.JSESSIONID = JSESSIONID;
    }

    public String getGuide1() {
        return guide1;
    }

    public void setGuide1(String guide1) {
        this.guide1 = guide1;
    }

    public String getOpenSwitching() {
        return openSwitching;
    }

    public void setOpenSwitching(String openSwitching) {
        this.openSwitching = openSwitching;
    }

    @Override
    public String toString() {
        return "JSESSIONID=" + getJSESSIONID() + ",guide1=" + getGuide1() + ",openSwitching=" + getOpenSwitching();
    }
}
