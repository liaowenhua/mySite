package org.mySite.common.constant;

public interface SSCConstants {
    //账户信息
    String user = "liaowenhua22";
    String jsessionId = "4D53A10AF27EDDA89C3BD6125FBE65AF3E70F745858D";
    String swtichOpen = "n621";

    //监控邮件相关信息
    String mail_host = "smtp.163.com";
    String mail_transport_protocol = "smtp";
    String mail_smtp_auth = "true";
    String mail_send_to = "502128553@qq.com";
    String mail_send_from = "13661323573@163.com";

    //资金管理参数
    /**模式：元(2)、角、分、厘(0.002).*/
    double min_unit = 0.02;

    //用于监控盈亏的初始资金。如果这里设置为0，那么会在监控任务中取当前余额为初始资金
    float ssc_monitor_init_amount = 746;
    //订单任务时间间隔，单位毫秒
    long interval_mill_second = 30000;//60*1000*1 1分钟
    //账户监控邮件间隔，单位毫秒
    long monitor_interval_mill_second = 60*1000*10;

    //对应规则
    String code_map_0 = "0369";
    String code_map_2 = "0578";
    String code_map_5 = "3467";
    String code_map_9 = "0378";

    /**
     * 资金管理自动调节相关参数
     */
    interface AutoStrategyConstant {
        /**分析的期数*/
        int analyse_count = 30;
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

    interface WinRateStrategyConstant {
        //防守模式，使用小的risk_rate
        int mode_defend = 0;
        //进攻模式，使用较大的risk_rate
        int mode_fighting = 1;
        //初始的模式，默认为防守模式
        int mode_init = mode_defend;
        //当盈利率小于等于该值时，模式调整为 mode_fighting
        double win_rate_threshold_dowm = -0.03;
        //当盈利率大于等于该值时，模式调整为 mode_defend
        double win_rate_threshold_up = 0.2;
        //防守模式下的资金风险比例
        double risk_defend = 0.005;
        //进攻模式下的资金风险比例，为防守模式下的10倍
        double risk_fighting = 10*risk_defend;
        //用于计算盈利率的初始资金，每次切换模式都会修改
        double amount_init = 0;
    }
}
