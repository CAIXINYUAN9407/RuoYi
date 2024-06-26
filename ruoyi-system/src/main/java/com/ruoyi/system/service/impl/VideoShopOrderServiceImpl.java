package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.VideoShopOrderMapper;
import com.ruoyi.system.domain.VideoShopOrder;
import com.ruoyi.system.service.IVideoShopOrderService;
import com.ruoyi.common.core.text.Convert;

/**
 * 订单表Service业务层处理
 * 
 * @author CaiXY
 * @date 2024-06-21
 */
@Service
public class VideoShopOrderServiceImpl implements IVideoShopOrderService 
{
    @Autowired
    private VideoShopOrderMapper videoShopOrderMapper;

    /**
     * 查询订单表
     * 
     * @param id 订单表主键
     * @return 订单表
     */
    @Override
    public VideoShopOrder selectVideoShopOrderById(Long id)
    {
        return videoShopOrderMapper.selectVideoShopOrderById(id);
    }

    /**
     * 查询订单表列表
     * 
     * @param videoShopOrder 订单表
     * @return 订单表
     */
    @Override
    public List<VideoShopOrder> selectVideoShopOrderList(VideoShopOrder videoShopOrder)
    {
        return videoShopOrderMapper.selectVideoShopOrderList(videoShopOrder);
    }

    /**
     * 新增订单表
     * 
     * @param videoShopOrder 订单表
     * @return 结果
     */
    @Override
    public int insertVideoShopOrder(VideoShopOrder videoShopOrder)
    {
        videoShopOrder.setCreateTime(DateUtils.getNowDate());
        return videoShopOrderMapper.insertVideoShopOrder(videoShopOrder);
    }

    /**
     * 修改订单表
     * 
     * @param videoShopOrder 订单表
     * @return 结果
     */
    @Override
    public int updateVideoShopOrder(VideoShopOrder videoShopOrder)
    {
        videoShopOrder.setUpdateTime(DateUtils.getNowDate());
        return videoShopOrderMapper.updateVideoShopOrder(videoShopOrder);
    }

    /**
     * 批量删除订单表
     * 
     * @param ids 需要删除的订单表主键
     * @return 结果
     */
    @Override
    public int deleteVideoShopOrderByIds(String ids)
    {
        return videoShopOrderMapper.deleteVideoShopOrderByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除订单表信息
     * 
     * @param id 订单表主键
     * @return 结果
     */
    @Override
    public int deleteVideoShopOrderById(Long id)
    {
        return videoShopOrderMapper.deleteVideoShopOrderById(id);
    }
}
