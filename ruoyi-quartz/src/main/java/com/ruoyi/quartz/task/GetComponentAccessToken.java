package com.ruoyi.quartz.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.mapper.SysUserMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.methods.HttpPost;

/**
 * 1688定时获取商品Task
 *
 * @author ruoyi
 */
@Component("GetComponentAccessToken")
public class GetComponentAccessToken {

    private static final Logger log = LoggerFactory.getLogger(GetComponentAccessToken.class);
    @Autowired
    private SysUserMapper userMapper;
    /*
    * 定时获取刷新ComponentAccessToken*/
    public void ryMultipleParams(String s, Boolean b, Integer j)
    {
        log.debug("====================开始执行定时任务获取令牌【component_access_token】====================");
        Map<String, String> reMap;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String resData= null;
        try {
            // 核心定时器，每一个小时执行一次
            Long userId = 1L;
            SysUser sysUser = userMapper.selectUserById(userId);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("component_appid", "wxdc5787bd0edbfc75");
            jsonObject.put("component_appsecret", "8daa24cec0c59b31f822b14dce8f6642");
            jsonObject.put("component_verify_ticket", sysUser.getComponentVerifyTicket());
            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/component/api_component_token");
            StringEntity stringEntity = new StringEntity(jsonObject.toString());
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
//                    JSONArray result = obj.getJSONArray("result");
                    System.out.println(obj.toString());
                    HashMap<String, String> hashMap = JSON.parseObject(resData, HashMap.class);
                    String componentAccessToken = hashMap.get("component_access_token");
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
        log.debug("====================结束执行定时任务获取令牌【component_access_token】====================");

    }


}
