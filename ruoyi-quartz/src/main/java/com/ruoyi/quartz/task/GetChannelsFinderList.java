package com.ruoyi.quartz.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.VideoShop;
import com.ruoyi.system.domain.VideoShopAfterSaleOrder;
import com.ruoyi.system.domain.VideoShopChannelsFinder;
import com.ruoyi.system.domain.VideoShopOrder;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.mapper.VideoShopAfterSaleOrderMapper;
import com.ruoyi.system.mapper.VideoShopChannelsFinderMapper;
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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 获取带货达人列表数据。（仅返回当日有带货数据的达人）
 */
@Component("GetChannelsFinderList")
public class GetChannelsFinderList {
    private static final Logger log = LoggerFactory.getLogger(GetComponentAccessToken.class);
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private VideoShopMapper videoShopMapper;
    @Autowired
    private VideoShopChannelsFinderMapper videoShopChannelsFinderMapper;

    /*
     * 定时获取刷新ComponentAccessToken*/
    public void ryMultipleParams() {
        log.debug("====================获取带货达人列表数据【GetChannelsFinderList】====================");
        Map<String, String> reMap;
        String resData = null;
        try {
            // 核心定时器，每一个小时执行一次
            Long userId = 1L;
            SysUser sysUser = userMapper.selectUserById(userId);
            List<VideoShop> videoShopNoExpireList = videoShopMapper.selectVideoShopListNoExpire();
            for (int i = 0; i < videoShopNoExpireList.size(); i++) {
                VideoShop videoShop = videoShopNoExpireList.get(i);

//                获取当前日期及30天内数据
                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                String formattedDate = currentDate.format(formatter);
                for (int l = 0; l <= 30; l++) {
                    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                    LocalDate pastDate = currentDate.minusDays(l);
                    String formattedDate2 = pastDate.format(formatter);
                    System.out.println("格式化日期2222：" + formattedDate2);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("ds", formattedDate2);

                    //访问借口获取数据
                    HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/channels/ec/compass/shop/finder/list/get?access_token=" + videoShop.getAccessToken());
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
                            JSONArray orderArray = (JSONArray) obj.get("finder_list");
                            if(orderArray==null){
                                continue;
                            }
                            Iterator<Object> iterator = orderArray.iterator();
                            while (iterator.hasNext()) {
                                JSONObject finderDetail = (JSONObject) iterator.next();
                                VideoShopChannelsFinder videoShopChannelsFinder = new VideoShopChannelsFinder();
                                videoShopChannelsFinder.setLocalShopId(videoShop.getId());
                                videoShopChannelsFinder.setFinderId((String) finderDetail.get("finder_id"));
                                videoShopChannelsFinder.setNickname((String) finderDetail.get("finder_nickname"));

                                JSONObject finderData = (JSONObject) finderDetail.get("data");
                                videoShopChannelsFinder.setPayGmv(new BigDecimal((String)finderData.get("pay_gmv")));
                                videoShopChannelsFinder.setPayProductIdCnt(Long.valueOf((String)finderData.get("pay_gmv")));
                                videoShopChannelsFinder.setPayUv(Long.valueOf((String)finderData.get("pay_gmv")));
                                videoShopChannelsFinder.setRefundGmv(new BigDecimal((String)finderData.get("pay_gmv")));
                                videoShopChannelsFinder.setPayRefundGmv(new BigDecimal((String)finderData.get("pay_gmv")));

                                ZoneId zone = ZoneId.systemDefault();
                                Instant instant = pastDate.atStartOfDay().atZone(zone).toInstant();
                                Date countDate = Date.from(instant);


                                videoShopChannelsFinder.setCountDate(countDate);
                                videoShopChannelsFinderMapper.deleteVideoShopChannelsFinderByFinderIdAndCountTime(videoShopChannelsFinder);
                                videoShopChannelsFinderMapper.insertVideoShopChannelsFinder(videoShopChannelsFinder);


                            }
                            System.out.println(obj.toString());
                        }
                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                    } finally {
                        // 释放资源
                        httpClient.close();
                        response.close();
                    }
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug("====================获取带货达人列表数据【GetChannelsFinderList】====================");

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
