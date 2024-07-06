package com.ruoyi.web.controller.shop;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.framework.shiro.service.SysPasswordService;
import com.ruoyi.system.domain.VideoShopAnchor;
import com.ruoyi.system.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * 用户信息
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/anchor")
public class CustomerController extends BaseController
{
    private String prefix = "shop/anchor";

    @Autowired
    private ISysUserService userService;
    @Autowired
    private IVideoShopAnchorService videoShopAnchorService;

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

    @PostMapping("/getAnchorList")
    @ResponseBody
    public List<HashMap<String, Object>> getAnchorList(VideoShopAnchor videoShopAnchor)
    {
        List<HashMap<String, Object>> ruseltList = videoShopAnchorService.getVideoShopAnchorList();
        return ruseltList;
    }

    @PostMapping("/getTemplateList")
    @ResponseBody
    public List<HashMap<String, Object>> getTemplateList(VideoShopAnchor videoShopAnchor)
    {
        List<HashMap<String, Object>> ruseltList = videoShopAnchorService.getVideoShopTemplateList();
        return ruseltList;
    }


}