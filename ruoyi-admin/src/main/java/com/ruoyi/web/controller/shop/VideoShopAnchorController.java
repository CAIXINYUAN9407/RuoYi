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
import com.ruoyi.system.domain.VideoShopAnchor;
import com.ruoyi.system.service.IVideoShopAnchorService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 主播信息Controller
 * 
 * @author CaiXY
 * @date 2024-06-24
 */
@Controller
@RequestMapping("/shop/anchor")
public class VideoShopAnchorController extends BaseController
{
    private String prefix = "shop/anchor";

    @Autowired
    private IVideoShopAnchorService videoShopAnchorService;

    @RequiresPermissions("shop:anchor:view")
    @GetMapping()
    public String anchor()
    {
        return prefix + "/anchor";
    }

    /**
     * 查询主播信息列表
     */
    @RequiresPermissions("shop:anchor:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(VideoShopAnchor videoShopAnchor)
    {
        startPage();
        List<VideoShopAnchor> list = videoShopAnchorService.selectVideoShopAnchorList(videoShopAnchor);
        return getDataTable(list);
    }

    /**
     * 导出主播信息列表
     */
    @RequiresPermissions("shop:anchor:export")
    @Log(title = "主播信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(VideoShopAnchor videoShopAnchor)
    {
        List<VideoShopAnchor> list = videoShopAnchorService.selectVideoShopAnchorList(videoShopAnchor);
        ExcelUtil<VideoShopAnchor> util = new ExcelUtil<VideoShopAnchor>(VideoShopAnchor.class);
        return util.exportExcel(list, "主播信息数据");
    }

    /**
     * 新增主播信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存主播信息
     */
    @RequiresPermissions("shop:anchor:add")
    @Log(title = "主播信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(VideoShopAnchor videoShopAnchor)
    {
        return toAjax(videoShopAnchorService.insertVideoShopAnchor(videoShopAnchor));
    }

    /**
     * 修改主播信息
     */
    @RequiresPermissions("shop:anchor:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        VideoShopAnchor videoShopAnchor = videoShopAnchorService.selectVideoShopAnchorById(id);
        mmap.put("videoShopAnchor", videoShopAnchor);
        return prefix + "/edit";
    }

    /**
     * 修改保存主播信息
     */
    @RequiresPermissions("shop:anchor:edit")
    @Log(title = "主播信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(VideoShopAnchor videoShopAnchor)
    {
        return toAjax(videoShopAnchorService.updateVideoShopAnchor(videoShopAnchor));
    }

    /**
     * 删除主播信息
     */
    @RequiresPermissions("shop:anchor:remove")
    @Log(title = "主播信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(videoShopAnchorService.deleteVideoShopAnchorByIds(ids));
    }
}
