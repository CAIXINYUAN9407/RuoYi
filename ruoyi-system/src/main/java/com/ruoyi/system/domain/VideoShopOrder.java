package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单表对象 video_shop_order_1
 *
 * @author CaiXY
 * @date 2024-06-21
 */
public class VideoShopOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 订单ID
     */
    @Excel(name = "订单ID")
    private String orderId;

    /**
     * 店铺ID
     */
    @Excel(name = "店铺ID")
    private Long shopId;

    /**
     * 商品id
     */
    @Excel(name = "商品id")
    private String productId;

    /**
     * 商品标题
     */
    @Excel(name = "商品标题")
    private String title;

    /**
     * sku小图
     */
    @Excel(name = "thumb_img")
    private String thumbImg;

    /**
     * sku_id
     */
    @Excel(name = "sku_id")
    private String skuId;

    /**
     * sku_id
     */
    @Excel(name = "sku_attrs")
    private String skuAttrs;

    /**
     * sku数量
     */
    @Excel(name = "sku数量")
    private Integer skuCnt;

    /**
     * 商品总价，单位为分
     */
    @Excel(name = "商品总价，单位为分")
    private BigDecimal productPrice;

    /**
     * 订单金额，单位为分
     */
    @Excel(name = "订单金额，单位为分")
    private BigDecimal orderPrice;

    /**
     * 运费，单位为分
     */
    @Excel(name = "运费，单位为分")
    private BigDecimal freight;

    /**
     * 商品数量
     */
    @Excel(name = "商品数量")
    private Long productCnt;

    /**
     * 10--待付款20--待发货21--部分发货30--待收货100--完成200--全部商品售后之后，订单取消250--未付款用户主动取消或超时未付款订单自动取消
     */
    @Excel(name = "10--待付款20--待发货21--部分发货30--待收货100--完成200--全部商品售后之后，订单取消250--未付款用户主动取消或超时未付款订单自动取消")
    private Integer status;

    /**
     * 买家身份标识
     */
    @Excel(name = "买家身份标识")
    private String openid;

    /**
     * 收货人姓名
     */
    @Excel(name = "收货人姓名")
    private String userName;

    /**
     * 联系方式
     */
    @Excel(name = "联系方式")
    private String telNumber;

    /**
     * 虚拟号码
     */
    @Excel(name = "虚拟号码")
    private String virtualTelNumber;

    /**
     * 虚拟号码过期时间
     */
    @Excel(name = "虚拟号码过期时间")
    private Integer virtualTelExpireTime;

    /**
     * 虚拟号码兑换次数
     */
    @Excel(name = "虚拟号码兑换次数")
    private Integer getVirtualTelCnt;

    /**
     * 省份
     */
    @Excel(name = "省份")
    private String provinceName;

    /**
     * 城市
     */
    @Excel(name = "城市")
    private String cityName;

    /**
     * 区
     */
    @Excel(name = "区")
    private String countyName;

    /**
     * 详细地址
     */
    @Excel(name = "详细地址")
    private String detailInfo;

    /**
     * 下单场景1--其他2--直播间3--短视频4--商品分享5--商品橱窗主页6--公众号文章商品卡片
     */
    @Excel(name = "下单场景1--其他2--直播间3--短视频4--商品分享5--商品橱窗主页6--公众号文章商品卡片")
    private Integer orderScene;

    /**
     * 用户备注
     */
    @Excel(name = "用户备注")
    private String customerNotes;

    /**
     * 商家备注
     */
    @Excel(name = "商家备注")
    private String merchantNotes;

    /**
     * 视频号id
     */
    @Excel(name = "视频号id")
    private String finderId;

    /**
     * 直播id
     */
    @Excel(name = "直播id")
    private String liveId;

    /**
     * 达人视频号id
     */
    @Excel(name = "达人视频号id")
    private String commissionFinderId;

    /**
     * 分账方昵称
     */
    @Excel(name = "分账方昵称")
    private String commissionNickname;

    /**
     * 分账方类型，0：达人，1：团长
     */
    @Excel(name = "分账方类型，0：达人，1：团长")
    private Integer commissionType;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "下单时间，包括用户主动确认收货和超时自动确认收货", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 确认收货时间，包括用户主动确认收货和超时自动确认收货
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "确认收货时间，包括用户主动确认收货和超时自动确认收货", width = 30, dateFormat = "yyyy-MM-dd")
    private Date confirmReceiptTime;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuCnt(Integer skuCnt) {
        this.skuCnt = skuCnt;
    }

    public Integer getSkuCnt() {
        return skuCnt;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setProductCnt(Long productCnt) {
        this.productCnt = productCnt;
    }

    public Long getProductCnt() {
        return productCnt;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo;
    }

    public String getDetailInfo() {
        return detailInfo;
    }

    public void setOrderScene(Integer orderScene) {
        this.orderScene = orderScene;
    }

    public Integer getOrderScene() {
        return orderScene;
    }

    public void setCustomerNotes(String customerNotes) {
        this.customerNotes = customerNotes;
    }

    public String getCustomerNotes() {
        return customerNotes;
    }

    public void setMerchantNotes(String merchantNotes) {
        this.merchantNotes = merchantNotes;
    }

    public String getMerchantNotes() {
        return merchantNotes;
    }

    public void setFinderId(String finderId) {
        this.finderId = finderId;
    }

    public String getFinderId() {
        return finderId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setConfirmReceiptTime(Date confirmReceiptTime) {
        this.confirmReceiptTime = confirmReceiptTime;
    }

    public Date getConfirmReceiptTime() {
        return confirmReceiptTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("orderId", getOrderId())
                .append("productId", getProductId())
                .append("title", getTitle())
                .append("skuId", getSkuId())
                .append("skuCnt", getSkuCnt())
                .append("productPrice", getProductPrice())
                .append("orderPrice", getOrderPrice())
                .append("freight", getFreight())
                .append("productCnt", getProductCnt())
                .append("status", getStatus())
                .append("openid", getOpenid())
                .append("userName", getUserName())
                .append("telNumber", getTelNumber())
                .append("provinceName", getProvinceName())
                .append("cityName", getCityName())
                .append("countyName", getCountyName())
                .append("detailInfo", getDetailInfo())
                .append("orderScene", getOrderScene())
                .append("customerNotes", getCustomerNotes())
                .append("merchantNotes", getMerchantNotes())
                .append("finderId", getFinderId())
                .append("liveId", getLiveId())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("confirmReceiptTime", getConfirmReceiptTime())
                .toString();
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getVirtualTelNumber() {
        return virtualTelNumber;
    }

    public void setVirtualTelNumber(String virtualTelNumber) {
        this.virtualTelNumber = virtualTelNumber;
    }

    public Integer getVirtualTelExpireTime() {
        return virtualTelExpireTime;
    }

    public void setVirtualTelExpireTime(Integer virtualTelExpireTime) {
        this.virtualTelExpireTime = virtualTelExpireTime;
    }

    public Integer getGetVirtualTelCnt() {
        return getVirtualTelCnt;
    }

    public void setGetVirtualTelCnt(Integer getVirtualTelCnt) {
        this.getVirtualTelCnt = getVirtualTelCnt;
    }

    public String getThumbImg() {
        return thumbImg;
    }

    public void setThumbImg(String thumbImg) {
        this.thumbImg = thumbImg;
    }

    public String getSkuAttrs() {
        return skuAttrs;
    }

    public void setSkuAttrs(String skuAttrs) {
        this.skuAttrs = skuAttrs;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCommissionFinderId() {
        return commissionFinderId;
    }

    public void setCommissionFinderId(String commissionFinderId) {
        this.commissionFinderId = commissionFinderId;
    }

    public String getCommissionNickname() {
        return commissionNickname;
    }

    public void setCommissionNickname(String commissionNickname) {
        this.commissionNickname = commissionNickname;
    }

    public Integer getCommissionType() {
        return commissionType;
    }

    public void setCommissionType(Integer commissionType) {
        this.commissionType = commissionType;
    }
}
