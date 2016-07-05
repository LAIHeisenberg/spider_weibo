package com.heisenberg;

import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class App
{

    private static String Referer = "http://s.weibo.com/weibo/%2523%25E5%2590%2591%25E5%2585%25A8%25E4%25B8%2596%25E7%2595%258C%25E5%25AE%2589%25E5%2588%25A9%25E6%2598%2593%25E7%2583%258A%25E5%258D%2583%25E7%258E%25BA%2523?topnav=1&wvr=6&b=1";
    private static String UserAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0";
    private static String Accept = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    private static String AcceptLanguage = "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3";
    private static String AcceptEncoding = "gzip, deflate";
    private static String Cookie = "SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WWhJ1POGKYBEURseYCOXd-.5JpX5o2p5NHD95Qp1KeN1KM4eoqpWs4Dqcj1K28iPXyLIPSLdJiLMJMt; UOR=www.yinyuetai.com,widget.weibo.com,blog.csdn.net; SINAGLOBAL=2916359265194.465.1453957912171; ULV=1467690245400:33:1:1:541504328025.32513.1467690245395:1465185311471; SUHB=0V5Uu6xqTC9cFb; ALF=1499226193; TC-Page-G0=0dba63c42a7d74c1129019fa3e7e6e7c; SCF=AggflPdmI0F-YGD_RpwWof8OYm_5HwkVRV5ldZPZpmyZ-Cr5VIjboQjMLLv-l0MIFd46ygrXkXZHYDxPd8fKZtQ.; SUB=_2A256f11uDeTxGedH6FUY9yfOzT2IHXVZDcmmrDV8PUJbmtAKLRfgkW-XlwMuESvZT7xTrhK-HWb-NSB-vw..; SSOLoginState=1467690195; _s_tentry=login.sina.com.cn; Apache=541504328025.32513.1467690245395; TC-V5-G0=9ec894e3c5cc0435786b4ee8ec8a55cc; TC-Ugrow-G0=e66b2e50a7e7f417f6cc12eec600f517; wvr=6; wb_bub_hot_1937979261=1";

    private static String REGEX = "<h1 title=\\\\\\\"#(.*?)#\\\\\\\" class=\\\\\\\"username\\\\\\\">#(.*?)#<\\\\/h1>";
    private static String REGEX2 = "<strong class=\\\\\\\"(.*?)\\\\\\\">(.*?)<\\\\\\/strong>";
//    <h1 title=\"#向全世界安利许魏洲#\" class=\"username\">#向全世界安利许魏洲#<\/h1>
    //  <strong class=\"W_f14\">6221<\/strong>


    public static void main( String[] args ) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet get = new HttpGet();

        get.setHeader("Referer: ",Referer);
        get.setHeader("User-Agent",UserAgent);
        get.setHeader("Accept",Accept);
        get.setHeader("Accept-Encoding",AcceptEncoding);
        get.setHeader("Accept-Language",AcceptLanguage);
        get.setHeader("Cookie",Cookie);



        FileReader fr = new FileReader(new File("C:\\Users\\YinYueTai-DEV\\Desktop\\top100Url.txt"));
        BufferedReader br = new BufferedReader(fr);

        String url = null;
        HttpEntity entity = null;

        try{
            while ( (url=br.readLine()) != null){
                get.setURI(new URI(url));
                CloseableHttpResponse response = httpClient.execute(get);
                entity = response.getEntity();
                String html = EntityUtils.toString(entity);

                Pattern pattern = Pattern.compile(REGEX2);
                Matcher matcher = pattern.matcher(html);
                while (matcher.find()){
                    System.out.println(matcher.group(2));
                }



                pattern = Pattern.compile(REGEX);
                matcher = pattern.matcher(html);

                System.out.println(url);
            }
        }catch (Exception e){
            e.printStackTrace();
        }



//        try {
//            HttpEntity entity = response.getEntity();
//            String html = EntityUtils.toString(entity);
//
//            System.out.println(html);
//

//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }
}
