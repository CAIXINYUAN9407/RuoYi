package com.ruoyi.web.controller.shop;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.VideoShopOrder;
import com.ruoyi.system.service.IVideoShopOrderService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单表Controller
 * 
 * @author CaiXY
 * @date 2024-06-21
 */
@Controller
@RequestMapping("/shop/profit")
public class VideoShopProfitController extends BaseController
{
    private String prefix = "shop/profit";

    @Autowired
    private IVideoShopOrderService videoShopOrderService;

    @RequiresPermissions("system:order:view")
    @GetMapping()
    public String order()
    {
        return prefix + "/order";
    }

    /**
     * 查询订单表列表
     */
    @RequiresPermissions("system:order:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(VideoShopOrder videoShopOrder)
    {
        startPage();
        List<VideoShopOrder> list = videoShopOrderService.selectVideoShopOrderList(videoShopOrder);
        return getDataTable(list);
    }

    /**
     * 导出订单表列表
     */
    @RequiresPermissions("system:order:export")
    @Log(title = "订单表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(VideoShopOrder videoShopOrder)
    {
        List<VideoShopOrder> list = videoShopOrderService.selectVideoShopOrderList(videoShopOrder);
        ExcelUtil<VideoShopOrder> util = new ExcelUtil<VideoShopOrder>(VideoShopOrder.class);
        return util.exportExcel(list, "订单表数据");
    }

    /**
     * 新增订单表
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存订单表
     */
    @RequiresPermissions("system:order:add")
    @Log(title = "订单表", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(VideoShopOrder videoShopOrder)
    {
        return toAjax(videoShopOrderService.insertVideoShopOrder(videoShopOrder));
    }

    /**
     * 修改订单表
     */
    @RequiresPermissions("system:order:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        VideoShopOrder videoShopOrder = videoShopOrderService.selectVideoShopOrderById(id);
        mmap.put("videoShopOrder", videoShopOrder);
        return prefix + "/edit";
    }

    /**
     * 修改保存订单表
     */
    @RequiresPermissions("system:order:edit")
    @Log(title = "订单表", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(VideoShopOrder videoShopOrder)
    {
        return toAjax(videoShopOrderService.updateVideoShopOrder(videoShopOrder));
    }

    /**
     * 删除订单表
     */
    @RequiresPermissions("system:order:remove")
    @Log(title = "订单表", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(videoShopOrderService.deleteVideoShopOrderByIds(ids));
    }

    /**
     * 成本管理
     */
    @PostMapping( "/cost")
    @ResponseBody
    public String cost(String ids)
    {
        return prefix + "/cost";
    }
}
