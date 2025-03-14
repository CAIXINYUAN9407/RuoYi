package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 排班信息对象 video_shop_scheduling1
 *
 * @author CaiXY
 * @date 2024-06-27
 */
public class SettlementListExportVO extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 店铺ID */
    private Long shopId;

    /** 直播间iD */
    private Integer roomId;
    /** 直播间名称 */
    private String roomName;

    /** 主播iD */
    private Long anchorId;

    /** 助播id */
    private Long helperId;

    /** 场控id */
    private Long controlerId;

    /** 场控id */
    @Excel(name = "主播名称")
    private String anchorName;

    /** 场控id */
    @Excel(name = "日期")
    private String formattedDate;

    /** 模板id */
    @Excel(name = "直播次数")
    private String  etc;

    /** 模板id */
//    @Excel(name = "直播时长")
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

    /** 主播分成 */
    @Excel(name = "主播分成")
    private String  anchorCommission;

    /** 主播分成 */
    @Excel(name = "薪酬模板")
    private String  templateName;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date starttime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endtime;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setAnchorId(Long anchorId) {
        this.anchorId = anchorId;
    }

    public Long getAnchorId() {
        return anchorId;
    }

    public void setHelperId(Long helperId) {
        this.helperId = helperId;
    }

    public Long getHelperId() {
        return helperId;
    }

    public void setControlerId(Long controlerId) {
        this.controlerId = controlerId;
    }

    public Long getControlerId() {
        return controlerId;
    }


    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Date getEndtime() {
        return endtime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("shopId", getShopId())
                .append("anchorId", getAnchorId())
                .append("helperId", getHelperId())
                .append("controlerId", getControlerId())
                .append("starttime", getStarttime())
                .append("endtime", getEndtime())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }

    public String getAnchorName() {
        return anchorName;
    }

    public void setAnchorName(String anchorName) {
        this.anchorName = anchorName;
    }


    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getValidProducts() {
        return validProducts;
    }

    public void setValidProducts(String validProducts) {
        this.validProducts = validProducts;
    }

    public String getCompletedOrders() {
        return completedOrders;
    }

    public void setCompletedOrders(String completedOrders) {
        this.completedOrders = completedOrders;
    }

    public String getValidOrders() {
        return validOrders;
    }

    public void setValidOrders(String validOrders) {
        this.validOrders = validOrders;
    }

    public String getUnpaidOrders() {
        return unpaidOrders;
    }

    public void setUnpaidOrders(String unpaidOrders) {
        this.unpaidOrders = unpaidOrders;
    }

    public String getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(String salesVolume) {
        this.salesVolume = salesVolume;
    }

    public String getAftersalesOrders() {
        return aftersalesOrders;
    }

    public void setAftersalesOrders(String aftersalesOrders) {
        this.aftersalesOrders = aftersalesOrders;
    }

    public String getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getAllAmount() {
        return allAmount;
    }

    public void setAllAmount(String allAmount) {
        this.allAmount = allAmount;
    }

    public String getAnchorCommission() {
        return anchorCommission;
    }

    public void setAnchorCommission(String anchorCommission) {
        this.anchorCommission = anchorCommission;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}