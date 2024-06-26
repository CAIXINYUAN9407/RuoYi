package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.VideoShopOrder;

/**
 * 订单表Service接口
 * 
 * @author CaiXY
 * @date 2024-06-21
 */
public interface IVideoShopOrderService 
{
    /**
     * 查询订单表
     * 
     * @param id 订单表主键
     * @return 订单表
     */
    public VideoShopOrder selectVideoShopOrderById(Long id);

    /**
     * 查询订单表列表
     * 
     * @param videoShopOrder 订单表
     * @return 订单表集合
     */
    public List<VideoShopOrder> selectVideoShopOrderList(VideoShopOrder videoShopOrder);

    /**
     * 新增订单表
     * 
     * @param videoShopOrder 订单表
     * @return 结果
     */
    public int insertVideoShopOrder(VideoShopOrder videoShopOrder);

    /**
     * 修改订单表
     * 
     * @param videoShopOrder 订单表
     * @return 结果
     */
    public int updateVideoShopOrder(VideoShopOrder videoShopOrder);

    /**
     * 批量删除订单表
     * 
     * @param ids 需要删除的订单表主键集合
     * @return 结果
     */
    public int deleteVideoShopOrderByIds(String ids);

    /**
     * 删除订单表信息
     * 
     * @param id 订单表主键
     * @return 结果
     */
    public int deleteVideoShopOrderById(Long id);
}
