package com.ruoyi.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 ali_customer_service
 * 
 * @author ruoyi
 * @date 2024-05-05
 */
public class AliCustomerService extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** 基础工资 */
    @Excel(name = "基础工资")
    private Long basicSalary;

    /** 父账号 */
    @Excel(name = "父账号")
    private String upId;

    /** 提成比率 */
    @Excel(name = "提成比率")
    private Long commissionRatio;

    /** 总工资 */
    @Excel(name = "总工资")
    private BigDecimal totalSalary;

    /** 销售金额 */
    @Excel(name = "销售金额")
    private BigDecimal salesAmount;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setBasicSalary(Long basicSalary) 
    {
        this.basicSalary = basicSalary;
    }

    public Long getBasicSalary() 
    {
        return basicSalary;
    }
    public void setUpId(String upId) 
    {
        this.upId = upId;
    }

    public String getUpId() 
    {
        return upId;
    }
    public void setCommissionRatio(Long commissionRatio) 
    {
        this.commissionRatio = commissionRatio;
    }

    public Long getCommissionRatio() 
    {
        return commissionRatio;
    }
    public void setTotalSalary(BigDecimal totalSalary) 
    {
        this.totalSalary = totalSalary;
    }

    public BigDecimal getTotalSalary() 
    {
        return totalSalary;
    }
    public void setsalesAmount(BigDecimal
salesAmount) 
    {
        this.
salesAmount = 
salesAmount;
    }

    public BigDecimal getsalesAmount()
    {
        return 
salesAmount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("basicSalary", getBasicSalary())
            .append("upId", getUpId())
            .append("commissionRatio", getCommissionRatio())
            .append("totalSalary", getTotalSalary())
            .append("salesAmount", getsalesAmount())
            .toString();
    }
}
