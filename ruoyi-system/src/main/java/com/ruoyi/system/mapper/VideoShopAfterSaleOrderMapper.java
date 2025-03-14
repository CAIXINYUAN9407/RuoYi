package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.VideoShopAfterSaleOrder;
import org.apache.ibatis.annotations.Param;

public interface VideoShopAfterSaleOrderMapper {
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public VideoShopAfterSaleOrder selectVideoShopAfterSaleOrderById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param videoShopAfterSaleOrder 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<VideoShopAfterSaleOrder> selectVideoShopAfterSaleOrderList(VideoShopAfterSaleOrder videoShopAfterSaleOrder);

    /**
     * 新增【请填写功能名称】
     *
     * @param videoShopAfterSaleOrder 【请填写功能名称】
     * @return 结果
     */
    public int insertVideoShopAfterSaleOrder(VideoShopAfterSaleOrder videoShopAfterSaleOrder);

    /**
     * 修改【请填写功能名称】
     *
     * @param videoShopAfterSaleOrder 【请填写功能名称】
     * @return 结果
     */
    public int updateVideoShopAfterSaleOrder(VideoShopAfterSaleOrder videoShopAfterSaleOrder);

    /**
     * 新增【请填写功能名称】
     *
     * @param videoShopAfterSaleOrder 【请填写功能名称】
     * @return 结果
     */
    public int insertVideoShopAfterSaleOrder_UP(VideoShopAfterSaleOrder videoShopAfterSaleOrder);

    /**
     * 修改【请填写功能名称】
     *
     * @param videoShopAfterSaleOrder 【请填写功能名称】
     * @return 结果
     */
    public int updateVideoShopAfterSaleOrder_UP(VideoShopAfterSaleOrder videoShopAfterSaleOrder);

    /**
     * 删除【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteVideoShopAfterSaleOrderById(Long id);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVideoShopAfterSaleOrderByIds(String[] ids);

    public VideoShopAfterSaleOrder selectVideoShopAfterSaleOrderByAfterSaleOrderId(String afterSaleOrderId);

    public VideoShopAfterSaleOrder selectVideoShopAfterSaleOrderByAfterSaleOrderId_UP(@Param("tableIndex")String tableIndex,@Param("shopId")String shopId,@Param("afterSaleOrderId")String afterSaleOrderId);

    public VideoShopAfterSaleOrder selectVideoShopAfterSaleOrderByOrderId(String OrderId);

    public List<VideoShopAfterSaleOrder> selectVideoShopAfterSaleOrderListByFinderId(String finderId);

    public List<String> selectVideoShopAfterSaleOrderIdList();

    VideoShopAfterSaleOrder selectVideoShopAfterSaleOrderByOrderId_UP(@Param("tableIndex")String tableIndex,@Param("shopId")String shopId, @Param("orderId")String orderId);
}