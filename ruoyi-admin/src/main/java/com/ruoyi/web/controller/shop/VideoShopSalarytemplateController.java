package com.ruoyi.web.controller.shop;

import java.util.List;

import com.ruoyi.system.service.IVideoShopSchedulingService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.VideoShopSalarytemplate;
import com.ruoyi.system.service.IVideoShopSalarytemplateService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 薪酬模版Controller
 * 
 * @author CaiXY
 * @date 2024-06-20
 */
@Controller
@RequestMapping("/system/salarytemplate")
public class VideoShopSalarytemplateController extends BaseController
{
    private String prefix = "system/salarytemplate";

    @Autowired
    private IVideoShopSalarytemplateService videoShopSalarytemplateService;

    @Autowired
    private IVideoShopSchedulingService videoShopSchedulingService;

    @RequiresPermissions("system:salarytemplate:view")
    @GetMapping()
    public String salarytemplate()
    {
        return prefix + "/salarytemplate";
    }

    /**
     * 查询薪酬模版列表
     */
    @RequiresPermissions("system:salarytemplate:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(VideoShopSalarytemplate videoShopSalarytemplate)
    {
        startPage();
        List<VideoShopSalarytemplate> list = videoShopSalarytemplateService.selectVideoShopSalarytemplateList(videoShopSalarytemplate);
        return getDataTable(list);
    }

    /**
     * 导出薪酬模版列表
     */
    @RequiresPermissions("system:salarytemplate:export")
    @Log(title = "薪酬模版", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(VideoShopSalarytemplate videoShopSalarytemplate)
    {
        List<VideoShopSalarytemplate> list = videoShopSalarytemplateService.selectVideoShopSalarytemplateList(videoShopSalarytemplate);
        ExcelUtil<VideoShopSalarytemplate> util = new ExcelUtil<VideoShopSalarytemplate>(VideoShopSalarytemplate.class);
        return util.exportExcel(list, "薪酬模版数据");
    }

    /**
     * 新增薪酬模版
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存薪酬模版
     */
    @RequiresPermissions("system:salarytemplate:add")
    @Log(title = "薪酬模版", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(VideoShopSalarytemplate videoShopSalarytemplate)
    {
        return toAjax(videoShopSalarytemplateService.insertVideoShopSalarytemplate(videoShopSalarytemplate));
    }

    /**
     * 修改薪酬模版
     */
    @RequiresPermissions("system:salarytemplate:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap)
    {
        VideoShopSalarytemplate videoShopSalarytemplate = videoShopSalarytemplateService.selectVideoShopSalarytemplateById(id);
        mmap.put("videoShopSalarytemplate", videoShopSalarytemplate);
        return prefix + "/edit";
    }

    /**
     * 修改保存薪酬模版
     */
    @RequiresPermissions("system:salarytemplate:edit")
    @Log(title = "薪酬模版", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(VideoShopSalarytemplate videoShopSalarytemplate)
    {
        return toAjax(videoShopSalarytemplateService.updateVideoShopSalarytemplate(videoShopSalarytemplate));
    }

    /**
     * 删除薪酬模版
     */
    @RequiresPermissions("system:salarytemplate:remove")
    @Log(title = "薪酬模版", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        //存在相应排班信息则不允许删除
        int count = videoShopSchedulingService.selectCountByTemplateId(ids);

        if(count>0){
            return AjaxResult.error("该模版已被应用，拒绝删除");
        }
        return toAjax(videoShopSalarytemplateService.deleteVideoShopSalarytemplateByIds(ids));
    }
}
