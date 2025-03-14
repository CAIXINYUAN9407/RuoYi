package com.ruoyi.system.service.impl;

import cn.hutool.core.util.XmlUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.Util.AesException;
import com.ruoyi.system.Util.MessageUtil;
import com.ruoyi.system.Util.WXBizMsgCrypt;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.domain.VideoShop;
import com.ruoyi.system.domain.VideoShopLiveRoom;
import com.ruoyi.system.domain.VideoShopOrder;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.VXAuthorizationService;
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
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户 业务层处理
 *
 * @author ruoyi
 */
@Service
public class VXAuthorizationServiceImpl implements VXAuthorizationService {
    private static final Logger log = LoggerFactory.getLogger(VXAuthorizationServiceImpl.class);

    public static final Map<String, String> COMPONENT_VETIFY_TICKET_MAP = new HashMap<>();


    @Autowired
    private SysUserMapper userMapper;
    //    SysUserRoleMapper
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private VideoShopMapper videoShopMapper;
    @Autowired
    private VideoShopOrderMapper videoShopOrderMapper;
    @Autowired
    private VideoShopLiveRoomMapper videoShopLiveRoomMapper;


    /**
     * @param
     * @return
     */
    @Override
    public String getComponentVerifyTicket(HttpServletRequest request, HttpServletResponse response) {
        log.info("接收微信服务器发送的Ticket");
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            // 微信加密签名
            String signature = request.getParameter("msg_signature");
            // 时间戳
            String timestamp = request.getParameter("timestamp");
            // 随机数
            String nonce = request.getParameter("nonce");
            // 从请求中读取整个post数据 这个工具类在下文写了
            Map<String, Object> xmlMap = MessageUtil.xmlToMap(request);
            log.info("加密签名msg_signature:{} 时间戳timestamp:{} 随机数nonce:{}", signature, timestamp, nonce);
            log.info("从request中获取xml信息：{}", xmlMap);
            //解密处理
            String encrypt = (String) xmlMap.get("Encrypt");
            log.info("Encrypt:{}", encrypt);
            String msg = decryptMsg(timestamp, nonce, signature, encrypt);
            //将XML格式字符串转为Map类型 使用的是hutool工具包，记得引入一下
            Map<String, Object> msgMap = XmlUtil.xmlToMap(msg);
            log.info("msg" + msgMap);
            String infoType = msgMap.get("InfoType").toString();
            log.info("类型" + infoType);
            log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            switch (infoType) {
                //验证票据
                case "authorized":
                    log.info("_______________验证——————————————————————————————");
                    String AuthorizerAppid = msgMap.get("AuthorizerAppid").toString();
                    log.info("AuthorizerAppid" + AuthorizerAppid);
                    SysUser sysUserNew = new SysUser();
                    SysUser sysUserOld = userMapper.selectUserByLoginName(AuthorizerAppid);
                    if (sysUserOld != null) {
                        String refershToken =  GetRefreshToken(AuthorizerAppid);
                        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(AuthorizerAppid);
                        videoShop.setRefreshToken(refershToken);
                        videoShopMapper.updateVideoShop(videoShop);
                        break;
                    }
                    sysUserNew.setLoginName(AuthorizerAppid);
                    sysUserNew.setSalt(ShiroUtils.randomSalt());
                    sysUserNew.setPassword(encryptPassword(sysUserNew.getLoginName(), sysUserNew.getLoginName(), sysUserNew.getSalt()));
                    userMapper.insertUser(sysUserNew);
                    Integer table_index = 0;
                    log.info("表索引01——————————————————————————————————" + table_index);
                    HashMap tableIndexMap = videoShopMapper.selectTableIndex();
                    if (tableIndexMap != null) {
                        table_index = (Integer) tableIndexMap.get("table_index");
                        log.info("表索引1——————————————————————————————————" + tableIndexMap.get("table_index").toString());
                    } else {
                        log.info("表索引2——————————————————————————————————" + table_index);
                        table_index = videoShopMapper.selectMaxTableIndex() + 1;
                        videoShopMapper.createShopGoods("video_shop_goods_" + table_index);
                        videoShopMapper.createShopOrder("video_shop_order_" + table_index);
                        videoShopMapper.createShopAfterSaleOrder("video_shop_after_sale_order_" + table_index);
                        videoShopMapper.createShopAnchor("video_shop_anchor_" + table_index);
                        videoShopMapper.createShopSalaryTemplate("video_shop_salarytemplate_" + table_index);
                        videoShopMapper.createShopScheduling("video_shop_scheduling_" + table_index);
                        videoShopMapper.createShopLiveRoom("video_shop_live_room_" + table_index);
                    }
                    // 新增用户与角色管理
                    List<SysUserRole> list = new ArrayList<SysUserRole>();
                    SysUserRole ur = new SysUserRole();
                    ur.setUserId(sysUserNew.getUserId());
                    ur.setRoleId(100L);
                    list.add(ur);
                    sysUserRoleMapper.batchUserRole(list);
                    String refreshToken = GetRefreshToken(AuthorizerAppid);
                    VideoShop videoShopOld = videoShopMapper.selectVideoShopByOwner(AuthorizerAppid);
                    if (videoShopOld == null) {
                        VideoShop videoShop = new VideoShop();
                        videoShop.setRefreshToken(refreshToken);
                        videoShop.setOwner(AuthorizerAppid);
                        videoShop.setLocalShopId(2);
                        videoShop.setTableIndex(table_index);
                        videoShop = getAuthorizerAccessToken(videoShop);


                        videoShopMapper.insertVideoShop(videoShop);
                        getBasicsInfo(videoShop);

                        //新建直播间
//                        System.out.println("账号新建直播间");
//                        VideoShopLiveRoom videoShopLiveRoom = new VideoShopLiveRoom();
//                        videoShopLiveRoom.getParams().put("tableIndex", videoShop.getTableIndex());
//                        videoShopLiveRoom.setShopId(videoShop.getId());
//                        videoShopLiveRoom.setIsMain(1);
//
//                        videoShopLiveRoom.setRoomName(videoShop.getShopName()+"直播号");
//                        videoShopLiveRoomMapper.insertVideoShopLiveRoom(videoShopLiveRoom);
                        //首次获取订单列表
//                        getOrderList(videoShop);
                    } else {
                        String refershToken =  GetRefreshToken(AuthorizerAppid);
                        CloseableHttpClient httpClient2 = HttpClientBuilder.create().build();
                        JSONObject jsonObject2 = new JSONObject();
                        HttpGet httpGet2 = new HttpGet("https://api.weixin.qq.com/channels/ec/basics/info/get?access_token=" + refershToken);
                        StringEntity stringEntity2 = new StringEntity(jsonObject2.toString());
                        stringEntity2.setContentType("text/json");
                        stringEntity2.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

                        CloseableHttpResponse response2 = null;

                        // 由客户端执行(发送)Post请求
                        response2 = httpClient2.execute(httpGet2);
                        // 从响应模型中获取响应实体
                        HttpEntity responseEntity2 = response2.getEntity();

                        System.out.println("响应状态为:" + response2.getStatusLine());

                        if (responseEntity2 != null) {
                            String resData2 = null;

                            System.out.println("响应内容长度为:" + responseEntity2.getContentLength());
                            resData2 = EntityUtils.toString(response2.getEntity());
                            JSONObject obj = JSONObject.parseObject(resData2);
                            System.out.println(obj.toString());
                            JSONObject orderDetail = (JSONObject) obj.get("info");
                            SysUser shopUser = userMapper.selectUserByLoginName(AuthorizerAppid);
                            shopUser.setUserName((String) orderDetail.get("nickname"));
                            userMapper.updateUser(shopUser);

                            VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(AuthorizerAppid);
                            videoShop.setShopName((String) orderDetail.get("nickname"));
                            videoShopMapper.updateVideoShop(videoShop);
                        }

                    }
                    //获取AccessToken




                    break;
                case "component_verify_ticket":
                    //查询库中的第三方信息，并且准备存储ticket
                    String componentVerifyTicket = msgMap.get("ComponentVerifyTicket").toString();
                    log.info("用户授权component_verify_ticket:{}", componentVerifyTicket);
                    COMPONENT_VETIFY_TICKET_MAP.put("wxdc5787bd0edbfc75", componentVerifyTicket);
                    Long userId = 1L;
                    SysUser sysUser = userMapper.selectUserById(userId);
                    log.info("sysUser----------------------getLoginName", sysUser.getLoginName());
                    sysUser.setComponentVerifyTicket(componentVerifyTicket.substring(9));
                    userMapper.updateUser(sysUser);
//                    redisTemplate.opsForValue().set(VXConstants.COMPONENT_VERIFY_TICKET, componentVerifyTicket);
//                    redisTemplate.expire(VXConstants.COMPONENT_VERIFY_TICKET, 2, TimeUnit.HOURS);
                    break;
                case "unauthorized"://用户取消授权
                    log.info("用户取消授权");
                    break;
            }
        } catch (Exception e) {
            log.error("获取Ticket失败：", e);
        }
        return "success";
    }

    //获取授权令牌
    private VideoShop getAuthorizerAccessToken(VideoShop videoShop) throws IOException {
        String resData = null;

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        Long userId = 1L;
        SysUser sysUser = userMapper.selectUserById(userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("component_appid", "wxdc5787bd0edbfc75");
        jsonObject.put("authorizer_appid", videoShop.getOwner());
        jsonObject.put("authorizer_refresh_token", videoShop.getRefreshToken());
        HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/component/api_authorizer_token?component_access_token=" + sysUser.getComponentAccessToken());
        StringEntity stringEntity = new StringEntity(jsonObject.toString());
        stringEntity.setContentType("text/json");
        stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        httpPost.setEntity(stringEntity);

        CloseableHttpResponse response = null;

        response = httpClient.execute(httpPost);
        // 从响应模型中获取响应实体
        HttpEntity responseEntity = response.getEntity();

        System.out.println("响应状态为:" + response.getStatusLine());
        if (responseEntity != null) {
            System.out.println("响应内容长度为:" + responseEntity.getContentLength());
            resData = EntityUtils.toString(response.getEntity());
            JSONObject obj = JSONObject.parseObject(resData);
            System.out.println(obj.toString());
            if (obj.get("errcode") != null) {
                return videoShop;
            }
//                    HashMap<String, String> hashMap = JSON.parseObject(resData, HashMap.class);


            //获取主账号finderId
            videoShop.setAccessToken(obj.get("authorizer_access_token").toString());
//            String finderId = getFinderId(obj.get("authorizer_access_token").toString());
//            videoShop.setMainFinderId(finderId);
        }
        return videoShop;
    }

    //获取商家信息
    private void getBasicsInfo(VideoShop videoShop) throws IOException {
        String resData = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        JSONObject jsonObject = new JSONObject();
        HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/channels/ec/basics/info/get?access_token=" + videoShop.getAccessToken());
        StringEntity stringEntity = new StringEntity(jsonObject.toString());
        stringEntity.setContentType("text/json");
        stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

        CloseableHttpResponse response = null;
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
            JSONObject orderDetail = (JSONObject) obj.get("info");
            videoShop.setShopName((String) orderDetail.get("nickname"));
//                    HashMap<String, String> hashMap = JSON.parseObject(resData, HashMap.class);
//                    videoShop.setAccessToken(obj.get("authorizer_access_token").toString());
            JSONObject objN = (JSONObject) obj.get("info");
            SysUser shopUser = userMapper.selectUserByLoginName(videoShop.getOwner());
            shopUser.setUserName((String) orderDetail.get("nickname"));
            userMapper.updateUser(shopUser);
            videoShopMapper.updateVideoShop(videoShop);

            //新建直播间
            VideoShopLiveRoom videoShopLiveRoom = new VideoShopLiveRoom();
            videoShopLiveRoom.getParams().put("tableIndex", videoShop.getTableIndex());
            videoShopLiveRoom.setShopId(videoShop.getId());
            videoShopLiveRoom.setIsMain(1);

            videoShopLiveRoom.setRoomName(videoShop.getShopName()+"直播间");
            videoShopLiveRoomMapper.insertVideoShopLiveRoom(videoShopLiveRoom);
        }
    }

    //获取订单列表
    @Async
    public void getOrderList(VideoShop videoShop) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String resData = null;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("page_size", 10);

        JSONObject jsonObject2 = new JSONObject();


        //获取7天时间戳
        long end_time = System.currentTimeMillis() / 1000; // 获取当前时间戳，单位是毫秒，除以1000即可得到10位时间戳
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
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

    // 加密操作
    public String encryptMsg(String encodingAesKey, String token, String timestamp, String nonce, String
            appId, String replyMsg) {
        log.info("加密前：{}", replyMsg);
        try {
            WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
            String ciphertext = pc.encryptMsg(replyMsg, timestamp, nonce);
            log.info("加密后：{}", ciphertext);
            return ciphertext;
        } catch (AesException e) {
            throw new RuntimeException(e);
        }
    }

    //获取订单详情
    public void getOrderDetail(VideoShop videoShop, String orderId) {
        log.debug("====================获取订单详情【GetOrderDetail】====================");
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

                    //处理加密信息
//                    videoShopOrder = getSensitiveInfo(videoShop,videoShopOrder,orderId);
                    VideoShopOrder videoShopOrderOld = videoShopOrderMapper.selectVideoShopOrderByOrderId((String) orderDetail.get("order_id"));
                    if (videoShopOrderOld == null) {
                        videoShopOrderMapper.insertVideoShopOrder(videoShopOrder);
                    } else {
                        videoShopOrderMapper.updateVideoShopOrder(videoShopOrder);
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
        log.debug("====================获取订单详情【GetOrderDetail】====================");
    }

    public void nextPageData(VideoShop videoShop, String nextKey, long startTime, long endTime) {
        log.debug("====================获取订单列表【GetOrderList】====================");
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
        log.debug("====================获取订单列表【GetOrderList】====================");

    }

    public Date formatDate(Integer timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timestamp * 1000L); // 将秒转换为毫秒
        String formattedDate = sdf.format(date);
        System.out.println("Date: " + formattedDate);
//        DateTime dateTime = DateTime.of(DateTime.parse(formattedDate));
        return date;
    }

    //解密操作
    public String decryptMsg(String timestamp, String nonce, String signature, String encrypt) {
        log.info("进行解密操作:{} {} {} {}", timestamp, nonce, signature, encrypt);
        try {
            log.info("token:{} encodingAesKey:{} componentAppId:{}", "token", "1111111111111111111111111111111111111111112", "wxdc5787bd0edbfc75");
            WXBizMsgCrypt pc = new WXBizMsgCrypt("12580", "1111111111111111111111111111111111111111112", "wxdc5787bd0edbfc75");
            String format = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
            String fromXML = String.format(format, encrypt);
            log.info("fromXML:{}", fromXML);
            // 第三方收到公众号平台发送的消息
            String plaintext = pc.decryptMsg(signature, timestamp, nonce, fromXML);
            log.info("解密后明文: {}", plaintext);
            return plaintext;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String encryptPassword(String loginName, String password, String salt) {
        return new Md5Hash(loginName + password + salt).toHex();
    }

    public String GetRefreshToken(String AuthorizerAppid) {
        log.debug("====================拉取已授权的帐号信息【GetAuthorizerList】====================");
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String resData = null;
        try {
            // 核心定时器，每一个小时执行一次
            Long userId = 1L;
            SysUser sysUser = userMapper.selectUserById(userId);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("component_appid", "wxdc5787bd0edbfc75");
            jsonObject.put("offset", 0);
            jsonObject.put("count", 500);
            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_list?access_token=" + sysUser.getComponentAccessToken());
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
                System.out.println("VXAuthorServiceImpl的GetRefreshToken方法响应状态为:" + response.getStatusLine());
                if (responseEntity != null) {
                    System.out.println("VXAuthorServiceImpl的GetRefreshToken方法响应内容长度为:" + responseEntity.getContentLength());
                    resData = EntityUtils.toString(response.getEntity());
                    JSONObject obj = JSONObject.parseObject(resData);
                    System.out.println("VXAuthorServiceImpl的GetRefreshToken方法"+obj.toString());
                    HashMap<String, String> hashMap = JSON.parseObject(resData, HashMap.class);
                    JSONArray result = obj.getJSONArray("list");

                    for (Object objN : result) {
                        JSONObject objM = (JSONObject) objN;
                        System.out.println(objM.get("authorizer_appid").toString());
                        if (objM.get("authorizer_appid").toString().equals(AuthorizerAppid)) {
                            return objM.get("refresh_token").toString();
                        } else {
                            return "0";
                        }
                    }
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (Exception e) {
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
        return "1";
    }

}
