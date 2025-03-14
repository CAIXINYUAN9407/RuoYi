package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.VideoShopScheduling;

import java.util.HashMap;
import java.util.List;

public interface IVideoShopSchedulingService {
    /**
     * 查询排班信息
     *
     * @param id 排班信息主键
     * @return 排班信息
     */
    public VideoShopScheduling selectVideoShopSchedulingById(Long id);

    /**
     * 查询排班信息列表
     *
     * @param videoShopScheduling 排班信息
     * @return 排班信息集合
     */
    public List<VideoShopScheduling> selectVideoShopSchedulingList(VideoShopScheduling videoShopScheduling);

    /**
     * 新增排班信息
     *
     * @param videoShopScheduling 排班信息
     * @return 结果
     */
    public int insertVideoShopScheduling(VideoShopScheduling videoShopScheduling);

    /**
     * 新增排班信息
     *
     * @param videoShopScheduling 排班信息
     * @return 结果
     */
    public int insertVideoShopScheduling_UP(VideoShopScheduling videoShopScheduling);

    /**
     * 修改排班信息
     *
     * @param videoShopScheduling 排班信息
     * @return 结果
     */
    public int updateVideoShopScheduling(VideoShopScheduling videoShopScheduling);

    /**
     * 批量删除排班信息
     *
     * @param ids 需要删除的排班信息主键集合
     * @return 结果
     */
    public int deleteVideoShopSchedulingByIds(String ids);


    /**
     * 导入用户数据
     *
     * @param videoShopSchedulingList 排班列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importScheduling(List<VideoShopScheduling> videoShopSchedulingList, Boolean isUpdateSupport, String operName);

    /**
     * 删除排班信息信息
     *
     * @param id 排班信息主键
     * @return 结果
     */
    public int deleteVideoShopSchedulingById(Long id);

    List<HashMap<String, Object>> getCalendarList(VideoShopScheduling videoShopScheduling);

    int selectCountByTimeBetween(Long roomId,Long anchorId, String timeBegin, String timeEnd);

    int selectCountByTimeBetweenEdit(Long roomId,Long anchorId, String timeBegin, String timeEnd);

    public int selectCountByTemplateId(String ids);
}
