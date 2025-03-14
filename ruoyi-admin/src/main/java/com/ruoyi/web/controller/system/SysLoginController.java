package com.ruoyi.web.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.domain.VideoShop;
import com.ruoyi.system.domain.VideoShopOrder;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.IVideoShopOrderService;
import com.ruoyi.system.service.IVideoShopService;
import com.ruoyi.web.controller.callback.CallbackController;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.ConfigService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * 登录验证
 *
 * @author ruoyi
 */
@Controller
public class SysLoginController extends BaseController {
    /**
     * 是否开启记住我功能
     */
    @Value("${shiro.rememberMe.enabled: false}")
    private boolean rememberMe;

    @Autowired
    private ConfigService configService;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private IVideoShopService videoShopService;

    @Autowired
    private IVideoShopOrderService videoShopOrderService;
    private static final Logger log = LoggerFactory.getLogger(SysLoginController.class);


    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response, ModelMap mmap) {
        // 如果是Ajax请求，返回Json字符串。
        if (ServletUtils.isAjaxRequest(request)) {
            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
        }
        // 是否开启记住我
        mmap.put("isRemembered", rememberMe);
        // 是否开启用户注册
        mmap.put("isAllowRegister", Convert.toBool(configService.getKey("sys.account.registerUser"), false));
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public AjaxResult ajaxLogin(String username, String password, Boolean rememberMe, String code) {
        UsernamePasswordToken Usertoken = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(Usertoken);
            return success();
        } catch (AuthenticationException e) {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage())) {
                msg = e.getMessage();
            }
            return error(msg);
        }
    }

    @GetMapping("/OtherLogin")
    public String OtherLogin(@RequestParam(name = "valueModelMap", required = false) String valueModelMap, ModelMap modelMap, String code) {
        log.info("第三方登录" + code);
        valueModelMap = code;
        UsernamePasswordToken Usertoken = new UsernamePasswordToken("wxb8e549fe8d045ac2", "wxb8e549fe8d045ac2");
        Subject subject = SecurityUtils.getSubject();
        modelMap.put("code", code);
        subject.login(Usertoken);
        return "otherLogin";
    }

    @PostMapping("/OtherLogin")
    public String OtherLoginPost(Model model, String code) {
        log.info("第si方登录" + code);
        UsernamePasswordToken Usertoken = new UsernamePasswordToken("wxb8e549fe8d045ac2", "wxb8e549fe8d045ac2");
        Subject subject = SecurityUtils.getSubject();

        subject.login(Usertoken);
        model.addAttribute("code", code);
        return "otherLogin";
    }

    @PostMapping("/OtherLoginAjax")
    @ResponseBody
    public AjaxResult OtherLoginAjax(String code) throws IOException {
        SysUser admin = sysUserService.selectUserById(1l);

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String resData = null;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        System.out.println("开始登录验证:");

        HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/wxa/servicemarket/service/login_auth?access_token=" + admin.getComponentAccessToken());
        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(jsonObject.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        stringEntity.setContentType("text/json");
        stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);

            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            System.out.println("登录认证响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                resData = EntityUtils.toString(response.getEntity());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JSONObject obj = JSONObject.parseObject(resData);
        String username = (String) obj.get("appid");
//        int isExpire =sysUserService.selectUserExpireCount(username);
//
//        if(isExpire>0){
//            String msg = "已过期";
//            return error(msg);
//        }

//        getServiceInfo(username);

        UsernamePasswordToken Usertoken = new UsernamePasswordToken(username, username);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(Usertoken);
            VideoShop videoShop = videoShopService.selectVideoShopByOwner(username);
            videoShop = getAccessToken(videoShop);

            return success();


        } catch (AuthenticationException e) {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage())) {
                msg = e.getMessage();
            }
            return error(msg);
        }
    }

    private VideoShop getAccessToken(VideoShop videoShop) throws IOException {
        String resData = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        JSONObject jsonObject = new JSONObject();
        // 核心定时器，每一个小时执行一次
        Long userId = 1L;
        SysUser sysUser = sysUserService.selectUserById(userId);
        jsonObject.put("component_appid", "wxdc5787bd0edbfc75");
        jsonObject.put("authorizer_appid", videoShop.getOwner());
        jsonObject.put("authorizer_refresh_token", videoShop.getRefreshToken());
        HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/component/api_authorizer_token?component_access_token=" + sysUser.getComponentAccessToken());
        StringEntity stringEntity = new StringEntity(jsonObject.toString());
        stringEntity.setContentType("text/json");
        stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        httpPost.setEntity(stringEntity);

        CloseableHttpResponse response = null;

        // 由客户端执行(发送)Post请求
        response = httpClient.execute(httpPost);
        // 从响应模型中获取响应实体
        HttpEntity responseEntity = response.getEntity();
        if (responseEntity != null) {
            System.out.println("getAccessToken响应内容长度为:" + responseEntity.getContentLength());
            resData = EntityUtils.toString(response.getEntity());
            JSONObject obj = JSONObject.parseObject(resData);
            System.out.println(obj.toString());

            //获取主账号finderId
            videoShop.setAccessToken(obj.get("authorizer_access_token").toString());
            videoShopService.updateVideoShop(videoShop);
        }
        return videoShop;
    }

    private void getServiceInfo(String username) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String resData = null;

        Long userId = 1L;
        SysUser sysUser = sysUserService.selectUserById(userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("service_id", "3536933729954938894");
        jsonObject.put("buyer_type", 1);
        jsonObject.put("appid", username);

        HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/wxa/servicemarket/service/get_service_buyer_list?access_token=" + sysUser.getMarketAccessToken());
        StringEntity stringEntity = new StringEntity(jsonObject.toString());
        stringEntity.setContentType("text/json");
        stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse response = null;

        response = httpClient.execute(httpPost);
        // 从响应模型中获取响应实体
        HttpEntity responseEntity = response.getEntity();
        if (responseEntity != null) {
            resData = EntityUtils.toString(response.getEntity());
            JSONObject obj = JSONObject.parseObject(resData);
            JSONArray orderArray = (JSONArray) obj.get("buyer");
            System.out.println("响应内容长度为:" + responseEntity.getContentLength());

        }


    }

    @GetMapping("/unauth")
    public String unauth() {
        return "error/unauth";
    }


}
