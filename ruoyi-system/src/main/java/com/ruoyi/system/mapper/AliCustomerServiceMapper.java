package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.AliCustomerService;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2024-05-05
 */
public interface AliCustomerServiceMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public AliCustomerService selectAliCustomerServiceById(String id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param aliCustomerService 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<AliCustomerService> selectAliCustomerServiceList(AliCustomerService aliCustomerService);

    /**
     * 新增【请填写功能名称】
     * 
     * @param aliCustomerService 【请填写功能名称】
     * @return 结果
     */
    public int insertAliCustomerService(AliCustomerService aliCustomerService);

    /**
     * 修改【请填写功能名称】
     * 
     * @param aliCustomerService 【请填写功能名称】
     * @return 结果
     */
    public int updateAliCustomerService(AliCustomerService aliCustomerService);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteAliCustomerServiceById(String id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAliCustomerServiceByIds(String[] ids);
}
