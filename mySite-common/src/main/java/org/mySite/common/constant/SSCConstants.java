package org.mySite.common.constant;

public interface SSCConstants {
    //账户信息
    String user = "liaowenhua22";
    String jsessionId = "7F3A00A70D31F97EC793A1E9EB1754789A7C197E45B6";
    String swtichOpen = "n579";
    String __cfduid = "dd65c93b7bcda0797acd3997aa217ad501537234507";

    //回测数据相关信息
    String HISTORY_BASE_PATH = "E:\\ssc/";

    //监控邮件相关信息
    String mail_host = "smtp.163.com";
    String mail_transport_protocol = "smtp";
    String mail_smtp_auth = "true";
    String mail_send_to = "502128553@qq.com";
    String mail_send_from = "13661323573@163.com";

    //用于监控盈亏的初始资金。如果这里设置为0，那么会在监控任务中取当前余额为初始资金
    double ssc_monitor_init_amount = 109;
    //订单任务时间间隔，单位毫秒
    long interval_mill_second = 30000;//60*1000*1 1分钟
    //账户监控邮件间隔，单位毫秒
    long monitor_interval_mill_second = 60*1000*10;

    /**
     * 资金管理自动调节相关参数
     */
    interface AutoStrategyConstant {
        /**分析的期数*/
        int analyse_count = 25;
        /**当超过最大连赢数后，需要降低投入*/
        int most_continue_win_num = 4;
        /**当超过最大连亏数后，需要增加投入*/
        int most_continue_lose_num = 6;
        double win_rate_threshold_up = 0.55;
        double win_rate_threshold_dowm = 0.3;
        double risk_dowm = 0.05;
        double risk_normal = 0.05;
        double risk_up = 0.05;
    }

}
