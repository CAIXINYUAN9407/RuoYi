package com.ruoyi.system.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.domain.VideoShop;
import com.ruoyi.system.domain.VideoShopAfterSaleOrder;
import com.ruoyi.system.domain.VideoShopOrder;
import com.ruoyi.system.mapper.VideoShopAfterSaleOrderMapper;
import com.ruoyi.system.mapper.VideoShopMapper;
import com.ruoyi.system.mapper.VideoShopOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.VideoShopChannelsFinderMapper;
import com.ruoyi.system.domain.VideoShopChannelsFinder;
import com.ruoyi.system.service.IVideoShopChannelsFinderService;
import com.ruoyi.common.core.text.Convert;

/**
 * 优选联盟达人Service业务层处理
 *
 * @author CaiXY
 * @date 2024-07-18
 */
@Service
public class VideoShopChannelsFinderServiceImpl implements IVideoShopChannelsFinderService
{
    @Autowired
    private VideoShopChannelsFinderMapper videoShopChannelsFinderMapper;
    @Autowired
    private VideoShopMapper videoShopMapper;
    @Autowired
    private VideoShopOrderMapper videoShopOrderMapper;
    @Autowired
    private VideoShopAfterSaleOrderMapper videoShopAfterSaleOrderMapper;

    /**
     * 查询优选联盟达人
     *
     * @param id 优选联盟达人主键
     * @return 优选联盟达人
     */
    @Override
    public VideoShopChannelsFinder selectVideoShopChannelsFinderById(Long id)
    {
        return videoShopChannelsFinderMapper.selectVideoShopChannelsFinderById(id);
    }

    /**
     * 查询优选联盟达人列表
     *
     * @param videoShopChannelsFinder 优选联盟达人
     * @return 优选联盟达人
     */
    @Override
    public List<VideoShopChannelsFinder> selectVideoShopChannelsFinderList(VideoShopChannelsFinder videoShopChannelsFinder)
    {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);
        videoShopChannelsFinder.setLocalShopId(videoShop.getId());
        return videoShopChannelsFinderMapper.selectVideoShopChannelsFinderList(videoShopChannelsFinder);
    }

    /**
     * 新增优选联盟达人
     *
     * @param videoShopChannelsFinder 优选联盟达人
     * @return 结果
     */
    @Override
    public int insertVideoShopChannelsFinder(VideoShopChannelsFinder videoShopChannelsFinder)
    {
        videoShopChannelsFinder.setCreateTime(DateUtils.getNowDate());
        return videoShopChannelsFinderMapper.insertVideoShopChannelsFinder(videoShopChannelsFinder);
    }

    /**
     * 修改优选联盟达人
     *
     * @param videoShopChannelsFinder 优选联盟达人
     * @return 结果
     */
    @Override
    public int updateVideoShopChannelsFinder(VideoShopChannelsFinder videoShopChannelsFinder)
    {
        videoShopChannelsFinder.setUpdateTime(DateUtils.getNowDate());
        return videoShopChannelsFinderMapper.updateVideoShopChannelsFinder(videoShopChannelsFinder);
    }

    /**
     * 批量删除优选联盟达人
     *
     * @param ids 需要删除的优选联盟达人主键
     * @return 结果
     */
    @Override
    public int deleteVideoShopChannelsFinderByIds(String ids)
    {
        VideoShopChannelsFinder videoShopChannelsFinder =videoShopChannelsFinderMapper.selectVideoShopChannelsFinderById(Long.valueOf(ids));
        List<VideoShopOrder> videoShopOrderList = videoShopOrderMapper.selectVideoShopOrderListByFinderId(videoShopChannelsFinder.getFinderId());
        if(videoShopOrderList.size()>0){
            return -1;
        }
        return videoShopChannelsFinderMapper.deleteVideoShopChannelsFinderByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除优选联盟达人信息
     *
     * @param id 优选联盟达人主键
     * @return 结果
     */
    @Override
    public int deleteVideoShopChannelsFinderById(Long id)
    {
        return videoShopChannelsFinderMapper.deleteVideoShopChannelsFinderById(id);
    }

    @Override
    public List<HashMap<String, Object>> getChannelsFinderCountData(VideoShopChannelsFinder videoShopChannelsFinder) {
        List<HashMap<String, Object>> resurtList = new ArrayList<HashMap<String, Object>>();
        List<VideoShopChannelsFinder> videoShopChannelsFinderList = videoShopChannelsFinderMapper.selectVideoShopChannelsFinderList(videoShopChannelsFinder);
        for(int i=0;i<videoShopChannelsFinderList.size();i++){
            HashMap<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("finderId",videoShopChannelsFinderList.get(i).getFinderId());
            resultMap.put("nickName",videoShopChannelsFinderList.get(i).getNickname());
            List<VideoShopOrder> videoShopOrderList = videoShopOrderMapper.selectVideoShopOrderListByFinderId(videoShopChannelsFinderList.get(i).getFinderId());
            BigDecimal sumOrderPrice = new BigDecimal(0);
            //循环订单统计数据
            for(int j=0;j<videoShopOrderList.size();j++){
                sumOrderPrice = sumOrderPrice.add(videoShopOrderList.get(j).getOrderPrice());
            }
            resultMap.put("sumOrderPrice",sumOrderPrice);

            //处理售后单
            List<VideoShopAfterSaleOrder> videoShopAfterSaleOrderList = videoShopAfterSaleOrderMapper.selectVideoShopAfterSaleOrderListByFinderId(videoShopChannelsFinderList.get(i).getFinderId());
            BigDecimal sumAfterAmount = new BigDecimal(0);

            for(int m=0;m<videoShopAfterSaleOrderList.size();m++){

                sumAfterAmount = sumAfterAmount.add(videoShopAfterSaleOrderList.get(m).getAmount());
            }
            System.out.println(sumAfterAmount);
            resultMap.put("sumAfterAmount",sumAfterAmount);
            resurtList.add(resultMap);
        }
        return resurtList;
    }
}