package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.VideoShop;

/**
 * 店铺Service接口
 * 
 * @author CaiXY
 * @date 2024-06-15
 */
public interface IVideoShopService 
{
    /**
     * 查询店铺
     * 
     * @param id 店铺主键
     * @return 店铺
     */
    public VideoShop selectVideoShopById(Long id);

    /**
     * 查询店铺列表
     * 
     * @param videoShop 店铺
     * @return 店铺集合
     */
    public List<VideoShop> selectVideoShopList(VideoShop videoShop);

    /**
     * 新增店铺
     * 
     * @param videoShop 店铺
     * @return 结果
     */
    public int insertVideoShop(VideoShop videoShop);

    /**
     * 修改店铺
     * 
     * @param videoShop 店铺
     * @return 结果
     */
    public int updateVideoShop(VideoShop videoShop);

    /**
     * 批量删除店铺
     * 
     * @param ids 需要删除的店铺主键集合
     * @return 结果
     */
    public int deleteVideoShopByIds(String ids);

    /**
     * 删除店铺信息
     * 
     * @param id 店铺主键
     * @return 结果
     */
    public int deleteVideoShopById(Long id);
}
