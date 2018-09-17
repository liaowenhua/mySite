package org.mySite.service.ssc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mySite.common.bean.SSCCookie;
import org.mySite.common.constant.CodeDic;
import org.mySite.common.constant.PositionEnum;
import org.mySite.common.constant.SSCConstants;
import org.mySite.common.constant.SSCConstants.AutoStrategyConstant;
import org.mySite.common.util.HttpRequestUtil;
import org.mySite.domain.*;
import org.mySite.service.ssc.riskStrategy.IRiskStrategy;
import org.mySite.service.ssc.riskStrategy.impl.AbsentRiskStategyImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class SSCService {

    private static Logger log = LogManager.getLogger(SSCService.class);

    private SSCCookie sscCookie;

    public SSCService() {
        sscCookie = new SSCCookie(SSCConstants.user, SSCConstants.jsessionId, SSCConstants.swtichOpen);
    }

    private static String lastestSeasonId = "0";
    private static SSCOrder currentOrders = new SSCOrder();

    private static String base_url_analyse = "https://web.4jc9.com/game/list?page=1&numMode=0&lotteryId=&playerId=+&seasonId=+&account=&includeChildren=0";
    private static String base_url_ssc_info = "https://web.4jc9.com/lotts/cqssc/info?_=";
    private static String base_url_submit_order = "https://web.4jc9.com/lotts/cqssc/bet?";

    public static SSCOrder getCurrentOrders() {
        return currentOrders;
    }

    public SSCInfo getSSCInfo() {
        SSCInfo sscInfo = new SSCInfo();
        String resultJson = sendHttpRequestForInfo();
        parseSSCInfo(resultJson, sscInfo);
        return sscInfo;
    }

    /**
     *
     * @param sscInfo
     * @return
     */
    public SSCOrder mergeOrder(SSCInfo sscInfo) {
        log.info("start merge order...");
        log.info("before merge:" + currentOrders);
        if (!sscInfo.isInTrading()) {
            fillCurrentOrder(currentOrders, sscInfo);
        }
        log.info("after merge:" + currentOrders);
        return currentOrders;
    }

    /**
     * 提交订单
     * @param order
     */
    public void submitOrders(SSCOrder order, SSCInfo sscInfo, RiskStrategyModel riskStrategyInfo) {
        if (order != null && order.getAvailableOrderCount() > 0) {
            List<SSCOrderNode> orderNodes = new ArrayList<SSCOrderNode>();
            for (PositionEnum positionEnum : PositionEnum.values()) {
                fillOrderNode(orderNodes, positionEnum.position() , order, riskStrategyInfo);
            }
            double orderMoney = 0;
            int count = 0;
            if (orderNodes.size() > 0) {
                for (SSCOrderNode node : orderNodes) {
                    orderMoney = orderMoney + node.getPrice() * riskStrategyInfo.getUnit() * 4;
                    count = count + 4;
                }
                SSCOrderParam sscOrderParam = new SSCOrderParam();
                SSCOrderNode[] nodeArrsy =  new SSCOrderNode[orderNodes.size()];
                orderNodes.toArray(nodeArrsy);
                sscOrderParam.setOrder(nodeArrsy);
                BigDecimal bigDecimal = new BigDecimal(orderMoney).setScale(2, RoundingMode.DOWN);
                sscOrderParam.setAmount(bigDecimal.doubleValue());
                sscOrderParam.setCount(count);
                HashMap<String,String> traceOrdersMap = new HashMap<String,String>();
                traceOrdersMap.put("seasonId",sscInfo.getCurrentOpenSeasonId());
                ArrayList<Map<String, String>> arrayList = new ArrayList<Map<String, String>>();
                arrayList.add(traceOrdersMap);
                sscOrderParam.setTraceOrders(arrayList);
                log.info("准备提交订单，订单信息\n" + JSONObject.toJSON(sscOrderParam).toString());

                //提交订单
                boolean success = submitOrder(sscOrderParam);
                //如果提交订单成功，则更新当前订单信息
                if (success) {
                    log.info("提交订单成功！更新当前订单，更新后为:" + currentOrders.toString());
                }
            }
        }else {
            log.info("订单为空，不提交！");
        }
    }

    private boolean submitOrder(SSCOrderParam sscOrderParam) {
        String url = wrapUrl(sscOrderParam);
        log.info("提交订单url:" + url);
        String result = HttpRequestUtil.post(url, JSONObject.toJSON(sscOrderParam).toString(), sscCookie);
        log.info("提交订单，返回结果：" + result);
        if (StringUtils.isNotEmpty(result)) {
            try{
                JSONObject resultJson = JSONObject.parseObject(result);
                int statusCode = resultJson.getIntValue("status");
                if (statusCode == 200) {
                    return true;
                } else {
                    log.info("submit order failed.result is " + result);
                }
            }catch (Exception e) {
                log.error("submit order occur error,msg is " + e.getMessage() + ".result is " + result);
            }
        }
        return false;
    }

    private String wrapUrl(SSCOrderParam sscOrderParam) {
        StringBuffer url = new StringBuffer(base_url_submit_order);
        url.append("isTrace=").append(sscOrderParam.getIsTrace());
        url.append("&traceWinStop=").append(sscOrderParam.getTraceWinStop());
        url.append("&bounsType=").append(sscOrderParam.getBounsType());
        url.append("&traceOrders[0].seasonId=").append(sscOrderParam.getTraceOrders().get(0).get("seasonId"));
        url.append("&amount=").append(sscOrderParam.getAmount());
        url.append("&count=").append(sscOrderParam.getCount());
        url.append("&force=").append(sscOrderParam.getForce());
        SSCOrderNode[] order = sscOrderParam.getOrder();
        for (int i=0; i<order.length; i++) {
            url.append("&order[").append(i).append("].").append("playId=").append(order[i].getPlayId());
            url.append("&order[").append(i).append("].").append("content=").append(order[i].getContent());
            url.append("&order[").append(i).append("].").append("betCount=").append(order[i].getBetCount());
            url.append("&order[").append(i).append("].").append("price=").append(order[i].getPrice());
            url.append("&order[").append(i).append("].").append("unit=").append(order[i].getUnit());
        }
        return url.toString();

    }

    private void fillOrderNode(List<SSCOrderNode> orderNodes, int position, SSCOrder order, RiskStrategyModel riskStrategyInfo) {
        Set<AbsentedNode> absentedNodeSet = order.getAbsentedNodeSet();
        Iterator<AbsentedNode> it = absentedNodeSet.iterator();
        while (it.hasNext()) {
            AbsentedNode node = it.next();
            if (!node.isAvaliable()) {
                continue;
            }
            if (node.getPosition() == position) {
                SSCOrderNode sscOrderNode = new SSCOrderNode();
                sscOrderNode.setUnit(riskStrategyInfo.getUnit());
                fillContent(position, node.getCode(), sscOrderNode);
                sscOrderNode.setPrice(riskStrategyInfo.getPriceWithAbsent(node.getAbsent()));
                orderNodes.add(sscOrderNode);
            }
        }
    }

    private void fillContent(int position, String code, SSCOrderNode sscOrderNode) {
        String content = CodeDic.dic.get(code);
        String[] codeArray = new String[]{"-","-","-","-","-"};
        if (position >= 0 && position <= 4 && content != null) {
            codeArray[position] = content;
            StringBuffer contentBuf = new StringBuffer();
            for (String s : codeArray) {
                contentBuf.append(s).append(",");
            }
            sscOrderNode.setContent(contentBuf.subSequence(0,contentBuf.length() - 1).toString());
        }
    }


    /**
     * 资金管理相关参数
     * @param sscInfo
     * @return
     */
    public RiskStrategyModel getRiskStrategyInfo(SSCInfo sscInfo, SSCOrder order) {
        IRiskStrategy riskStrategy;
        //riskStrategy = new WinCountRiskStrategyImpl();//按照近期连赢或者连亏的数量动态调整
        //riskStrategy = new WinRateRiskStrategyImpl();//按照整体盈利率动态调整
        //riskStrategy = new RecentWinRateRistStrategyImpl();//按照近期的盈利单比亏损单动态调整
        //riskStrategy = new ManueRiskStrategyImpl();
        //riskStrategy = new FixedLoseMoneyStrategyImpl();
        riskStrategy = new AbsentRiskStategyImpl();
        ResultAnalyseModle analyseResult = this.analyseResult(sscInfo, "", "", AutoStrategyConstant.analyse_count);
        RiskStrategyModel strategyModel = riskStrategy.getRiskRate(analyseResult, order);
        return strategyModel;
    }

    private void fillCurrentOrder(SSCOrder currentOrder, SSCInfo sscInfo) {
        String[] lastestCode = sscInfo.getLastestCode();
        Set<AbsentedNode> absentedNodeSet = currentOrder.getAbsentedNodeSet();
        Set<AbsentedNode> tempSet = new HashSet<AbsentedNode>();
        for (int i=0; i<lastestCode.length; i++) {
            Iterator<AbsentedNode> it = absentedNodeSet.iterator();
            while (it.hasNext()) {
                AbsentedNode node = it.next();
                if (node.getPosition() == i) {
                    if (!CodeDic.dic.get(node.getCode()).contains(lastestCode[i])) {
                        //未中奖，遗漏加1
                        node.setAbsent(node.getAbsent() + 1);
                        tempSet.add(node);
                    }
                 }
            }
            if (CodeDic.dic.containsKey(lastestCode[i])) {
                tempSet.add(new AbsentedNode(lastestCode[i], i));
            }
       }
        currentOrder.setAbsentedNodeSet(tempSet);
    }
    public static  void main(String[] args) {
        SSCService sscService = new SSCService();
//        SSCInfo sscInfo = sscService.getSSCInfo();
//        SSCOrder sscOrder = sscService.mergeOrder(sscInfo);
//        log.info(sscInfo);
//        log.info(sscOrder);
//        sscService.submitOrders(sscOrder, sscInfo);
        //ResultAnalyseModle result = sscService.analyseResult("", "", 50);
        //log.info(result.toString());
        //log.error(sscService.getPrice(99.4f, 0.002, 3));
    }

    private String sendHttpRequestForInfo() {
        String url = base_url_ssc_info + System.currentTimeMillis();
        log.info("请求sscInfo,url===>>" + url);
        String result = HttpRequestUtil.get(url, sscCookie);
        log.info("sscInfo结果===>>" + result);
        return  result;
    }

    private void parseSSCInfo(String json, SSCInfo sscInfo) {
        if (StringUtils.isNotBlank(json)) {
            JSONObject infoJSON = JSONObject.parseObject(json);
            JSONObject contentJson;
            try {
                contentJson = infoJSON.getJSONObject("content");
            }catch (Exception e) {
                log.error("parse json error,msg:", e, e.getMessage());
                return ;
            }

            //当前可用余额
            float amount = contentJson.getFloat("amount");
            sscInfo.setAmount(amount);

            JSONObject lastOpen = contentJson.getJSONObject("lastOpen");
            if (lastOpen != null) {
                //最近一次开奖期数
                String lastestSeesonId = lastOpen.getString("seasonId");
                sscInfo.setLastestSeasonId(lastestSeesonId);
                //最近一次开奖结果
                JSONArray nums = lastOpen.getJSONArray("nums");
                if (nums != null && nums.size() == 5) {
                    String[] numArray = new String[5];
                    nums.toArray(numArray);
                    sscInfo.setLastestCode(numArray);
                }
            }

            //当前投注的期数
            String currentSeasonId = contentJson.getString("seasonId");
            sscInfo.setCurrentOpenSeasonId(currentSeasonId);

            //判断当前是否有未开奖的订单
            String seasonId = "";//未开奖订单的期数
            JSONArray bets = contentJson.getJSONArray("bets");
            if (bets != null && bets.size() > 0) {
                Object latestBet = bets.get(0);
                JSONObject betJson = JSONObject.parseObject(latestBet.toString());
                int status = betJson.getIntValue("status");
                if (status == 0) {
                    sscInfo.setInTrading(true);
                    seasonId = betJson.getString("seasonId");
                }else {
                    sscInfo.setInTrading(false);
                }

                //解析当前订单
                SSCOrder currentOrder = sscInfo.getCurrentOrder();
                for (Object bet : bets) {
                    JSONObject jsonObject = JSONObject.parseObject(bet.toString());
                    int statusCode = jsonObject.getIntValue("status");
                    //status=0：未开奖
                    if (statusCode == 0) {
                        currentOrder.setSeasonId(seasonId);
                        String content = jsonObject.getString("content");
                        if (StringUtils.isNotEmpty(content)) {
                            String[] codeArray = content.split(",");
                            Set<AbsentedNode> absentedNodeSet = currentOrder.getAbsentedNodeSet();
                            for (int i=0; i<codeArray.length; i++) {
                                if (StringUtils.isNumeric(codeArray[i])) {
                                    absentedNodeSet.add(new AbsentedNode(codeArray[i], i));
                                }
                            }
                        }
                    }else {
                        break;
                    }
                }
                //最近开奖结果
                sscInfo.setOpensArray( contentJson.getJSONArray("opens"));
            }
        }
    }

    public ResultAnalyseModle analyseResult(SSCInfo sscInfo, String startTime, String endTime, int pageSize) {
        String resultJsonStr = sendHttpRequestForAnalyseInfo(startTime, endTime, pageSize);
        ResultAnalyseModle result = parseAnalyseResult(sscInfo, resultJsonStr);
        return result;
    }

    private ResultAnalyseModle parseAnalyseResult(SSCInfo sscInfo, String resultJsonStr) {
        ResultAnalyseModle result = new ResultAnalyseModle();
        result.setCurrentAmount(sscInfo.getAmount());
        if (ResultAnalyseModle.getInitAmount() == 0) {
            log.info("初始化资金为:" + sscInfo.getAmount());
            ResultAnalyseModle.setInitAmount(sscInfo.getAmount());
        }
        if (StringUtils.isNotEmpty(resultJsonStr)) {
            JSONObject jsonObj = null;
            try {
                jsonObj = JSONObject.parseObject(resultJsonStr);
                int status = jsonObj.getIntValue("status");
                if (status == 200) {
                    JSONObject contentJsonObj = jsonObj.getJSONObject("content");
                    if (contentJsonObj != null && contentJsonObj.containsKey("rows")) {
                        int totalCount = contentJsonObj.getJSONArray("rows").size();
                        //本次统计的总共盈利数量
                        int totalWinCount = 0;
                        //本次统计的总共亏损数量
                        int totalLoseCount = 0;
                        Map<String, ResultAnalyseModle.Node> detailResult = result.getDetailResult();
                        //记录最近连赢次数
                        int continueWinCount = 0;
                        //记录最近连亏次数
                        int continueLoseCount = 0;
                        //是否是连赢
                        boolean isContinueWin = true;
                        //是否是连输
                        boolean isContinueLose = true;
                        //当前未开奖单的数量
                        int inTradingCount = 0;
                        for (Object rows : contentJsonObj.getJSONArray("rows")) {
                            JSONObject rowNodeJsonObj = JSONObject.parseObject(rows.toString());
                            //winStatus: 0未开奖 1盈利 2亏损
                            int winStatus = rowNodeJsonObj.getIntValue("status");
                            //未开奖状态
                            if (winStatus == 0) {
                                inTradingCount += 1;
                                continue;
                            }
                            //记录盈利单的数量
                            boolean isWin = false;
                            if (winStatus == 1) {
                                isContinueLose = false;
                                //记录连赢次数，如果中断了一次则不记录了
                                if (isContinueWin) {
                                    continueWinCount += 1;
                                }
                                isWin = true;
                                totalWinCount += 1;
                            }else if (winStatus == 2){
                                isContinueWin = false;
                                if (isContinueLose) {
                                    continueLoseCount += 1;
                                }
                                totalLoseCount += 1;
                            }
                            //详细信息
                            wrapDetailInfo(detailResult, rowNodeJsonObj.getString("content"), isWin);
                        }
                        result.setInTradingCount(inTradingCount);
                        result.setContinueWinCount(continueWinCount);
                        result.setContinueLoseCount(continueLoseCount);
                        result.setTotalCount(totalCount);
                        result.setWinCount(totalWinCount);
                        result.setLoseCount(totalLoseCount);
                    }
                }else {
                    log.info("请求数据分析接口失败！");
                }
            }catch (Exception e) {
                log.info("解析数据分析接口失败.详细信息：" + e);

            }
        }
        return result;
    }

    private void wrapDetailInfo(Map<String,ResultAnalyseModle.Node> detailResult, String content, boolean isWin) {
        if (StringUtils.isNotEmpty(content)) {
            for (String s : content.split(",")) {
                ResultAnalyseModle.Node node = detailResult.get(s);
                if (node != null) {
                    node.setTotalCount(node.getTotalCount() + 1);
                    if (isWin) {
                        node.setWinCount(node.getWinCount() + 1);
                    }
                }
            }
        }
    }

    private String sendHttpRequestForAnalyseInfo(String startTime, String endTime, int pageSize) {
        StringBuffer url = new StringBuffer(base_url_analyse);
        if (StringUtils.isNotEmpty(startTime)) {
            url.append("&startTime=").append(startTime);
        }
        if (StringUtils.isNotEmpty(endTime)) {
            url.append("&endTime=").append(endTime);
        }
        if (pageSize != 0) {
            url.append("&rows=").append(pageSize);
        }

       String resultJsonStr =  HttpRequestUtil.get(url.toString(), sscCookie);
        return resultJsonStr;
    }

    public String getAnalyseInfo(ResultAnalyseModle analyseModle, SSCInfo sscInfo) {
        StringBuffer contentBuf = new StringBuffer();
        contentBuf
                .append("当前账户总金额:").append(sscInfo.getAmount()).append("<br>")
                .append("原始资金为").append(SSCConstants.ssc_monitor_init_amount).append("<br>")
                .append("本次初始资金为：").append(ResultAnalyseModle.getInitAmount()).append("<br>")
                .append("整体盈亏额：").append(sscInfo.getAmount() - SSCConstants.ssc_monitor_init_amount).append("<br>")
                .append("本次盈利率为").append(analyseModle.getCurrentWinRate() *100).append("%").append("<br>")
                .append("整体盈利率为:").append(analyseModle.getOriginWinRate() * 100).append("%").append("<br>");

        //contentBuf.append("当前模式为：").append(WinRateRiskStrategyImpl.getMode_current()).append("<br>");

        //contentBuf.append("当前默认风险比例为：").append(AutoStrategy.risk_normal).append("<br>");
        contentBuf.append(analyseModle.getTotalCount()).append("期中:<br>")
                .append("盈利率为：").append(analyseModle.getWinCountRate() * 100).append("%").append("<br>")
                .append("总共盈利").append(analyseModle.getWinCount()).append("单<br>")
                .append("亏损") .append(analyseModle.getLoseCount()).append("单<br>")
                .append("连赢").append(analyseModle.getContinueWinCount()).append("单<br>")
                .append("连亏").append(analyseModle.getContinueLoseCount()).append("单<br>")
                .append("未开奖").append(analyseModle.getInTradingCount()).append("单<br>");
               // .append("当前使用的风险比例为：").append(this.getRiskStrategyInfo(sscInfo, sscInfo.getCurrentOrder().getAvailableOrderCount()).getRiskRate()).append("<br>");
        return contentBuf.toString();
    }

    public String genAnalyseReport(SSCInfo sscInfo) {
        ResultAnalyseModle analyseModle = analyseResult(sscInfo, "", "", AutoStrategyConstant.analyse_count);
        return getAnalyseInfo(analyseModle, sscInfo);
    }

    public static String getLastestSeasonId() {
        return lastestSeasonId;
    }

    public static void setLastestSeasonId(String lastestSeasonId) {
        SSCService.lastestSeasonId = lastestSeasonId;
    }

    public SSCOrder initOrder(SSCInfo sscInfo) {
        JSONArray opensArray = sscInfo.getOpensArray();
        if (opensArray != null && opensArray.size() > 0) {
            for(int j=opensArray.size() - 1; j>=0; j--) {
                JSONObject openJson = JSONObject.parseObject(JSON.toJSONString(opensArray.get(j)));
                JSONArray nums = openJson.getJSONArray("nums");
                log.info("nums:" + nums.toString());
                for (int i=0; i<nums.size(); i++) {
                    currentOrders.addOrderNode(i, nums.get(i).toString());
                }
            }
        }
        log.info("initOrder:" + currentOrders);
        return currentOrders;
    }
}
