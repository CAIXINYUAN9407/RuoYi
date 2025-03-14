package com.ruoyi.system.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.service.IVideoShopAnchorService;
import com.ruoyi.common.core.text.Convert;

import static com.ruoyi.common.utils.PageUtils.startPage;

/**
 * 主播信息Service业务层处理
 *
 * @author CaiXY
 * @date 2024-06-24
 */
@Service
public class VideoShopAnchorServiceImpl implements IVideoShopAnchorService {
    @Autowired
    private VideoShopAnchorMapper videoShopAnchorMapper;
    @Autowired
    private VideoShopOrderMapper videoShopOrderMapper;
    @Autowired
    private VideoShopMapper videoShopMapper;
    @Autowired
    private VideoShopSalarytemplateMapper videoShopSalarytemplateMapper;
    @Autowired
    private VideoShopAfterSaleOrderMapper videoShopAfterSaleOrderMapper;
    @Autowired
    private VideoShopSchedulingMapper videoShopSchedulingMapper;

    /**
     * 查询主播信息
     *
     * @param id 主播信息主键
     * @return 主播信息
     */
    @Override
    public VideoShopAnchor selectVideoShopAnchorById(Long id) {
        return videoShopAnchorMapper.selectVideoShopAnchorById(id);
    }

    /**
     * 查询主播信息列表
     *
     * @param videoShopAnchor 主播信息
     * @return 主播信息
     */
    @Override
    public List<VideoShopAnchor> selectVideoShopAnchorList(VideoShopAnchor videoShopAnchor) {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);
        videoShopAnchor.setShopId(videoShop.getId());
        videoShopAnchor.getParams().put("tableIndex", videoShop.getTableIndex());
        return videoShopAnchorMapper.selectVideoShopAnchorList(videoShopAnchor);
    }

    /**
     * 新增主播信息
     *
     * @param videoShopAnchor 主播信息
     * @return 结果
     */
    @Override
    public int insertVideoShopAnchor(VideoShopAnchor videoShopAnchor) {
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);
        videoShopAnchor.setShopId(videoShop.getId());
        videoShopAnchor.setCreateTime(DateUtils.getNowDate());
        videoShopAnchor.getParams().put("tableIndex", videoShop.getTableIndex());

