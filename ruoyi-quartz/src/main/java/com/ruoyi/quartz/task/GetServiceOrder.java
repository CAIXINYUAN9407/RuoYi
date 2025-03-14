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

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

/**
 * 拉取服务用户已支付订单
 */
@Component("GetServiceOrder")
public class GetServiceOrder {
    private static final Logger log = LoggerFactory.getLogger(GetServiceOrder.class);

    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private VideoShopMapper videoShopMapper;

    public void ryMultipleParams(String s, Boolean b, Integer j)
    {
        log.debug("====================拉取服务用户已支付订单【GetServiceOrder】====================");

        List<VideoShop> videoShopList = videoShopMapper.selectVideoShopListNoExpire();
        for (int i = 0; i < videoShopList.size(); i++) {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            String resData= null;
            try {
                // 核心定时器，每一个小时执行一次
                Long userId = 1L;
                SysUser sysUser = userMapper.selectUserById(userId);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("appid", videoShopList.get(i).getOwner());
                jsonObject.put("service_id", "3536933729954938894");
                jsonObject.put("offset", 0);
                jsonObject.put("limit", 20);
                jsonObject.put("buyer_type", 1);
                HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/wxa/servicemarket/get_paid_order_list?access_token="+sysUser.getMarketAccessToken());
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
                        JSONArray orderListArray = obj.getJSONArray("order_list");
                        for(Object buyer : orderListArray){
                            JSONObject objM = (JSONObject) buyer;
Integer expireTime= (Integer) objM.get("expire_time");
                            // 假设这是需要判断的十位时间戳
                            int tenDigitTimestamp = 1609459200; // 2021-01-01 00:00:00 UTC

                            // 获取当前时间的秒数
                            int currentTimestamp = (int) Instant.now().getEpochSecond();

                            // 判断十位时间戳是否小于当前时间
                            if(expireTime < currentTimestamp){
                                continue;
                            }


                            Integer specId = (Integer) objM.get("sku_id");
                            VideoShop videoShop = videoShopList.get(i);
                            videoShop.setServiceSpecId(specId);
                            videoShopMapper.updateVideoShop(videoShopList.get(i));
                        }
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

        log.debug("====================拉取服务用户已支付订单【GetServiceOrder】====================");
    }
}
