package com.ruoyi.quartz.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.VideoShop;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.mapper.VideoShopMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;

/**
 * 获取订单解密信息
 */
@Component("GetSensitiveInfo")
public class GetSensitiveInfo {
    private static final Logger log = LoggerFactory.getLogger(GetComponentAccessToken.class);
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private VideoShopMapper videoShopMapper;
    /*
     * 定时获取刷新ComponentAccessToken*/
    public void ryMultipleParams(VideoShop videoShop,String orderId)
    {
        log.debug("====================获取订单解密信息【GetOrderDetail】====================");
        Map<String, String> reMap;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String resData= null;
        try {
            // 核心定时器，每一个小时执行一次
            Long userId = 1L;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("order_id", orderId);

            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/channels/ec/order/sensitiveinfo/decode?access_token="+videoShop.getAccessToken());
            StringEntity stringEntity = new StringEntity(jsonObject.toString());
            stringEntity.setContentType("text/json");
            stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpPost.setEntity(stringEntity);

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
                    JSONObject addressJson = (JSONObject) obj.get("address_info");

                    addressJson.get("user_name");
                    addressJson.get("tel_number");

                    addressJson.get("province_name");
                    addressJson.get("city_name");
                    addressJson.get("county_name");
                    addressJson.get("detail_info");
                    System.out.println(obj.toString());
//                    HashMap<String, String> hashMap = JSON.parseObject(resData, HashMap.class);
//                     videoShop = videoShopMapper.selectVideoShopByOwner("wx08fc080a10109484");
//                    videoShop.setAccessToken(obj.get("authorizer_access_token").toString().substring(15));
//                    videoShopMapper.updateVideoShop(videoShop);
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            }  finally {
                // 释放资源
                httpClient.close();
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug("====================获取订单解密信息【GetOrderDetail】====================");

    }
}
