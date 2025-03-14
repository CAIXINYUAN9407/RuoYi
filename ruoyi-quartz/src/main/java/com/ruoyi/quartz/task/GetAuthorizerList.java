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
import java.util.Map;
import org.apache.http.client.methods.HttpPost;
/**
 * 拉取已授权的帐号信息
 */
@Component("GetAuthorizerList")
public class GetAuthorizerList {
    private static final Logger log = LoggerFactory.getLogger(GetComponentAccessToken.class);
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private VideoShopMapper videoShopMapper;
    /*
     * 定时获取刷新ComponentAccessToken*/
    public void ryMultipleParams(String s, Boolean b, Integer j)
    {
        log.debug("====================拉取已授权的帐号信息【GetAuthorizerList】====================");
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String resData= null;
        try {
            // 核心定时器，每一个小时执行一次
            Long userId = 1L;
            SysUser sysUser = userMapper.selectUserById(userId);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("component_appid", "wxdc5787bd0edbfc75");
            jsonObject.put("offset", 0);
            jsonObject.put("count", 500);
            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_list?access_token="+sysUser.getComponentAccessToken());
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
                    JSONArray result = obj.getJSONArray("list");

                    for(Object objN : result){
                        JSONObject objM = (JSONObject) objN;
                        System.out.println(objM.get("authorizer_appid").toString());
//                        System.out.println(objM.get("refresh_token").toString());
//                        System.out.println(objM.get("auth_time").toString());

                        HashMap tableIndexMap = videoShopMapper.selectTableIndex();
                        Integer table_index = 0;
                        if (tableIndexMap != null){
                            table_index = (Integer) tableIndexMap.get("table_index");
                            log.info("表索引1——————————————————————————————————"+tableIndexMap.get("table_index").toString());
                        }
                        else{
                            table_index = videoShopMapper.selectMaxTableIndex()+1;
//                            videoShopMapper.createShopGoods("video_shop_goods_"+table_index);
                        }
                        VideoShop videoShopOld = videoShopMapper.selectVideoShopByOwner(objM.get("authorizer_appid").toString());
                        VideoShop videoShop = new VideoShop();
                        if(videoShopOld != null){
                            if(objM.get("refresh_token")!=null){
                                videoShopOld.setRefreshToken(objM.get("refresh_token").toString().substring(15));
//                                videoShopOld.setTableIndex((table_index));
                                videoShopMapper.updateVideoShop(videoShopOld);
                            }
                            else {
                                continue;
                            }
                        }
                        else {
                            videoShop.setOwner((objM.get("authorizer_appid").toString()));
                            videoShop.setTableIndex(table_index);
                            //刷新令牌
                            if(objM.get("refresh_token")==null){
                                continue;
                            }
                            videoShop.setRefreshToken(objM.get("refresh_token").toString().substring(15));

                            videoShopMapper.insertVideoShop(videoShop);
                        }

                    }
//                    String componentAccessToken = hashMap.get("list");
//                    log.debug("！！！！！！！！！！！！！！！！！！！！！！"+componentAccessToken);
//                    sysUser.setComponentAccessToken(componentAccessToken);
//                    userMapper.updateUser(sysUser);
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            }  catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 释放资源
                httpClient.close();
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug("====================拉取已授权的帐号信息【GetAuthorizerList】====================");

    }
}
