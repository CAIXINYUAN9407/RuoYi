package com.ruoyi.quartz.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.VideoShop;
import com.ruoyi.system.domain.VideoShopOrder;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.mapper.VideoShopMapper;
import com.ruoyi.system.mapper.VideoShopOrderMapper;
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

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * 获取订单列表
 */
@Component("GetOrderList")
public class GetOrderList {
    private static final Logger log = LoggerFactory.getLogger(GetComponentAccessToken.class);
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private VideoShopMapper videoShopMapper;
    @Autowired
    private VideoShopOrderMapper videoShopOrderMapper;
    /*
     * 定时获取刷新ComponentAccessToken*/
    public void ryMultipleParams(String s, Boolean b, Integer j)
    {
        log.debug("====================获取订单列表【GetOrderList】====================");
        Map<String, String> reMap;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String resData= null;
        try {
            // 核心定时器，每一个小时执行一次
            Long userId = 1L;
            SysUser sysUser = userMapper.selectUserById(userId);
            VideoShop videoShop = videoShopMapper.selectVideoShopByOwner("wx08fc080a10109484");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("page_size", 10);

            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("start_time",1716267753);
            jsonObject2.put("end_time",1716858953);

            jsonObject.put("create_time_range", jsonObject2);
            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/channels/ec/order/list/get?access_token="+videoShop.getAccessToken());
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
                    JSONArray orderArray = (JSONArray) obj.get("order_id_list");
                    Iterator<Object> iterator = orderArray.iterator();
                    while(iterator.hasNext()){
                        //订单列表处理
                        getOrderDetail(videoShop,(String) iterator.next());
                    }
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
        log.debug("====================获取订单列表【GetOrderList】====================");

    }

    public void getOrderDetail(VideoShop videoShop,String orderId){
        log.debug("====================获取订单详情【GetOrderDetail】====================");
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String resData= null;
        try {
            // 核心定时器，每一个小时执行一次
            Long userId = 1L;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("order_id", orderId);

            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/channels/ec/order/get?access_token="+videoShop.getAccessToken());
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
                    JSONObject orderDetail = (JSONObject) obj.get("order");
                    System.out.println(obj.toString());

                    VideoShopOrder videoShopOrder = new VideoShopOrder();
                    videoShopOrder.setOrderId((String) orderDetail.get("order_id"));
                    videoShopOrder.setOpenid((String) orderDetail.get("openid"));
                    videoShopOrder.setStatus((Integer) orderDetail.get("status"));
                    videoShopOrder.setCreateTime(formatDate((Integer) orderDetail.get("create_time")));
                    videoShopOrder.setUpdateTime(formatDate((Integer) orderDetail.get("update_time")));

                    JSONObject orderDetailObject = (JSONObject) orderDetail.get("order_detail");
                    JSONObject productInfosObject = (JSONObject) ((JSONArray) orderDetailObject.get("product_infos")).get(0);
                    videoShopOrder.setProductId((String) productInfosObject.get("product_id"));
                    videoShopOrder.setTitle((String) productInfosObject.get("title"));
                    videoShopOrder.setSkuId((String) productInfosObject.get("sku_id"));
                    videoShopOrder.setSkuCnt((Integer) productInfosObject.get("sku_cnt"));

                    JSONObject priceInfoObject = (JSONObject) (orderDetailObject.get("price_info"));
                    videoShopOrder.setProductPrice(BigDecimal.valueOf((Integer) priceInfoObject.get("product_price")/100));
                    videoShopOrder.setOrderPrice(BigDecimal.valueOf((Integer) priceInfoObject.get("order_price")/100));
                    videoShopOrder.setFreight(BigDecimal.valueOf((Integer)priceInfoObject.get("freight")/100));

                    JSONObject extInfoObject = (JSONObject) (orderDetailObject.get("ext_info"));
                    videoShopOrder.setCustomerNotes((String) extInfoObject.get("customer_notes"));
                    videoShopOrder.setMerchantNotes((String) extInfoObject.get("merchant_notes"));
                    videoShopOrder.setConfirmReceiptTime(formatDate((Integer) extInfoObject.get("confirm_receipt_time")));
                    videoShopOrder.setFinderId((String) extInfoObject.get("finder_id"));
                    videoShopOrder.setLiveId((String) extInfoObject.get("live_id"));
                    videoShopOrder.setOrderScene((Integer) extInfoObject.get("order_scene"));

                    //处理加密信息
                    videoShopOrder = getSensitiveInfo(videoShop,videoShopOrder,orderId);
                    videoShopOrderMapper.insertVideoShopOrder(videoShopOrder);


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
        log.debug("====================获取订单详情【GetOrderDetail】====================");
    }

    public VideoShopOrder getSensitiveInfo(VideoShop videoShop,VideoShopOrder videoShopOrder,String orderId){
        log.debug("====================获取订单解密信息【GetOrderDetail】====================");
        Map<String, String> reMap;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String resData= null;
        try {
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

                    videoShopOrder.setUserName((String) addressJson.get("user_name"));
                    videoShopOrder.setTelNumber((String) addressJson.get("tel_number"));

                    videoShopOrder.setProvinceName((String) addressJson.get("province_name"));
                    videoShopOrder.setCityName((String) addressJson.get("city_name"));
                    videoShopOrder.setCountyName((String) addressJson.get("county_name"));
                    videoShopOrder.setDetailInfo((String) addressJson.get("detail_info"));
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

        return videoShopOrder;
    }

    public Date formatDate(Integer timestamp){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timestamp * 1000L); // 将秒转换为毫秒
        String formattedDate = sdf.format(date);
        System.out.println("Date: " + formattedDate);
//        DateTime dateTime = DateTime.of(DateTime.parse(formattedDate));
        return date;
    }
}
