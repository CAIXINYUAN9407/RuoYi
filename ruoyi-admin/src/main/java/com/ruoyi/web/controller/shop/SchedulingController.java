package com.ruoyi.web.controller.shop;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.domain.VideoShopScheduling;
import com.ruoyi.system.mapper.VideoShopSalarytemplateMapper;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.IVideoShopSalarytemplateService;
import com.ruoyi.system.service.IVideoShopSchedulingService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.apache.shiro.SecurityUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 用户信息
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/shop/scheduling")
public class SchedulingController extends BaseController
{
    private String prefix = "shop/scheduling";

    @Autowired
    private ISysUserService userService;

    @Autowired
    private IVideoShopSalarytemplateService videoShopSalarytemplateService;

    @Autowired
    private IVideoShopSchedulingService videoShopSchedulingService;

    @RequiresPermissions("system:user:view")
    @GetMapping()
    public String user()
    {
        return prefix + "/test";
    }

    @RequiresPermissions("system:user:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysUser user)
    {


        startPage();
        List<SysUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    @PostMapping("/GetCalendarList")
    @ResponseBody
    public List<HashMap<String,Object>> GetCalendarList(VideoShopScheduling videoShopScheduling)
    {
        String username = ShiroUtils.getLoginName();
        List<VideoShopScheduling> list = videoShopSchedulingService.selectVideoShopSchedulingList(videoShopScheduling);
        List<HashMap<String,Object>> listMap = videoShopSchedulingService.getCalendarList(videoShopScheduling);
        return listMap;
    }


    @PostMapping("/addScheduling")
    @ResponseBody
    public AjaxResult addScheduling(@RequestBody HashMap<String, Object> data) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date  Begindate = sdf.parse((String) data.get("timeBegin"));
        Date  Enddate = sdf.parse((String) data.get("timeEnd"));

        int count = videoShopSchedulingService.selectCountByTimeBetween(Long.valueOf((String) data.get("anchorId")),(String) data.get("timeBegin"),(String) data.get("timeEnd"));

        if(count>0){
            return AjaxResult.error("该时间段已存在直播排班，请重新设置");
        }
        VideoShopScheduling videoShopScheduling = new VideoShopScheduling();
        videoShopScheduling.setAnchorId(Long.valueOf((String) data.get("anchorId")));
        videoShopScheduling.setAnchorName((String) data.get("anchorName"));
        videoShopScheduling.setTemplateId(Long.valueOf((String) data.get("templateId")));
        videoShopScheduling.setTemplateName((String) data.get("templateName"));



        videoShopScheduling.setStarttime(Begindate);
        videoShopScheduling.setEndtime(Enddate);

        return toAjax(videoShopSchedulingService.insertVideoShopScheduling(videoShopScheduling));
    }

    @PostMapping("/updateScheduling")
    @ResponseBody
    public AjaxResult updateScheduling(@RequestBody HashMap<String, Object> data) throws ParseException {

        int count = videoShopSchedulingService.selectCountByTimeBetween(Long.valueOf((String) data.get("anchorId")),(String) data.get("timeBegin"),(String) data.get("timeEnd"));
        if(count>0){
            return AjaxResult.error("该时间段已存在直播排班，请重新设置");
        }
        VideoShopScheduling videoShopScheduling = videoShopSchedulingService.selectVideoShopSchedulingById(Long.valueOf((String) data.get("Id")));
        videoShopScheduling.setAnchorId(Long.valueOf((String) data.get("anchorId")));
        videoShopScheduling.setAnchorName((String) data.get("anchorName"));
        videoShopScheduling.setTemplateId(Long.valueOf((String) data.get("templateId")));
        videoShopScheduling.setTemplateName((String) data.get("templateName"));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date  Begindate = sdf.parse((String) data.get("timeBegin"));
        Date  Enddate = sdf.parse((String) data.get("timeEnd"));

        videoShopScheduling.setStarttime(Begindate);
        videoShopScheduling.setEndtime(Enddate);

        return toAjax(videoShopSchedulingService.updateVideoShopScheduling(videoShopScheduling));
    }


}