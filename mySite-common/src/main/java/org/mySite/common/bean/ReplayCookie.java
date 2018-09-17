package org.mySite.common.bean;

/**
 *
 *
 *
 *
 * ck_RegFromUrl=http%3A//kaijiang.500.com/ssc.shtml;
 * sdc_session=1537060076543;
 * seo_key=baidu%7C%7Chttps://www.baidu.com/link?url=yhotVrjpgrA9yxaNAqiR9JT67ISypqHaf5wMq_g2JnNPt2bViFmwxFbkZpA03k9w&wd=&eqid=e1c16e1100025f43000000025b9dacf1;
 * Hm_lvt_4f816d475bb0b9ed640ae412d6b42cab=1537022811,1537060080;
 * bdshare_firstime=1537060080092;
 * __utma=63332592.1556691595.1537022813.1537022813.1537060081.2;
 * __utmc=63332592;
 * __utmz=63332592.1537060081.2.2.utmcsr=baidu|utmccn=(organic)|utmcmd=organic;
 * __utmt=1;
 * ck_RegUrl=kaijiang.500.com;
 * WT_FPC=id=undefined:lv=1537060093498:ss=1537060076541;
 * sdc_userflag=1537060076543::1537060093501::2;
 * Hm_lpvt_4f816d475bb0b9ed640ae412d6b42cab=1537060095;
 * __utmb=63332592.2.10.1537060081;
 * motion_id=1537060136888_0.7994994867095533;
 * CLICKSTRN_ID=61.48.36.89-1537022842.774494::1B0EB7CEBEE11A4F8E903B0B3571FE0A
 */
public class ReplayCookie implements Cookie{
    private String ck_RegFromUrl = "http%3A//kaijiang.500.com/ssc.shtml";
    private String sdc_session;
    private String seo_key = "baidu%7C%7Chttps://www.baidu.com/link?url=yhotVrjpgrA9yxaNAqiR9JT67ISypqHaf5wMq_g2JnNPt2bViFmwxFbkZpA03k9w&wd=&eqid=e1c16e1100025f43000000025b9dacf1";
    private String Hm_lvt_4f816d475bb0b9ed640ae412d6b42cab;
    private String bdshare_firstime;
    private String __utma;
    private String __utmc;
    private String __utmz;
    private String __utmt;
    private String ck_RegUrl = "kaijiang.500.com";
    private String WT_FPC;
    private String sdc_userflag;
    private String Hm_lpvt_4f816d475bb0b9ed640ae412d6b42cab;
    private String __utmb;
    private String motion_id;
    private String CLICKSTRN_ID;

    public String getCk_RegFromUrl() {
        return ck_RegFromUrl;
    }

    public void setCk_RegFromUrl(String ck_RegFromUrl) {
        this.ck_RegFromUrl = ck_RegFromUrl;
    }

    public String getSdc_session() {
        return sdc_session;
    }

    public void setSdc_session(String sdc_session) {
        this.sdc_session = sdc_session;
    }

    public String getSeo_key() {
        return seo_key;
    }

    public void setSeo_key(String seo_key) {
        this.seo_key = seo_key;
    }

    public String getHm_lvt_4f816d475bb0b9ed640ae412d6b42cab() {
        return Hm_lvt_4f816d475bb0b9ed640ae412d6b42cab;
    }

    public void setHm_lvt_4f816d475bb0b9ed640ae412d6b42cab(String hm_lvt_4f816d475bb0b9ed640ae412d6b42cab) {
        Hm_lvt_4f816d475bb0b9ed640ae412d6b42cab = hm_lvt_4f816d475bb0b9ed640ae412d6b42cab;
    }

    public String getBdshare_firstime() {
        return bdshare_firstime;
    }

    public void setBdshare_firstime(String bdshare_firstime) {
        this.bdshare_firstime = bdshare_firstime;
    }

    public String get__utma() {
        return __utma;
    }

    public void set__utma(String __utma) {
        this.__utma = __utma;
    }

    public String get__utmc() {
        return __utmc;
    }

    public void set__utmc(String __utmc) {
        this.__utmc = __utmc;
    }

    public String get__utmz() {
        return __utmz;
    }

    public void set__utmz(String __utmz) {
        this.__utmz = __utmz;
    }

    public String get__utmt() {
        return __utmt;
    }

    public void set__utmt(String __utmt) {
        this.__utmt = __utmt;
    }

    public String getCk_RegUrl() {
        return ck_RegUrl;
    }

    public void setCk_RegUrl(String ck_RegUrl) {
        this.ck_RegUrl = ck_RegUrl;
    }

    public String getWT_FPC() {
        return WT_FPC;
    }

    public void setWT_FPC(String WT_FPC) {
        this.WT_FPC = WT_FPC;
    }

    public String getSdc_userflag() {
        return sdc_userflag;
    }

    public void setSdc_userflag(String sdc_userflag) {
        this.sdc_userflag = sdc_userflag;
    }

    public String getHm_lpvt_4f816d475bb0b9ed640ae412d6b42cab() {
        return Hm_lpvt_4f816d475bb0b9ed640ae412d6b42cab;
    }

    public void setHm_lpvt_4f816d475bb0b9ed640ae412d6b42cab(String hm_lpvt_4f816d475bb0b9ed640ae412d6b42cab) {
        Hm_lpvt_4f816d475bb0b9ed640ae412d6b42cab = hm_lpvt_4f816d475bb0b9ed640ae412d6b42cab;
    }

    public String get__utmb() {
        return __utmb;
    }

    public void set__utmb(String __utmb) {
        this.__utmb = __utmb;
    }

    public String getMotion_id() {
        return motion_id;
    }

    public void setMotion_id(String motion_id) {
        this.motion_id = motion_id;
    }

    public String getCLICKSTRN_ID() {
        return CLICKSTRN_ID;
    }

    public void setCLICKSTRN_ID(String CLICKSTRN_ID) {
        this.CLICKSTRN_ID = CLICKSTRN_ID;
    }
}
