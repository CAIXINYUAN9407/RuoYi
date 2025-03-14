package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.VideoShopLiveRoom;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单表Mapper接口
 * 
 * @author CaiXY
 * @date 2024-06-21
 */
public interface VideoShopLiveRoomMapper
{
    /**
     * 查询直播间表
     * 
     * @param id 直播间表主键
     * @return 直播间表
     */
    public VideoShopLiveRoom selectVideoShopLiveRoomById(Long id);

    /**
     * 查询直播间表列表
     * 
     * @param videoShopLiveRoom 直播间表
     * @return 直播间表集合
     */
    public List<VideoShopLiveRoom> selectVideoShopLiveRoomList(VideoShopLiveRoom videoShopLiveRoom);

    /**
     * 新增订单表
     *
     * @param videoShopLiveRoom 订单表
     * @return 结果
     */
    public int insertVideoShopLiveRoom(VideoShopLiveRoom videoShopLiveRoom);

    VideoShopLiveRoom selectVideoShopLiveRoomByName(@Param("tableIndex")Integer tableIndex,@Param("shopId")Long shopId,@Param("roomName")String roomName);

    Integer selectVideoShopMainLiveRoomId(@Param("tableIndex")Integer tableIndex, @Param("shopId")Long shopId, @Param("roomName") Integer isMain);

    public void deleteVideoShopLiveRoomById(@Param("tableIndex")Integer tableIndex, @Param("id")String id);

    public VideoShopLiveRoom selectLiveRoomByRoomId(@Param("tableIndex")Integer tableIndex, @Param("roomId")String roomId);
}
