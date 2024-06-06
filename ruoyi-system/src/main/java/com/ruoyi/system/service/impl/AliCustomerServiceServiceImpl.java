package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.AliCustomerServiceMapper;
import com.ruoyi.system.domain.AliCustomerService;
import com.ruoyi.system.service.IAliCustomerServiceService;
import com.ruoyi.common.core.text.Convert;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-05-05
 */
@Service
public class AliCustomerServiceServiceImpl implements IAliCustomerServiceService 
{
    @Autowired
    private AliCustomerServiceMapper aliCustomerServiceMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public AliCustomerService selectAliCustomerServiceById(String id)
    {
        return aliCustomerServiceMapper.selectAliCustomerServiceById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param aliCustomerService 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<AliCustomerService> selectAliCustomerServiceList(AliCustomerService aliCustomerService)
    {
        return aliCustomerServiceMapper.selectAliCustomerServiceList(aliCustomerService);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param aliCustomerService 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertAliCustomerService(AliCustomerService aliCustomerService)
    {
        return aliCustomerServiceMapper.insertAliCustomerService(aliCustomerService);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param aliCustomerService 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateAliCustomerService(AliCustomerService aliCustomerService)
    {
        return aliCustomerServiceMapper.updateAliCustomerService(aliCustomerService);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteAliCustomerServiceByIds(String ids)
    {
        return aliCustomerServiceMapper.deleteAliCustomerServiceByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteAliCustomerServiceById(String id)
    {
        return aliCustomerServiceMapper.deleteAliCustomerServiceById(id);
    }
}
