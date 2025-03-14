package com.ruoyi.web.Util;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.system.domain.VideoShop;
import com.ruoyi.system.domain.VideoShopOrder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;

/**
 * 兑换虚拟号码
 */
public class GetVirtualtelNumber {

    public VideoShopOrder getVirtualtelNumber(VideoShop videoShop,String orderId){
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String resData= null;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("order_id", orderId);

        HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/channels/ec/order/virtualtelnumber/get?access_token="+videoShop.getAccessToken());
        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(jsonObject.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        stringEntity.setContentType("text/json");
        stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        httpPost.setEntity(stringEntity);

        return new VideoShopOrder();
    }
}
