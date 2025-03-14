package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 优选联盟达人对象 video_shop_channels_finder
 *
 * @author CaiXY
 * @date 2024-07-18
 */
public class VideoShopChannelsFinder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 店铺Id
     */
    @Excel(name = "店铺Id")
    private Long localShopId;
    /**
     * 昵称
     */
    @Excel(name = "视频号Id")
    private String finderId;
    /**
     * 昵称
     */
    @Excel(name = "昵称")
    private String nickname;

    /**
     * 成交金额，单位分
     */
    @Excel(name = "成交金额，单位分")
    private BigDecimal payGmv;

    /**
     * 动销商品数
     */
    @Excel(name = "动销商品数")
    private Long payProductIdCnt;

    /**
     * 成交人数
     */
    @Excel(name = "成交人数")
    private Long payUv;

    /**
     * 退款金额，单位分
     */
    @Excel(name = "退款金额，单位分")
    private BigDecimal refundGmv;

    /**
     * 成交退款金额，单位分
     */
    @Excel(name = "成交退款金额，单位分")
    private BigDecimal payRefundGmv;
    /**
     * 统计日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date countDate;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setLocalShopId(Long localShopId) {
        this.localShopId = localShopId;
    }

    public Long getLocalShopId() {
        return localShopId;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setPayGmv(BigDecimal payGmv) {
        this.payGmv = payGmv;
    }

    public BigDecimal getPayGmv() {
        return payGmv;
    }

    public void setPayProductIdCnt(Long payProductIdCnt) {
        this.payProductIdCnt = payProductIdCnt;
    }

    public Long getPayProductIdCnt() {
        return payProductIdCnt;
    }

    public void setPayUv(Long payUv) {
        this.payUv = payUv;
    }

    public Long getPayUv() {
        return payUv;
    }

    public void setRefundGmv(BigDecimal refundGmv) {
        this.refundGmv = refundGmv;
    }

    public BigDecimal getRefundGmv() {
        return refundGmv;
    }

    public void setPayRefundGmv(BigDecimal payRefundGmv) {
        this.payRefundGmv = payRefundGmv;
    }

    public BigDecimal getPayRefundGmv() {
        return payRefundGmv;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("localShopId", getLocalShopId())
                .append("nickname", getNickname())
                .append("payGmv", getPayGmv())
                .append("payProductIdCnt", getPayProductIdCnt())
                .append("payUv", getPayUv())
                .append("refundGmv", getRefundGmv())
                .append("payRefundGmv", getPayRefundGmv())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }

    public String getFinderId() {
        return finderId;
    }

    public void setFinderId(String finderId) {
        this.finderId = finderId;
    }

    public Date getCountDate() {
        return countDate;
    }

    public void setCountDate(Date countDate) {
        this.countDate = countDate;
    }
}