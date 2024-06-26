package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.VideoShopSalarytemplateMapper;
import com.ruoyi.system.domain.VideoShopSalarytemplate;
import com.ruoyi.system.service.IVideoShopSalarytemplateService;
import com.ruoyi.common.core.text.Convert;

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
        return videoShopSalarytemplateMapper.insertVideoShopSalarytemplate(videoShopSalarytemplate);
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
        return videoShopSalarytemplateMapper.deleteVideoShopSalarytemplateByIds(Convert.toStrArray(ids));
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
        return videoShopSalarytemplateMapper.deleteVideoShopSalarytemplateById(id);
    }
}
