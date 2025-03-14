package com.ruoyi.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.common.utils.security.Md5Utils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.service.IVideoShopSchedulingService;
import com.ruoyi.common.core.text.Convert;

import static com.ruoyi.common.utils.PageUtils.startPage;

/**
 * 排班信息Service业务层处理
 *
 * @author CaiXY
 * @date 2024-06-27
 */
@Service
public class VideoShopSchedulingServiceImpl implements IVideoShopSchedulingService
{
    private static final Logger log = LoggerFactory.getLogger(VideoShopSchedulingServiceImpl.class);

    @Autowired
    private VideoShopMapper videoShopMapper;
    @Autowired
    private VideoShopSchedulingMapper videoShopSchedulingMapper;
    @Autowired
    private VideoShopAnchorMapper videoShopAnchorMapper;
    @Autowired
    private VideoShopSalarytemplateMapper videoShopSalarytemplateMapper;
    @Autowired
    private VideoShopLiveRoomMapper videoShopLiveRoomMapper;

    /**
     * 查询排班信息
     *
     * @param id 排班信息主键
     * @return 排班信息
     */
    @Override
    public VideoShopScheduling selectVideoShopSchedulingById(Long id)
    {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);
        return videoShopSchedulingMapper.selectVideoShopSchedulingById(String.valueOf(videoShop.getTableIndex()),id);
    }

    /**
     * 查询排班信息列表
     *
     * @param videoShopScheduling 排班信息
     * @return 排班信息
     */
    @Override
    public List<VideoShopScheduling> selectVideoShopSchedulingList(VideoShopScheduling videoShopScheduling)
    {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);
        videoShopScheduling.getParams().put("tableIndex", videoShop.getTableIndex());
        videoShopScheduling.setShopId(videoShop.getId());
        return videoShopSchedulingMapper.selectVideoShopSchedulingList(videoShopScheduling);
    }

    /**
     * 新增排班信息
     *
     * @param videoShopScheduling 排班信息
     * @return 结果
     */
    @Override
    public int insertVideoShopScheduling(VideoShopScheduling videoShopScheduling)
    {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);
        videoShopScheduling.setShopId(videoShop.getId());
        videoShopScheduling.setCreateTime(DateUtils.getNowDate());
        return videoShopSchedulingMapper.insertVideoShopScheduling(videoShopScheduling);
    }

    @Override
    public int insertVideoShopScheduling_UP(VideoShopScheduling videoShopScheduling) {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);

        videoShopScheduling.setShopId(videoShop.getId());
        videoShopScheduling.setCreateTime(DateUtils.getNowDate());

        videoShopScheduling.getParams().put("tableIndex", videoShop.getTableIndex());
        return videoShopSchedulingMapper.insertVideoShopScheduling_UP(videoShopScheduling);
    }

    /**
     * 修改排班信息
     *
     * @param videoShopScheduling 排班信息
     * @return 结果
     */
    @Override
    public int updateVideoShopScheduling(VideoShopScheduling videoShopScheduling)
    {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);
        videoShopScheduling.getParams().put("tableIndex", videoShop.getTableIndex());

        videoShopScheduling.setShopId(videoShop.getId());
        videoShopScheduling.setUpdateTime(DateUtils.getNowDate());
        return videoShopSchedulingMapper.updateVideoShopScheduling(videoShopScheduling);
    }

    /**
     * 批量删除排班信息
     *
     * @param ids 需要删除的排班信息主键
     * @return 结果
     */
    @Override
    public int deleteVideoShopSchedulingByIds(String ids)
    {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);
        return videoShopSchedulingMapper.deleteVideoShopSchedulingByIds(String.valueOf(videoShop.getTableIndex()),Convert.toStrArray(ids));
    }

    @Override
    public String importScheduling(List<VideoShopScheduling> videoShopSchedulingList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(videoShopSchedulingList) || videoShopSchedulingList.size() == 0)
        {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (VideoShopScheduling videoShopScheduling : videoShopSchedulingList)
        {
            try
            {
                boolean flag = false;

                String roomName = videoShopScheduling.getRoomName();
                String shopNameRoom = videoShop.getShopName()+"直播间";

                videoShopScheduling.getParams().put("tableIndex", videoShop.getTableIndex());

                VideoShopAnchor videoShopAnchor = new VideoShopAnchor();
                videoShopAnchor.setShopId(videoShop.getId());
                videoShopAnchor.setName(videoShopScheduling.getAnchorName());
                videoShopAnchor.getParams().put("tableIndex", videoShop.getTableIndex());
                videoShopAnchor = videoShopAnchorMapper.selectVideoShopAnchorByName(String.valueOf(videoShop.getTableIndex()),videoShop.getId(),videoShopScheduling.getAnchorName());
                videoShopScheduling.setAnchorId(videoShopAnchor.getId());

                if(roomName.equals(shopNameRoom)){
                    VideoShopLiveRoom videoShopLiveRoom =  videoShopLiveRoomMapper.selectVideoShopLiveRoomByName(videoShop.getTableIndex(),videoShop.getId(),roomName);
                    videoShopScheduling.setRoomId(Long.valueOf(videoShopLiveRoom.getId()).intValue());

                    int count = videoShopSchedulingMapper.selectCountByTimeBetweenEdit(Long.valueOf(videoShop.getTableIndex()),videoShop.getId(),videoShopLiveRoom.getId(),videoShopAnchor.getId(),formatter.format(videoShopScheduling.getStarttime()),formatter.format(videoShopScheduling.getEndtime()));
                    if(count>0){
                        int p = 1/0;
                    }
                    flag = true;
                }
                else{
                    VideoShopLiveRoom videoShopLiveRoom =  videoShopLiveRoomMapper.selectVideoShopLiveRoomByName(videoShop.getTableIndex(),videoShop.getId(),roomName);
                    if(videoShopLiveRoom!=null){
                        flag = true;
                        int count = videoShopSchedulingMapper.selectCountByTimeBetweenEdit(Long.valueOf(videoShop.getTableIndex()),videoShop.getId(),videoShopLiveRoom.getId(),videoShopAnchor.getId(),formatter.format(videoShopScheduling.getStarttime()),formatter.format(videoShopScheduling.getEndtime()));
                        if(count>0){
                            int p = 1/0;
                        }
                        videoShopScheduling.setRoomId(Long.valueOf(videoShopLiveRoom.getId()).intValue());
                    }
                    else{
                        int p = 1/0;
                    }

                }

                if(!flag){
                    failureMsg.append("<br/>" + failureNum + "格式错误");
                    int p = 1/0;
                }

                VideoShopSalarytemplate videoShopSalarytemplate = new VideoShopSalarytemplate();
                videoShopSalarytemplate.getParams().put("tableIndex", videoShop.getTableIndex());
                videoShopSalarytemplate.setLocalShopId(videoShop.getId());
                videoShopSalarytemplate.setName(videoShopScheduling.getTemplateName());
                videoShopSalarytemplate = videoShopSalarytemplateMapper.selectVideoShopSalarytemplateByName(videoShopSalarytemplate);
                videoShopScheduling.setTemplateId(Long.valueOf(videoShopSalarytemplate.getId()));


                videoShopScheduling.setShopId(videoShop.getId());
                // 验证是否存在这个用户
//                SysUser u = userMapper.selectUserByLoginName(user.getLoginName());
//                if (StringUtils.isNull(u))
                if (0<1)

                {
//                    BeanValidators.validateWithException(validator, user);
                    videoShopScheduling.setCreateBy(operName);
                    videoShopSchedulingMapper.insertVideoShopScheduling_UP(videoShopScheduling);
                    successNum++;
                    successMsg.append("<br/>" + successNum +   " 导入成功");
                }
//                else if (isUpdateSupport)
//                {
//                    BeanValidators.validateWithException(validator, user);
//                    checkUserAllowed(u);
//                    checkUserDataScope(u.getUserId());
//                    user.setUserId(u.getUserId());
//                    user.setUpdateBy(operName);
//                    userMapper.updateUser(user);
//                    successNum++;
//                    successMsg.append("<br/>" + successNum + "、账号 " + user.getLoginName() + " 更新成功");
//                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号  已存在");
//                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getLoginName() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号  导入失败：";
//                String msg = "<br/>" + failureNum + "、账号 " + user.getLoginName() + " 导入失败：";

                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 删除排班信息信息
     *
     * @param id 排班信息主键
     * @return 结果
     */
    @Override
    public int deleteVideoShopSchedulingById(Long id)
    {
        return videoShopSchedulingMapper.deleteVideoShopSchedulingById(id);
    }

    @Override
    public List<HashMap<String, Object>> getCalendarList(VideoShopScheduling videoShopScheduling) {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);
        videoShopScheduling.setShopId(videoShop.getId());
        videoShopScheduling.getParams().put("tableIndex", videoShop.getTableIndex());
        return videoShopSchedulingMapper.getCalendarList(videoShopScheduling);
    }

    @Override
    public int selectCountByTimeBetween(Long roomId,Long anchorId, String timeBegin, String timeEnd) {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);
        return videoShopSchedulingMapper.selectCountByTimeBetween(Long.valueOf(videoShop.getTableIndex()),roomId,anchorId,timeBegin,timeEnd);
    }

    @Override
    public int selectCountByTimeBetweenEdit(Long roomId, Long anchorId, String timeBegin, String timeEnd) {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);
        return videoShopSchedulingMapper.selectCountByTimeBetweenEdit(Long.valueOf(videoShop.getTableIndex()),videoShop.getId(),roomId,anchorId,timeBegin,timeEnd);
    }


    @Override
    public int selectCountByTemplateId(String templateId) {
        return  videoShopSchedulingMapper.selectCountByTemplateId(templateId);
    }
}
