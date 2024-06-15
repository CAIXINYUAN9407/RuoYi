package com.ruoyi.web.controller.shop;

import java.util.List;
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
import com.ruoyi.system.domain.VideoShop;
import com.ruoyi.system.service.IVideoShopService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 店铺Controller
 * 
 * @author CaiXY
 * @date 2024-06-15
 */
@Controller
@RequestMapping("/system/shop")
public class VideoShopController extends BaseController
{
    private String prefix = "system/shop";

    @Autowired
    private IVideoShopService videoShopService;

    @RequiresPermissions("system:shop:view")
    @GetMapping()
    public String shop()
    {
        return prefix + "/shop";
    }

    /**
     * 查询店铺列表
     */
    @RequiresPermissions("system:shop:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(VideoShop videoShop)
    {
        startPage();
        List<VideoShop> list = videoShopService.selectVideoShopList(videoShop);
        return getDataTable(list);
    }

    /**
     * 导出店铺列表
     */
    @RequiresPermissions("system:shop:export")
    @Log(title = "店铺", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(VideoShop videoShop)
    {
        List<VideoShop> list = videoShopService.selectVideoShopList(videoShop);
        ExcelUtil<VideoShop> util = new ExcelUtil<VideoShop>(VideoShop.class);
        return util.exportExcel(list, "店铺数据");
    }

    /**
     * 新增店铺
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存店铺
     */
    @RequiresPermissions("system:shop:add")
    @Log(title = "店铺", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(VideoShop videoShop)
    {
        return toAjax(videoShopService.insertVideoShop(videoShop));
    }

    /**
     * 修改店铺
     */
    @RequiresPermissions("system:shop:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        VideoShop videoShop = videoShopService.selectVideoShopById(id);
        mmap.put("videoShop", videoShop);
        return prefix + "/edit";
    }

    /**
     * 修改保存店铺
     */
    @RequiresPermissions("system:shop:edit")
    @Log(title = "店铺", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(VideoShop videoShop)
    {
        return toAjax(videoShopService.updateVideoShop(videoShop));
    }

    /**
     * 删除店铺
     */
    @RequiresPermissions("system:shop:remove")
    @Log(title = "店铺", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(videoShopService.deleteVideoShopByIds(ids));
    }
}
