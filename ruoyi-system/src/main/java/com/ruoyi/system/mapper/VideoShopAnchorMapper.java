package com.ruoyi.system.mapper;

import java.util.HashMap;
import java.util.List;

import com.ruoyi.system.domain.VideoShopAnchor;
import com.ruoyi.system.domain.VideoShopSchedulingVO;
import org.apache.ibatis.annotations.Param;

/**
 * 主播信息Mapper接口
 * 
 * @author CaiXY
 * @date 2024-06-24
 */
public interface VideoShopAnchorMapper 
{
    /**
     * 查询主播信息
     * 
     * @param id 主播信息主键
     * @return 主播信息
     */
    public VideoShopAnchor selectVideoShopAnchorById(Long id);

    /**
     * 查询主播信息列表
     * 
     * @param videoShopAnchor 主播信息
     * @return 主播信息集合
     */
    public List<VideoShopAnchor> selectVideoShopAnchorList(VideoShopAnchor videoShopAnchor);

    /**
     * 新增主播信息
     * 
     * @param videoShopAnchor 主播信息
     * @return 结果
     */
    public int insertVideoShopAnchor(VideoShopAnchor videoShopAnchor);

    /**
     * 修改主播信息
     * 
     * @param videoShopAnchor 主播信息
     * @return 结果
     */
    public int updateVideoShopAnchor(VideoShopAnchor videoShopAnchor);

    /**
     * 删除主播信息
     * 
     * @param id 主播信息主键
     * @return 结果
     */
    public int deleteVideoShopAnchorById(Long id);

    /**
     * 删除主播信息
     *
     * @param id 主播信息主键
     * @return 结果
     */
    public int deleteVideoShopAnchorById(@Param("tableIndex")String tableIndex,@Param("id")Long id);

    /**
     * 批量删除主播信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVideoShopAnchorByIds(String[] ids);

    /**
     * 批量删除主播信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVideoShopAnchorByIds(@Param("tableIndex")String tableIndex,@Param("array")String[] ids);

    List<HashMap<String, Object>> selectJournalingByAnchor(VideoShopAnchor videoShopAnchor);

    List<HashMap<String, Object>> selectAnchorEX(VideoShopAnchor videoShopAnchor);

    List<HashMap<String, Object>> selectJournalingByAnchorCount(VideoShopAnchor videoShopAnchor);

    List<HashMap<String, Object>> selectSettlementByAnchor(VideoShopAnchor videoShopAnchor);

    List<HashMap<String, Object>> getVideoShopAnchorList(VideoShopAnchor videoShopAnchor);

    VideoShopAnchor selectVideoShopAnchorByName(@Param("tableIndex")String tableIndex,@Param("shopId")Long shopId,@Param("name")String name);


    List<HashMap<String, Object>> getCommissionList(@Param("shopId")Long shopId, @Param("tableIndex")String tableIndex);
}
