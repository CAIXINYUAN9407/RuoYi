package com.ruoyi.web.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.VideoShop;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.web.controller.callback.CallbackController;
import org.apache.http.HttpEntity;
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
import java.util.Map;

/**
 * 登录验证
 * 
 * @author ruoyi
 */
@Controller
public class SysLoginController extends BaseController
{
    /**
     * 是否开启记住我功能
     */
    @Value("${shiro.rememberMe.enabled: false}")
    private boolean rememberMe;

    @Autowired
    private ConfigService configService;
    @Autowired
    private ISysUserService sysUserService;
    private static final Logger log = LoggerFactory.getLogger(SysLoginController.class);


    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response, ModelMap mmap)
    {
        // 如果是Ajax请求，返回Json字符串。
        if (ServletUtils.isAjaxRequest(request))
        {
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
    public AjaxResult ajaxLogin(String username, String password, Boolean rememberMe, String code)
    {
        UsernamePasswordToken Usertoken = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try
        {
            subject.login(Usertoken);
            return success();
        }
        catch (AuthenticationException e)
        {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage()))
            {
                msg = e.getMessage();
            }
            return error(msg);
        }
    }

    @GetMapping("/OtherLogin")
    public String OtherLogin(@RequestParam(name = "valueModelMap",required = false) String valueModelMap, ModelMap modelMap, String code)
    {
        log.info("第三方登录"+code);
        valueModelMap=code;
        UsernamePasswordToken Usertoken = new UsernamePasswordToken("wxb8e549fe8d045ac2","wxb8e549fe8d045ac2");
        Subject subject = SecurityUtils.getSubject();
        modelMap.put("code",code);
        subject.login(Usertoken);
        return "otherLogin";
    }

    @PostMapping("/OtherLogin")
    public String OtherLoginPost(Model model, String code)
    {
        log.info("第si方登录"+code);
        UsernamePasswordToken Usertoken = new UsernamePasswordToken("wxb8e549fe8d045ac2","wxb8e549fe8d045ac2");
        Subject subject = SecurityUtils.getSubject();

        subject.login(Usertoken);
        model.addAttribute("code", code);
        return "otherLogin";
    }

    @PostMapping("/OtherLoginAjax")
    @ResponseBody
    public AjaxResult OtherLoginAjax(String code)
    {
        SysUser admin = sysUserService.selectUserById(1l);
        log.info("第三方登录"+code);

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String resData= null;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);

        HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/wxa/servicemarket/service/login_auth?access_token="+admin.getComponentAccessToken());
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
            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                resData = EntityUtils.toString(response.getEntity());
            }
        }catch (IOException e) {
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


        UsernamePasswordToken Usertoken = new UsernamePasswordToken(username,username);
        Subject subject = SecurityUtils.getSubject();
        try
        {
            subject.login(Usertoken);
            return success();
        }
        catch (AuthenticationException e)
        {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage()))
            {
                msg = e.getMessage();
            }
            return error(msg);
        }
    }

    @GetMapping("/unauth")
    public String unauth()
    {
        return "error/unauth";
    }
}
