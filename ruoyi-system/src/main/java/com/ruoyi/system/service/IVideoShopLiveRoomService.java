package com.ruoyi.system.service;

import com.ruoyi.system.domain.VideoShopLiveRoom;

import java.util.List;

/**
 * 店铺Service接口
 * 
 * @author CaiXY
 * @date 2024-06-15
 */
public interface IVideoShopLiveRoomService
{
    /**
     * 查询直播间
     * 
     * @param  videoShopLiveRoom 店铺主键
     * @return 店铺
     */
    public List<VideoShopLiveRoom> selectLiveRoomList(VideoShopLiveRoom videoShopLiveRoom);


    public int insertVideoShopOrder(VideoShopLiveRoom videoShopLiveRoom);

    public int deleteVideoShopLiveRoomById(String id);

    public VideoShopLiveRoom selectLiveRoomByRoomId(String roomId);
}
