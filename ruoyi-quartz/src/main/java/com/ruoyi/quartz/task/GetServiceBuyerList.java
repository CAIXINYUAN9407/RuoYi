package com.ruoyi.quartz.task;

import com.alibaba.fastjson.JSON;
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

import java.util.HashMap;

/**
 * 拉取服务用户有效期列表
 */
@Component("GetServiceBuyerList")
public class GetServiceBuyerList {
    private static final Logger log = LoggerFactory.getLogger(GetServiceBuyerList.class);

    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private VideoShopMapper videoShopMapper;

    public void ryMultipleParams(String s, Boolean b, Integer j) {
        log.debug("====================拉取服务用户有效期列表【GetServiceBuyerList】====================");

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String resData = null;
        //偏移量
        int i = 0;
        try {
            // 核心定时器，每一个小时执行一次
            Long userId = 1L;
            SysUser sysUser = userMapper.selectUserById(userId);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("service_id", "3536933729954938894");
            jsonObject.put("offset", i);
            jsonObject.put("limit", 10);
            jsonObject.put("buyer_type", 1);
            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/wxa/servicemarket/service/get_service_buyer_list?access_token=" + sysUser.getMarketAccessToken());
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
                    System.out.println(obj.toString());
                    HashMap<String, String> hashMap = JSON.parseObject(resData, HashMap.class);
                    JSONArray buyerListArray = obj.getJSONArray("buyer_list");
                    for (Object buyer : buyerListArray) {
                        JSONObject objM = (JSONObject) buyer;
                        JSONArray specListArray = objM.getJSONArray("spec_list");
                        Integer expire_time = (Integer) ((JSONObject) specListArray.get(0)).get("expire_time");
                        System.out.println("过期时间" + expire_time);
                        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner((String) objM.get("appid"));
                        videoShop.setServiceEndTime(Long.valueOf(expire_time));
                        videoShopMapper.updateVideoShop(videoShop);
                    }
                    if (buyerListArray.size() == 10) {
                        i++;
                        GetServiceBuyerListNextPage(i);
                    }

                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } finally {
                // 释放资源
                httpClient.close();
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug("====================拉取服务用户有效期列表【GetServiceBuyerList】====================");
    }

    public void GetServiceBuyerListNextPage(Integer j) {
        log.debug("====================拉取服务用户有效期列表【GetServiceBuyerList】====================");

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String resData = null;
        try {
            // 核心定时器，每一个小时执行一次
            Long userId = 1L;
            SysUser sysUser = userMapper.selectUserById(userId);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("service_id", "3536933729954938894");
            jsonObject.put("offset", 10 * (j));
            jsonObject.put("limit", 10);
            jsonObject.put("buyer_type", 1);
            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/wxa/servicemarket/service/get_service_buyer_list?access_token=" + sysUser.getMarketAccessToken());
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
                    System.out.println(obj.toString());
                    HashMap<String, String> hashMap = JSON.parseObject(resData, HashMap.class);
                    JSONArray buyerListArray = obj.getJSONArray("buyer_list");
                    for (Object buyer : buyerListArray) {
                        JSONObject objM = (JSONObject) buyer;
                        JSONArray specListArray = objM.getJSONArray("spec_list");
                        Integer expire_time = (Integer) ((JSONObject) specListArray.get(0)).get("expire_time");
                        System.out.println("过期时间" + expire_time);
                        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner((String) objM.get("appid"));
                        videoShop.setServiceEndTime(Long.valueOf(expire_time));
                        videoShopMapper.updateVideoShop(videoShop);
                    }
                    if (buyerListArray.size() == 10) {
                        j++;
                        GetServiceBuyerListNextPage(j);
                    }

                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } finally {
                // 释放资源
                httpClient.close();
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug("====================拉取服务用户有效期列表【GetServiceBuyerList】====================");
    }
}
