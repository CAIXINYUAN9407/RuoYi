package com.ruoyi.quartz.task;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.VideoShop;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.mapper.VideoShopMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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

import java.util.Map;

/**
 * 获取视频号小店信息
 */
@Component("GetBasicsInfo")
public class GetBasicsInfo {
    private static final Logger log = LoggerFactory.getLogger(GetComponentAccessToken.class);
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private VideoShopMapper videoShopMapper;
    /*
     * 定时获取刷新ComponentAccessToken*/
    public void ryMultipleParams(String s, Boolean b, Integer j)
    {
        log.debug("====================获取视频号小店信息【GetBasicsInfo】====================");
        Map<String, String> reMap;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String resData= null;
        try {
            // 核心定时器，每一个小时执行一次
            Long userId = 1L;
            SysUser sysUser = userMapper.selectUserById(userId);
            JSONObject jsonObject = new JSONObject();
            HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/channels/ec/basics/info/get?access_token=81_NwacrJbGtKsQ_Ws46hrPGig-pCkvnbu9Z1u09k4_hng_G6vv5AhNcgP7WUkeWKwBmqYGgzGzYZJ0iccFl4pVplKAE5r1vZJzl9WCrwKDGFbIj7lZAXlBXEFy8tZ_e4CR1Nt3qncvCrYw54LsIPHbAKDOVN");
            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/channels/ec/basics/info/get?access_token="+sysUser.getComponentAccessToken());
            StringEntity stringEntity = new StringEntity(jsonObject.toString());
            stringEntity.setContentType("text/json");
            stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpPost.setEntity(stringEntity);


            CloseableHttpResponse response = null;
            try {
                // 由客户端执行(发送)Post请求
                response = httpClient.execute(httpGet);
                // 从响应模型中获取响应实体
                HttpEntity responseEntity = response.getEntity();

                System.out.println("响应状态为:" + response.getStatusLine());
                if (responseEntity != null) {
                    System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                    resData = EntityUtils.toString(response.getEntity());
                    JSONObject obj = JSONObject.parseObject(resData);
                    System.out.println(obj.toString());
//                    HashMap<String, String> hashMap = JSON.parseObject(resData, HashMap.class);
                    VideoShop videoShop = videoShopMapper.selectVideoShopByOwner("wx08fc080a10109484");
                    videoShop.setAccessToken(obj.get("authorizer_access_token").toString());
                    videoShopMapper.updateVideoShop(videoShop);
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
        log.debug("====================获取视频号小店信息【GetBasicsInfo】====================");

    }
}
