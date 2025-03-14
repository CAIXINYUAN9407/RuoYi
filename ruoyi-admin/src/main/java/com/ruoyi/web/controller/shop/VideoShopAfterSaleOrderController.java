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
import com.ruoyi.system.domain.VideoShopAfterSaleOrder;
import com.ruoyi.system.service.IVideoShopAfterSaleOrderService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 售后信息Controller
 *
 * @author CaiXY
 * @date 2024-07-16
 */
@Controller
@RequestMapping("/shop/afterSale")
public class VideoShopAfterSaleOrderController extends BaseController
{
    private String prefix = "shop/afterSale";

    @Autowired
    private IVideoShopAfterSaleOrderService videoShopAfterSaleOrderService;

    @RequiresPermissions("shop:afterSale:view")
    @GetMapping()
    public String afterSale()
    {
        return prefix + "/list";
    }

    /**
     * 查询售后信息列表
     */
    @RequiresPermissions("shop:afterSale:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(VideoShopAfterSaleOrder videoShopAfterSaleOrder)
    {
        List<VideoShopAfterSaleOrder> list = videoShopAfterSaleOrderService.selectVideoShopAfterSaleOrderList(videoShopAfterSaleOrder);
        return getDataTable(list);
    }

    /**
     * 导出售后信息列表
     */
    @RequiresPermissions("shop:afterSale:export")
    @Log(title = "售后信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(VideoShopAfterSaleOrder videoShopAfterSaleOrder)
    {
        List<VideoShopAfterSaleOrder> list = videoShopAfterSaleOrderService.selectVideoShopAfterSaleOrderList(videoShopAfterSaleOrder);
        ExcelUtil<VideoShopAfterSaleOrder> util = new ExcelUtil<VideoShopAfterSaleOrder>(VideoShopAfterSaleOrder.class);
        return util.exportExcel(list, "售后信息数据");
    }

    /**
     * 新增售后信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存售后信息
     */
    @RequiresPermissions("shop:afterSale:add")
    @Log(title = "售后信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(VideoShopAfterSaleOrder videoShopAfterSaleOrder)
    {
        return toAjax(videoShopAfterSaleOrderService.insertVideoShopAfterSaleOrder(videoShopAfterSaleOrder));
    }

    /**
     * 修改售后信息
     */
    @RequiresPermissions("shop:afterSale:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        VideoShopAfterSaleOrder videoShopAfterSaleOrder = videoShopAfterSaleOrderService.selectVideoShopAfterSaleOrderById(id);
        mmap.put("videoShopAfterSaleOrder", videoShopAfterSaleOrder);
        return prefix + "/edit";
    }
    /**
     * 修改保存售后信息
     */
    @RequiresPermissions("shop:afterSale:edit")
    @Log(title = "售后信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(VideoShopAfterSaleOrder videoShopAfterSaleOrder)
    {
        return toAjax(videoShopAfterSaleOrderService.updateVideoShopAfterSaleOrder(videoShopAfterSaleOrder));
    }


    /**
     * 删除售后信息
     */
    @RequiresPermissions("shop:afterSale:remove")
    @Log(title = "售后信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(videoShopAfterSaleOrderService.deleteVideoShopAfterSaleOrderByIds(ids));
    }
}