package com.ruoyi.system.mapper;

import java.util.HashMap;
import java.util.List;

import com.ruoyi.system.domain.VideoShopAnchor;
import com.ruoyi.system.domain.VideoShopScheduling;
import org.apache.ibatis.annotations.Param;

/**
 * 排班信息Mapper接口
 *
 * @author CaiXY
 * @date 2024-06-27
 */
public interface VideoShopSchedulingMapper
{

    /**
     * 查询排班信息
     *
     * @param id 排班信息主键
     * @return 排班信息
     */
    public VideoShopScheduling selectVideoShopSchedulingById(@Param("tableIndex")String tableIndex,@Param("id")Long id);

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
     * 删除排班信息
     *
     * @param id 排班信息主键
     * @return 结果
     */
    public int deleteVideoShopSchedulingById(Long id);

    /**
     * 批量删除排班信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVideoShopSchedulingByIds(@Param("tableIndex")String tableIndex,@Param("array")String[] ids);

    List<HashMap<String, Object>> getCalendarList(VideoShopScheduling videoShopScheduling);

    int selectCountByTimeBetween(@Param("tableIndex")Long tableIndex,@Param("roomId")Long roomId,@Param("anchorId")Long anchorId, @Param("timeBegin")String timeBegin, @Param("timeEnd")String timeEnd);

    int selectCountByTimeBetweenEdit(@Param("tableIndex")Long tableIndex,@Param("shopId")Long shopId,@Param("roomId")Long roomId,@Param("anchorId")Long anchorId, @Param("timeBegin")String timeBegin, @Param("timeEnd")String timeEnd);

    List<HashMap<String, Object>> selectSettlementCountByAnchor(VideoShopAnchor videoShopAnchor);

    public int selectCountByTemplateId(String templateId);

    public int selectVideoShopSchedulingCountByAnchorId(@Param("anchorId")String anchorId);

    public int selectSettlementCountByRoomId(@Param("tableIndex")String tableIndex,@Param("shopId")String shopId, @Param("roomId")String roomId);

}