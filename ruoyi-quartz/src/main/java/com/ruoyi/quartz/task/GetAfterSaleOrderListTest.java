package com.ruoyi.quartz.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.VideoShop;
import com.ruoyi.system.domain.VideoShopAfterSaleOrder;
import com.ruoyi.system.domain.VideoShopOrder;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.mapper.VideoShopAfterSaleOrderMapper;
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

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 获取售后订单列表
 */
@Component("GetAfterSaleOrderListTest")
public class GetAfterSaleOrderListTest {
    private static final Logger log = LoggerFactory.getLogger(GetComponentAccessToken.class);
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private VideoShopMapper videoShopMapper;
    @Autowired
    private VideoShopAfterSaleOrderMapper videoShopAfterSaleOrderMapper;

    /*
     * 定时获取刷新ComponentAccessToken*/
    public void ryMultipleParams() {
        log.debug("====================获取售后订单列表【GetAfterSaleOrderList】====================");
        Map<String, String> reMap;

        // 核心定时器，每一个小时执行一次
        Long userId = 1L;
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner("wxdfc696e84fd39e21");
        for (int o = 30; o >= 0; o--) {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            String resData = null;
            JSONObject jsonObject = new JSONObject();
            JSONObject jsonObject2 = new JSONObject();

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, -o); // 设置为30天前的日期

            calendar.set(Calendar.HOUR_OF_DAY, 0); // 设置为23点
            calendar.set(Calendar.MINUTE, 0); // 设置分钟为59，表示当天的最后一分钟
            calendar.set(Calendar.SECOND, 0); // 设置秒为59，表示当天的最后一秒
            calendar.set(Calendar.MILLISECOND, 0); // 设置毫秒为999，表示当天的最后一毫秒
            long starttimestamp = calendar.getTimeInMillis() / 1000; // 获取30天前23点56分59秒的时间戳

            calendar.set(Calendar.HOUR_OF_DAY, 23); // 设置为23点
            calendar.set(Calendar.MINUTE, 59); // 设置分钟为59，表示当天的最后一分钟
            calendar.set(Calendar.SECOND, 59); // 设置秒为59，表示当天的最后一秒
            calendar.set(Calendar.MILLISECOND, 999); // 设置毫秒为999，表示当天的最后一毫秒
            long endtimestamp = calendar.getTimeInMillis() / 1000; // 获取30天前23点56分59秒的时间戳
            System.out.println(o + "天前23点的时间戳: " + endtimestamp);
            calendar = Calendar.getInstance();

            jsonObject.put("begin_create_time", starttimestamp);
            jsonObject.put("end_create_time", endtimestamp);

            try {
                HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/channels/ec/aftersale/getaftersalelist?access_token=" + videoShop.getAccessToken());
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
                        JSONArray orderArray = (JSONArray) obj.get("after_sale_order_id_list");
                        Iterator<Object> iterator = orderArray.iterator();
                        while (iterator.hasNext()) {
                            //订单列表处理
                            getAfterSaleOrderDetail(videoShop, (String) iterator.next());
                        }
                        System.out.println(obj.toString());
//                    HashMap<String, String> hashMap = JSON.parseObject(resData, HashMap.class);
//                     videoShop = videoShopMapper.selectVideoShopByOwner("wx08fc080a10109484");
//                    videoShop.setAccessToken(obj.get("authorizer_access_token").toString().substring(15));
//                    videoShopMapper.updateVideoShop(videoShop);
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
        }


        log.debug("====================获取订单列表【GetOrderList】====================");

    }

