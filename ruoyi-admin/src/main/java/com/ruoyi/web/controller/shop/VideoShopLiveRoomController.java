package com.ruoyi.web.controller.shop;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.VideoShopLiveRoom;
import com.ruoyi.system.service.IVideoShopLiveRoomService;
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
@RequestMapping("/videoShop/liveRoom")
public class VideoShopLiveRoomController extends BaseController
{
    private String prefix = "shop/liveRoom";

    @Autowired(required = false)
    private IVideoShopLiveRoomService videoShopLiveRoomService;

//    @GetMapping()
//    @ResponseBody
//    public Object order()
//    {
//        VideoShopOrder videoShopOrder = new VideoShopOrder();
//        List<VideoShopOrder> list = videoShopOrderService.selectVideoShopOrderList(videoShopOrder);
//        HashMap<String,Object> hashMap = new HashMap<String,Object>();
//        hashMap.put("qqq","normal");
//        hashMap.put("beginDateTime","beginDateTime");
//        hashMap.put("endDateTime","endDateTime");
//        hashMap.put("type","type");
//        return new ModelAndView(prefix + "/order","q",hashMap);
//    }

    /**
     * 查询直播间表列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(VideoShopLiveRoom videoShopLiveRoom)
    {
        startPage();
        List<VideoShopLiveRoom> list = videoShopLiveRoomService.selectLiveRoomList(videoShopLiveRoom);
        return getDataTable(list);
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
     * 新增保存直播间表
     */
    @Log(title = "直播间表", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(VideoShopLiveRoom videoShopLiveRoom)
    {
        return toAjax(videoShopLiveRoomService.insertVideoShopOrder(videoShopLiveRoom));
    }

    /**
     * 修改直播间
     */
    @GetMapping("/addScheduling/{roomId}")
    public String edit(@PathVariable("roomId") Integer roomId, ModelMap mmap)
    {
//        userService.checkUserDataScope(userId);
//        List<SysRole> roles = roleService.selectRolesByUserId(userId);
//        mmap.put("user", userService.selectUserById(userId));
//        mmap.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        mmap.put("roomId", roomId);
        return "shop/scheduling/scheduling";
    }

    /**
     * 删除直播间
     */
    @PostMapping( "/remove/{id}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("id")String id)
    {
        int result=videoShopLiveRoomService.deleteVideoShopLiveRoomById(id);

        if(result == -1){
            return error("该直播间存在排班，删除会影响数据，不允许删除");
        }
        return toAjax(result);
    }
}
