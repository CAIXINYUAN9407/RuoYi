package com.ruoyi.web.controller.shop;

import java.util.HashMap;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.VideoShopOrder;
import com.ruoyi.system.service.IVideoShopOrderService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.servlet.ModelAndView;

/**
 * 订单表Controller
 * 
 * @author CaiXY
 * @date 2024-06-21
 */
@Controller
@RequestMapping("/system/order")
public class VideoShopOrderController extends BaseController
{
    private String prefix = "system/order";

    @Autowired
    private IVideoShopOrderService videoShopOrderService;

    @RequiresPermissions("system:order:view")
    @GetMapping()
    @ResponseBody
    public Object order()
    {
        VideoShopOrder videoShopOrder = new VideoShopOrder();
        List<VideoShopOrder> list = videoShopOrderService.selectVideoShopOrderList(videoShopOrder);
        HashMap<String,Object> hashMap = new HashMap<String,Object>();
        hashMap.put("qqq","normal");
        hashMap.put("beginDateTime","beginDateTime");
        hashMap.put("endDateTime","endDateTime");
        hashMap.put("type","type");
        return new ModelAndView(prefix + "/order","q",hashMap);
    }

    /**
     * 查询订单表列表
     */
    @RequiresPermissions("system:order:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(VideoShopOrder videoShopOrder)
    {
//        startPage();
        List<VideoShopOrder> list = videoShopOrderService.selectVideoShopOrderList(videoShopOrder);
        return getDataTable(list);
    }

    /**
     * 查询订单表列表
     */
    @RequiresPermissions("system:order:list")
    @GetMapping("/goToUrl")
    @ResponseBody
    public Object goToUrl(@RequestParam("date") String date, @RequestParam("etc") String etc, @RequestParam("type") String type)
    {
//        startPage();
        VideoShopOrder videoShopOrder = new VideoShopOrder();
        String beginDateTime = date +" "+ etc.substring(0,8);
        String endDateTime = date +  " " +etc.substring(9);
        HashMap<String,Object> hashMap = new HashMap<String,Object>();
        hashMap.put("qqq","goToUrl");
        hashMap.put("beginDateTime",beginDateTime);
        hashMap.put("endDateTime",endDateTime);
        if(type.equals("10")){
            hashMap.put("type","(10,20,21,30,100,200,250)");
        }
        else if(type.equals("11")){
            hashMap.put("type","(20,21,30,100)");
        } else if (type.equals("22")) {
            hashMap.put("type","(250)");
        }
        return new ModelAndView(prefix + "/order","q",hashMap);
    }
    /**
     * 导出订单表列表
     */
    @Log(title = "订单表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(VideoShopOrder videoShopOrder)
    {
//        ExcelUtil<HashMap<String,Object>> util = new ExcelUtil<HashMap<String,Object>>();

        List<VideoShopOrder> list = videoShopOrderService.selectVideoShopOrderListExport(videoShopOrder);
        ExcelUtil<VideoShopOrder> util = new ExcelUtil<VideoShopOrder>(VideoShopOrder.class);
        util.hideColumn("id", "shopId","thumbImg","skuId","productCnt","openid","userName","telNumber","virtualTelNumber","virtualTelExpireTime","getVirtualTelCnt","provinceName","cityName","countyName","detailInfo");
//        util.addR("1",1,1,1,1);
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
     * 解密订单手机号
     */
    @PostMapping( "/decryptTel")
    @ResponseBody
    public AjaxResult decryptTel(String orderId,String localShopId)
    {
        return success(videoShopOrderService.decryptTel(orderId,localShopId));
    }
}
