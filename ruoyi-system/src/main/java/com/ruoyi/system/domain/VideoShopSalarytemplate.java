package com.ruoyi.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 薪酬模版对象 video_shop_salarytemplate_1
 * 
 * @author CaiXY
 * @date 2024-06-20
 */
public class VideoShopSalarytemplate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;

    /** 店铺ID */
    @Excel(name = "店铺ID")
    private Long localShopId;

    /** 模版名称 */
    @Excel(name = "模版名称")
    private String name;

    /** 模板类型 1-主播底薪 2-比例佣金 3-阶梯佣金 4-底薪+比例佣金 5-底薪+阶梯佣金 6-件数提成 */
    @Excel(name = "模板类型 1-主播底薪 2-比例佣金 3-阶梯佣金 4-底薪+比例佣金 5-底薪+阶梯佣金 6-件数提成")
    private Long type;

    /** 主播底薪 */
    @Excel(name = "主播底薪")
    private String anchorBasicSalary;

    /** 助播底薪 */
    @Excel(name = "助播底薪")
    private String helperBasicSalary;

    /** 中控底薪 */
    @Excel(name = "中控底薪")
    private String controlerBasicSalary;

    /** 比例佣金--主播默认商品佣金 */
    @Excel(name = "比例佣金--主播默认商品佣金")
    private String ratioAnchorDefault;

    /** 比例佣金--助播默认商品佣金 */
    @Excel(name = "比例佣金--助播默认商品佣金")
    private String ratioHelperDefault;

    /** 比例佣金--中控默认商品佣金 */
    @Excel(name = "比例佣金--中控默认商品佣金")
    private String ratioControlerDefault;

    /** 比例佣金--特定佣金 */
    @Excel(name = "比例佣金--特定佣金")
    private String radioGiven;

    /** 阶梯佣金 */
    @Excel(name = "阶梯佣金")
    private String ladder;

    /** 底薪+比例--主播底薪 */
    @Excel(name = "底薪+比例--主播底薪")
    private String basicRadioAnchorBasicSalary;

    /** 底薪+比例--助播底薪 */
    @Excel(name = "底薪+比例--助播底薪")
    private String basicRadioHelperBasicSalary;

    /** 底薪+比例--中控底薪 */
    @Excel(name = "底薪+比例--中控底薪")
    private String basicRadioControlerBasicSalary;

    /** 底薪+比例--特定佣金 */
    @Excel(name = "底薪+比例--特定佣金")
    private String basicRadioGiven;

    /** 底薪+阶梯--主播底薪 */
    @Excel(name = "底薪+阶梯--主播底薪")
    private String basicLadderAnchorBasicSalary;

    /** 底薪+阶梯--助播底薪 */
    @Excel(name = "底薪+阶梯--助播底薪")
    private String basicLadderHelperBasicSalary;

    /** 底薪+阶梯--中控底薪 */
    @Excel(name = "底薪+阶梯--中控底薪")
    private String basicLadderControlerBasicSalary;

    /** 底薪+阶梯--阶梯佣金 */
    @Excel(name = "底薪+阶梯--阶梯佣金")
    private String basicLadder;

    /** 件数提成--主播提成 */
    @Excel(name = "件数提成--主播提成")
    private BigDecimal quantityAnchorCommission;

    /** 件数提成--助播提成 */
    @Excel(name = "件数提成--助播提成")
    private BigDecimal quantityHelperCommission;

    /** 件数提成--中控提成 */
    @Excel(name = "件数提成--中控提成")
    private BigDecimal quantityControlerCommission;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setLocalShopId(Long localShopId) 
    {
        this.localShopId = localShopId;
    }

    public Long getLocalShopId() 
    {
        return localShopId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setType(Long type) 
    {
        this.type = type;
    }

    public Long getType() 
    {
        return type;
    }
    public void setAnchorBasicSalary(String anchorBasicSalary) 
    {
        this.anchorBasicSalary = anchorBasicSalary;
    }

    public String getAnchorBasicSalary() 
    {
        return anchorBasicSalary;
    }
    public void setHelperBasicSalary(String helperBasicSalary) 
    {
        this.helperBasicSalary = helperBasicSalary;
    }

    public String getHelperBasicSalary() 
    {
        return helperBasicSalary;
    }
    public void setControlerBasicSalary(String controlerBasicSalary) 
    {
        this.controlerBasicSalary = controlerBasicSalary;
    }

    public String getControlerBasicSalary() 
    {
        return controlerBasicSalary;
    }
    public void setRatioAnchorDefault(String ratioAnchorDefault) 
    {
        this.ratioAnchorDefault = ratioAnchorDefault;
    }

    public String getRatioAnchorDefault() 
    {
        return ratioAnchorDefault;
    }
    public void setRatioHelperDefault(String ratioHelperDefault) 
    {
        this.ratioHelperDefault = ratioHelperDefault;
    }

    public String getRatioHelperDefault() 
    {
        return ratioHelperDefault;
    }
    public void setRatioControlerDefault(String ratioControlerDefault) 
    {
        this.ratioControlerDefault = ratioControlerDefault;
    }

    public String getRatioControlerDefault() 
    {
        return ratioControlerDefault;
    }
    public void setRadioGiven(String radioGiven) 
    {
        this.radioGiven = radioGiven;
    }

    public String getRadioGiven() 
    {
        return radioGiven;
    }
    public void setLadder(String ladder) 
    {
        this.ladder = ladder;
    }

    public String getLadder() 
    {
        return ladder;
    }
    public void setBasicRadioAnchorBasicSalary(String basicRadioAnchorBasicSalary) 
    {
        this.basicRadioAnchorBasicSalary = basicRadioAnchorBasicSalary;
    }

    public String getBasicRadioAnchorBasicSalary() 
    {
        return basicRadioAnchorBasicSalary;
    }
    public void setBasicRadioHelperBasicSalary(String basicRadioHelperBasicSalary) 
    {
        this.basicRadioHelperBasicSalary = basicRadioHelperBasicSalary;
    }

    public String getBasicRadioHelperBasicSalary() 
    {
        return basicRadioHelperBasicSalary;
    }
    public void setBasicRadioControlerBasicSalary(String basicRadioControlerBasicSalary) 
    {
        this.basicRadioControlerBasicSalary = basicRadioControlerBasicSalary;
    }

    public String getBasicRadioControlerBasicSalary() 
    {
        return basicRadioControlerBasicSalary;
    }
    public void setBasicRadioGiven(String basicRadioGiven) 
    {
        this.basicRadioGiven = basicRadioGiven;
    }

    public String getBasicRadioGiven() 
    {
        return basicRadioGiven;
    }
    public void setBasicLadderAnchorBasicSalary(String basicLadderAnchorBasicSalary) 
    {
        this.basicLadderAnchorBasicSalary = basicLadderAnchorBasicSalary;
    }

    public String getBasicLadderAnchorBasicSalary() 
    {
        return basicLadderAnchorBasicSalary;
    }
    public void setBasicLadderHelperBasicSalary(String basicLadderHelperBasicSalary) 
    {
        this.basicLadderHelperBasicSalary = basicLadderHelperBasicSalary;
    }

    public String getBasicLadderHelperBasicSalary() 
    {
        return basicLadderHelperBasicSalary;
    }
    public void setBasicLadderControlerBasicSalary(String basicLadderControlerBasicSalary) 
    {
        this.basicLadderControlerBasicSalary = basicLadderControlerBasicSalary;
    }

    public String getBasicLadderControlerBasicSalary() 
    {
        return basicLadderControlerBasicSalary;
    }
    public void setBasicLadder(String basicLadder) 
    {
        this.basicLadder = basicLadder;
    }

    public String getBasicLadder() 
    {
        return basicLadder;
    }
    public void setQuantityAnchorCommission(BigDecimal quantityAnchorCommission) 
    {
        this.quantityAnchorCommission = quantityAnchorCommission;
    }

    public BigDecimal getQuantityAnchorCommission() 
    {
        return quantityAnchorCommission;
    }
    public void setQuantityHelperCommission(BigDecimal quantityHelperCommission) 
    {
        this.quantityHelperCommission = quantityHelperCommission;
    }

    public BigDecimal getQuantityHelperCommission() 
    {
        return quantityHelperCommission;
    }
    public void setQuantityControlerCommission(BigDecimal quantityControlerCommission) 
    {
        this.quantityControlerCommission = quantityControlerCommission;
    }

    public BigDecimal getQuantityControlerCommission() 
    {
        return quantityControlerCommission;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("localShopId", getLocalShopId())
            .append("name", getName())
            .append("type", getType())
            .append("anchorBasicSalary", getAnchorBasicSalary())
            .append("helperBasicSalary", getHelperBasicSalary())
            .append("controlerBasicSalary", getControlerBasicSalary())
            .append("ratioAnchorDefault", getRatioAnchorDefault())
            .append("ratioHelperDefault", getRatioHelperDefault())
            .append("ratioControlerDefault", getRatioControlerDefault())
            .append("radioGiven", getRadioGiven())
            .append("ladder", getLadder())
            .append("basicRadioAnchorBasicSalary", getBasicRadioAnchorBasicSalary())
            .append("basicRadioHelperBasicSalary", getBasicRadioHelperBasicSalary())
            .append("basicRadioControlerBasicSalary", getBasicRadioControlerBasicSalary())
            .append("basicRadioGiven", getBasicRadioGiven())
            .append("basicLadderAnchorBasicSalary", getBasicLadderAnchorBasicSalary())
            .append("basicLadderHelperBasicSalary", getBasicLadderHelperBasicSalary())
            .append("basicLadderControlerBasicSalary", getBasicLadderControlerBasicSalary())
            .append("basicLadder", getBasicLadder())
            .append("quantityAnchorCommission", getQuantityAnchorCommission())
            .append("quantityHelperCommission", getQuantityHelperCommission())
            .append("quantityControlerCommission", getQuantityControlerCommission())
            .toString();
    }
}
