package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 排班信息对象 video_shop_scheduling1
 *
 * @author CaiXY
 * @date 2024-06-27
 */
public class VideoShopLiveRoom extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 店铺ID */
    private Long shopId;


    /** 是否是主直播间 */
    private Integer isMain;

    /** 店铺ID */
    private String roomName;

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

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("shopId", getShopId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }

    public Integer getIsMain() {
        return isMain;
    }

    public void setIsMain(Integer isMain) {
        this.isMain = isMain;
    }
}