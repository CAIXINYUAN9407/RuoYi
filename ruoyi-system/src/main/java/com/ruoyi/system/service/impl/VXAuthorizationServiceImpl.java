package com.ruoyi.system.service.impl;

import cn.hutool.core.util.XmlUtil;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.Util.AesException;
import com.ruoyi.system.Util.MessageUtil;
import com.ruoyi.system.Util.WXBizMsgCrypt;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.domain.VideoShop;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.VXAuthorizationService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户 业务层处理
 * 
 * @author ruoyi
 */
@Service
public class VXAuthorizationServiceImpl implements VXAuthorizationService
{
    private static final Logger log = LoggerFactory.getLogger(VXAuthorizationServiceImpl.class);

    public static final Map<String, String> COMPONENT_VETIFY_TICKET_MAP = new HashMap<>();


    @Autowired
    private SysUserMapper userMapper;
//    SysUserRoleMapper
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private VideoShopMapper videoShopMapper;


    /**
     *
     * @param
     * @return
     */
    @Override
    public String getComponentVerifyTicket(HttpServletRequest request, HttpServletResponse response)
    {
        log.info("接收微信服务器发送的Ticket");
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            // 微信加密签名
            String signature = request.getParameter("msg_signature");
            // 时间戳
            String timestamp = request.getParameter("timestamp");
            // 随机数
            String nonce = request.getParameter("nonce");
            // 从请求中读取整个post数据 这个工具类在下文写了
            Map<String, Object> xmlMap = MessageUtil.xmlToMap(request);
            log.info("加密签名msg_signature:{} 时间戳timestamp:{} 随机数nonce:{}", signature, timestamp, nonce);
            log.info("从request中获取xml信息：{}", xmlMap);
            //解密处理
            String encrypt = (String) xmlMap.get("Encrypt");
            log.info("Encrypt:{}", encrypt);
            String msg = decryptMsg(timestamp, nonce, signature, encrypt);
            //将XML格式字符串转为Map类型 使用的是hutool工具包，记得引入一下
            Map<String, Object> msgMap = XmlUtil.xmlToMap(msg);
            log.info("msg"+msgMap);
            String infoType = msgMap.get("InfoType").toString();
            log.info("类型"+infoType);
            log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            switch (infoType) {
                //验证票据
                case "authorized":
                    log.info("_______________验证——————————————————————————————");
                    String AuthorizerAppid = msgMap.get("AuthorizerAppid").toString();
                    log.info("AuthorizerAppid"+AuthorizerAppid);
                    SysUser sysUserNew = new SysUser();
                    sysUserNew.setLoginName(AuthorizerAppid);
                    sysUserNew.setSalt(ShiroUtils.randomSalt());
                    sysUserNew.setPassword(encryptPassword(sysUserNew.getLoginName(),sysUserNew.getLoginName(),sysUserNew.getSalt()));
                    userMapper.insertUser(sysUserNew);
                    Integer table_index = 0;
                    log.info("表索引0——————————————————————————————————"+table_index);
                    HashMap tableIndexMap = videoShopMapper.selectTableIndex();
                    if (tableIndexMap.get("table_index").toString() != "" || tableIndexMap.get("table_index").toString() != null){
                        table_index = (Integer) tableIndexMap.get("table_index");
                        log.info("表索引1——————————————————————————————————"+tableIndexMap.get("table_index").toString());
                    }
                    else {
                        table_index = videoShopMapper.selectMaxTableIndex()+1;
                        log.info("表索引2——————————————————————————————————"+table_index);
                        videoShopMapper.createShopGoods(table_index);
                        videoShopMapper.createShopOrder(table_index);
                        videoShopMapper.createShopAnchor(table_index);
                        videoShopMapper.createShopSalaryTemplate(table_index);
                        videoShopMapper.createShopScheduling(table_index);
                    }

                    VideoShop videoShopOld = videoShopMapper.selectVideoShopByOwner(AuthorizerAppid);
                    if(videoShopOld == null){
                        VideoShop videoShop = new VideoShop();
                        videoShop.setOwner(AuthorizerAppid);
                        videoShop.setLocalShopId(2);
                        videoShop.setTableIndex(Long.valueOf(table_index));
                        videoShopMapper.insertVideoShop(videoShop);
                    }else {

                    }



                    // 新增用户与角色管理
                    List<SysUserRole> list = new ArrayList<SysUserRole>();
                    SysUserRole ur = new SysUserRole();
                    ur.setUserId(sysUserNew.getUserId());
                    ur.setRoleId(100L);
                    list.add(ur);
                    sysUserRoleMapper.batchUserRole(list);

                    break;
                case "component_verify_ticket":
                    //查询库中的第三方信息，并且准备存储ticket
                    String componentVerifyTicket = msgMap.get("ComponentVerifyTicket").toString();
                    log.info("用户授权component_verify_ticket:{}", componentVerifyTicket);
                    COMPONENT_VETIFY_TICKET_MAP.put("wxdc5787bd0edbfc75", componentVerifyTicket);
                    Long userId = 1L;
                    SysUser sysUser = userMapper.selectUserById(userId);
                    log.info("sysUser----------------------getLoginName", sysUser.getLoginName());
                    sysUser.setComponentVerifyTicket(componentVerifyTicket.substring(9));
                    userMapper.updateUser(sysUser);
                    //使用StringRedisTemplate将票据值写入Redis缓存中 存不存，怎么存看你自己
//                    redisTemplate.opsForValue().set(VXConstants.COMPONENT_VERIFY_TICKET, componentVerifyTicket);
//                    redisTemplate.expire(VXConstants.COMPONENT_VERIFY_TICKET, 2, TimeUnit.HOURS);
                    break;
                case "unauthorized"://用户取消授权
                    log.info("用户取消授权");
                    break;
            }
        } catch (Exception e) {
            log.error("获取Ticket失败：", e);
        }
        return "success";
    }

    // 加密操作
    public String encryptMsg(String encodingAesKey, String token, String timestamp, String nonce, String appId, String replyMsg) {
        log.info("加密前：{}", replyMsg);
        try {
            WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
            String ciphertext = pc.encryptMsg(replyMsg, timestamp, nonce);
            log.info("加密后：{}", ciphertext);
            return ciphertext;
        } catch (AesException e) {
            throw new RuntimeException(e);
        }
    }

    //解密操作
    public String decryptMsg(String timestamp, String nonce, String signature, String encrypt) {
        log.info("进行解密操作:{} {} {} {}", timestamp, nonce, signature, encrypt);
        try {
            log.info("token:{} encodingAesKey:{} componentAppId:{}", "token", "1111111111111111111111111111111111111111112", "wxdc5787bd0edbfc75");
            WXBizMsgCrypt pc = new WXBizMsgCrypt("token", "1111111111111111111111111111111111111111112", "wxdc5787bd0edbfc75");
            String format = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
            String fromXML = String.format(format, encrypt);
            log.info("fromXML:{}", fromXML);
            // 第三方收到公众号平台发送的消息
            String plaintext = pc.decryptMsg(signature, timestamp, nonce, fromXML);
            log.info("解密后明文: {}", plaintext);
            return plaintext;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String encryptPassword(String loginName, String password, String salt)
    {
        return new Md5Hash(loginName + password + salt).toHex();
    }

}
