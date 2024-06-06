package com.ruoyi.web.test;


import com.alibaba.account.param.AlibabaAccountBasicResult;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.ocean.rawsdk.ApiExecutor;
import com.alibaba.product.param.AlibabaProductSampleInfo;
import com.alibaba.wangwang.param.WangwangChatMessageMatchQueryData;
import com.ruoyi.web.Util.CommonUtil;
import com.ruoyi.web.Util.StringUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AccessToken {
    @Value("${app.appKey}")
    private String appKey;
    @Value("${app.appSecret}")
    private String appSecret;

    public static void main(String[] args) throws IOException, JSONException {


        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        Map<String, String> hasMap = new HashMap<String, String>();
        hasMap.put("access_token", "fc5f92d2-8179-42ca-9335-1d1d1a164861");
//        hasMap.put("pageNo", "1");
//        hasMap.put("pageSize", "20");
        String resData= null;

//        String time = "2023-04-03 12:00:00";
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        ParsePosition pos = new ParsePosition(0);
//        Date strtodate = dateFormat.parse(time, pos);
//        String aop_timestamp = StringUtil.format(strtodate);

        long timeTest1 = Calendar.getInstance().getTimeInMillis();

        //根据Code获取Token
//        String url1 ="https://gw.open.1688.com/openapi/http/1/system.oauth2/getToken/6277113?grant_type=authorization_code&need_refresh_token=true&client_id=6277113&client_secret=UUPmYVlG0y&redirect_uri=http://localhost/&code=0e1b6ec9-7a7c-4f4d-acac-9176da2b276a";
//        http://gw.open.1688.com/openapi/
//      @param urlPath protocol/version/namespace/name/appKey
        String url1 = "param2/1/com.alibaba.trade/alibaba.trade.ec.getOrderList.sellerView/6277113";
//                +"&_aop_signature=DE1D9BDE00646F5C1704930003C9FC011AADDE25";
        url1 = CommonUtil.signatureWithParamsAndUrlPath(url1, hasMap, "UUPmYVlG0y");
        url1 = "https://gw.open.1688.com/openapi/param2/1/com.alibaba.trade/alibaba.trade.ec.getOrderList.sellerView/" +
                "6277113?" +
                "access_token=fc5f92d2-8179-42ca-9335-1d1d1a164861&pageNo=1&pageSize=20" +
                "&_aop_signature=" + url1;
        String url = "https://gw.open.1688.com/auth/authorize.htm";
        String clientId = "6277113 ";
        String redirectUri = "http://localhost:8080";
        String state = "teststate";
        URIBuilder uriBuilder;

        {
            try {
                uriBuilder = new URIBuilder(url);
                uriBuilder.addParameter("client_id", clientId);
                uriBuilder.addParameter("site", "china");
                uriBuilder.addParameter("redirect_uri", redirectUri);
                uriBuilder.addParameter("state", state);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }


        HttpGet httpGet;
        HttpPost httpPost;

        {
            try {
                httpGet = new HttpGet(uriBuilder.build());
                httpPost = new HttpPost(url1);

            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
//        a) grant_type为授权类型，使用authorization_code即可
//        b) need_refresh_token为是否需要返回refresh_token，如果返回了refresh_token，原来获取的refresh_token也不会失效，除非超过半年有效期
//        c) client_id为app唯一标识，即appKey
//        d) client_secret为app密钥
//        e) redirect_uri为app入口地址
//        f) code为授权完成后返回的一次性令牌
//        g) 调用getToken接口不需要签名
//        String url1 ="  https://gw.open.1688.com/openapi/http/1/system.oauth2/getToken/6277113?grant_type=authorization_code&need_refresh_token=true&client_id=6277113&client_secret=UUPmYVlG0y&redirect_uri=http://localhost/&code=e2c6924a-fc1a-4b6a-88c7-a910628cb09b";

//        http://gw.open.1688.com:80/openapi/param2/1/com.alibaba.product/alibaba.category.get/6277113
//        String url1 = "https://gw.open.1688.com/openapi/param2/1/alibaba.open.ec.order.list.get";

//        换取accessToken的url示例如下：
//        https://gw.open.1688.com/openapi/param2/1/system.oauth2/getToken/YOUR_APPKEY
//        请求参数如下：
//        grant_type=refresh_token&client_id=YOUR_APPKEY&client_secret=YOUR_APPSECRET&refresh_token=REFRESH_TOKEN

        String accessToken = "067f2487-1830-44d8-9acd-48dc55cf5f4a";
        String appKey = "6277113";
        String appSecret = "UUPmYVlG0y";
        String memberId = "源之纵横"; // 买家会员id
        int pageSize = 10;
        int pageNo = 1;
        ApiExecutor apiExecutor = new ApiExecutor("6277113", "UUPmYVlG0y");
//        AlibabaProductSampleInfo
//        apiExecutor.execute(param, "{ba8b5cc6-c03f-4d70-8a84-84403209f501");
//        AlibabatradefastCreateOrderParam
//        https://gw.open.1688.com/openapi/param2/1/com.alibaba.product/alibaba.category.get/6277113?
//        // access_token=067f2487-1830-44d8-9acd-48dc55cf5f4a
//        // &_aop_signature=A7D1D9E503ADB6B33CB2538F6AA9859B4FC530DF&categoryID=1035885

        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();

            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                resData = EntityUtils.toString(response.getEntity());
                JSONObject obj = JSONObject.parseObject(resData);
                JSONArray result = obj.getJSONArray("result");
                System.out.println(obj.toString());

            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        }  finally {
                // 释放资源
                    httpClient.close();
                    response.close();
                }

        }

    }



