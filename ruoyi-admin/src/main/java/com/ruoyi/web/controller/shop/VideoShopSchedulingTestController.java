package com.ruoyi.web.controller.shop;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.VideoShop;
import com.ruoyi.system.domain.VideoShopLiveRoom;
import com.ruoyi.system.domain.VideoShopScheduling;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.IVideoShopLiveRoomService;
import com.ruoyi.system.service.IVideoShopSchedulingService;
import com.ruoyi.system.service.IVideoShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 排班信息
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/videoShop/schedulingTest")
public class VideoShopSchedulingTestController extends BaseController
{
    private String prefix = "shop/scheduling";

    @Autowired
    private ISysUserService userService;

    @Autowired
    private IVideoShopService videoShopService;

    @Autowired
    private IVideoShopSchedulingService videoShopSchedulingService;

    @Autowired
    private IVideoShopLiveRoomService videoShopLiveRoomService;

    @GetMapping()
    public String html()
    {
        return prefix + "/liveRoom";
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(VideoShopLiveRoom videoShopLiveRoom)
    {


        startPage();
        List<VideoShopLiveRoom> list = videoShopLiveRoomService.selectLiveRoomList(videoShopLiveRoom);
        return getDataTable(list);
    }

    @PostMapping("/GetCalendarList")
    @ResponseBody
    public List<HashMap<String,Object>> GetCalendarList(VideoShopScheduling videoShopScheduling)
    {
        List<HashMap<String,Object>> listMap = videoShopSchedulingService.getCalendarList(videoShopScheduling);
        return listMap;
    }


//    @PostMapping("/addScheduling")
//    @ResponseBody
//    public AjaxResult addScheduling(@RequestBody HashMap<String, Object> data) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        String beginDate = (String) data.get("beginDate");
//        String endDate = (String) data.get("endDate");
//
//        Date  timeBegin = sdf.parse((String) data.get("timeBegin"));
//        Date  timeEnd = sdf.parse((String) data.get("timeEnd"));
//
//        Date  beginDateEndTime = sdf.parse((String) data.get("beginDateEndTime"));
//
//
//        int count = videoShopSchedulingService.selectCountByTimeBetween_UP(Long.valueOf((String) data.get("Id")),Long.valueOf((String) data.get("anchorId")),(String) data.get("timeBegin"),(String) data.get("timeEnd"));
//
//        if(count>0){
//            return AjaxResult.error("该时间段已存在直播排班，请重新设置");
//        }
//
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(timeBegin);
//        Calendar calendar2 = Calendar.getInstance();
//        calendar2.setTime(timeEnd);
//        List<Date> dateList = new ArrayList<>();
//        while (calendar.getTime().before(timeEnd) || calendar.getTime().equals(timeEnd)){
//
//
//            VideoShopScheduling videoShopScheduling = new VideoShopScheduling();
//            videoShopScheduling.setAnchorId(Long.valueOf((String) data.get("anchorId")));
//            videoShopScheduling.setAnchorName((String) data.get("anchorName"));
//            videoShopScheduling.setTemplateId(Long.valueOf((String) data.get("templateId")));
//            videoShopScheduling.setTemplateName((String) data.get("templateName"));
//
//            videoShopScheduling.setStarttime(calendar.getTime());
//
//            long diffInMillies = calendar2.getTimeInMillis() - calendar.getTimeInMillis();
//            long diffInHours = diffInMillies / (60 * 60 * 1000);
//            if(diffInHours>24){
//                int diffDays = (int)diffInMillies / (24 * 60 * 60 * 1000);
//                calendar2.add(Calendar.DATE,-diffDays);
//                videoShopScheduling.setEndtime(calendar2.getTime());
//                calendar2.add(Calendar.DATE,diffDays);
//
//            }else{
//                videoShopScheduling.setEndtime(calendar2.getTime());
//            }
//
//
//            calendar.add(Calendar.DATE,1);
//            videoShopSchedulingService.insertVideoShopScheduling(videoShopScheduling);
//        }
//
//        return AjaxResult.success("OK");
//    }
//
//    @PostMapping("/updateScheduling")
//    @ResponseBody
//    public AjaxResult updateScheduling(@RequestBody HashMap<String, Object> data) throws ParseException {
//
////        int count = videoShopSchedulingService.selectCountByTimeBetween(Long.valueOf((String) data.get("anchorId")),(String) data.get("timeBegin"),(String) data.get("timeEnd"),Integer.valueOf((String) data.get("roomId")));
//        int count = videoShopSchedulingService.selectCountByTimeBetween_UP(Long.valueOf((String) data.get("Id")),Long.valueOf((String) data.get("anchorId")),(String) data.get("timeBegin"),(String) data.get("timeEnd"));
//
//        if(count>0){
//            return AjaxResult.error("该时间段已存在直播排班，请重新设置");
//        }
//        VideoShopScheduling videoShopScheduling = videoShopSchedulingService.selectVideoShopSchedulingById(Long.valueOf((String) data.get("Id")));
//        videoShopScheduling.setAnchorId(Long.valueOf((String) data.get("anchorId")));
//        videoShopScheduling.setAnchorName((String) data.get("anchorName"));
//        videoShopScheduling.setTemplateId(Long.valueOf((String) data.get("templateId")));
//        videoShopScheduling.setTemplateName((String) data.get("templateName"));
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date  timeBegin = sdf.parse((String) data.get("timeBegin"));
//        Date  timeEnd = sdf.parse((String) data.get("timeEnd"));
//
//        String date1 = "2012-02-26";
//        String date2 = "2012-03-04";
//
//        String dateFormat = "yyyy-MM-dd";
//        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
//        Date date11 = format.parse(date1);
//        Date date22 = format.parse(date2);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date11);
//        List<Date> dateList = new ArrayList<>();
//        while (calendar.getTime().before(date22) || calendar.getTime().equals(date22)){
//            dateList.add(calendar.getTime());
//            calendar.add(Calendar.DATE,1);
//        }
////        return AjaxResult.error();
//        videoShopScheduling.setStarttime(timeBegin);
//        videoShopScheduling.setEndtime(timeEnd);
//
//        return toAjax(videoShopSchedulingService.updateVideoShopScheduling(videoShopScheduling));
//    }
//

    /**
     * 导出排班信息列表
     */
//    @RequiresPermissions("system:scheduling:export")
    @Log(title = "排班信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(VideoShopScheduling videoShopScheduling)
    {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopService.selectVideoShopByOwner(loginName);
        videoShopScheduling.setShopId(videoShop.getId());
        List<VideoShopScheduling> list = videoShopSchedulingService.selectVideoShopSchedulingList(videoShopScheduling);
        ExcelUtil<VideoShopScheduling> util = new ExcelUtil<VideoShopScheduling>(VideoShopScheduling.class);

        return util.exportExcel(list, "排班信息数据");
    }

    @Log(title = "排班信息", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<VideoShopScheduling> util = new ExcelUtil<VideoShopScheduling>(VideoShopScheduling.class);
        List<VideoShopScheduling> videoShopSchedulingList = util.importExcel(file.getInputStream());
        //检查数据格式
        String message = videoShopSchedulingService.importScheduling(videoShopSchedulingList, updateSupport, getLoginName());
        return AjaxResult.success(message);
    }

    /**
     * 删除排班信息
     */
    @Log(title = "排班信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(videoShopSchedulingService.deleteVideoShopSchedulingByIds(ids));
    }

}


