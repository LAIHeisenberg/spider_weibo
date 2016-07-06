package com.heisenberg.job;

import com.heisenberg.datamodel.DataModel;
import com.heisenberg.datamodel.WeiboData;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Heisenberg on 7/6/16.
 */
public class SpiderRobot {

    private static Logger logger = LoggerFactory.getLogger(SpiderRobot.class);

    private static String Referer = "http://s.weibo.com/weibo/%2523%25E5%2590%2591%25E5%2585%25A8%25E4%25B8%2596%25E7%2595%258C%25E5%25AE%2589%25E5%2588%25A9%25E6%2598%2593%25E7%2583%258A%25E5%258D%2583%25E7%258E%25BA%2523?topnav=1&wvr=6&b=1";
    private static String UserAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0";
    private static String Accept = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    private static String AcceptLanguage = "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3";
    private static String AcceptEncoding = "gzip, deflate";
    private static String Cookie = "SINAGLOBAL=598801048472.5237.1456214361453; wvr=6; wb_bub_hot_5976404444=1; TC-Ugrow-G0=370f21725a3b0b57d0baaf8dd6f16a18; TC-V5-G0=52dad2141fc02c292fc30606953e43ef; WBStore=8ca40a3ef06ad7b2|undefined; _s_tentry=-; UOR=lyz.yinyuetai.com,widget.weibo.com,www.baidu.com; Apache=2878380124457.1807.1467782467201; ULV=1467782467207:44:2:2:2878380124457.1807.1467782467201:1467690266053; SCF=Ag4qp28R7GZ1sYWfa131ZVQ213iw3aMrObNfZoMVPhfa2Uz_Fpg8Ez6q4MTfNlH-0JpngEON1SmzcBQ0NLkob9w.; SUB=_2A256eOV4DeTxGeNH7FQV8CrIzziIHXVZDFGwrDV8PUNbmtAKLRHfkW9SXDd0n0FPNBRNia0ho7hYsvuTXg..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9Whm-xvu8kGnkDPfSkpZuylC5JpX5K2hUgL.Fo-4S0qXehBXShB2dJLoI0QLxK-L1h-L1hBLxK-LB.eL1-qLxKnLBoMLB-qLxK-LB-BL1K5LxK.L1hML12-LxK-L1K-L122Eeh2c; SUHB=010G4V2NDXgPcQ; ALF=1499318440; SSOLoginState=1467782440; un=18310018698; TC-Page-G0=444eec11edc8886c2f0ba91990c33cda; WBtopGlobal_register_version=2a1db7c83c247632";

    private static String REGEX = "<h1 title=\\\\\\\"#(.*?)#\\\\\\\" class=\\\\\\\"username\\\\\\\">#(.*?)#<\\\\/h1>";
    private static String REGEX2 = "<strong class=\\\\\\\"(.*?)\\\\\\\">(.*?)<\\\\\\/strong>";

    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private HttpGet httpGet = new HttpGet();

    {
        httpGet.setHeader("Referer: ",Referer);
        httpGet.setHeader("User-Agent",UserAgent);
        httpGet.setHeader("Accept",Accept);
        httpGet.setHeader("Accept-Encoding",AcceptEncoding);
        httpGet.setHeader("Accept-Language",AcceptLanguage);
        httpGet.setHeader("Cookie",Cookie);
    }


    public List<DataModel> doWork(List<String> urls){

        List<DataModel> result = new ArrayList<DataModel>();
        int count = 0;
        try{
            for (String url : urls){
                httpGet.setURI(new URI(url));
                CloseableHttpResponse response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                String html = EntityUtils.toString(entity);
                DataModel dataModel = extractKeyword(html);
                result.add(dataModel);
                logger.info("数据{}: {}",++count,dataModel.toString());

                Thread.sleep(800);
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return result;

    }


    public DataModel extractKeyword(String html){

        Pattern pattern1 = Pattern.compile(REGEX);
        Pattern pattern2 = Pattern.compile(REGEX2);
        Matcher matcher = pattern1.matcher(html);

        String[] result = new String[16];
        int i = 0;
        while (matcher.find()){
            result[i++] = matcher.group(2);
        }
        matcher = pattern2.matcher(html);
        while (matcher.find()){
            result[i++] = matcher.group(2);
        }

        return new WeiboData(result);

    }

}
