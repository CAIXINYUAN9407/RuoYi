package com.ruoyi.web.controller.shop;

import java.util.*;

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
import com.ruoyi.system.domain.VideoShopChannelsFinder;
import com.ruoyi.system.service.IVideoShopChannelsFinderService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 优选联盟达人Controller
 *
 * @author CaiXY
 * @date 2024-07-18
 */
@Controller
@RequestMapping("/shop/channelsFinder")
public class VideoShopChannelsFinderController extends BaseController
{
    private String prefix = "shop/channelsFinder";

    @Autowired
    private IVideoShopChannelsFinderService videoShopChannelsFinderService;

    @RequiresPermissions("shop:channelsFinder:view")
    @GetMapping()
    public String channelsFinder()
    {
        return prefix + "/list";
    }

    /**
     * 查询优选联盟达人列表
     */
    @RequiresPermissions("shop:channelsFinder:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(VideoShopChannelsFinder videoShopChannelsFinder)
    {
        startPage();
        List<VideoShopChannelsFinder> list = videoShopChannelsFinderService.selectVideoShopChannelsFinderList(videoShopChannelsFinder);
        return getDataTable(list);
    }

    /**
     * 导出优选联盟达人列表
     */
    @RequiresPermissions("shop:channelsFinder:export")
    @Log(title = "优选联盟达人", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(VideoShopChannelsFinder videoShopChannelsFinder)
    {
        List<VideoShopChannelsFinder> list = videoShopChannelsFinderService.selectVideoShopChannelsFinderList(videoShopChannelsFinder);
        ExcelUtil<VideoShopChannelsFinder> util = new ExcelUtil<VideoShopChannelsFinder>(VideoShopChannelsFinder.class);
        return util.exportExcel(list, "优选联盟达人数据");
    }

    /**
     * 新增优选联盟达人
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存优选联盟达人
     */
    @RequiresPermissions("shop:channelsFinder:add")
    @Log(title = "优选联盟达人", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(VideoShopChannelsFinder videoShopChannelsFinder)
    {
        return toAjax(videoShopChannelsFinderService.insertVideoShopChannelsFinder(videoShopChannelsFinder));
    }

    /**
     * 修改优选联盟达人
     */
    @RequiresPermissions("shop:channelsFinder:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        VideoShopChannelsFinder videoShopChannelsFinder = videoShopChannelsFinderService.selectVideoShopChannelsFinderById(id);
        mmap.put("videoShopChannelsFinder", videoShopChannelsFinder);
        return prefix + "/edit";
    }

    /**
     * 修改保存优选联盟达人
     */
    @RequiresPermissions("shop:channelsFinder:edit")
    @Log(title = "优选联盟达人", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(VideoShopChannelsFinder videoShopChannelsFinder)
    {

        return toAjax(videoShopChannelsFinderService.updateVideoShopChannelsFinder(videoShopChannelsFinder));
    }

    /**
     * 删除优选联盟达人
     */
    @RequiresPermissions("shop:channelsFinder:remove")
    @Log(title = "优选联盟达人", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        int result = videoShopChannelsFinderService.deleteVideoShopChannelsFinderByIds(ids);
        if(result == -1){
            return AjaxResult.error("该账号下有相关订单数据，不允许删除");
        }
        else{
            return AjaxResult.success("删除成功");
        }
    }

    @GetMapping("/dataPage")
    public String dataPage(VideoShopChannelsFinder videoShopChannelsFinder)
    {
        startPage();
        List<VideoShopChannelsFinder> list = videoShopChannelsFinderService.selectVideoShopChannelsFinderList(videoShopChannelsFinder);
        return prefix + "/dataPage";
    }

    /**
     * 查询优选联盟达人列表
     */
    @PostMapping("/dataPage/list")
    @ResponseBody
    public TableDataInfo dataPageList(VideoShopChannelsFinder videoShopChannelsFinder)
    {
        startPage();
        List<VideoShopChannelsFinder> list = videoShopChannelsFinderService.selectVideoShopChannelsFinderList(videoShopChannelsFinder);
        List<HashMap<String,Object>> mapList = new ArrayList<HashMap<String, Object>>();
        for(int i=0;i<10;i++){
            HashMap<String,Object> hashMap = new HashMap<String,Object>();
            hashMap.put("nickname","测试"+i);
            mapList.add(hashMap);
        }

        mapList = videoShopChannelsFinderService.getChannelsFinderCountData(videoShopChannelsFinder);
        return getDataTable(mapList);
    }
}