package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

public class VideoShopAfterSaleOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 店铺ID
     */
    @Excel(name = "店铺ID")
    private Long localShopId;

    /**
     * 售后订单号
     */
    @Excel(name = "售后订单号")
    private String afterSaleOrderId;

    /**
     * 售后单当前状态
     * USER_CANCELD用户取消申请
     * MERCHANT_PROCESSING商家受理中
     * MERCHANT_REJECT_REFUND商家拒绝退款
     * MERCHANT_REJECT_RETURN商家拒绝退货退款
     * USER_WAIT_RETURN待买家退货
     * RETURN_CLOSED退货退款关闭
     * MERCHANT_WAIT_RECEIPT待商家收货
     * MERCHANT_OVERDUE_REFUND商家逾期未退款
     * MERCHANT_REFUND_SUCCESS退款完成
     * MERCHANT_RETURN_SUCCESS退货退款完成
     * PLATFORM_REFUNDING平台退款中
     * PLATFORM_REFUND_FAIL平台退款失败
     */
    @Excel(name = "售后单当前状态 USER_CANCELD用户取消申请 MERCHANT_PROCESSING商家受理中 MERCHANT_REJECT_REFUND商家拒绝退款")
    private String status;

    /**
     * REFUND:退款；RETURN:退货退款。
     */
    @Excel(name = "售后类型")
    private String type;


    /**
     * REFUND:退款；RETURN:退货退款。
     */
    @Excel(name = "退款原因")
    private String reasonText;

    /**
     * 订单号
     */
    @Excel(name = "订单号")
    private String orderId;

    /**
     * 买家身份标识
     */
    @Excel(name = "买家身份标识")
    private String openid;

    /**
     * 商品ID
     */
    @Excel(name = "商品ID")
    private String productId;

    /**
     * 退款金额（分）
     */
    @Excel(name = "退款金额", readConverterExp = "分=")
    private BigDecimal amount;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

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

    public void setAfterSaleOrderId(String afterSaleOrderId) {
        this.afterSaleOrderId = afterSaleOrderId;
    }

    public String getAfterSaleOrderId() {
        return afterSaleOrderId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("localShopId", getLocalShopId())
                .append("afterSaleOrderId", getAfterSaleOrderId())
                .append("status", getStatus())
                .append("orderId", getOrderId())
                .append("openid", getOpenid())
                .append("productId", getProductId())
                .append("amount", getAmount())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReasonText() {
        return reasonText;
    }

    public void setReasonText(String reasonText) {
        this.reasonText = reasonText;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
