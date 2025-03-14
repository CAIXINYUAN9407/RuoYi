package com.ruoyi.system.service;

import com.ruoyi.system.domain.VideoShopAfterSaleOrder;
import java.util.List;

public interface IVideoShopAfterSaleOrderService {
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
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteVideoShopAfterSaleOrderByIds(String ids);

    /**
     * 根据售后订单号查询是否存在
     *
     * @param afterSaleOrderId 售后订单号
     * @return 结果
     */
    public VideoShopAfterSaleOrder selectVideoShopAfterSaleOrderByAfterSaleOrderId(String afterSaleOrderId);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteVideoShopAfterSaleOrderById(Long id);

}