    public void getAfterSaleOrderDetail(VideoShop videoShop, String after_sale_order_id) {
        log.debug("====================获取订单详情【GetOrderDetail】====================");
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String resData = null;
        try {
            // 核心定时器，每一个小时执行一次
            Long userId = 1L;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("after_sale_order_id", after_sale_order_id);

            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/channels/ec/aftersale/getaftersaleorder?access_token=" + videoShop.getAccessToken());
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
                    JSONObject orderDetail = (JSONObject) obj.get("after_sale_order");
                    System.out.println(obj.toString());
                    String afterSaleOrderId = (String) orderDetail.get("after_sale_order_id");
                    VideoShopAfterSaleOrder videoShopAfterSaleOrder = new VideoShopAfterSaleOrder();

                    VideoShopAfterSaleOrder videoShopAfterSaleOrderOld = videoShopAfterSaleOrderMapper.selectVideoShopAfterSaleOrderByAfterSaleOrderId(afterSaleOrderId);
                    if (videoShopAfterSaleOrderOld != null) {
                        videoShopAfterSaleOrderOld.getParams().put("tableIndex", videoShop.getTableIndex());
                        videoShopAfterSaleOrderOld.setStatus((String) orderDetail.get("status"));
                        videoShopAfterSaleOrderOld.setType((String) orderDetail.get("type"));
                        videoShopAfterSaleOrderOld.setUpdateTime(formatDate((Integer) orderDetail.get("update_time")));
                        JSONObject refundinfoObject = (JSONObject) orderDetail.get("refund_info");
                        videoShopAfterSaleOrderOld.setAmount(BigDecimal.valueOf((Integer) refundinfoObject.get("amount") / 100));
                        videoShopAfterSaleOrderMapper.updateVideoShopAfterSaleOrder_UP(videoShopAfterSaleOrderOld);
                    } else {
                        videoShopAfterSaleOrder.getParams().put("tableIndex", videoShop.getTableIndex());
                        videoShopAfterSaleOrder.setLocalShopId(videoShop.getId());
                        videoShopAfterSaleOrder.setAfterSaleOrderId((String) orderDetail.get("after_sale_order_id"));

                        videoShopAfterSaleOrder.setStatus((String) orderDetail.get("status"));
                        videoShopAfterSaleOrder.setType((String) orderDetail.get("type"));
                        videoShopAfterSaleOrder.setReasonText((String) orderDetail.get("reason_text"));
                        videoShopAfterSaleOrder.setOpenid((String) orderDetail.get("openid"));
                        videoShopAfterSaleOrder.setOrderId((String) orderDetail.get("order_id"));
                        videoShopAfterSaleOrder.setCreateTime(formatDate((Integer) orderDetail.get("create_time")));
                        videoShopAfterSaleOrder.setUpdateTime(formatDate((Integer) orderDetail.get("update_time")));

                        JSONObject productinfoObject = (JSONObject) orderDetail.get("product_info");
                        videoShopAfterSaleOrder.setProductId((String) productinfoObject.get("product_id"));

                        JSONObject refundinfoObject = (JSONObject) orderDetail.get("refund_info");
                        videoShopAfterSaleOrder.setAmount(BigDecimal.valueOf((Integer) refundinfoObject.get("amount") / 100));
                        videoShopAfterSaleOrderMapper.insertVideoShopAfterSaleOrder_UP(videoShopAfterSaleOrder);
                    }


                    //处理加密信息
//                    videoShopOrder = getSensitiveInfo(videoShop,videoShopOrder,orderId);
//                    videoShopOrderMapper.insertVideoShopOrder(videoShopOrder);


//                    HashMap<String, String> hashMap = JSON.parseObject(resData, HashMap.class);
//                     videoShop = videoShopMapper.selectVideoShopByOwner("wx08fc080a10109484");
//                    videoShop.setAccessToken(obj.get("authorizer_access_token").toString().substring(15));
//                    videoShopMapper.updateVideoShop(videoShop);
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
        log.debug("====================获取订单详情【GetOrderDetail】====================");
    }

    public VideoShopOrder getSensitiveInfo(VideoShop videoShop, VideoShopOrder videoShopOrder, String orderId) {
        log.debug("====================获取订单解密信息【GetOrderDetail】====================");
        Map<String, String> reMap;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String resData = null;
        try {
            Long userId = 1L;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("order_id", orderId);

            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/channels/ec/order/sensitiveinfo/decode?access_token=" + videoShop.getAccessToken());
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
            } finally {
                // 释放资源
                httpClient.close();
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug("====================获取售后订单列表【GetAfterSaleOrderList】====================");

        return videoShopOrder;
    }

    public Date formatDate(Integer timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timestamp * 1000L); // 将秒转换为毫秒
        String formattedDate = sdf.format(date);
        System.out.println("Date: " + formattedDate);
//        DateTime dateTime = DateTime.of(DateTime.parse(formattedDate));
        return date;
    }
}
