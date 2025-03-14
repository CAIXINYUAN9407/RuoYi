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
import java.util.List;
import java.util.Map;

/**
 * 获取授权账号授权令牌
 */
@Component("GetAuthorizerAccessToken")
public class GetAuthorizerAccessToken {
    private static final Logger log = LoggerFactory.getLogger(GetComponentAccessToken.class);
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private VideoShopMapper videoShopMapper;
    /*
     * 定时获取刷新ComponentAccessToken*/
    public void ryMultipleParams(String s, Boolean b, Integer j)
    {
        log.debug("====================获取授权账号授权令牌【AuthorizerAccessToken】====================");
        Map<String, String> reMap;
        String resData= null;
        List<VideoShop> videoShopList = videoShopMapper.selectVideoShopListNoExpire();
        for (int i=0;i<videoShopList.size();i++){
            try {
                CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                // 核心定时器，每一个小时执行一次
                Long userId = 1L;
                SysUser sysUser = userMapper.selectUserById(userId);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("component_appid", "wxdc5787bd0edbfc75");
                jsonObject.put("authorizer_appid", videoShopList.get(i).getOwner());
                VideoShop videoShop = videoShopList.get(i);
                jsonObject.put("authorizer_refresh_token", videoShop.getRefreshToken());
                HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/component/api_authorizer_token?component_access_token="+sysUser.getComponentAccessToken());
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
                        if(obj.get("errcode")!=null){
                            continue;
                        }
//                    HashMap<String, String> hashMap = JSON.parseObject(resData, HashMap.class);


                        //获取主账号finderId
                        videoShop.setAccessToken(obj.get("authorizer_access_token").toString());
                        String finderId = getFinderId(obj.get("authorizer_access_token").toString());
                        videoShop.setMainFinderId(finderId);
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
        }
        log.debug("====================获取授权账号授权令牌【AuthorizerAccessToken】====================");

    }

    /**
     * 获取店铺主finderId
     * @param accessToken
     * @return
     */
    private String getFinderId(String accessToken) {
        String finderId = "";
        try {
            String resData= null;
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            // 核心定时器，每一个小时执行一次
            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/channels/ec/compass/shop/finder/authorization/list/get?access_token="+accessToken);
            CloseableHttpResponse response = null;
            JSONObject jsonObject = new JSONObject();
            StringEntity stringEntity = new StringEntity(jsonObject.toString());
            stringEntity.setContentType("text/json");
            stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpPost.setEntity(stringEntity);
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
//                    HashMap<String, String> hashMap = JSON.parseObject(resData, HashMap.class);


                    //获取主账号finderId
                    finderId = (String) obj.get("main_finder_id");

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
        return finderId;
    }
}
