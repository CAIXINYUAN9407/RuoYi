package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.VideoShopAnchor;

/**
 * 主播信息Mapper接口
 * 
 * @author CaiXY
 * @date 2024-06-24
 */
public interface VideoShopAnchorMapper 
{
    /**
     * 查询主播信息
     * 
     * @param id 主播信息主键
     * @return 主播信息
     */
    public VideoShopAnchor selectVideoShopAnchorById(Long id);

    /**
     * 查询主播信息列表
     * 
     * @param videoShopAnchor 主播信息
     * @return 主播信息集合
     */
    public List<VideoShopAnchor> selectVideoShopAnchorList(VideoShopAnchor videoShopAnchor);

    /**
     * 新增主播信息
     * 
     * @param videoShopAnchor 主播信息
     * @return 结果
     */
    public int insertVideoShopAnchor(VideoShopAnchor videoShopAnchor);

    /**
     * 修改主播信息
     * 
     * @param videoShopAnchor 主播信息
     * @return 结果
     */
    public int updateVideoShopAnchor(VideoShopAnchor videoShopAnchor);

    /**
     * 删除主播信息
     * 
     * @param id 主播信息主键
     * @return 结果
     */
    public int deleteVideoShopAnchorById(Long id);

    /**
     * 批量删除主播信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVideoShopAnchorByIds(String[] ids);
}
