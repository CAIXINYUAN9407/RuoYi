package com.ruoyi.web.controller.callback;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.system.Util.AesException;
import com.ruoyi.system.Util.WXBizMsgCrypt;
import com.ruoyi.system.Util.MessageUtil;
import com.ruoyi.system.service.VXAuthorizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Anonymous
@Controller
@RequestMapping("/wxdc5787bd0edbfc75/callback")
public class OrderCallbackController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(OrderCallbackController.class);

    @Autowired
    private VXAuthorizationService vxAuthorizationService;

    @RequestMapping("/information")
    @Anonymous
    @ResponseBody
    public String information(HttpServletRequest req, HttpServletResponse resp)throws AesException
    {
       WXBizMsgCrypt wxcpt = new WXBizMsgCrypt("12580",
                    "1111111111111111111111111111111111111111112", "wxdc5787bd0edbfc75");
        // 微信加密签名

        // 时间戳
        String timeStamp = req.getParameter("timestamp");
        // 随机数
        String nonce = req.getParameter("nonce");
        // 随机数
        String signature = req.getParameter("signature");
        // 随机数
        String echostr = req.getParameter("echostr");
        log.info("timeStamp:"+timeStamp+"nonce:"+nonce+"signature:"+signature);

        String o = wxcpt.verifyUrl(signature, timeStamp, nonce, echostr);



        log.info("接收到消息推送请求");
        return o;
    }

    @RequestMapping("/test")
    public void testVerifyUrl() throws AesException {
        WXBizMsgCrypt wxcpt = new WXBizMsgCrypt("12580",
                "1111111111111111111111111111111111111111112", "wxdc5787bd0edbfc75");
        String verifyMsgSig = "3a278c68fde079924867091d91f0fb9c9958f0e6";
        String timeStamp = "1722326289";
        String nonce = "194599140";
        String echoStr = "7874409049975487835";
        wxcpt.verifyUrl(verifyMsgSig, timeStamp, nonce, echoStr);
        // 只要不抛出异常就好
    }
}
