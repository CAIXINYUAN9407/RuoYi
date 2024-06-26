package com.ruoyi.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 主播信息对象 video_shop_anchor_1
 * 
 * @author CaiXY
 * @date 2024-06-24
 */
public class VideoShopAnchor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /**  */
    @Excel(name = "")
    private Long shopId;

    /** 主播名称 */
    @Excel(name = "主播名称")
    private String name;

    /** 直播账号 */
    @Excel(name = "直播账号")
    private String liveAccount;

    /** 底薪 */
    @Excel(name = "底薪")
    private BigDecimal baseSalary;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setShopId(Long shopId) 
    {
        this.shopId = shopId;
    }

    public Long getShopId() 
    {
        return shopId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setLiveAccount(String liveAccount) 
    {
        this.liveAccount = liveAccount;
    }

    public String getLiveAccount() 
    {
        return liveAccount;
    }
    public void setBaseSalary(BigDecimal baseSalary) 
    {
        this.baseSalary = baseSalary;
    }

    public BigDecimal getBaseSalary() 
    {
        return baseSalary;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("shopId", getShopId())
            .append("name", getName())
            .append("liveAccount", getLiveAccount())
            .append("baseSalary", getBaseSalary())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
