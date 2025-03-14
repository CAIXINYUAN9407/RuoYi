package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.domain.VideoShop;
import com.ruoyi.system.mapper.VideoShopMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.VideoShopSalarytemplateMapper;
import com.ruoyi.system.domain.VideoShopSalarytemplate;
import com.ruoyi.system.service.IVideoShopSalarytemplateService;
import com.ruoyi.common.core.text.Convert;

import static com.ruoyi.common.utils.PageUtils.startPage;

/**
 * 薪酬模版Service业务层处理
 * 
 * @author CaiXY
 * @date 2024-06-20
 */
@Service
public class VideoShopSalarytemplateServiceImpl implements IVideoShopSalarytemplateService 
{
    @Autowired
    private VideoShopMapper videoShopMapper;
    @Autowired
    private VideoShopSalarytemplateMapper videoShopSalarytemplateMapper;

    /**
     * 查询薪酬模版
     * 
     * @param id 薪酬模版主键
     * @return 薪酬模版
     */
    @Override
    public VideoShopSalarytemplate selectVideoShopSalarytemplateById(Integer id)
    {
        return videoShopSalarytemplateMapper.selectVideoShopSalarytemplateById(id);
    }

    /**
     * 查询薪酬模版列表
     * 
     * @param videoShopSalarytemplate 薪酬模版
     * @return 薪酬模版
     */
    @Override
    public List<VideoShopSalarytemplate> selectVideoShopSalarytemplateList(VideoShopSalarytemplate videoShopSalarytemplate)
    {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);
        videoShopSalarytemplate.setLocalShopId(videoShop.getId());
        videoShopSalarytemplate.getParams().put("tableIndex",videoShop.getTableIndex());
        startPage();
        return videoShopSalarytemplateMapper.selectVideoShopSalarytemplateList(videoShopSalarytemplate);
    }

    /**
     * 新增薪酬模版
     * 
     * @param videoShopSalarytemplate 薪酬模版
     * @return 结果
     */
    @Override
    public int insertVideoShopSalarytemplate(VideoShopSalarytemplate videoShopSalarytemplate)
    {
        VideoShopSalarytemplate newVideoShopSalarytemplate = new VideoShopSalarytemplate();
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);

        newVideoShopSalarytemplate.getParams().put("tableIndex",videoShop.getTableIndex());

        newVideoShopSalarytemplate.setLocalShopId(videoShop.getId());
        newVideoShopSalarytemplate.setName(videoShopSalarytemplate.getName());
        newVideoShopSalarytemplate.setType(videoShopSalarytemplate.getType());
        
        if(videoShopSalarytemplate.getType() == 1){
            newVideoShopSalarytemplate.setAnchorBasicSalary(videoShopSalarytemplate.getAnchorBasicSalary());
            newVideoShopSalarytemplate.setHelperBasicSalary(videoShopSalarytemplate.getHelperBasicSalary());
            newVideoShopSalarytemplate.setControlerBasicSalary(videoShopSalarytemplate.getControlerBasicSalary());
        } else if (videoShopSalarytemplate.getType() == 2) {
            newVideoShopSalarytemplate.setRatioAnchorDefault(videoShopSalarytemplate.getRatioAnchorDefault());
            newVideoShopSalarytemplate.setRatioHelperDefault(videoShopSalarytemplate.getRatioHelperDefault());
            newVideoShopSalarytemplate.setRatioControlerDefault(videoShopSalarytemplate.getRatioControlerDefault());
            newVideoShopSalarytemplate.setRadioGiven(videoShopSalarytemplate.getRadioGiven());
        } else if (videoShopSalarytemplate.getType() == 3) {
            newVideoShopSalarytemplate.setLadder(videoShopSalarytemplate.getLadder());
        } else if (videoShopSalarytemplate.getType() == 4) {
            newVideoShopSalarytemplate.setBasicRadioAnchorBasicSalary(videoShopSalarytemplate.getBasicRadioAnchorBasicSalary());
            newVideoShopSalarytemplate.setBasicRadioHelperBasicSalary(videoShopSalarytemplate.getBasicRadioHelperBasicSalary());
            newVideoShopSalarytemplate.setBasicRadioControlerBasicSalary(videoShopSalarytemplate.getBasicRadioControlerBasicSalary());
            newVideoShopSalarytemplate.setBasicRadioAnchorDefault(videoShopSalarytemplate.getBasicRadioAnchorDefault());
            newVideoShopSalarytemplate.setBasicRadioHelperDefault(videoShopSalarytemplate.getBasicRadioHelperDefault());
            newVideoShopSalarytemplate.setBasicRadioControlerDefault(videoShopSalarytemplate.getBasicRadioControlerDefault());
            newVideoShopSalarytemplate.setBasicRadioGiven(videoShopSalarytemplate.getBasicRadioGiven());
        } else if (videoShopSalarytemplate.getType() == 5) {
            newVideoShopSalarytemplate.setBasicLadderAnchorBasicSalary(videoShopSalarytemplate.getBasicLadderAnchorBasicSalary());
            newVideoShopSalarytemplate.setBasicLadderHelperBasicSalary(videoShopSalarytemplate.getBasicLadderHelperBasicSalary());
            newVideoShopSalarytemplate.setBasicLadderControlerBasicSalary(videoShopSalarytemplate.getBasicLadderControlerBasicSalary());
            newVideoShopSalarytemplate.setBasicLadder(videoShopSalarytemplate.getBasicLadder());
        } else if (videoShopSalarytemplate.getType() == 6) {
            newVideoShopSalarytemplate.setQuantityAnchorCommission(videoShopSalarytemplate.getQuantityAnchorCommission());
            newVideoShopSalarytemplate.setQuantityHelperCommission(videoShopSalarytemplate.getQuantityHelperCommission());
            newVideoShopSalarytemplate.setQuantityControlerCommission(videoShopSalarytemplate.getQuantityControlerCommission());
        }
        return videoShopSalarytemplateMapper.insertVideoShopSalarytemplate(newVideoShopSalarytemplate);
    }

    /**
     * 修改薪酬模版
     * 
     * @param videoShopSalarytemplate 薪酬模版
     * @return 结果
     */
    @Override
    public int updateVideoShopSalarytemplate(VideoShopSalarytemplate videoShopSalarytemplate)
    {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);

        videoShopSalarytemplate.getParams().put("tableIndex",videoShop.getTableIndex());
        return videoShopSalarytemplateMapper.updateVideoShopSalarytemplate(videoShopSalarytemplate);
    }

    /**
     * 批量删除薪酬模版
     * 
     * @param ids 需要删除的薪酬模版主键
     * @return 结果
     */
    @Override
    public int deleteVideoShopSalarytemplateByIds(String ids)
    {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);
        return videoShopSalarytemplateMapper.deleteVideoShopSalarytemplateByIds(Long.valueOf(videoShop.getTableIndex()),Convert.toStrArray(ids));
    }

    /**
     * 删除薪酬模版信息
     * 
     * @param id 薪酬模版主键
     * @return 结果
     */
    @Override
    public int deleteVideoShopSalarytemplateById(Integer id)
    {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);
        return videoShopSalarytemplateMapper.deleteVideoShopSalarytemplateById(Long.valueOf(videoShop.getTableIndex()),id);
    }
}
