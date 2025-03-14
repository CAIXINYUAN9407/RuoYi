package com.ruoyi.system.mapper;

import java.util.HashMap;
import java.util.List;
import com.ruoyi.system.domain.VideoShop;
import org.apache.ibatis.annotations.Param;

/**
 * 店铺Mapper接口
 * 
 * @author CaiXY
 * @date 2024-06-15
 */
public interface VideoShopMapper 
{
    /**
     * 查询店铺
     * 
     * @param id 店铺主键
     * @return 店铺
     */
    public VideoShop selectVideoShopById(Long id);

    /**
     * 查询店铺列表
     * 
     * @param videoShop 店铺
     * @return 店铺集合
     */
    public List<VideoShop> selectVideoShopList(VideoShop videoShop);

    /**
     * 查询表索引
     * 
     * @return 结果
     */
    public HashMap<String,Object> selectTableIndex();

    /**
     * 查询表索引最大值
     *
     * @return 结果
     */
    public int selectMaxTableIndex();
    /**
     * 新增店铺
     *
     * @param videoShop 店铺
     * @return 结果
     */
    public int insertVideoShop(VideoShop videoShop);
    /**
     * 修改店铺
     * 
     * @param videoShop 店铺
     * @return 结果
     */
    public int updateVideoShop(VideoShop videoShop);

    /**
     * 删除店铺
     * 
     * @param id 店铺主键
     * @return 结果
     */
    public int deleteVideoShopById(Long id);

    /**
     * 批量删除店铺
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVideoShopByIds(String[] ids);

    VideoShop selectVideoShopByOwner(String Owner);

    void createShopGoods(@Param("tableIndex")String tableIndex);

    void createShopOrder(@Param("tableIndex")String tableIndex);

    void createShopAfterSaleOrder(@Param("tableIndex")String tableIndex);

    void createShopAnchor(@Param("tableIndex")String tableIndex);

    void createShopSalaryTemplate(@Param("tableIndex")String tableIndex);

    void createShopScheduling(@Param("tableIndex")String tableIndex);

    void createShopLiveRoom(@Param("tableIndex")String tableIndex);

    List<VideoShop> selectVideoShopListNoExpire();

    HashMap<String, Object> selectVideoShopByOwner1(String owner);
}
