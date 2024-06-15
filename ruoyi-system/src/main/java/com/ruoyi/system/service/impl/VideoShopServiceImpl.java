package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.VideoShopMapper;
import com.ruoyi.system.domain.VideoShop;
import com.ruoyi.system.service.IVideoShopService;
import com.ruoyi.common.core.text.Convert;

/**
 * 店铺Service业务层处理
 * 
 * @author CaiXY
 * @date 2024-06-15
 */
@Service
public class VideoShopServiceImpl implements IVideoShopService 
{
    @Autowired
    private VideoShopMapper videoShopMapper;

    /**
     * 查询店铺
     * 
     * @param id 店铺主键
     * @return 店铺
     */
    @Override
    public VideoShop selectVideoShopById(Long id)
    {
        return videoShopMapper.selectVideoShopById(id);
    }

    /**
     * 查询店铺列表
     * 
     * @param videoShop 店铺
     * @return 店铺
     */
    @Override
    public List<VideoShop> selectVideoShopList(VideoShop videoShop)
    {
        return videoShopMapper.selectVideoShopList(videoShop);
    }

    /**
     * 新增店铺
     * 
     * @param videoShop 店铺
     * @return 结果
     */
    @Override
    public int insertVideoShop(VideoShop videoShop)
    {
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
    public int updateVideoShop(VideoShop videoShop)
    {
        return videoShopMapper.updateVideoShop(videoShop);
    }

    /**
     * 批量删除店铺
     * 
     * @param ids 需要删除的店铺主键
     * @return 结果
     */
    @Override
    public int deleteVideoShopByIds(String ids)
    {
        return videoShopMapper.deleteVideoShopByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除店铺信息
     * 
     * @param id 店铺主键
     * @return 结果
     */
    @Override
    public int deleteVideoShopById(Long id)
    {
        return videoShopMapper.deleteVideoShopById(id);
    }
}
