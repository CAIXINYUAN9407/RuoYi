package com.ruoyi.system.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.domain.VideoShop;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.VideoShopOrderMapper;
import com.ruoyi.system.domain.VideoShopOrder;
import com.ruoyi.system.service.IVideoShopOrderService;
import com.ruoyi.common.core.text.Convert;

import static com.ruoyi.common.utils.PageUtils.startPage;

/**
 * 订单表Service业务层处理
 *
 * @author CaiXY
 * @date 2024-06-21
 */
@Service
public class VideoShopOrderServiceImpl implements IVideoShopOrderService {
    @Autowired
    private VideoShopOrderMapper videoShopOrderMapper;
    @Autowired
    private VideoShopMapper videoShopMapper;


    /**
     * 查询订单表
     *
     * @param id 订单表主键
     * @return 订单表
     */
    @Override
    public VideoShopOrder selectVideoShopOrderById(Long id) {
        return videoShopOrderMapper.selectVideoShopOrderById(id);
    }

    /**
     * 查询订单表列表
     *
     * @param videoShopOrder 订单表
     * @return 订单表
     */
    @Override
    public List<VideoShopOrder> selectVideoShopOrderList(VideoShopOrder videoShopOrder) {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);
        videoShopOrder.setShopId(videoShop.getId());
        videoShopOrder.getParams().put("tableIndex", videoShop.getTableIndex());
        startPage();
        List<VideoShopOrder> videoShopOrderList = videoShopOrderMapper.selectVideoShopOrderList(videoShopOrder);
        return (videoShopOrderList);
    }

    /**
     * 查询订单表列表
     *
     * @param videoShopOrder 订单表
     * @return 订单表
     */
    @Override
    public List<VideoShopOrder> selectVideoShopOrderListExport(VideoShopOrder videoShopOrder) {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);
        videoShopOrder.setShopId(videoShop.getId());
        videoShopOrder.getParams().put("tableIndex", videoShop.getTableIndex());
        List<VideoShopOrder> videoShopOrderList = videoShopOrderMapper.selectVideoShopOrderList(videoShopOrder);
        return (videoShopOrderList);
    }

    /**
     * 新增订单表
     *
     * @param videoShopOrder 订单表
     * @return 结果
     */
    @Override
    public int insertVideoShopOrder(VideoShopOrder videoShopOrder) {
        return videoShopOrderMapper.insertVideoShopOrder(videoShopOrder);
    }

    /**
     * 修改订单表
     *
     * @param videoShopOrder 订单表
     * @return 结果
     */
    @Override
    public int updateVideoShopOrder(VideoShopOrder videoShopOrder) {
        return videoShopOrderMapper.updateVideoShopOrder(videoShopOrder);
    }

    /**
     * 批量删除订单表
     *
     * @param ids 需要删除的订单表主键
     * @return 结果
     */
    @Override
    public int deleteVideoShopOrderByIds(String ids) {
        return videoShopOrderMapper.deleteVideoShopOrderByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除订单表信息
     *
     * @param id 订单表主键
     * @return 结果
     */
    @Override
    public int deleteVideoShopOrderById(Long id) {
        return videoShopOrderMapper.deleteVideoShopOrderById(id);
    }

    @Override
    public JSONObject decryptTel(String orderId, String localShopId) {
        VideoShopOrder videoShopOrder = videoShopOrderMapper.selectVideoShopOrderByOrderId(orderId);
        Integer expireTime = 0;
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String resData = null;
        //获取虚拟号码过期时间
        if (videoShopOrder.getVirtualTelExpireTime() != null) {
            expireTime = videoShopOrder.getVirtualTelExpireTime();
        }
        //是否超过三次解密次数
//        if (expireTime < System.currentTimeMillis() && videoShopOrder.getGetVirtualTelCnt() == 3) {
//            return 0;
//        }
        //解密
//        if (videoShopOrder.getVirtualTelNumber() == "" || (videoShopOrder.getGetVirtualTelCnt() < System.currentTimeMillis())) {
            VideoShop videoShop = videoShopMapper.selectVideoShopById(Long.valueOf(localShopId));
            JSONObject jsonObject = new JSONObject();
            JSONObject address_info = new JSONObject();
            jsonObject.put("order_id", orderId);

            //虚拟号订单
//            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/channels/ec/order/virtualtelnumber/get?access_token="+videoShop.getAccessToken());
            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/channels/ec/order/sensitiveinfo/decode?access_token=" + videoShop.getAccessToken());
            StringEntity stringEntity = null;
            try {
                stringEntity = new StringEntity(jsonObject.toString());

                stringEntity.setContentType("text/json");
                stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                httpPost.setEntity(stringEntity);
                try {
                    response = httpClient.execute(httpPost);
                    // 从响应模型中获取响应实体
                    HttpEntity responseEntity = response.getEntity();
                    System.out.println("响应状态为:" + response.getStatusLine());
                    if (responseEntity != null) {
                        resData = EntityUtils.toString(response.getEntity());
                        JSONObject obj = JSONObject.parseObject(resData);
                        address_info = JSONObject.parseObject(obj.get("address_info").toString());
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
//        }

        return address_info;
    }

    @Override
    public VideoShopOrder selectVideoShopOrderByOrderId(String orderId) {
        return videoShopOrderMapper.selectVideoShopOrderByOrderId(orderId);
    }

    protected TableDataInfo getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }
}
