package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.VideoShopAnchorMapper;
import com.ruoyi.system.domain.VideoShopAnchor;
import com.ruoyi.system.service.IVideoShopAnchorService;
import com.ruoyi.common.core.text.Convert;

/**
 * 主播信息Service业务层处理
 * 
 * @author CaiXY
 * @date 2024-06-24
 */
@Service
public class VideoShopAnchorServiceImpl implements IVideoShopAnchorService 
{
    @Autowired
    private VideoShopAnchorMapper videoShopAnchorMapper;

    /**
     * 查询主播信息
     * 
     * @param id 主播信息主键
     * @return 主播信息
     */
    @Override
    public VideoShopAnchor selectVideoShopAnchorById(Long id)
    {
        return videoShopAnchorMapper.selectVideoShopAnchorById(id);
    }

    /**
     * 查询主播信息列表
     * 
     * @param videoShopAnchor 主播信息
     * @return 主播信息
     */
    @Override
    public List<VideoShopAnchor> selectVideoShopAnchorList(VideoShopAnchor videoShopAnchor)
    {
        return videoShopAnchorMapper.selectVideoShopAnchorList(videoShopAnchor);
    }

    /**
     * 新增主播信息
     * 
     * @param videoShopAnchor 主播信息
     * @return 结果
     */
    @Override
    public int insertVideoShopAnchor(VideoShopAnchor videoShopAnchor)
    {
        videoShopAnchor.setCreateTime(DateUtils.getNowDate());
        return videoShopAnchorMapper.insertVideoShopAnchor(videoShopAnchor);
    }

    /**
     * 修改主播信息
     * 
     * @param videoShopAnchor 主播信息
     * @return 结果
     */
    @Override
    public int updateVideoShopAnchor(VideoShopAnchor videoShopAnchor)
    {
        videoShopAnchor.setUpdateTime(DateUtils.getNowDate());
        return videoShopAnchorMapper.updateVideoShopAnchor(videoShopAnchor);
    }

    /**
     * 批量删除主播信息
     * 
     * @param ids 需要删除的主播信息主键
     * @return 结果
     */
    @Override
    public int deleteVideoShopAnchorByIds(String ids)
    {
        return videoShopAnchorMapper.deleteVideoShopAnchorByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除主播信息信息
     * 
     * @param id 主播信息主键
     * @return 结果
     */
    @Override
    public int deleteVideoShopAnchorById(Long id)
    {
        return videoShopAnchorMapper.deleteVideoShopAnchorById(id);
    }
}
