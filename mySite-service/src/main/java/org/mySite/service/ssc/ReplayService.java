package org.mySite.service.ssc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mySite.common.bean.Cookie;
import org.mySite.common.bean.RequestHeader;
import org.mySite.common.constant.SSCConstants;
import org.mySite.common.util.HttpRequestUtil;
import org.mySite.domain.SSCOrder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReplayService {

    private static Logger log = LogManager.getLogger(ReplayService.class);

    //http://kaijiang.500.com/static/public/ssc/xml/qihaoxml/20170126.xml
    private static String historyResultUrl = "http://kaijiang.500.com/static/public/ssc/xml/qihaoxml/$$.xml";
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");


    public List<String> getResultOfDates(int from, int to, Cookie cookie, RequestHeader header) throws Exception  {
        List<String> result = new ArrayList<String>();
        Date fromDate = sdf.parse(from + "");
        Date toDate = sdf.parse(to + "");
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(fromDate);
        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(toDate);
        while (startCalendar.before(toCalendar) || startCalendar.equals(toCalendar)) {
            String date = sdf.format(startCalendar.getTime());
            List<String> dataList = getResultOfDate(date, cookie, header);
            result.addAll(dataList);
            startCalendar.add(Calendar.DAY_OF_MONTH, 1);
            Thread.sleep(200);
        }
        return result;
    }

    public List<String> getResultOfDate(String date, Cookie cookie, RequestHeader header){
        log.info("request date:" + date);
        List<String> result = new ArrayList<String>();
        InputStream is = getResultIsFromUrl(date, cookie, header);
        Document document = parseDocument(is);
        if (document == null) return result;
        Element root = document.getDocumentElement();
        NodeList childNodes = root.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Element element = (Element)childNodes.item(i);
            String opencode = element.getAttribute("opencode");
            result.add(opencode);
        }
        return result;
    }

    private Document parseDocument(InputStream is) {
        if (is == null) {
            return null;
        }
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
            log.info("parseDocument parse error!");
            return null;
        }
        return document;
    }

    private  ByteArrayOutputStream cloneInputStream(InputStream input) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            return baos;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void storeHistoryData (int from, int to, Cookie cookie, RequestHeader header) throws Exception{
        Date fromDate = sdf.parse(from + "");
        Date toDate = sdf.parse(to + "");
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(fromDate);
        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(toDate);
        while (startCalendar.before(toCalendar) || startCalendar.equals(toCalendar)) {
            String fileName = sdf.format(startCalendar.getTime());
            log.info("write " + fileName);
            File file = new File(SSCConstants.HISTORY_BASE_PATH + fileName);
            if (file != null && file.exists()) {
                startCalendar.add(Calendar.DAY_OF_MONTH, 1);
                continue;
            }
            InputStream is = getResultIsFromUrl(fileName, cookie, header);
            if (is == null) {
                startCalendar.add(Calendar.DAY_OF_MONTH, 1);
                continue;
            }
            writeToFile(is, fileName);
            startCalendar.add(Calendar.DAY_OF_MONTH, 1);
            Thread.sleep(200);

        }
    }

    public static void writeToFile(InputStream is,String fileName) throws IOException{
        BufferedInputStream in=null;
        BufferedOutputStream out=null;
        in=new BufferedInputStream(is);
        out=new BufferedOutputStream(new FileOutputStream(SSCConstants.HISTORY_BASE_PATH + fileName));
        int len=-1;
        byte[] b=new byte[1024];
        while((len=in.read(b))!=-1) {
            out.write(b,0,len);
        }
        in.close();
        out.close();
    }

    private InputStream getResultIsFromUrl(String date, Cookie cookie, RequestHeader header) {
        String url = historyResultUrl.replace("$$", date) + "?t=" + new Date().getTime();
        String resultStr = HttpRequestUtil.get(url, cookie, header);
        InputStream is = new ByteArrayInputStream(resultStr.getBytes());
        ByteArrayOutputStream copy = cloneInputStream(is);
        Document document = parseDocument(new ByteArrayInputStream(copy.toByteArray()));
        if (document == null) {
            return null;
        }

        return new ByteArrayInputStream(copy.toByteArray());
    }

    private InputStream getResultFromFile(String date) {
        String path = SSCConstants.HISTORY_BASE_PATH + date;

        return null;
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
