package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.domain.VideoShop;
import com.ruoyi.system.domain.VideoShopAfterSaleOrder;
import com.ruoyi.system.mapper.VideoShopAfterSaleOrderMapper;
import com.ruoyi.system.mapper.VideoShopMapper;
import com.ruoyi.system.service.IVideoShopAfterSaleOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.ruoyi.common.utils.PageUtils.startPage;

@Service
public class VideoShopAfterSaleOrderServiceImpl implements IVideoShopAfterSaleOrderService {

    @Autowired
    private VideoShopMapper videoShopMapper;
    @Autowired
    private VideoShopAfterSaleOrderMapper videoShopAfterSaleOrderMapper;
    @Override
    public VideoShopAfterSaleOrder selectVideoShopAfterSaleOrderById(Long id) {
        return videoShopAfterSaleOrderMapper.selectVideoShopAfterSaleOrderById(id);
    }

    @Override
    public List<VideoShopAfterSaleOrder> selectVideoShopAfterSaleOrderList(VideoShopAfterSaleOrder videoShopAfterSaleOrder) {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);
        videoShopAfterSaleOrder.setLocalShopId(videoShop.getId());
        videoShopAfterSaleOrder.getParams().put("tableIndex", videoShop.getTableIndex());
        startPage();

            return videoShopAfterSaleOrderMapper.selectVideoShopAfterSaleOrderList(videoShopAfterSaleOrder);
    }

    @Override
    public int insertVideoShopAfterSaleOrder(VideoShopAfterSaleOrder videoShopAfterSaleOrder) {
        videoShopAfterSaleOrder.setCreateTime(DateUtils.getNowDate());
        return videoShopAfterSaleOrderMapper.insertVideoShopAfterSaleOrder(videoShopAfterSaleOrder);
    }


    /**
     * 修改售后信息
     *
     * @param videoShopAfterSaleOrder 售后信息
     * @return 结果
     */
    @Override
    public int updateVideoShopAfterSaleOrder(VideoShopAfterSaleOrder videoShopAfterSaleOrder)
    {
        videoShopAfterSaleOrder.setUpdateTime(DateUtils.getNowDate());
        return videoShopAfterSaleOrderMapper.updateVideoShopAfterSaleOrder(videoShopAfterSaleOrder);
    }

    @Override
    public int deleteVideoShopAfterSaleOrderByIds(String ids) {
        return videoShopAfterSaleOrderMapper.deleteVideoShopAfterSaleOrderByIds(Convert.toStrArray(ids));
    }

    @Override
    public VideoShopAfterSaleOrder selectVideoShopAfterSaleOrderByAfterSaleOrderId(String afterSaleOrderId) {
        return videoShopAfterSaleOrderMapper.selectVideoShopAfterSaleOrderByAfterSaleOrderId(afterSaleOrderId);
    }

    @Override
    public int deleteVideoShopAfterSaleOrderById(Long id) {
        return videoShopAfterSaleOrderMapper.deleteVideoShopAfterSaleOrderById(id);
    }
}
