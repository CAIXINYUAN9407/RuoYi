package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 排班信息对象 video_shop_scheduling1
 *
 * @author CaiXY
 * @date 2024-06-27
 */
public class VideoShopSchedulingVO extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /** 场控id */
    @Excel(name = "主播名称")
    private String anchorName;

    /** 场控id */
    @Excel(name = "主播名称")
    private String formattedDate;

    /** 模板id */
    @Excel(name = "场次")
    private String  etc;

    /** 模板id */
    @Excel(name = "直播时长")
    private String  minutes;


    /** 模板id */
    @Excel(name = "有效商品数")
    private String  validProducts;

    /** 模板id */
    @Excel(name = "成交订单数")
    private String  completedOrders;

    /** 模板id */
    @Excel(name = "有效订单数")
    private String  validOrders;

    /** 模板id */
    @Excel(name = "未付款订单数")
    private String  unpaidOrders;

    /** 模板id */
    @Excel(name = "实付销售额")
    private String  salesVolume;

    /** 模板id */
    @Excel(name = "售后订单数")
    private String  aftersalesOrders;

    /** 模板id */
    @Excel(name = "退款金额")
    private String  refundAmount;

    /** 模板id */
    @Excel(name = "净销售额")
    private String  allAmount;

    public String getAllAmount() {
        return allAmount;
    }

    public void setAllAmount(String allAmount) {
        this.allAmount = allAmount;
    }

    public String getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getAftersalesOrders() {
        return aftersalesOrders;
    }

    public void setAftersalesOrders(String aftersalesOrders) {
        this.aftersalesOrders = aftersalesOrders;
    }

    public String getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(String salesVolume) {
        this.salesVolume = salesVolume;
    }

    public String getUnpaidOrders() {
        return unpaidOrders;
    }

    public void setUnpaidOrders(String unpaidOrders) {
        this.unpaidOrders = unpaidOrders;
    }

    public String getValidOrders() {
        return validOrders;
    }

    public void setValidOrders(String validOrders) {
        this.validOrders = validOrders;
    }

    public String getCompletedOrders() {
        return completedOrders;
    }

    public void setCompletedOrders(String completedOrders) {
        this.completedOrders = completedOrders;
    }

    public String getValidProducts() {
        return validProducts;
    }

    public void setValidProducts(String validProducts) {
        this.validProducts = validProducts;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    public String getAnchorName() {
        return anchorName;
    }

    public void setAnchorName(String anchorName) {
        this.anchorName = anchorName;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }
}