//        videoShopAnchor.setLiveAccount(videoShop.getShopName() + "直播号");
        return videoShopAnchorMapper.insertVideoShopAnchor(videoShopAnchor);
    }

    /**
     * 修改主播信息
     *
     * @param videoShopAnchor 主播信息
     * @return 结果
     */
    @Override
    public int updateVideoShopAnchor(VideoShopAnchor videoShopAnchor) {
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);
        videoShopAnchor.setShopId(videoShop.getId());
        videoShopAnchor.setUpdateTime(DateUtils.getNowDate());
        videoShopAnchor.setLiveAccount(videoShop.getShopName() + "直播号");
        videoShopAnchor.getParams().put("tableIndex", videoShop.getTableIndex());

        return videoShopAnchorMapper.updateVideoShopAnchor(videoShopAnchor);
    }

    /**
     * 批量删除主播信息
     *
     * @param ids 需要删除的主播信息主键
     * @return 结果
     */
    @Override
    public int deleteVideoShopAnchorByIds(String ids) {
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);
        int count = videoShopSchedulingMapper.selectVideoShopSchedulingCountByAnchorId(ids);
        if (count > 0) {
            return -1;
        }
        return videoShopAnchorMapper.deleteVideoShopAnchorByIds(String.valueOf(videoShop.getTableIndex()), Convert.toStrArray(ids));
    }

    /**
     * 删除主播信息信息
     *
     * @param id 主播信息主键
     * @return 结果
     */
    @Override
    public int deleteVideoShopAnchorById(Long id) {
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);

        return videoShopAnchorMapper.deleteVideoShopAnchorById(String.valueOf(videoShop.getTableIndex()), id);
    }

    /**
     * 删除主播信息信息
     *
     * @param videoShopAnchor 主播信息主键
     * @return 结果
     */
    @Override
    public List<HashMap<String, Object>> selectJournalingByAnchor(VideoShopAnchor videoShopAnchor) {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);
        videoShopAnchor.setShopId(videoShop.getId());
        videoShopAnchor.getParams().put("tableIndex", videoShop.getTableIndex());
        startPage();
        List<HashMap<String, Object>> resultList = videoShopAnchorMapper.selectJournalingByAnchor(videoShopAnchor);
        return resultList;
    }

    /**
     * 导出主播数据
     *
     * @param videoShopAnchor 主播信息主键
     * @return 结果
     */
    @Override
    public List<VideoShopSchedulingVO> selectJournalingByAnchorEX(VideoShopAnchor videoShopAnchor) {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);
        videoShopAnchor.setShopId(videoShop.getId());
        videoShopAnchor.getParams().put("tableIndex", videoShop.getTableIndex());
        List<HashMap<String, Object>> resultList = videoShopAnchorMapper.selectAnchorEX(videoShopAnchor);

        List<VideoShopSchedulingVO> VOList = new ArrayList<VideoShopSchedulingVO>();
        for (HashMap<String, Object> map : resultList) {
            VideoShopSchedulingVO videoShopSchedulingVO = new VideoShopSchedulingVO();
            videoShopSchedulingVO.setAnchorName(map.get("anchorName").toString());
            videoShopSchedulingVO.setFormattedDate(map.get("formattedDate").toString());

            videoShopSchedulingVO.setEtc(map.get("etc").toString());
            videoShopSchedulingVO.setMinutes(map.get("minutes").toString());

            videoShopSchedulingVO.setValidProducts(map.get("validProducts").toString());
            videoShopSchedulingVO.setCompletedOrders(map.get("completedOrders").toString());
            videoShopSchedulingVO.setValidOrders(map.get("validOrders").toString());
            videoShopSchedulingVO.setUnpaidOrders(map.get("unpaidOrders").toString());

            videoShopSchedulingVO.setSalesVolume(map.get("salesVolume").toString());
            videoShopSchedulingVO.setAftersalesOrders(map.get("aftersalesOrders").toString());
            videoShopSchedulingVO.setRefundAmount(map.get("refundAmount").toString());
            videoShopSchedulingVO.setAllAmount(String.valueOf(Double.parseDouble(map.get("salesVolume").toString()) - Double.parseDouble(map.get("refundAmount").toString())));
            VOList.add(videoShopSchedulingVO);
        }
        return VOList;
    }

    @Override
    public List<HashMap<String, Object>> selectSettlementByAnchor(VideoShopAnchor videoShopAnchor) throws JsonProcessingException {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);
        videoShopAnchor.setShopId(videoShop.getId());
        videoShopAnchor.getParams().put("tableIndex", videoShop.getTableIndex());

        List<HashMap<String, Object>> resultList = videoShopAnchorMapper.selectSettlementByAnchor(videoShopAnchor);
        Iterator<HashMap<String, Object>> iterator = resultList.iterator();

        while (iterator.hasNext()) {
            BigDecimal anchorCommission = new BigDecimal("0.00");
            Integer sceneLiveCount = 0;
            Integer sceneVideoCount = 0;
            Integer sceneShopCount = 0;

            //直播场次
            Integer liveNum = 0;

            //有效商品数
            Integer productCount = 0;
            //成交订单数
            Integer orderCount = 0;
            //未支付订单数
            Integer unOrderCount = 0;
            //成交用户数
            Integer userCount = 0;
            //售后订单数
            Integer afterCount = 0;
//            售后退款金额
            BigDecimal afterSun = new BigDecimal("0.00");
            //实付销售额
            BigDecimal priceSum = new BigDecimal("0.00");

            //主播底薪
            BigDecimal basicSalary = new BigDecimal("0.00");
            //直播时长
            Integer liveInter = 0;


            BigDecimal sceneLiveSum = new BigDecimal("0.00");
            BigDecimal sceneVideoSum = new BigDecimal("0.00");
            BigDecimal sceneShopSum = new BigDecimal("0.00");

            VideoShopOrder videoShopOrder = new VideoShopOrder();
            videoShopOrder.setShopId(videoShop.getId());
            HashMap<String, Object> resultMap = iterator.next();
            //模版名称

//            模板类型 1-主播底薪 2-比例佣金 3-阶梯佣金 4-底薪+比例佣金 5-底薪+阶梯佣金 6-件数提成
            Long type = (Long) resultMap.get("type");
            if (type.intValue() == -1) {
                iterator.remove();
                continue;
            }
            if (resultMap.get("template_name") == null) {
                continue;
            }
            String templateName = resultMap.get("template_name").toString();
            if (type.intValue() == 1) {
                basicSalary = BigDecimal.valueOf(Long.parseLong((String) resultMap.get("anchor_basic_salary")));
//                anchorCommission = BigDecimal.valueOf(Long.parseLong((String) resultMap.get("anchor_basic_salary")));
                resultMap.put("anchorCommission", anchorCommission);
//查询范围内未支付订单
                unOrderCount = (Integer) resultMap.get("unpaid_orders");
                priceSum = (BigDecimal) resultMap.get("sales_volume");
                orderCount = (Integer) resultMap.get("completed_orders");
                productCount = (Integer) resultMap.get("valid_products");
                afterCount = (Integer) resultMap.get("aftersales_orders");
                afterSun = (BigDecimal) resultMap.get("refund_amount");
                sceneLiveCount =(Integer) resultMap.get("scene_livecount");
                sceneLiveSum =(BigDecimal) resultMap.get("scene_livesum");
                sceneVideoCount= (Integer) resultMap.get("scene_videocount");
                sceneVideoSum = (BigDecimal)resultMap.get("scene_videosum");
                sceneShopCount = (Integer)resultMap.get("scene_shopcount");
                sceneShopSum =(BigDecimal) resultMap.get("scene_shopsum");
                anchorCommission = (BigDecimal)resultMap.get("anchor_commission");
                //直播次数
                liveNum++;
                resultMap.put("liveNum", liveNum);

                resultMap.put("basicSalary", basicSalary);

                resultMap.put("sceneLiveCount", sceneLiveCount);
                resultMap.put("sceneVideoCount", sceneVideoCount);
                resultMap.put("sceneShopCount", sceneShopCount);
                resultMap.put("sceneLiveSum", sceneLiveSum);
                resultMap.put("sceneVideoSum", sceneVideoSum);
                resultMap.put("sceneShopSum", sceneShopSum);

                resultMap.put("productCount", productCount);
                resultMap.put("orderCount", orderCount);

                resultMap.put("afterCount", afterCount);
                resultMap.put("unOrderCount", unOrderCount);

                resultMap.put("priceSum", priceSum);
                resultMap.put("afterSun", afterSun);

                resultMap.put("templateName", resultMap.get("template_name"));
            } else if (type.intValue() == 2) {
                basicSalary = new BigDecimal("0.00");
                String radioGivenString = (String) resultMap.get("radio_given");
                ObjectMapper objectMapper = new ObjectMapper();
                List<HashMap<String, String>> listMap = new LinkedList<>();
                System.out.println(radioGivenString);
                if (!radioGivenString.isEmpty()) {
                    listMap = objectMapper.readValue(radioGivenString, new TypeReference<List<HashMap<String, String>>>() {
                    });
                }
                Iterator<HashMap<String, String>> listMapIterator = listMap.iterator();

                videoShopOrder.setStatus(100);
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss", Locale.getDefault());
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = ((LocalDateTime) resultMap.get("starttime")).format(formatter);
                String formattedDateTime2 = ((LocalDateTime) resultMap.get("endtime")).format(formatter);
                videoShopOrder.getParams().put("beginTime", formattedDateTime);
                videoShopOrder.getParams().put("endTime", formattedDateTime2);


                //查询范围内未支付订单
                unOrderCount = (Integer) resultMap.get("unpaid_orders");
                priceSum = (BigDecimal) resultMap.get("sales_volume");
                orderCount = (Integer) resultMap.get("completed_orders");
                productCount = (Integer) resultMap.get("valid_products");
                afterCount = (Integer) resultMap.get("aftersales_orders");
                afterSun = (BigDecimal) resultMap.get("refund_amount");
                sceneLiveCount =(Integer) resultMap.get("scene_livecount");
                sceneLiveSum =(BigDecimal) resultMap.get("scene_livesum");
                sceneVideoCount= (Integer) resultMap.get("scene_videocount");
                sceneVideoSum = (BigDecimal)resultMap.get("scene_videosum");
                sceneShopCount = (Integer)resultMap.get("scene_shopcount");
                sceneShopSum =(BigDecimal) resultMap.get("scene_shopsum");
                anchorCommission = (BigDecimal)resultMap.get("anchor_commission");
                liveNum++;
                resultMap.put("liveNum", liveNum);

                resultMap.put("basicSalary", basicSalary);

                resultMap.put("anchorCommission", anchorCommission);
                resultMap.put("sceneLiveCount", sceneLiveCount);
                resultMap.put("sceneVideoCount", sceneVideoCount);
                resultMap.put("sceneShopCount", sceneShopCount);
                resultMap.put("sceneLiveSum", sceneLiveSum);
                resultMap.put("sceneVideoSum", sceneVideoSum);
                resultMap.put("sceneShopSum", sceneShopSum);

                resultMap.put("productCount", productCount);
                resultMap.put("orderCount", orderCount);

                resultMap.put("afterCount", afterCount);
                resultMap.put("unOrderCount", unOrderCount);

                resultMap.put("priceSum", priceSum);
                resultMap.put("afterSun", afterSun);

                resultMap.put("templateName", resultMap.get("template_name"));
            } else if (type.intValue() == 3) {
                basicSalary = new BigDecimal("0.00");
                String ladderString = (String) resultMap.get("ladder");
                ObjectMapper objectMapper = new ObjectMapper();
                List<HashMap<String, String>> listMap = objectMapper.readValue(ladderString, new TypeReference<List<HashMap<String, String>>>() {
                });
                Collections.reverse(listMap);
                Iterator<HashMap<String, String>> listMapIterator = listMap.iterator();

                videoShopOrder.setStatus(100);
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss", Locale.getDefault());
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = ((LocalDateTime) resultMap.get("starttime")).format(formatter);
                String formattedDateTime2 = ((LocalDateTime) resultMap.get("endtime")).format(formatter);
                videoShopOrder.getParams().put("beginTime", formattedDateTime);
                videoShopOrder.getParams().put("endTime", formattedDateTime2);

                //查询范围内未支付订单
                unOrderCount = videoShopOrderMapper.selectUnPayCount(videoShop.getId(), formattedDateTime, formattedDateTime2);
                //查询范围内售后订单
                List<String> afterOrderIdList = videoShopAfterSaleOrderMapper.selectVideoShopAfterSaleOrderIdList();
                //获取订单总金额
                List<VideoShopOrder> resultVideoShopOrderList = videoShopOrderMapper.selectVideoShopOrderListAndScene(videoShopOrder);
                Iterator<VideoShopOrder> VideoShopOrderIterator = resultVideoShopOrderList.iterator();
                BigDecimal sumPrice = new BigDecimal("0.00");
                while (VideoShopOrderIterator.hasNext()) {
                    VideoShopOrder videoShopOrder1 = VideoShopOrderIterator.next();
                    sumPrice = sumPrice.add(videoShopOrder1.getOrderPrice());
                    //成交订单数量
                    orderCount++;
                    if (videoShopOrder1.getStatus() == 200) {
                        VideoShopAfterSaleOrder videoShopAfterSaleOrder = videoShopAfterSaleOrderMapper.selectVideoShopAfterSaleOrderByOrderId(videoShopOrder1.getOrderId());
                        //售后订单逻辑
                        if (videoShopAfterSaleOrder != null) {
                            afterCount++;
                            afterSun = afterSun.add(videoShopAfterSaleOrder.getAmount());
                        } else {
                            //成交商品数量
                            productCount += videoShopOrder1.getProductCnt().intValue();
                        }
                    } else {
                        //成交商品数量
                        productCount += videoShopOrder1.getProductCnt().intValue();
                    }
                    //订单来源统计
                    if (videoShopOrder1.getOrderScene() == 2) {
                        sceneLiveCount++;
                        sceneLiveSum = sceneLiveSum.add(videoShopOrder1.getOrderPrice());
                    } else if (videoShopOrder1.getOrderScene() == 3) {
                        sceneVideoCount++;
                        sceneVideoSum = sceneVideoSum.add(videoShopOrder1.getOrderPrice());
                    } else {
                        sceneShopCount++;
                        sceneShopSum = sceneShopSum.add(videoShopOrder1.getOrderPrice());
                    }
                }

                while (listMapIterator.hasNext()) {
                    HashMap<String, String> listMapHashMap = listMapIterator.next();
                    boolean zeroFlag = false;
                    sumPrice = sumPrice.subtract(BigDecimal.valueOf(Long.parseLong((String) listMapHashMap.get("key2"))));
                    int flag = sumPrice.compareTo(BigDecimal.ZERO);
                    if (flag > 0) {
                        zeroFlag = true;
                    }
                    if (zeroFlag) {
                        anchorCommission = anchorCommission.add(sumPrice.multiply(BigDecimal.valueOf(Long.parseLong((String) listMapHashMap.get("key3")) * 0.01)));
                    }
                }
                liveNum++;
                resultMap.put("liveNum", liveNum);
                resultMap.put("basicSalary", basicSalary);
                resultMap.put("anchorCommission", anchorCommission);

                resultMap.put("sceneLiveCount", sceneLiveCount);
                resultMap.put("sceneVideoCount", sceneVideoCount);
                resultMap.put("sceneShopCount", sceneShopCount);
                resultMap.put("sceneLiveSum", sceneLiveSum);
                resultMap.put("sceneVideoSum", sceneVideoSum);
                resultMap.put("sceneShopSum", sceneShopSum);

                resultMap.put("productCount", productCount);
                resultMap.put("orderCount", orderCount);

                resultMap.put("afterCount", afterCount);
                resultMap.put("unOrderCount", unOrderCount);

                resultMap.put("priceSum", sumPrice);
                resultMap.put("afterSun", afterSun);

                resultMap.put("templateName", resultMap.get("template_name"));
            } else if (type.intValue() == 4) {
                basicSalary = new BigDecimal("0.00");
                String basicRadioGivenString = (String) resultMap.get("basic_radio_given");
                ObjectMapper objectMapper = new ObjectMapper();
                List<HashMap<String, String>> listMap = objectMapper.readValue(basicRadioGivenString, new TypeReference<List<HashMap<String, String>>>() {
                });
                //查询范围内未支付订单
                unOrderCount = (Integer) resultMap.get("unpaid_orders");
                priceSum = (BigDecimal) resultMap.get("sales_volume");
                orderCount = (Integer) resultMap.get("completed_orders");
                productCount = (Integer) resultMap.get("valid_products");
                afterCount = (Integer) resultMap.get("aftersales_orders");
                afterSun = (BigDecimal) resultMap.get("refund_amount");
                sceneLiveCount =(Integer) resultMap.get("scene_livecount");
                sceneLiveSum =(BigDecimal) resultMap.get("scene_livesum");
                sceneVideoCount= (Integer) resultMap.get("scene_videocount");
                sceneVideoSum = (BigDecimal)resultMap.get("scene_videosum");
                sceneShopCount = (Integer)resultMap.get("scene_shopcount");
                sceneShopSum =(BigDecimal) resultMap.get("scene_shopsum");
                anchorCommission = (BigDecimal)resultMap.get("anchor_commission");
//                Iterator<VideoShopOrder> VideoShopOrderIterator = resultVideoShopOrderList.iterator();
//                while (VideoShopOrderIterator.hasNext()) {
//                    VideoShopOrder videoShopOrder1 = VideoShopOrderIterator.next();
//
//                    //订单来源统计
//                    if (videoShopOrder1.getOrderScene() == 2) {
//                        sceneLiveCount++;
//                        sceneLiveSum = sceneLiveSum.add(videoShopOrder1.getOrderPrice());
//                    } else if (videoShopOrder1.getOrderScene() == 3) {
//                        sceneVideoCount++;
//                        sceneVideoSum = sceneVideoSum.add(videoShopOrder1.getOrderPrice());
//                    } else {
//                        sceneShopCount++;
//                        sceneShopSum = sceneShopSum.add(videoShopOrder1.getOrderPrice());
//                    }
//                    Iterator<HashMap<String, String>> listMapIterator = listMap.iterator();
//                    if(!listMapIterator.hasNext()){
//                        anchorCommission = anchorCommission.add(videoShopOrder1.getOrderPrice().multiply(BigDecimal.valueOf(Double.parseDouble((String) resultMap.get("basic_radio_anchor_default")) * 0.01)));
//                    }
//                    else{
//                        while (listMapIterator.hasNext()) {
//                            HashMap<String, String> listMapHashMap = listMapIterator.next();
//                            boolean containsValue = listMapHashMap.containsValue(videoShopOrder1.getProductId());
//                            if(videoShopOrder1.getStatus() == 100 || videoShopOrder1.getStatus() == 20||videoShopOrder1.getStatus() == 21||videoShopOrder1.getStatus() == 30){
//                                if (containsValue) {
//                                    String bilu = (String) listMapHashMap.get("key3");
//                                    anchorCommission = anchorCommission.add(videoShopOrder1.getOrderPrice().multiply(BigDecimal.valueOf(Long.parseLong(bilu) * 0.01)));
//                                } else {
//                                    anchorCommission = anchorCommission.add(videoShopOrder1.getOrderPrice().multiply(BigDecimal.valueOf(Double.parseDouble((String) resultMap.get("basic_radio_anchor_default")) * 0.01)));
//                                }
//                            }
//
//                        }
//                    }
//
//                }
                //底薪
                basicSalary = BigDecimal.valueOf(Long.parseLong((String) resultMap.get("basic_radio_anchor_basic_salary")));

//                anchorCommission = anchorCommission.add(BigDecimal.valueOf(Long.parseLong((String)resultMap.get("basic_radio_anchor_basic_salary"))));
                liveNum++;
                resultMap.put("liveNum", liveNum);

                resultMap.put("basicSalary", basicSalary);
                resultMap.put("anchorCommission", anchorCommission);
                resultMap.put("sceneLiveCount", sceneLiveCount);
                resultMap.put("sceneVideoCount", sceneVideoCount);
                resultMap.put("sceneShopCount", sceneShopCount);
                resultMap.put("sceneLiveSum", sceneLiveSum);
                resultMap.put("sceneVideoSum", sceneVideoSum);
                resultMap.put("sceneShopSum", sceneShopSum);

                resultMap.put("productCount", productCount);
                resultMap.put("orderCount", orderCount);

                resultMap.put("afterCount", afterCount);
                resultMap.put("unOrderCount", unOrderCount);

                resultMap.put("priceSum", priceSum);
                resultMap.put("afterSun", afterSun);

                resultMap.put("templateName", resultMap.get("template_name"));
            } else if (type.intValue() == 5) {
                basicSalary = new BigDecimal("0.00");

                String ladderString = (String) resultMap.get("basic_ladder");
                ObjectMapper objectMapper = new ObjectMapper();
                List<HashMap<String, String>> listMap = objectMapper.readValue(ladderString, new TypeReference<List<HashMap<String, String>>>() {
                });
                Collections.reverse(listMap);
                Iterator<HashMap<String, String>> listMapIterator = listMap.iterator();

                videoShopOrder.setStatus(100);
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss", Locale.getDefault());
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = ((LocalDateTime) resultMap.get("starttime")).format(formatter);
                String formattedDateTime2 = ((LocalDateTime) resultMap.get("endtime")).format(formatter);
                videoShopOrder.getParams().put("beginTime", formattedDateTime);
                videoShopOrder.getParams().put("endTime", formattedDateTime2);

                //查询范围内未支付订单
                unOrderCount = videoShopOrderMapper.selectUnPayCount(videoShop.getId(), formattedDateTime, formattedDateTime2);
                //获取订单总金额
                List<VideoShopOrder> resultVideoShopOrderList = videoShopOrderMapper.selectVideoShopOrderListAndScene(videoShopOrder);
                Iterator<VideoShopOrder> VideoShopOrderIterator = resultVideoShopOrderList.iterator();
                BigDecimal sumPrice = new BigDecimal("0.00");

                //查询范围内未支付订单
                unOrderCount = (Integer) resultMap.get("unpaid_orders");
                priceSum = (BigDecimal) resultMap.get("sales_volume");
                orderCount = (Integer) resultMap.get("completed_orders");
                productCount = (Integer) resultMap.get("valid_products");
                afterCount = (Integer) resultMap.get("aftersales_orders");
                afterSun = (BigDecimal) resultMap.get("refund_amount");

                while (VideoShopOrderIterator.hasNext()) {
                    VideoShopOrder videoShopOrder1 = VideoShopOrderIterator.next();
                    priceSum = priceSum.add(videoShopOrder1.getOrderPrice());


                    //订单来源统计
                    if (videoShopOrder1.getOrderScene() == 2) {
                        sceneLiveCount++;
                        sceneLiveSum = sceneLiveSum.add(videoShopOrder1.getOrderPrice());
                    } else if (videoShopOrder1.getOrderScene() == 3) {
                        sceneVideoCount++;
                        sceneVideoSum = sceneVideoSum.add(videoShopOrder1.getOrderPrice());
                    } else {
                        sceneShopCount++;
                        sceneShopSum = sceneShopSum.add(videoShopOrder1.getOrderPrice());
                    }
                    sumPrice = sumPrice.add(videoShopOrder1.getOrderPrice());
                }

                while (listMapIterator.hasNext()) {
                    HashMap<String, String> listMapHashMap = listMapIterator.next();
                    boolean zeroFlag = false;
                    sumPrice = sumPrice.subtract(BigDecimal.valueOf(Long.parseLong((String) listMapHashMap.get("key2"))));
                    int flag = sumPrice.compareTo(BigDecimal.ZERO);
                    if (flag > 0) {
                        zeroFlag = true;
                    }
                    if (zeroFlag) {
                        anchorCommission = anchorCommission.add(sumPrice.multiply(BigDecimal.valueOf(Long.parseLong((String) listMapHashMap.get("key3")) * 0.01)));
                    }
                }
                basicSalary = BigDecimal.valueOf(Long.parseLong((String) resultMap.get("basic_ladder_anchor_basic_salary")));

//                anchorCommission = anchorCommission.add(BigDecimal.valueOf(Long.parseLong((String) resultMap.get("basic_ladder_anchor_basic_salary"))));
                liveNum++;
                resultMap.put("liveNum", liveNum);

                resultMap.put("basicSalary", basicSalary);
                resultMap.put("anchorCommission", anchorCommission);
                resultMap.put("sceneLiveCount", sceneLiveCount);
                resultMap.put("sceneVideoCount", sceneVideoCount);
                resultMap.put("sceneShopCount", sceneShopCount);
                resultMap.put("sceneLiveSum", sceneLiveSum);
                resultMap.put("sceneVideoSum", sceneVideoSum);
                resultMap.put("sceneShopSum", sceneShopSum);

                resultMap.put("productCount", productCount);
                resultMap.put("orderCount", orderCount);

                resultMap.put("afterCount", afterCount);
                resultMap.put("unOrderCount", unOrderCount);

                resultMap.put("priceSum", priceSum);
                resultMap.put("afterSun", afterSun);

                resultMap.put("templateName", resultMap.get("template_name"));
            } else if (type.intValue() == 6) {
                basicSalary = new BigDecimal("0.00");
                ObjectMapper objectMapper = new ObjectMapper();

                videoShopOrder.setStatus(100);
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss", Locale.getDefault());
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = ((LocalDateTime) resultMap.get("starttime")).format(formatter);
                String formattedDateTime2 = ((LocalDateTime) resultMap.get("endtime")).format(formatter);
                videoShopOrder.getParams().put("beginTime", formattedDateTime);
                videoShopOrder.getParams().put("endTime", formattedDateTime2);

                //查询范围内未支付订单
                unOrderCount = videoShopOrderMapper.selectUnPayCount(videoShop.getId(), formattedDateTime, formattedDateTime2);
                //获取订单总金额
                List<VideoShopOrder> resultVideoShopOrderList = videoShopOrderMapper.selectVideoShopOrderListAndScene(videoShopOrder);
                Iterator<VideoShopOrder> VideoShopOrderIterator = resultVideoShopOrderList.iterator();
                BigDecimal sumCnt = new BigDecimal("0.00");
                while (VideoShopOrderIterator.hasNext()) {
                    VideoShopOrder videoShopOrder1 = VideoShopOrderIterator.next();
                    priceSum = priceSum.add(videoShopOrder1.getOrderPrice());


                    //成交订单数量
                    orderCount++;
                    if (videoShopOrder1.getStatus() == 200) {
                        VideoShopAfterSaleOrder videoShopAfterSaleOrder = videoShopAfterSaleOrderMapper.selectVideoShopAfterSaleOrderByOrderId(videoShopOrder1.getOrderId());
                        //售后订单逻辑
                        if (videoShopAfterSaleOrder != null) {
                            afterCount++;
                            afterSun = afterSun.add(videoShopAfterSaleOrder.getAmount());
                        } else {
                            //成交商品数量
                            productCount += videoShopOrder1.getProductCnt().intValue();
                        }
                    } else {
                        //成交商品数量
                        productCount += videoShopOrder1.getProductCnt().intValue();
                    }
                    //订单来源统计
                    if (videoShopOrder1.getOrderScene() == 2) {
                        sceneLiveCount++;
                        sceneLiveSum = sceneLiveSum.add(videoShopOrder1.getOrderPrice());
                    } else if (videoShopOrder1.getOrderScene() == 3) {
                        sceneVideoCount++;
                        sceneVideoSum = sceneVideoSum.add(videoShopOrder1.getOrderPrice());
                    } else {
                        sceneShopCount++;
                        sceneShopSum = sceneShopSum.add(videoShopOrder1.getOrderPrice());
                    }
                    sumCnt = sumCnt.add(BigDecimal.valueOf(videoShopOrder1.getProductCnt()));
                }

                anchorCommission = anchorCommission.add(sumCnt.multiply((BigDecimal) resultMap.get("quantity_anchor_commission")));
                liveNum++;
                resultMap.put("liveNum", liveNum);

                resultMap.put("basicSalary", basicSalary);
                resultMap.put("anchorCommission", anchorCommission);
                resultMap.put("sceneLiveCount", sceneLiveCount);
                resultMap.put("sceneVideoCount", sceneVideoCount);
                resultMap.put("sceneShopCount", sceneShopCount);
                resultMap.put("sceneLiveSum", sceneLiveSum);
                resultMap.put("sceneVideoSum", sceneVideoSum);
                resultMap.put("sceneShopSum", sceneShopSum);

                resultMap.put("productCount", productCount);
                resultMap.put("orderCount", orderCount);

                resultMap.put("afterCount", afterCount);
                resultMap.put("unOrderCount", unOrderCount);

                resultMap.put("priceSum", priceSum);
                resultMap.put("afterSun", afterSun);

                resultMap.put("templateName", resultMap.get("template_name"));
            }
            resultMap.put("liveInter", resultMap.get("liveInter"));
        }
        return resultList;
    }

    @Override
    public List<HashMap<String, Object>> getVideoShopAnchorList() {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);
        VideoShopAnchor videoShopAnchor = new VideoShopAnchor();
        videoShopAnchor.setShopId(videoShop.getId());
        videoShopAnchor.getParams().put("tableIndex", videoShop.getTableIndex());

        return videoShopAnchorMapper.getVideoShopAnchorList(videoShopAnchor);
    }

    @Override
    public List<HashMap<String, Object>> getVideoShopTemplateList() {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);
        VideoShopSalarytemplate videoShopSalarytemplate = new VideoShopSalarytemplate();
        videoShopSalarytemplate.setLocalShopId(videoShop.getId());
        videoShopSalarytemplate.getParams().put("tableIndex", videoShop.getTableIndex());

        return videoShopSalarytemplateMapper.getVideoShopTemplateList(videoShopSalarytemplate);
    }

    @Override
    public List<HashMap<String, Object>> getCommissionList() {
        // 获取当前的用户信息
        SysUser currentUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = currentUser.getLoginName();
        VideoShop videoShop = videoShopMapper.selectVideoShopByOwner(loginName);

        List<HashMap<String, Object>> commissionList = videoShopAnchorMapper.getCommissionList(videoShop.getId(), String.valueOf(videoShop.getTableIndex()));
        HashMap<String, Object> mainAccount = new HashMap<>();
        mainAccount.put("id", -1);
        mainAccount.put("name", videoShop.getShopName() + "直播号");
        commissionList.add(mainAccount);
        return commissionList;
    }

}
