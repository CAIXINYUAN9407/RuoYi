package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.VideoShopChannelsFinder;

/**
 * 优选联盟达人Mapper接口
 *
 * @author CaiXY
 * @date 2024-07-18
 */
public interface VideoShopChannelsFinderMapper
{
    /**
     * 查询优选联盟达人
     *
     * @param id 优选联盟达人主键
     * @return 优选联盟达人
     */
    public VideoShopChannelsFinder selectVideoShopChannelsFinderById(Long id);

    /**
     * 查询优选联盟达人列表
     *
     * @param videoShopChannelsFinder 优选联盟达人
     * @return 优选联盟达人集合
     */
    public List<VideoShopChannelsFinder> selectVideoShopChannelsFinderList(VideoShopChannelsFinder videoShopChannelsFinder);

    /**
     * 新增优选联盟达人
     *
     * @param videoShopChannelsFinder 优选联盟达人
     * @return 结果
     */
    public int insertVideoShopChannelsFinder(VideoShopChannelsFinder videoShopChannelsFinder);

    /**
     * 修改优选联盟达人
     *
     * @param videoShopChannelsFinder 优选联盟达人
     * @return 结果
     */
    public int updateVideoShopChannelsFinder(VideoShopChannelsFinder videoShopChannelsFinder);

    /**
     * 删除优选联盟达人
     *
     * @param id 优选联盟达人主键
     * @return 结果
     */
    public int deleteVideoShopChannelsFinderById(Long id);

    /**
     * 批量删除优选联盟达人
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVideoShopChannelsFinderByIds(String[] ids);

    public int deleteVideoShopChannelsFinderByFinderIdAndCountTime(VideoShopChannelsFinder videoShopChannelsFinder);
}