package com.ruoyi.system.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.*;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.service.IVideoShopService;
import com.ruoyi.common.core.text.Convert;

/**
 * 店铺Service业务层处理
 *
 * @author CaiXY
 * @date 2024-06-15
 */
@Service
public class VideoShopServiceImpl implements IVideoShopService {
    @Autowired
    private VideoShopMapper videoShopMapper;
    @Autowired
    private VideoShopAnchorMapper videoShopAnchorMapper;
    @Autowired
    private VideoShopSchedulingMapper videoShopSchedulingMapper;
    @Autowired
    private VideoShopOrderMapper videoShopOrderMapper;
    @Autowired
    private VideoShopAfterSaleOrderMapper videoShopAfterSaleOrderMapper;
    @Autowired
    private VideoShopSalarytemplateMapper videoShopSalarytemplateMapper;

    /**
     * 查询店铺
     *
     * @param id 店铺主键
     * @return 店铺
     */
    @Override
    public VideoShop selectVideoShopById(Long id) {
        return videoShopMapper.selectVideoShopById(id);
    }

    /**
     * 查询店铺列表
     *
     * @param videoShop 店铺
     * @return 店铺
     */
    @Override
    public List<VideoShop> selectVideoShopList(VideoShop videoShop) {
        return videoShopMapper.selectVideoShopList(videoShop);
    }

    /**
     * 新增店铺
     *
     * @param videoShop 店铺
     * @return 结果
     */
    @Override
    public int insertVideoShop(VideoShop videoShop) {
        videoShop.setCreateTime(DateUtils.getNowDate());
        return videoShopMapper.insertVideoShop(videoShop);
    }

    /**
     * 修改店铺
     *
     * @param videoShop 店铺
     * @return 结果
     */
    @Override
    public int updateVideoShop(VideoShop videoShop) {
        return videoShopMapper.updateVideoShop(videoShop);
    }

