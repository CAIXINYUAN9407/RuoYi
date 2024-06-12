package com.ruoyi.web.controller.callback;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.VXAuthorizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/callback")
public class CallbackController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(CallbackController.class);

    @Autowired
    private VXAuthorizationService vxAuthorizationService;

    @PostMapping("/authorization")
    public String authorization(HttpServletRequest req, HttpServletResponse resp)
    {
        log.info("接收到验证票据请求");
        String result =  vxAuthorizationService.getComponentVerifyTicket(req, resp);
        log.info("验证票据"+result);
        return result;
    }
}
