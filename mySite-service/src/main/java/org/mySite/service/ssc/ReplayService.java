package org.mySite.service.ssc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mySite.common.util.HttpRequestUtil;
import org.mySite.domain.SSCOrder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReplayService {

    private static Logger log = LogManager.getLogger(ReplayService.class);

    //http://kaijiang.500.com/static/public/ssc/xml/qihaoxml/20180129.xml
    private static String historyResultUrl = "http://kaijiang.500.com/static/public/ssc/xml/qihaoxml/$$.xml";
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");


    public List<String> getResultOfDates(int from, int to) throws Exception  {
        List<String> result = new ArrayList<String>();
        Date fromDate = sdf.parse(from + "");
        Date toDate = sdf.parse(to + "");
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(fromDate);
        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(toDate);
        while (startCalendar.after(toCalendar) || startCalendar.equals(toCalendar)) {
            String date = sdf.format(startCalendar.getTime());
            List<String> dataList = getResultOfDate(date);
            result.addAll(dataList);
            startCalendar.add(Calendar.DAY_OF_MONTH, -1);
        }
        return result;
    }

    public List<String> getResultOfDate(String date){
        log.info("request date:" + date);
        String url = historyResultUrl.replace("$$", date) + "?t=" + new Date().getTime();
        String resultStr = HttpRequestUtil.get(url, null);
        InputStream is = new ByteArrayInputStream(resultStr.getBytes());

        List<String> result = new ArrayList<String>();
        DocumentBuilderFactory bdf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {
            db = bdf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = null;
        try {
            document = db.parse(is);
        } catch (Exception e) {
            return result;
        }
        Element root = document.getDocumentElement();
        NodeList childNodes = root.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Element element = (Element)childNodes.item(i);
            String opencode = element.getAttribute("opencode");
            result.add(opencode);
        }
        return result;
    }

    public void replay(SSCOrder sscOrder, List<String> data) {
        if (data != null && data.size() > 0) {
            for (int j=data.size()-1; j>=0; j--) {
                String codeStr = data.get(j);
                String[] codeArray = codeStr.split(",");
                for (int i=0; i<codeArray.length; i++) {
                    sscOrder.addOrderNode(i, codeArray[i]);
                }
            }
        }
    }

}
