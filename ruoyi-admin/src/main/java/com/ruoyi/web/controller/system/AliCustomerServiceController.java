package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.web.Util.UUIDGenerator;
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
import com.ruoyi.system.domain.AliCustomerService;
import com.ruoyi.system.service.IAliCustomerServiceService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 【请填写功能名称】Controller
 * 
 * @author ruoyi
 * @date 2024-05-05
 */
@Controller
@RequestMapping("/system/service")
public class AliCustomerServiceController extends BaseController
{
    private String prefix = "system/service";

    @Autowired
    private IAliCustomerServiceService aliCustomerServiceService;

    UUIDGenerator uuidGenerator = new UUIDGenerator();

    @RequiresPermissions("system:service:view")
    @GetMapping()
    public String service()
    {
        return prefix + "/service";
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("system:service:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(AliCustomerService aliCustomerService)
    {
        startPage();
        List<AliCustomerService> list = aliCustomerServiceService.selectAliCustomerServiceList(aliCustomerService);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("system:service:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AliCustomerService aliCustomerService)
    {
        List<AliCustomerService> list = aliCustomerServiceService.selectAliCustomerServiceList(aliCustomerService);
        ExcelUtil<AliCustomerService> util = new ExcelUtil<AliCustomerService>(AliCustomerService.class);
        return util.exportExcel(list, "【请填写功能名称】数据");
    }

    /**
     * 新增【请填写功能名称】
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存【请填写功能名称】
     */
    @RequiresPermissions("system:service:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AliCustomerService aliCustomerService)
    {
        String UUID = uuidGenerator.setUUID();
        aliCustomerService.setId(UUID);
        return toAjax(aliCustomerServiceService.insertAliCustomerService(aliCustomerService));
    }

    /**
     * 修改【请填写功能名称】
     */
    @RequiresPermissions("system:service:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        AliCustomerService aliCustomerService = aliCustomerServiceService.selectAliCustomerServiceById(id);
        mmap.put("aliCustomerService", aliCustomerService);
        return prefix + "/edit";
    }

    /**
     * 修改保存【请填写功能名称】
     */
    @RequiresPermissions("system:service:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AliCustomerService aliCustomerService)
    {
        return toAjax(aliCustomerServiceService.updateAliCustomerService(aliCustomerService));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("system:service:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(aliCustomerServiceService.deleteAliCustomerServiceByIds(ids));
    }
}