    /**
     * 批量删除店铺
     *
     * @param ids 需要删除的店铺主键
     * @return 结果
     */
    @Override
    public int deleteVideoShopByIds(String ids) {
        return videoShopMapper.deleteVideoShopByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除店铺信息
     *
     * @param id 店铺主键
     * @return 结果
     */
    @Override
    public int deleteVideoShopById(Long id) {
        return videoShopMapper.deleteVideoShopById(id);
    }

    @Override
    public VideoShop selectVideoShopByOwner(String owner) {
        return videoShopMapper.selectVideoShopByOwner(owner);
    }

    @Override
    public HashMap<String, Object> selectVideoShopByOwner1(String owner) {
        return videoShopMapper.selectVideoShopByOwner1(owner);
    }

    @Override
    public void syncjournal() {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);

        VideoShopAnchor videoShopAnchor = new VideoShopAnchor();
        videoShopAnchor.setShopId(videoShop.getId());
        videoShopAnchor.getParams().put("tableIndex", videoShop.getTableIndex());
        List<HashMap<String, Object>> resultList = videoShopAnchorMapper.selectJournalingByAnchorCount(videoShopAnchor);

        for (int i = 0; i < resultList.size(); i++) {

            Integer sceneLiveCount = 0;
            Integer sceneVideoCount = 0;
            Integer sceneShopCount = 0;

            BigDecimal sceneLiveSum = new BigDecimal("0.00");
            BigDecimal sceneVideoSum = new BigDecimal("0.00");
            BigDecimal sceneShopSum = new BigDecimal("0.00");

            BigDecimal anchorCommission = new BigDecimal("0.00");


            VideoShopScheduling videoShopScheduling = videoShopSchedulingMapper.selectVideoShopSchedulingById(String.valueOf(videoShop.getTableIndex()), Long.valueOf(String.valueOf(resultList.get(i).get("id"))));

            videoShopScheduling.getParams().put("tableIndex", videoShop.getTableIndex());

            videoShopScheduling.setValidProducts(Long.valueOf(String.valueOf(resultList.get(i).get("valid_products"))));
            videoShopScheduling.setCompletedOrders(Long.valueOf(String.valueOf(resultList.get(i).get("completed_orders"))));
            videoShopScheduling.setValidOrders(Long.valueOf(String.valueOf(resultList.get(i).get("valid_orders"))));
            videoShopScheduling.setUnpaidOrders(Long.valueOf(String.valueOf(resultList.get(i).get("unpaid_orders"))));
            videoShopScheduling.setSalesVolume(new BigDecimal(String.valueOf(resultList.get(i).get("sales_volume"))));
            videoShopScheduling.setAftersalesOrders(Long.valueOf(String.valueOf(resultList.get(i).get("aftersales_orders"))));
            videoShopScheduling.setRefundAmount(new BigDecimal(String.valueOf(resultList.get(i).get("refund_amount"))));

            VideoShopOrder videoShopOrder = new VideoShopOrder();
            videoShopOrder.setShopId(videoShop.getId());
            videoShopOrder.getParams().put("tableIndex", videoShop.getTableIndex());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = ((LocalDateTime) resultList.get(i).get("starttime")).format(formatter);
            String formattedDateTime2 = ((LocalDateTime) resultList.get(i).get("endtime")).format(formatter);
            videoShopOrder.getParams().put("beginTime", formattedDateTime);
            videoShopOrder.getParams().put("endTime", formattedDateTime2);

            Long template_id = videoShopScheduling.getTemplateId();
            VideoShopSalarytemplate videoShopSalarytemplate = new VideoShopSalarytemplate();
            videoShopSalarytemplate.getParams().put("tableIndex", videoShop.getTableIndex());
            videoShopSalarytemplate.setLocalShopId(videoShop.getId());
            videoShopSalarytemplate.setId(Math.toIntExact(template_id));
            VideoShopSalarytemplate videoShopSalarytemplateResult = videoShopSalarytemplateMapper.selectVideoShopSalarytemplateList(videoShopSalarytemplate).get(0);
            List<HashMap<String, String>> listMap = null;

            //（比率）特定商品提成
            if (videoShopSalarytemplateResult.getType().equals(2l)) {
                String radioGivenString = (String) videoShopSalarytemplateResult.getRadioGiven();
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    if (radioGivenString != null && !radioGivenString.equals("")) {
                        listMap = objectMapper.readValue(radioGivenString, new TypeReference<List<HashMap<String, String>>>() {
                        });
                    }
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }

            }
            //特定商品提成
            else if (videoShopSalarytemplateResult.getType().equals(4l)) {
                String basicRadioGivenString = videoShopSalarytemplateResult.getBasicRadioGiven();
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    listMap = objectMapper.readValue(basicRadioGivenString, new TypeReference<List<HashMap<String, String>>>() {
                    });
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }


            List<VideoShopOrder> resultVideoShopOrderList = videoShopOrderMapper.selectVideoShopOrderListAndScene_UP(videoShopOrder);
            Iterator<VideoShopOrder> VideoShopOrderIterator = resultVideoShopOrderList.iterator();
            while (VideoShopOrderIterator.hasNext()) {
                VideoShopOrder videoShopOrder1 = VideoShopOrderIterator.next();

//                VideoShopAfterSaleOrder videoShopAfterSaleOrder = videoShopAfterSaleOrderMapper.selectVideoShopAfterSaleOrderByOrderId_UP(String.valueOf(videoShop.getTableIndex()), String.valueOf(videoShop.getId()), videoShopOrder1.getOrderId());
//                if (videoShopAfterSaleOrder != null) {
//                    continue;
//                }
                if (videoShopOrder1.getStatus() == 100 || videoShopOrder1.getStatus() == 20 || videoShopOrder1.getStatus() == 21 || videoShopOrder1.getStatus() == 30) {
                    //订单来源统计
                    if (videoShopOrder1.getOrderScene() == 2) {
                        if (videoShopOrder1.getCommissionNickname() == "" || videoShopOrder1.getCommissionNickname() == null || videoShopOrder1.getCommissionNickname().equals(String.valueOf(resultList.get(i).get("live_account"))) || String.valueOf(resultList.get(i).get("live_account")).equals(videoShop.getShopName() + "直播号")) {

                            if (videoShopSalarytemplateResult.getType().equals(1l)) {
                                sceneLiveCount++;
                                sceneLiveSum = sceneLiveSum.add(videoShopOrder1.getOrderPrice());
                            } else if (videoShopSalarytemplateResult.getType().equals(2l)) {
                                if (listMap ==null) {
                                    anchorCommission = anchorCommission.add(videoShopOrder1.getOrderPrice().multiply(BigDecimal.valueOf(Long.parseLong(videoShopSalarytemplateResult.getRatioAnchorDefault()) * 0.01)));
                                } else {
                                    Iterator<HashMap<String, String>> listMapIterator = listMap.iterator();
                                    while (listMapIterator.hasNext()) {
                                        HashMap<String, String> listMapHashMap = listMapIterator.next();
                                        boolean containsValue = listMapHashMap.containsValue(videoShopOrder1.getProductId());
                                        if (containsValue) {
                                            String bilu = listMapHashMap.get("key3");
                                            anchorCommission = anchorCommission.add(videoShopOrder1.getOrderPrice().multiply(BigDecimal.valueOf(Long.parseLong(bilu) * 0.01)));
                                        } else {
                                            anchorCommission = anchorCommission.add(videoShopOrder1.getOrderPrice().multiply(BigDecimal.valueOf(Long.parseLong(videoShopSalarytemplateResult.getRatioAnchorDefault()) * 0.01)));
                                        }
                                    }
                                }
                            }
                            //默认+特定商品比率
                            else if (videoShopSalarytemplateResult.getType().equals(4l)) {
                                Iterator<HashMap<String, String>> listMapIterator = listMap.iterator();
                                if (!listMapIterator.hasNext()) {
                                    anchorCommission = anchorCommission.add(videoShopOrder1.getOrderPrice().multiply(BigDecimal.valueOf(Double.parseDouble(videoShopSalarytemplateResult.getBasicRadioAnchorDefault()) * 0.01)));
                                } else {
                                    while (listMapIterator.hasNext()) {
                                        HashMap<String, String> listMapHashMap = listMapIterator.next();
                                        boolean containsValue = listMapHashMap.containsValue(videoShopOrder1.getProductId());
                                        sceneLiveCount++;
                                        sceneLiveSum = sceneLiveSum.add(videoShopOrder1.getOrderPrice());
                                        if (containsValue) {
                                            String bilu = (String) listMapHashMap.get("key3");
                                            anchorCommission = anchorCommission.add(videoShopOrder1.getOrderPrice().multiply(BigDecimal.valueOf(Long.parseLong(bilu) * 0.01)));
                                        } else {
                                            anchorCommission = anchorCommission.add(videoShopOrder1.getOrderPrice().multiply(BigDecimal.valueOf(Double.parseDouble(videoShopSalarytemplateResult.getBasicRadioAnchorDefault()) * 0.01)));
                                        }

                                    }
                                }
                            }
                        }
                    } else if (videoShopOrder1.getOrderScene() == 3) {
                        sceneVideoCount++;
                        sceneVideoSum = sceneVideoSum.add(videoShopOrder1.getOrderPrice());
                    } else {
                        sceneShopCount++;
                        sceneShopSum = sceneShopSum.add(videoShopOrder1.getOrderPrice());
                    }


                }


                videoShopScheduling.setSceneLivecount(Long.valueOf(sceneLiveCount));
                videoShopScheduling.setSceneVideocount(Long.valueOf(sceneVideoCount));
                videoShopScheduling.setSceneShopcount(Long.valueOf(sceneShopCount));

                videoShopScheduling.setSceneLivesum(sceneLiveSum);
                videoShopScheduling.setSceneVideosum(sceneVideoSum);
                videoShopScheduling.setSceneShopsum(sceneShopSum);
                videoShopScheduling.setAnchorCommission(anchorCommission);
            }
            //默认+特定商品比率
            if (videoShopSalarytemplateResult.getType().equals(4l)) {
                BigDecimal i1 = new BigDecimal(String.valueOf(resultList.get(i).get("refund_amount")));
                i1 = i1.multiply(BigDecimal.valueOf(Double.parseDouble(videoShopSalarytemplateResult.getBasicRadioAnchorDefault()) * 0.01));
                System.out.println("比率" + videoShopSalarytemplateResult.getBasicRadioAnchorDefault());
                System.out.println(i1);
                videoShopScheduling.setAnchorCommission(anchorCommission.subtract(i1));
            }
            videoShopSchedulingMapper.updateVideoShopScheduling(videoShopScheduling);
        }
    }

    @Override
    public void syncOrder() {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);

        try {
            getOrderList(videoShop);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void syncAfterSaleOrder() {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);

        try {
            getAfterSaleOrderList(videoShop);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getAfterSaleOrderList(VideoShop videoShop) throws IOException {
        for (int o = 3; o >= 0; o--) {
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
    }

    public void getAfterSaleOrderDetail(VideoShop videoShop, String after_sale_order_id) {
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

                    VideoShopAfterSaleOrder videoShopAfterSaleOrderOld = videoShopAfterSaleOrderMapper.selectVideoShopAfterSaleOrderByAfterSaleOrderId_UP(String.valueOf(videoShop.getTableIndex()), String.valueOf(videoShop.getId()), afterSaleOrderId);
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
    }

    public void getBeforeOrder(VideoShop videoShop, Calendar calendar, int i) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String resData = null;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("page_size", 10);

        JSONObject jsonObject2 = new JSONObject();

        long end_time = calendar.getTimeInMillis() / 1000;
        calendar.add(Calendar.DAY_OF_MONTH, -5);
        long start_time = calendar.getTimeInMillis() / 1000;

        jsonObject2.put("start_time", start_time);
        jsonObject2.put("end_time", end_time);

        jsonObject.put("create_time_range", jsonObject2);

        HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/channels/ec/order/list/get?access_token=" + videoShop.getAccessToken());
        StringEntity stringEntity = null;
//                try {
        stringEntity = new StringEntity(jsonObject.toString());

        stringEntity.setContentType("text/json");
        stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse response = null;

        response = httpClient.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();
        System.out.println("响应状态为:" + response.getStatusLine());

        if (responseEntity != null) {
            System.out.println("响应内容长度为:" + responseEntity.getContentLength());
            resData = EntityUtils.toString(response.getEntity());
            JSONObject obj = JSONObject.parseObject(resData);
            JSONArray orderArray = (JSONArray) obj.get("order_id_list");

            if (orderArray == null) {
                return;
            }
            Iterator<Object> iterator = orderArray.iterator();
            while (iterator.hasNext()) {
                //订单列表处理
                getOrderDetail(videoShop, (String) iterator.next());
            }
            boolean has_more = (boolean) obj.get("has_more");
            if (has_more) {
                String nextKey = (String) obj.get("next_key");
                nextPageData(videoShop, nextKey, start_time, end_time);
            }
            System.out.println(obj.toString());
            httpClient.close();
            response.close();
        }
    }

    public void getOrderList(VideoShop videoShop) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String resData = null;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("page_size", 10);

        JSONObject jsonObject2 = new JSONObject();


        //获取7天时间戳
        long end_time = System.currentTimeMillis() / 1000; // 获取当前时间戳，单位是毫秒，除以1000即可得到10位时间戳
        Calendar calendar = Calendar.getInstance();
        if (videoShop.getIsOrderStatus1Sync() == 0) {
            for (int i = 0; i < 6; i++) {
                getBeforeOrder(videoShop, calendar, i);
            }
        } else {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            getBeforeOrder(videoShop, calendar, 1);
        }
        long start_time = calendar.getTimeInMillis() / 1000;

//        jsonObject2.put("start_time", start_time);
//        jsonObject2.put("end_time", end_time);
//
//        jsonObject.put("create_time_range", jsonObject2);
//        HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/channels/ec/order/list/get?access_token=" + videoShop.getAccessToken());
//        StringEntity stringEntity = null;
////                try {
//        stringEntity = new StringEntity(jsonObject.toString());
//
//        stringEntity.setContentType("text/json");
//        stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//        httpPost.setEntity(stringEntity);
//        CloseableHttpResponse response = null;
//
//        response = httpClient.execute(httpPost);
//        HttpEntity responseEntity = response.getEntity();
//        System.out.println("响应状态为:" + response.getStatusLine());
//
//        if (responseEntity != null) {
//            System.out.println("响应内容长度为:" + responseEntity.getContentLength());
//            resData = EntityUtils.toString(response.getEntity());
//            JSONObject obj = JSONObject.parseObject(resData);
//            JSONArray orderArray = (JSONArray) obj.get("order_id_list");
//
//            if (orderArray == null) {
//                return;
//            }
//            Iterator<Object> iterator = orderArray.iterator();
//            while (iterator.hasNext()) {
//                //订单列表处理
//                getOrderDetail(videoShop, (String) iterator.next());
//            }
//            boolean has_more = (boolean) obj.get("has_more");
//            if (has_more) {
//                String nextKey = (String) obj.get("next_key");
//                nextPageData(videoShop, nextKey, start_time, end_time);
//            }
//            System.out.println(obj.toString());
//            httpClient.close();
//            response.close();
        videoShop.setIsOrderStatus1Sync(1);
        videoShopMapper.updateVideoShop(videoShop);

    }

    //获取订单详情
    public void getOrderDetail(VideoShop videoShop, String orderId) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String resData = null;
        try {
            // 核心定时器，每一个小时执行一次
            Long userId = 1L;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("order_id", orderId);

            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/channels/ec/order/get?access_token=" + videoShop.getAccessToken());
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
                    videoShopOrder.getParams().put("tableIndex", videoShop.getTableIndex());

                    videoShopOrder.setShopId(videoShop.getId());
                    videoShopOrder.setOrderId((String) orderDetail.get("order_id"));
                    videoShopOrder.setOpenid((String) orderDetail.get("openid"));
                    videoShopOrder.setStatus((Integer) orderDetail.get("status"));
                    videoShopOrder.setCreateTime(formatDate((Integer) orderDetail.get("create_time")));
                    videoShopOrder.setUpdateTime(formatDate((Integer) orderDetail.get("update_time")));

                    JSONObject orderDetailObject = (JSONObject) orderDetail.get("order_detail");
                    JSONObject productInfosObject = (JSONObject) ((JSONArray) orderDetailObject.get("product_infos")).get(0);
                    videoShopOrder.setProductId((String) productInfosObject.get("product_id"));
                    videoShopOrder.setTitle((String) productInfosObject.get("title"));
                    videoShopOrder.setThumbImg((String) productInfosObject.get("thumb_img"));
                    videoShopOrder.setSkuId((String) productInfosObject.get("sku_id"));
                    JSONArray skuAttrsArray = (JSONArray) productInfosObject.get("sku_attrs");
                    videoShopOrder.setSkuAttrs(skuAttrsArray.toString());
                    videoShopOrder.setSkuCnt((Integer) productInfosObject.get("sku_cnt"));
                    videoShopOrder.setProductCnt(Long.valueOf((Integer) productInfosObject.get("sku_cnt")));

                    JSONObject priceInfoObject = (JSONObject) (orderDetailObject.get("price_info"));
                    videoShopOrder.setProductPrice(BigDecimal.valueOf((Integer) priceInfoObject.get("product_price") / 100));
                    videoShopOrder.setOrderPrice(BigDecimal.valueOf((Integer) priceInfoObject.get("order_price") / 100));
                    videoShopOrder.setFreight(BigDecimal.valueOf((Integer) priceInfoObject.get("freight") / 100));

                    JSONObject extInfoObject = (JSONObject) (orderDetailObject.get("ext_info"));
                    videoShopOrder.setCustomerNotes((String) extInfoObject.get("customer_notes"));
                    videoShopOrder.setMerchantNotes((String) extInfoObject.get("merchant_notes"));
                    if (extInfoObject.get("confirm_receipt_time") != null) {
                        videoShopOrder.setConfirmReceiptTime(formatDate((Integer) extInfoObject.get("confirm_receipt_time")));
                    }
                    videoShopOrder.setFinderId((String) extInfoObject.get("finder_id"));
                    videoShopOrder.setLiveId((String) extInfoObject.get("live_id"));
                    videoShopOrder.setOrderScene((Integer) extInfoObject.get("order_scene"));

                    String finderId = (String) extInfoObject.get("finder_id");
                    //处理分佣金信息
                    JSONArray commissionInfosObject = (JSONArray) (orderDetailObject.get("commission_infos"));
                    if (commissionInfosObject != null) {
                        for (int i = 0; i < commissionInfosObject.size(); i++) {
                            JSONObject commissionInfosJsonObject = commissionInfosObject.getJSONObject(i);
                            // 处理jsonObject
                            System.out.println(commissionInfosJsonObject);
                            if (commissionInfosJsonObject.get("type").toString().equals("0")) {
                                videoShopOrder.setCommissionFinderId(commissionInfosJsonObject.get("finder_id").toString());
                                videoShopOrder.setCommissionNickname(commissionInfosJsonObject.get("nickname").toString());
                                videoShopOrder.setCommissionType(Integer.valueOf(commissionInfosJsonObject.get("type").toString()));
                            }
                        }
                    }
                    JSONArray sourceInfosObject = (JSONArray) (orderDetailObject.get("source_infos"));
                    if (sourceInfosObject != null) {
                        //处理分佣金信息
                        for (int i = 0; i < sourceInfosObject.size(); i++) {
                            JSONObject sourceInfosJsonObject = sourceInfosObject.getJSONObject(i);
                            // 处理jsonObject
                            System.out.println("分佣信息：" + sourceInfosJsonObject);
                            if (sourceInfosJsonObject.get("account_id").toString().equals(finderId)) {
                                videoShopOrder.setCommissionNickname(sourceInfosJsonObject.get("account_nickname").toString());
                            }
                        }
                    }
                    //处理加密信息
//                    videoShopOrder = getSensitiveInfo(videoShop,videoShopOrder,orderId);
                    VideoShopOrder videoShopOrderOld = videoShopOrderMapper.selectVideoShopOrderByOrderId_UP(String.valueOf(videoShop.getTableIndex()), (String) orderDetail.get("order_id"));
                    if (videoShopOrderOld == null) {
                        System.out.println("登录新增订单：" + videoShopOrder.getOrderId());
                        videoShopOrderMapper.insertVideoShopOrder_UP(videoShopOrder);
                    } else {
                        System.out.println("登录更新订单：" + videoShopOrder.getOrderId());
                        videoShopOrderMapper.updateVideoShopOrder_UP(videoShopOrder);
                    }


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

    public void nextPageData(VideoShop videoShop, String nextKey, long startTime, long endTime) {
        Map<String, String> reMap;
        String resData = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("page_size", 10);
            jsonObject.put("next_key", nextKey);
            JSONObject jsonObject2 = new JSONObject();


            jsonObject2.put("start_time", startTime);
            jsonObject2.put("end_time", endTime);

            jsonObject.put("create_time_range", jsonObject2);
            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/channels/ec/order/list/get?access_token=" + videoShop.getAccessToken());
            StringEntity stringEntity = null;
//                try {
            stringEntity = new StringEntity(jsonObject.toString());
//                } catch (UnsupportedEncodingException e) {
//                    throw new RuntimeException(e);
//                }
            stringEntity.setContentType("text/json");
            stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpPost.setEntity(stringEntity);
            CloseableHttpResponse response = null;

            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());

            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                resData = EntityUtils.toString(response.getEntity());
                JSONObject obj = JSONObject.parseObject(resData);
                JSONArray orderArray = (JSONArray) obj.get("order_id_list");

                Iterator<Object> iterator = orderArray.iterator();
                while (iterator.hasNext()) {
                    //订单列表处理
                    getOrderDetail(videoShop, (String) iterator.next());
                }
                boolean has_more = (boolean) obj.get("has_more");
                if (has_more) {
                    nextKey = (String) obj.get("next_key");
                    nextPageData(videoShop, nextKey, startTime, endTime);
                }
                System.out.println(obj.toString());
                httpClient.close();
                response.close();
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
