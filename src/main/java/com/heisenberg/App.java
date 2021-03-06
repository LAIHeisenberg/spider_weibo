package com.heisenberg;

import org.apache.http.client.methods.HttpGet;

import java.io.*;
import java.net.URI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class App
{
    private static String Referer = "http://s.weibo.com/weibo/%2523%25E5%2590%2591%25E5%2585%25A8%25E4%25B8%2596%25E7%2595%258C%25E5%25AE%2589%25E5%2588%25A9%25E6%2598%2593%25E7%2583%258A%25E5%258D%2583%25E7%258E%25BA%2523?topnav=1&wvr=6&b=1";
    private static String UserAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0";
    private static String Accept = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    private static String AcceptLanguage = "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3";
    private static String AcceptEncoding = "gzip, deflate";
//    private static String Cookie = "SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WWhJ1POGKYBEURseYCOXd-.5JpX5o2p5NHD95Qp1KeN1KM4eoqpWs4Dqcj1K28iPXyLIPSLdJiLMJMt; UOR=www.yinyuetai.com,widget.weibo.com,blog.csdn.net; SINAGLOBAL=2916359265194.465.1453957912171; ULV=1467690245400:33:1:1:541504328025.32513.1467690245395:1465185311471; SUHB=0V5Uu6xqTC9cFb; ALF=1499226193; TC-Page-G0=0dba63c42a7d74c1129019fa3e7e6e7c; SCF=AggflPdmI0F-YGD_RpwWof8OYm_5HwkVRV5ldZPZpmyZ-Cr5VIjboQjMLLv-l0MIFd46ygrXkXZHYDxPd8fKZtQ.; SUB=_2A256f11uDeTxGedH6FUY9yfOzT2IHXVZDcmmrDV8PUJbmtAKLRfgkW-XlwMuESvZT7xTrhK-HWb-NSB-vw..; SSOLoginState=1467690195; _s_tentry=login.sina.com.cn; Apache=541504328025.32513.1467690245395; TC-V5-G0=9ec894e3c5cc0435786b4ee8ec8a55cc; TC-Ugrow-G0=e66b2e50a7e7f417f6cc12eec600f517; wvr=6; wb_bub_hot_1937979261=1";
    private static String Cookie = "SINAGLOBAL=1289981417443.7395.1466945405888; UOR=www.liaoxuefeng.com,widget.weibo.com,www.liaoxuefeng.com; YF-Ugrow-G0=9642b0b34b4c0d569ed7a372f8823a8e; YF-V5-G0=55fccf7be1706b6814a78384fa94e30c; WBStore=8ca40a3ef06ad7b2|undefined; _s_tentry=-; Apache=1685448022833.329.1467728868837; ULV=1467728868855:2:1:1:1685448022833.329.1467728868837:1466945405902; YF-Page-G0=dc8d8d4964cd93a7c3bfa7640c1bd10c; SCF=AhHI5euzarerqfbEK9q4PHxo6gls4qluNc65HeOgBg-nr3Gfiw4Zg0poeF74v5pcXOUon_DVQmeJyXgttpqDvus.; SUB=_2A256f7TRDeTxGeNH7FQV8CrIzziIHXVZDKEZrDV8PUNbmtBeLRCskW8vHudUIhiD4vfTexiMwsZh-K3g2Q..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9Whm-xvu8kGnkDPfSkpZuylC5JpX5K2hUgL.Fo-4S0qXehBXShB2dJLoI0QLxK-L1h-L1hBLxK-LB.eL1-qLxKnLBoMLB-qLxK-LB-BL1K5LxK.L1hML12-LxK-L1K-L122Eeh2c; SUHB=00xpKxwbO0KlRg; ALF=1499265025; SSOLoginState=1467729025; un=18310018698; wvr=6; wb_bub_hot_5976404444=1; WBtopGlobal_register_version=2a1db7c83c247632";

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

        FileReader fr = new FileReader(new File("C:\\Users\\LaiDaShen\\Desktop\\top100.txt"));
        BufferedReader br = new BufferedReader(fr);
        FileWriter fw = new FileWriter(new File("C:\\Users\\LaiDaShen\\Desktop\\out.txt"));
        BufferedWriter bw = new BufferedWriter(fw);

        String url = null;
        HttpEntity entity = null;
        Pattern pattern = null;
        Matcher matcher = null;
        try{
            while ( (url=br.readLine()) != null){
                get.setURI(new URI(url));
                CloseableHttpResponse response = httpClient.execute(get);
                entity = response.getEntity();
                String html = EntityUtils.toString(entity);

                pattern = Pattern.compile(REGEX);
                matcher = pattern.matcher(html);
                String artist = null;

                while (matcher.find()){
                    artist = matcher.group(2);
                }
                bw.write(artist+",");
                pattern = Pattern.compile(REGEX2);
                matcher = pattern.matcher(html);
                while (matcher.find()){
                    System.out.println(matcher.group(2));
                    bw.write(matcher.group(2)+",");
                }
                bw.flush();
                bw.newLine();

                Thread.sleep(1000);

            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
