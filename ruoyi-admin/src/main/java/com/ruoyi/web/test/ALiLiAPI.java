package com.ruoyi.web.test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.PrintWriter;
import java.net.URLConnection;

public class ALiLiAPI {
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return  sb.toString();
    }
    public static JSONObject postRequestFromUrl(String url, String body) throws IOException, JSONException {
        URL realUrl = new URL(url);
        URLConnection conn = realUrl.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        PrintWriter out = new PrintWriter(conn.getOutputStream());
        out.print(body);
        out.flush();
        InputStream instream = conn.getInputStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(instream, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            instream.close();
        }
    }
    public static JSONObject getRequestFromUrl(String url) throws IOException, JSONException {
        URL realUrl = new URL(url);
        URLConnection conn = realUrl.openConnection();
        InputStream instream = conn.getInputStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(instream, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            instream.close();
        }
    }
    public static void main(String[] args) throws IOException, JSONException {
        // 请求示例 url 默认请求参数已经URL编码处理
//      String url = "http://gw.open.1688.com/openapi/param2/1/cn.alibaba.open/member.get/6277113?memberId=tonytp4&_aop_timestamp=1375703483649&access_token=HMKSwKPeSHB7Zk7712OfC2Gn1-kkfVsaM-P&_aop_signature=DE1D9BDE00646F5C1704930003C9FC011AADDE25";
//        String url = "http://gw.open.1688.com/item_search/?key=6277113&secret=UUPmYVlG0y&q=女装&start_price=0&end_price=0&page=1&cat=0&discount_only=&sort=&page_size=40&seller_info=no&nick=&seller_info=&nick=&ppath=&imgid=&filter=";
//        https://gw.open.1688.com/openapi/param2/1/com.alibaba.wangwang/wangwang.chat.relation.query/6277113
       String url = " https://gw.open.1688.com/openapi/http/1/system.oauth2/getToken/6277113?grant_type=authorization_code&need_refresh_token=true&client_id=6277113&client_secret=UUPmYVlG0y&redirect_uri=YOUR_REDIRECT_URI&code=CODE";



        JSONObject json = getRequestFromUrl(url);
        System.out.println(json.toString());
    }

}