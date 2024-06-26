package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.VideoShopSalarytemplate;

/**
 * 薪酬模版Service接口
 * 
 * @author CaiXY
 * @date 2024-06-20
 */
public interface IVideoShopSalarytemplateService 
{
    /**
     * 查询薪酬模版
     * 
     * @param id 薪酬模版主键
     * @return 薪酬模版
     */
    public VideoShopSalarytemplate selectVideoShopSalarytemplateById(Integer id);

    /**
     * 查询薪酬模版列表
     * 
     * @param videoShopSalarytemplate 薪酬模版
     * @return 薪酬模版集合
     */
    public List<VideoShopSalarytemplate> selectVideoShopSalarytemplateList(VideoShopSalarytemplate videoShopSalarytemplate);

    /**
     * 新增薪酬模版
     * 
     * @param videoShopSalarytemplate 薪酬模版
     * @return 结果
     */
    public int insertVideoShopSalarytemplate(VideoShopSalarytemplate videoShopSalarytemplate);

    /**
     * 修改薪酬模版
     * 
     * @param videoShopSalarytemplate 薪酬模版
     * @return 结果
     */
    public int updateVideoShopSalarytemplate(VideoShopSalarytemplate videoShopSalarytemplate);

    /**
     * 批量删除薪酬模版
     * 
     * @param ids 需要删除的薪酬模版主键集合
     * @return 结果
     */
    public int deleteVideoShopSalarytemplateByIds(String ids);

    /**
     * 删除薪酬模版信息
     * 
     * @param id 薪酬模版主键
     * @return 结果
     */
    public int deleteVideoShopSalarytemplateById(Integer id);
}
