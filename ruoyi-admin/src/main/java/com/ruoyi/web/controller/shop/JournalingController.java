package com.ruoyi.web.controller.shop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SettlementListExportVO;
import com.ruoyi.system.domain.VideoShopAnchor;
import com.ruoyi.system.domain.VideoShopSchedulingVO;
import com.ruoyi.system.mapper.VideoShopSchedulingMapper;
import com.ruoyi.system.service.IVideoShopAnchorService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Controller
@RequestMapping("/journaling")
public class JournalingController extends BaseController {
    private String prefix = "shop/journaling";

    @Autowired
    private IVideoShopAnchorService videoShopAnchorService;
    @Autowired
    private VideoShopSchedulingMapper videoShopSchedulingMapper;

    @RequiresPermissions("system:user:view")
    @GetMapping("/anchor")
    public String user()
    {
        return prefix + "/anchor";
    }

    @RequiresPermissions("system:user:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(VideoShopAnchor videoShopAnchor)
    {
        List<HashMap<String,Object>> resultList = videoShopAnchorService.selectJournalingByAnchor(videoShopAnchor);
        return getDataTable(resultList);
    }

    @RequiresPermissions("system:user:list")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(VideoShopAnchor videoShopAnchor)
    {
        List<VideoShopSchedulingVO> resultList = videoShopAnchorService.selectJournalingByAnchorEX(videoShopAnchor);

        ExcelUtil<VideoShopSchedulingVO> util = new ExcelUtil<VideoShopSchedulingVO>(VideoShopSchedulingVO.class);
//        util.addR("1",1,1,1,1);
//        return util.exportExcel(list, "订单表数据");
        return util.exportExcel(resultList, "用户数据");
    }

    @RequiresPermissions("system:user:view")
    @GetMapping("/settlement")
    public String settlement()
    {
        return prefix + "/settlement";
    }


    @RequiresPermissions("system:user:view")
    @GetMapping("/live")
    public String live()
    {
        return prefix + "/live";
    }


    @RequiresPermissions("system:user:list")
    @PostMapping("/settlementList")
    @ResponseBody
    public TableDataInfo settlementList(VideoShopAnchor videoShopAnchor) throws JsonProcessingException {
        settlementListBefor(videoShopAnchor);

        //查询每场直播的数据
        List<HashMap<String,Object>> resultList = videoShopAnchorService.selectSettlementByAnchor(videoShopAnchor);
        List<HashMap<String, Object>> newResultList = settlementListAfter(videoShopAnchor,resultList);

        return getDataTable(newResultList);
    }

    public VideoShopAnchor settlementListBefor(VideoShopAnchor videoShopAnchor) {
        if (!videoShopAnchor.getParams().isEmpty()) {
            //日期处理
            String selectDate = (String) videoShopAnchor.getParams().get("beginTime");
            if (((String) videoShopAnchor.getParams().get("selectDateType")).equals("1")) {

                if (!((String) videoShopAnchor.getParams().get("beginTime1")).isEmpty()) {
                    String endMonth = (String) videoShopAnchor.getParams().get("endTime1");

                    StringTokenizer tokenizer = new StringTokenizer(endMonth, "-");
                    Integer substr = Integer.valueOf(tokenizer.nextToken()); // 截取第一个逗号前的部分
                    Integer substr2 = Integer.valueOf(tokenizer.nextToken()); // 截取第一个逗号后的部分
                    // 创建一个LocalDate实例，只有年和月，日默认为月份的最后一天
                    LocalDate date = LocalDate.of(substr, substr2, 1).with(TemporalAdjusters.lastDayOfMonth());
                    // 创建一个DateTimeFormatter来格式化日期
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    // 使用formatter格式化LocalDate实例，并输出结果
                    String formattedDate = date.format(formatter);
                    videoShopAnchor.getParams().put("beginTime", (String) videoShopAnchor.getParams().get("beginTime1") + "-01");
                    videoShopAnchor.getParams().put("endTime", formattedDate);
                }
            } else if (((String) videoShopAnchor.getParams().get("selectDateType")).equals("2")) {
                String beginDate = (String) videoShopAnchor.getParams().get("beginTime2");
                StringTokenizer tokenizer = new StringTokenizer(beginDate, "~");
                String beginWeekDate = (tokenizer.nextToken()); // 截取第一个逗号前的部分
                String endWeekDate = (tokenizer.nextToken()); // 截取第一个逗号后的部分
                videoShopAnchor.getParams().put("beginTime", beginWeekDate);
                videoShopAnchor.getParams().put("endTime", endWeekDate);
            } else {
                videoShopAnchor.getParams().put("beginTime", (String) videoShopAnchor.getParams().get("beginTime3"));
                videoShopAnchor.getParams().put("endTime", (String) videoShopAnchor.getParams().get("endTime3"));
            }
            if (selectDate.contains("~")) {
                System.out.println("000000000000000000000000000000");
            }
        }
        return videoShopAnchor;
    }
    public List<HashMap<String, Object>> settlementListAfter(VideoShopAnchor videoShopAnchor,List<HashMap<String, Object>> resultList) {
        List<HashMap<String, Object>> newResultList = new ArrayList<HashMap<String, Object>>();

        Iterator<HashMap<String, Object>> iterator = resultList.iterator();
        Integer liveCount = 0;


        String MonthStrOld = "";
        //主播底薪
        BigDecimal basicSalary = new BigDecimal("0.00");
        BigDecimal anchorCommission = new BigDecimal("0.00");

        Integer sceneLiveCount = 0;
        Integer sceneVideoCount = 0;
        Integer sceneShopCount = 0;
        //直播次数
        Integer liveNum = 0;
        //直播时长
        Integer liveInter = 0;
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

        BigDecimal sceneLiveSum = new BigDecimal("0.00");
        BigDecimal sceneVideoSum = new BigDecimal("0.00");
        BigDecimal sceneShopSum = new BigDecimal("0.00");
        String monthStrOld = "";
        String monthStrNew = "";

        String anchorNameOld = "";
        String anchorNameNew = "";

        String templateName = "";

        while (iterator.hasNext()) {

            HashMap<String, Object> resultMapNew = new HashMap<String, Object>();

            HashMap<String, Object> resultMap = iterator.next();
            if (((String) videoShopAnchor.getParams().get("selectDateType")).equals("1")) {
                monthStrNew = resultMap.get("starttime").toString().substring(0, 7);
            } else if (((String) videoShopAnchor.getParams().get("selectDateType")).equals("2")) {
                monthStrNew = resultMap.get("starttime").toString().substring(0, 10);
            } else {
                monthStrNew = resultMap.get("starttime").toString().substring(0, 10);
            }
            anchorNameNew = resultMap.get("anchor_name").toString();
            //第一条数据
            if (monthStrOld.equals("")) {
                templateName = resultMap.get("templateName").toString();
                liveNum += (Integer) resultMap.get("liveNum");
                liveInter += Integer.parseInt(String.valueOf(resultMap.get("time_interval")));

                sceneLiveCount += (Integer) resultMap.get("sceneLiveCount");
                sceneVideoCount += (Integer) resultMap.get("sceneVideoCount");
                sceneShopCount += (Integer) resultMap.get("sceneShopCount");

                sceneLiveSum = sceneLiveSum.add(new BigDecimal(resultMap.get("sceneLiveSum").toString()));
                sceneVideoSum = sceneVideoSum.add(new BigDecimal(resultMap.get("sceneVideoSum").toString()));
                sceneShopSum = sceneShopSum.add(new BigDecimal(resultMap.get("sceneShopSum").toString()));

                productCount += (Integer) resultMap.get("productCount");
                orderCount += (Integer) resultMap.get("orderCount");
                afterCount += (Integer) resultMap.get("afterCount");
                unOrderCount += (Integer) resultMap.get("unOrderCount");

                priceSum = priceSum.add(new BigDecimal(resultMap.get("priceSum").toString()));
                afterSun = afterSun.add(new BigDecimal(resultMap.get("afterSun").toString()));

                basicSalary = (new BigDecimal(resultMap.get("basicSalary").toString()));
                anchorCommission = anchorCommission.add(new BigDecimal(resultMap.get("anchorCommission").toString()));

                monthStrOld = monthStrNew;
                anchorNameOld = resultMap.get("anchor_name").toString();

            } else {
//                根据主播名称统计数据
                if (anchorNameNew.equals(anchorNameOld)) {
                    templateName = resultMap.get("templateName").toString();
                    //根据日期统计数据
                    if (monthStrNew.equals(monthStrOld)) {

                        liveNum += (Integer) resultMap.get("liveNum");

                        liveInter += Integer.parseInt(String.valueOf(resultMap.get("time_interval")));

                        sceneLiveCount += (Integer) resultMap.get("sceneLiveCount");
                        sceneVideoCount += (Integer) resultMap.get("sceneVideoCount");
                        sceneShopCount += (Integer) resultMap.get("sceneShopCount");

                        sceneLiveSum = sceneLiveSum.add(new BigDecimal(resultMap.get("sceneLiveSum").toString()));
                        sceneVideoSum = sceneVideoSum.add(new BigDecimal(resultMap.get("sceneVideoSum").toString()));
                        sceneShopSum = sceneShopSum.add(new BigDecimal(resultMap.get("sceneShopSum").toString()));

                        productCount += (Integer) resultMap.get("productCount");
                        orderCount += (Integer) resultMap.get("orderCount");
                        afterCount += (Integer) resultMap.get("afterCount");
                        unOrderCount += (Integer) resultMap.get("unOrderCount");

                        priceSum = priceSum.add(new BigDecimal(resultMap.get("priceSum").toString()));
                        afterSun = afterSun.add(new BigDecimal(resultMap.get("afterSun").toString()));

                        basicSalary = new BigDecimal(resultMap.get("basicSalary").toString());
                        anchorCommission = anchorCommission.add(new BigDecimal(resultMap.get("anchorCommission").toString()));

                    } else {
                        resultMapNew.put("anchor_name", anchorNameNew);
                        resultMapNew.put("liveNum", liveNum);
                        resultMapNew.put("liveInter", liveInter);

                        resultMapNew.put("dateStr", monthStrOld);

                        resultMapNew.put("sceneLiveCount", sceneLiveCount);
                        resultMapNew.put("sceneVideoCount", sceneVideoCount);
                        resultMapNew.put("sceneShopCount", sceneShopCount);

                        resultMapNew.put("sceneLiveSum", sceneLiveSum);
                        resultMapNew.put("sceneVideoSum", sceneVideoSum);
                        resultMapNew.put("sceneShopSum", sceneShopSum);

                        resultMapNew.put("productCount", productCount);
                        resultMapNew.put("orderCount", orderCount);
                        resultMapNew.put("afterCount", afterCount);
                        resultMapNew.put("unOrderCount", unOrderCount);


                        resultMapNew.put("priceSum", priceSum);
                        resultMapNew.put("afterSun", afterSun);

                        resultMapNew.put("basicSalary", basicSalary);
                        resultMapNew.put("anchorCommission", anchorCommission);
                        resultMapNew.put("templateName", templateName);

                        monthStrOld = monthStrNew;

                        newResultList.add(resultMapNew);
//                    resultMapNew.clear();
                        liveInter = Integer.parseInt(String.valueOf(resultMap.get("time_interval")));
                        liveNum = Integer.parseInt(String.valueOf(resultMap.get("liveNum")));
                        sceneLiveCount = (Integer) resultMap.get("sceneLiveCount");
                        sceneVideoCount = (Integer) resultMap.get("sceneVideoCount");
                        sceneShopCount = (Integer) resultMap.get("sceneShopCount");
                        sceneLiveSum = new BigDecimal(resultMap.get("sceneLiveSum").toString());
                        sceneVideoSum = new BigDecimal(resultMap.get("sceneVideoSum").toString());
                        sceneShopSum = new BigDecimal(resultMap.get("sceneShopSum").toString());

                        productCount = (Integer) resultMap.get("productCount");
                        orderCount = (Integer) resultMap.get("orderCount");
                        afterCount = (Integer) resultMap.get("afterCount");
                        unOrderCount = (Integer) resultMap.get("unOrderCount");

                        priceSum = new BigDecimal(resultMap.get("priceSum").toString());
                        afterSun = new BigDecimal(resultMap.get("afterSun").toString());

                        basicSalary = new BigDecimal(resultMap.get("basicSalary").toString());
                        anchorCommission = new BigDecimal(resultMap.get("anchorCommission").toString());

                    }
                }

                //主播名称不同，
                else {
                    //将原数据保存
                    basicSalary = new BigDecimal(resultMap.get("basicSalary").toString());
                    anchorCommission = new BigDecimal(resultMap.get("anchorCommission").toString());

                    resultMapNew.put("anchor_name", anchorNameOld);
                    resultMapNew.put("liveNum", liveNum);
                    resultMapNew.put("liveInter", liveInter);

                    resultMapNew.put("dateStr", monthStrOld);

                    resultMapNew.put("sceneLiveCount", sceneLiveCount);
                    resultMapNew.put("sceneVideoCount", sceneVideoCount);
                    resultMapNew.put("sceneShopCount", sceneShopCount);

                    resultMapNew.put("sceneLiveSum", sceneLiveSum);
                    resultMapNew.put("sceneVideoSum", sceneVideoSum);
                    resultMapNew.put("sceneShopSum", sceneShopSum);

                    resultMapNew.put("productCount", productCount);
                    resultMapNew.put("orderCount", orderCount);
                    resultMapNew.put("afterCount", afterCount);
                    resultMapNew.put("unOrderCount", unOrderCount);


                    resultMapNew.put("priceSum", priceSum);
                    resultMapNew.put("afterSun", afterSun);

                    resultMapNew.put("basicSalary", basicSalary);
                    resultMapNew.put("anchorCommission", anchorCommission);
                    resultMapNew.put("templateName", templateName);

                    newResultList.add(resultMapNew);


                    templateName = resultMap.get("templateName").toString();
                    liveNum = (Integer) resultMap.get("liveNum");
                    liveInter = Integer.parseInt(String.valueOf(resultMap.get("time_interval")));

                    sceneLiveCount = (Integer) resultMap.get("sceneLiveCount");
                    sceneVideoCount = (Integer) resultMap.get("sceneVideoCount");
                    sceneShopCount = (Integer) resultMap.get("sceneShopCount");

                    sceneLiveSum = new BigDecimal(resultMap.get("sceneLiveSum").toString());
                    sceneVideoSum = new BigDecimal(resultMap.get("sceneVideoSum").toString());
                    sceneShopSum = new BigDecimal(resultMap.get("sceneShopSum").toString());

                    productCount = (Integer) resultMap.get("productCount");
                    orderCount = (Integer) resultMap.get("orderCount");
                    afterCount = (Integer) resultMap.get("afterCount");
                    unOrderCount = (Integer) resultMap.get("unOrderCount");

                    priceSum = new BigDecimal(resultMap.get("priceSum").toString());
                    afterSun = new BigDecimal(resultMap.get("afterSun").toString());

                    basicSalary = (new BigDecimal(resultMap.get("basicSalary").toString()));
                    anchorCommission = new BigDecimal(resultMap.get("anchorCommission").toString());

                    //11111
                    /*liveInter = Integer.parseInt(String.valueOf(resultMap.get("time_interval")));
                    liveNum = Integer.parseInt(String.valueOf(resultMap.get("liveNum")));
                    sceneLiveCount=(Integer) resultMap.get("sceneLiveCount");
                    sceneVideoCount=(Integer) resultMap.get("sceneVideoCount");
                    sceneShopCount=(Integer) resultMap.get("sceneShopCount");*/
//                    sceneLiveSum = new BigDecimal(resultMap.get("sceneLiveSum").toString());
//                    sceneVideoSum = new BigDecimal(resultMap.get("sceneVideoSum").toString());
//                    sceneShopSum = new BigDecimal(resultMap.get("sceneShopSum").toString());

                 /*   productCount=(Integer) resultMap.get("productCount");
                    orderCount=(Integer) resultMap.get("orderCount");
                    afterCount=(Integer) resultMap.get("afterCount");
                    unOrderCount=(Integer) resultMap.get("unOrderCount");*/

//                    priceSum = new BigDecimal(resultMap.get("priceSum").toString());
//                    afterSun = new BigDecimal(resultMap.get("afterSun").toString());
//
//                    basicSalary = new BigDecimal(resultMap.get("basicSalary").toString());
//                    anchorCommission = new BigDecimal(resultMap.get("anchorCommission").toString());
                    anchorNameOld = anchorNameNew;
                    monthStrOld = monthStrNew;
                }
            }
        }

        HashMap<String, Object> resultMapNew = new HashMap<String, Object>();
        resultMapNew.put("anchor_name", anchorNameOld);
        resultMapNew.put("liveNum", liveNum);

        resultMapNew.put("liveInter", liveInter);

        resultMapNew.put("dateStr", monthStrOld);

        resultMapNew.put("sceneLiveCount", sceneLiveCount);
        resultMapNew.put("sceneVideoCount", sceneVideoCount);
        resultMapNew.put("sceneShopCount", sceneShopCount);

        resultMapNew.put("sceneLiveSum", sceneLiveSum);
        resultMapNew.put("sceneVideoSum", sceneVideoSum);
        resultMapNew.put("sceneShopSum", sceneShopSum);

        resultMapNew.put("productCount", productCount);
        resultMapNew.put("orderCount", orderCount);
        resultMapNew.put("afterCount", afterCount);
        resultMapNew.put("unOrderCount", unOrderCount);

        resultMapNew.put("priceSum", priceSum);
        resultMapNew.put("afterSun", afterSun);

        resultMapNew.put("basicSalary", basicSalary);
        resultMapNew.put("anchorCommission", anchorCommission);

        resultMapNew.put("templateName", templateName);

        newResultList.add(resultMapNew);

        //查询方式为周时特殊处理
        List<HashMap<String, Object>> newResultWeek = new ArrayList<HashMap<String, Object>>();

        if (((String) videoShopAnchor.getParams().get("selectDateType")).equals("2")) {
            liveNum = 0;
            liveInter = 0;
            sceneLiveCount = 0;
            sceneVideoCount = 0;
            sceneShopCount = 0;
            sceneLiveSum = new BigDecimal("0.00");
            sceneVideoSum = new BigDecimal("0.00");
            sceneShopSum = new BigDecimal("0.00");

            productCount = 0;
            orderCount = 0;
            afterCount = 0;
            unOrderCount = 0;
            priceSum = new BigDecimal("0.00");
            afterSun = new BigDecimal("0.00");

            basicSalary = new BigDecimal("0.00");
            anchorCommission = new BigDecimal("0.00");
            HashMap<String, Object> resultMapWeek = new HashMap<String, Object>();
            for (int i = 0; i < newResultList.size(); i++) {

                liveNum += (Integer) newResultList.get(i).get("liveNum");
                liveInter += Integer.parseInt(String.valueOf(newResultList.get(i).get("liveInter")));

                sceneLiveCount += (Integer) newResultList.get(i).get("sceneLiveCount");
                sceneVideoCount += (Integer) newResultList.get(i).get("sceneVideoCount");
                sceneShopCount += (Integer) newResultList.get(i).get("sceneShopCount");

                sceneLiveSum = sceneLiveSum.add(new BigDecimal(newResultList.get(i).get("sceneLiveSum").toString()));
                sceneVideoSum = sceneVideoSum.add(new BigDecimal(newResultList.get(i).get("sceneVideoSum").toString()));
                sceneShopSum = sceneShopSum.add(new BigDecimal(newResultList.get(i).get("sceneShopSum").toString()));

                productCount += (Integer) newResultList.get(i).get("productCount");
                orderCount += (Integer) newResultList.get(i).get("orderCount");
                afterCount += (Integer) newResultList.get(i).get("afterCount");
                unOrderCount += (Integer) newResultList.get(i).get("unOrderCount");

                priceSum = priceSum.add(new BigDecimal(newResultList.get(i).get("priceSum").toString()));
                afterSun = afterSun.add(new BigDecimal(newResultList.get(i).get("afterSun").toString()));

                basicSalary = new BigDecimal(newResultList.get(i).get("basicSalary").toString());
                anchorCommission = anchorCommission.add(new BigDecimal(newResultList.get(i).get("anchorCommission").toString()));


            }
            resultMapWeek.put("liveNum", liveNum);

            resultMapWeek.put("liveInter", liveInter);

            resultMapWeek.put("dateStr", monthStrOld);

            resultMapWeek.put("sceneLiveCount", sceneLiveCount);
            resultMapWeek.put("sceneVideoCount", sceneVideoCount);
            resultMapWeek.put("sceneShopCount", sceneShopCount);

            resultMapWeek.put("sceneLiveSum", sceneLiveSum);
            resultMapWeek.put("sceneVideoSum", sceneVideoSum);
            resultMapWeek.put("sceneShopSum", sceneShopSum);

            resultMapWeek.put("productCount", productCount);
            resultMapWeek.put("orderCount", orderCount);
            resultMapWeek.put("afterCount", afterCount);
            resultMapWeek.put("unOrderCount", unOrderCount);

            resultMapWeek.put("priceSum", priceSum);
            resultMapWeek.put("afterSun", afterSun);

            resultMapWeek.put("basicSalary", basicSalary);
            resultMapWeek.put("anchorCommission", anchorCommission);
            resultMapNew.put("templateName", templateName);

//                newResultList.get(i)

            newResultWeek.add(resultMapWeek);
            return newResultWeek;
        }

        return newResultList;
    }

    @PostMapping("/settlementList/export")
    @ResponseBody
    public AjaxResult settlementListExport(VideoShopAnchor videoShopAnchor) {
        settlementListBefor(videoShopAnchor);
        List<HashMap<String, Object>> resultList = null;
        try {
            resultList = videoShopAnchorService.selectSettlementByAnchor(videoShopAnchor);
        } catch (JsonProcessingException e) {
            return AjaxResult.error("导出失败");
        }

        List<HashMap<String, Object>> newResultList = settlementListAfter(videoShopAnchor,resultList);
        List<SettlementListExportVO> VOResultList = new ArrayList<SettlementListExportVO>();

        for(int i=0;i<newResultList.size();i++){
            SettlementListExportVO settlementListExportVO = new SettlementListExportVO();

            settlementListExportVO.setAnchorName(newResultList.get(i).get("anchor_name").toString());
            settlementListExportVO.setFormattedDate(newResultList.get(i).get("dateStr").toString());
            settlementListExportVO.setEtc(newResultList.get(i).get("liveNum").toString());

            settlementListExportVO.setValidProducts(newResultList.get(i).get("productCount").toString());
            settlementListExportVO.setCompletedOrders(newResultList.get(i).get("orderCount").toString());
            BigDecimal validOrders = new BigDecimal("0.00");
            BigDecimal orderCount = new BigDecimal(newResultList.get(i).get("orderCount").toString());
            BigDecimal unOrderCount = new BigDecimal(newResultList.get(i).get("unOrderCount").toString());
            BigDecimal afterCount = new BigDecimal(newResultList.get(i).get("afterCount").toString());

            validOrders = orderCount.subtract(unOrderCount).subtract(afterCount);
            settlementListExportVO.setValidOrders(validOrders.toString());

            settlementListExportVO.setUnpaidOrders(newResultList.get(i).get("unOrderCount").toString());
            settlementListExportVO.setSalesVolume(newResultList.get(i).get("priceSum").toString());
            settlementListExportVO.setAftersalesOrders(newResultList.get(i).get("afterCount").toString());
            settlementListExportVO.setRefundAmount(newResultList.get(i).get("afterSun").toString());

            BigDecimal allAmount = new BigDecimal("0.00");
            BigDecimal priceSum = new BigDecimal(newResultList.get(i).get("priceSum").toString());
            BigDecimal afterSun = new BigDecimal(newResultList.get(i).get("afterSun").toString());
            allAmount = priceSum.subtract(afterSun);
            settlementListExportVO.setAllAmount(String.valueOf(allAmount));

            settlementListExportVO.setAnchorCommission(newResultList.get(i).get("anchorCommission").toString());
            settlementListExportVO.setTemplateName(newResultList.get(i).get("templateName").toString());
            VOResultList.add(settlementListExportVO);
        }

        ExcelUtil<SettlementListExportVO> util = new ExcelUtil<SettlementListExportVO>(SettlementListExportVO.class);
        return util.exportExcel(VOResultList, "用户数据");
    }
}
