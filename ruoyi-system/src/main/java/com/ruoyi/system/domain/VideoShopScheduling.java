package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 排班信息对象 video_shop_scheduling1
 *
 * @author CaiXY
 * @date 2024-06-27
 */
public class VideoShopScheduling extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 店铺ID */
    private Long shopId;

    /** 直播间iD */
    private Integer roomId;
    /** 直播间名称 */
    @Excel(name = "直播间名称")
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

    /** 模板id */
    private Long templateId;

    /** 模版名称 */
    @Excel(name = "模版名称")
    private String templateName;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date starttime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endtime;

    /** ID */
    private Long validProducts;
    /** ID */
    private Long completedOrders;
    /** ID */
    private Long validOrders;
    /** ID */
    private Long unpaidOrders;
    /** ID */
    private BigDecimal salesVolume;
    /** ID */
    private Long aftersalesOrders;
    /** ID */
    private BigDecimal refundAmount;

    /** 主播提成 */
    private BigDecimal anchorCommission;
    /** 模板id */
    private Long sceneLivecount;
    /** 模板id */
    private Long sceneVideocount;
    /** 模板id */
    private Long sceneShopcount;

    /** 主播提成 */
    private BigDecimal sceneLivesum;
    /** 主播提成 */
    private BigDecimal sceneVideosum;
    /** 主播提成 */
    private BigDecimal sceneShopsum;

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
    public void setAnchorId(Long anchorId)
    {
        this.anchorId = anchorId;
    }

    public Long getAnchorId()
    {
        return anchorId;
    }
    public void setHelperId(Long helperId)
    {
        this.helperId = helperId;
    }

    public Long getHelperId()
    {
        return helperId;
    }
    public void setControlerId(Long controlerId)
    {
        this.controlerId = controlerId;
    }

    public Long getControlerId()
    {
        return controlerId;
    }
    public void setTemplateId(Long templateId)
    {
        this.templateId = templateId;
    }

    public Long getTemplateId()
    {
        return templateId;
    }
    public void setTemplateName(String templateName)
    {
        this.templateName = templateName;
    }

    public String getTemplateName()
    {
        return templateName;
    }
    public void setStarttime(Date starttime)
    {
        this.starttime = starttime;
    }

    public Date getStarttime()
    {
        return starttime;
    }
    public void setEndtime(Date endtime)
    {
        this.endtime = endtime;
    }

    public Date getEndtime()
    {
        return endtime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("shopId", getShopId())
                .append("anchorId", getAnchorId())
                .append("helperId", getHelperId())
                .append("controlerId", getControlerId())
                .append("templateId", getTemplateId())
                .append("templateName", getTemplateName())
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

    public Long getValidProducts() {
        return validProducts;
    }

    public void setValidProducts(Long validProducts) {
        this.validProducts = validProducts;
    }

    public Long getCompletedOrders() {
        return completedOrders;
    }

    public void setCompletedOrders(Long completedOrders) {
        this.completedOrders = completedOrders;
    }

    public Long getValidOrders() {
        return validOrders;
    }

    public void setValidOrders(Long validOrders) {
        this.validOrders = validOrders;
    }

    public Long getUnpaidOrders() {
        return unpaidOrders;
    }

    public void setUnpaidOrders(Long unpaidOrders) {
        this.unpaidOrders = unpaidOrders;
    }

    public BigDecimal getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(BigDecimal salesVolume) {
        this.salesVolume = salesVolume;
    }

    public Long getAftersalesOrders() {
        return aftersalesOrders;
    }

    public void setAftersalesOrders(Long aftersalesOrders) {
        this.aftersalesOrders = aftersalesOrders;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
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

    public BigDecimal getAnchorCommission() {
        return anchorCommission;
    }

    public void setAnchorCommission(BigDecimal anchorCommission) {
        this.anchorCommission = anchorCommission;
    }

    public Long getSceneLivecount() {
        return sceneLivecount;
    }

    public void setSceneLivecount(Long sceneLivecount) {
        this.sceneLivecount = sceneLivecount;
    }

    public Long getSceneVideocount() {
        return sceneVideocount;
    }

    public void setSceneVideocount(Long sceneVideocount) {
        this.sceneVideocount = sceneVideocount;
    }

    public Long getSceneShopcount() {
        return sceneShopcount;
    }

    public void setSceneShopcount(Long sceneShopcount) {
        this.sceneShopcount = sceneShopcount;
    }

    public BigDecimal getSceneLivesum() {
        return sceneLivesum;
    }

    public void setSceneLivesum(BigDecimal sceneLivesum) {
        this.sceneLivesum = sceneLivesum;
    }

    public BigDecimal getSceneVideosum() {
        return sceneVideosum;
    }

    public void setSceneVideosum(BigDecimal sceneVideosum) {
        this.sceneVideosum = sceneVideosum;
    }

    public BigDecimal getSceneShopsum() {
        return sceneShopsum;
    }

    public void setSceneShopsum(BigDecimal sceneShopsum) {
        this.sceneShopsum = sceneShopsum;
    }
}