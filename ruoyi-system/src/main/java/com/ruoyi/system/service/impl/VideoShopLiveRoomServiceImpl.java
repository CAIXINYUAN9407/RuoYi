package com.ruoyi.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.domain.VideoShop;
import com.ruoyi.system.domain.VideoShopLiveRoom;
import com.ruoyi.system.mapper.VideoShopLiveRoomMapper;
import com.ruoyi.system.mapper.VideoShopMapper;
import com.ruoyi.system.mapper.VideoShopSchedulingMapper;
import com.ruoyi.system.service.IVideoShopLiveRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单表Service业务层处理
 *
 * @author CaiXY
 * @date 2024-06-21
 */
@Service
public class VideoShopLiveRoomServiceImpl implements IVideoShopLiveRoomService {

    @Autowired
    private VideoShopMapper videoShopMapper;
    @Autowired
    private VideoShopLiveRoomMapper videoShopLiveRoomMapper;
    @Autowired
    private VideoShopSchedulingMapper videoShopSchedulingMapper;

    /**
     * 查询直播间表
     *
     * @param videoShopLiveRoom 订单表主键
     * @return 订单表
     */
    @Override
    public List<VideoShopLiveRoom> selectLiveRoomList(VideoShopLiveRoom videoShopLiveRoom) {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);
        videoShopLiveRoom.setShopId(videoShop.getId());
        videoShopLiveRoom.getParams().put("tableIndex", videoShop.getTableIndex());
        return videoShopLiveRoomMapper.selectVideoShopLiveRoomList(videoShopLiveRoom);
    }

    @Override
    public int insertVideoShopOrder(VideoShopLiveRoom videoShopLiveRoom) {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);
        videoShopLiveRoom.setShopId(videoShop.getId());
        videoShopLiveRoom.getParams().put("tableIndex", videoShop.getTableIndex());

        //非主直播间
        videoShopLiveRoom.setIsMain(0);
        VideoShopLiveRoom videoShopLiveRoomOld = videoShopLiveRoomMapper.selectVideoShopLiveRoomByName(videoShop.getTableIndex(), videoShop.getId(), videoShopLiveRoom.getRoomName());
        if (videoShopLiveRoomOld != null) {
            return -1;
        }

        videoShopLiveRoomMapper.insertVideoShopLiveRoom(videoShopLiveRoom);
        return 1;
    }

    @Override
    public int deleteVideoShopLiveRoomById(String id) {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);

        Integer count = videoShopSchedulingMapper.selectSettlementCountByRoomId(String.valueOf(videoShop.getTableIndex()), String.valueOf(videoShop.getId()),id);
        if(count>0){
            return -1;
        }
        videoShopLiveRoomMapper.deleteVideoShopLiveRoomById(videoShop.getTableIndex(),id);
        return 1;
    }

    @Override
    public VideoShopLiveRoom selectLiveRoomByRoomId(String roomId) {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);

        VideoShopLiveRoom videoShopLiveRoom=videoShopLiveRoomMapper.selectLiveRoomByRoomId(videoShop.getTableIndex(),roomId);
        return videoShopLiveRoom;
    }

    protected TableDataInfo getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }
}
