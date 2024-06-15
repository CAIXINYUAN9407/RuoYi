package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 店铺对象 video_shop
 * 
 * @author CaiXY
 * @date 2024-06-15
 */
public class VideoShop extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 所属商家id */
    @Excel(name = "所属商家id")
    private String owner;

    /** 店铺id */
    @Excel(name = "店铺id")
    private Long shopId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shopName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String accessToken;

    /** ccess_token接口调用凭证超时时间，单位（秒），默认有效期：7天 */
    @Excel(name = "ccess_token接口调用凭证超时时间，单位", readConverterExp = "秒=")
    private Long expiresIn;

    /** 时间戳 */
    @Excel(name = "时间戳")
    private Long expiresAt;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String scope;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String refreshToken;

    /** 授权ID */
    @Excel(name = "授权ID")
    private String authorityId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String authSubjectType;

//    /** 0 否 1是 */
//    private Integer default;

    /** 绑定码 */
    @Excel(name = "绑定码")
    private String code;

    /** 1 抖音 2拼多 3淘宝 4京东 */
    @Excel(name = "1 抖音 2拼多 3淘宝 4京东")
    private Long platform;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long pid;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String logo;

    /** 订单最后一次同步时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "订单最后一次同步时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date orderLastSyncTime;

    /** 评价最后一次同步时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "评价最后一次同步时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date commentLastSyncTime;

    /** 售后最后一次同步时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "售后最后一次同步时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date afterSaleLastSyncTime;

    /** 购买服务之后服务的开始生效时间，时间戳，单位：秒 */
    @Excel(name = "购买服务之后服务的开始生效时间，时间戳，单位：秒")
    private Long serviceStartTime;

    /** 购买服务之后服务的结束时间，时间戳，单位：秒 */
    @Excel(name = "购买服务之后服务的结束时间，时间戳，单位：秒")
    private Long serviceEndTime;

    /** 购买人手机号 */
    @Excel(name = "购买人手机号")
    private String phone;

    /** 用户实际支付的金额 */
    @Excel(name = "用户实际支付的金额")
    private BigDecimal money;

    /** 购买规格名称 */
    @Excel(name = "购买规格名称")
    private String spec;

    /** 使用时长,购买时间按照自然月，1个月，3个月，6个月，12个月。 */
    @Excel(name = "使用时长,购买时间按照自然月，1个月，3个月，6个月，12个月。")
    private Long duration;

    /** 时长单位, 0:天，1:月，2:年 */
    @Excel(name = "时长单位, 0:天，1:月，2:年")
    private Integer unit;

    /** ：微信，2：支付宝，7：该订单为试用订单，0元单 */
    @Excel(name = "：微信，2：支付宝，7：该订单为试用订单，0元单")
    private Integer payType;

    /** 可用短信数 */
    @Excel(name = "可用短信数")
    private Long smsNum;

    /** 订购订单号 */
    @Excel(name = "订购订单号")
    private String orderId;

    /** 订单同步锁 */
    @Excel(name = "订单同步锁")
    private Integer orderSyncLock;

    /** 售后同步锁 */
    @Excel(name = "售后同步锁")
    private Integer afterSaleSyncLock;

    /** 表索引 */
    @Excel(name = "表索引")
    private Long tableIndex;

    /** 1状态是否同步 */
    @Excel(name = "1状态是否同步")
    private Integer isOrderStatus1Sync;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String encryptOperator;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String operatorName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shopBizType;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    public String getOwner()
    {
        return owner;
    }
    public void setShopId(Long shopId) 
    {
        this.shopId = shopId;
    }

    public Long getShopId() 
    {
        return shopId;
    }
    public void setShopName(String shopName) 
    {
        this.shopName = shopName;
    }

    public String getShopName() 
    {
        return shopName;
    }
    public void setAccessToken(String accessToken) 
    {
        this.accessToken = accessToken;
    }

    public String getAccessToken() 
    {
        return accessToken;
    }
    public void setExpiresIn(Long expiresIn) 
    {
        this.expiresIn = expiresIn;
    }

    public Long getExpiresIn() 
    {
        return expiresIn;
    }
    public void setExpiresAt(Long expiresAt) 
    {
        this.expiresAt = expiresAt;
    }

    public Long getExpiresAt() 
    {
        return expiresAt;
    }
    public void setScope(String scope) 
    {
        this.scope = scope;
    }

    public String getScope() 
    {
        return scope;
    }
    public void setRefreshToken(String refreshToken) 
    {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() 
    {
        return refreshToken;
    }
    public void setAuthorityId(String authorityId) 
    {
        this.authorityId = authorityId;
    }

    public String getAuthorityId() 
    {
        return authorityId;
    }
    public void setAuthSubjectType(String authSubjectType) 
    {
        this.authSubjectType = authSubjectType;
    }

    public String getAuthSubjectType() 
    {
        return authSubjectType;
    }
//    public void setDefault(Integer default)
//    {
//        this.default = default;
//    }
//
//    public Integer getDefault()
//    {
//        return default;
//    }
    public void setCode(String code) 
    {
        this.code = code;
    }

    public String getCode() 
    {
        return code;
    }
    public void setPlatform(Long platform) 
    {
        this.platform = platform;
    }

    public Long getPlatform() 
    {
        return platform;
    }
    public void setPid(Long pid) 
    {
        this.pid = pid;
    }

    public Long getPid() 
    {
        return pid;
    }
    public void setLogo(String logo) 
    {
        this.logo = logo;
    }

    public String getLogo() 
    {
        return logo;
    }
    public void setOrderLastSyncTime(Date orderLastSyncTime) 
    {
        this.orderLastSyncTime = orderLastSyncTime;
    }

    public Date getOrderLastSyncTime() 
    {
        return orderLastSyncTime;
    }
    public void setCommentLastSyncTime(Date commentLastSyncTime) 
    {
        this.commentLastSyncTime = commentLastSyncTime;
    }

    public Date getCommentLastSyncTime() 
    {
        return commentLastSyncTime;
    }
    public void setAfterSaleLastSyncTime(Date afterSaleLastSyncTime) 
    {
        this.afterSaleLastSyncTime = afterSaleLastSyncTime;
    }

    public Date getAfterSaleLastSyncTime() 
    {
        return afterSaleLastSyncTime;
    }
    public void setServiceStartTime(Long serviceStartTime) 
    {
        this.serviceStartTime = serviceStartTime;
    }

    public Long getServiceStartTime() 
    {
        return serviceStartTime;
    }
    public void setServiceEndTime(Long serviceEndTime) 
    {
        this.serviceEndTime = serviceEndTime;
    }

    public Long getServiceEndTime() 
    {
        return serviceEndTime;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setMoney(BigDecimal money) 
    {
        this.money = money;
    }

    public BigDecimal getMoney() 
    {
        return money;
    }
    public void setSpec(String spec) 
    {
        this.spec = spec;
    }

    public String getSpec() 
    {
        return spec;
    }
    public void setDuration(Long duration) 
    {
        this.duration = duration;
    }

    public Long getDuration() 
    {
        return duration;
    }
    public void setUnit(Integer unit) 
    {
        this.unit = unit;
    }

    public Integer getUnit() 
    {
        return unit;
    }
    public void setPayType(Integer payType) 
    {
        this.payType = payType;
    }

    public Integer getPayType() 
    {
        return payType;
    }
    public void setSmsNum(Long smsNum) 
    {
        this.smsNum = smsNum;
    }

    public Long getSmsNum() 
    {
        return smsNum;
    }
    public void setOrderId(String orderId) 
    {
        this.orderId = orderId;
    }

    public String getOrderId() 
    {
        return orderId;
    }
    public void setOrderSyncLock(Integer orderSyncLock) 
    {
        this.orderSyncLock = orderSyncLock;
    }

    public Integer getOrderSyncLock() 
    {
        return orderSyncLock;
    }
    public void setAfterSaleSyncLock(Integer afterSaleSyncLock) 
    {
        this.afterSaleSyncLock = afterSaleSyncLock;
    }

    public Integer getAfterSaleSyncLock() 
    {
        return afterSaleSyncLock;
    }
    public void setTableIndex(Long tableIndex) 
    {
        this.tableIndex = tableIndex;
    }

    public Long getTableIndex() 
    {
        return tableIndex;
    }
    public void setIsOrderStatus1Sync(Integer isOrderStatus1Sync) 
    {
        this.isOrderStatus1Sync = isOrderStatus1Sync;
    }

    public Integer getIsOrderStatus1Sync() 
    {
        return isOrderStatus1Sync;
    }
    public void setEncryptOperator(String encryptOperator) 
    {
        this.encryptOperator = encryptOperator;
    }

    public String getEncryptOperator() 
    {
        return encryptOperator;
    }
    public void setOperatorName(String operatorName) 
    {
        this.operatorName = operatorName;
    }

    public String getOperatorName() 
    {
        return operatorName;
    }
    public void setShopBizType(String shopBizType) 
    {
        this.shopBizType = shopBizType;
    }

    public String getShopBizType() 
    {
        return shopBizType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("owner", getOwner())
            .append("shopId", getShopId())
            .append("shopName", getShopName())
            .append("accessToken", getAccessToken())
            .append("expiresIn", getExpiresIn())
            .append("expiresAt", getExpiresAt())
            .append("scope", getScope())
            .append("refreshToken", getRefreshToken())
            .append("authorityId", getAuthorityId())
            .append("authSubjectType", getAuthSubjectType())
            .append("createTime", getCreateTime())
//            .append("default", getDefault())
            .append("code", getCode())
            .append("platform", getPlatform())
            .append("pid", getPid())
            .append("logo", getLogo())
            .append("orderLastSyncTime", getOrderLastSyncTime())
            .append("commentLastSyncTime", getCommentLastSyncTime())
            .append("afterSaleLastSyncTime", getAfterSaleLastSyncTime())
            .append("serviceStartTime", getServiceStartTime())
            .append("serviceEndTime", getServiceEndTime())
            .append("phone", getPhone())
            .append("money", getMoney())
            .append("spec", getSpec())
            .append("duration", getDuration())
            .append("unit", getUnit())
            .append("payType", getPayType())
            .append("smsNum", getSmsNum())
            .append("orderId", getOrderId())
            .append("orderSyncLock", getOrderSyncLock())
            .append("afterSaleSyncLock", getAfterSaleSyncLock())
            .append("tableIndex", getTableIndex())
            .append("isOrderStatus1Sync", getIsOrderStatus1Sync())
            .append("encryptOperator", getEncryptOperator())
            .append("operatorName", getOperatorName())
            .append("shopBizType", getShopBizType())
            .toString();
    }
}
