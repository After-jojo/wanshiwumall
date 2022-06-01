package com.wangc.fmmall.controller;

import com.github.wxpay.sdk.WXPayUtil;
import com.wangc.fmmall.service.OrderService;
import com.wangc.fmmall.webSocket.WebSocketServer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pay")  // 支付回调  接收微信支付平台接口
public class PayController {
    @Resource
    private OrderService orderService;
    @RequestMapping("/success")
    public String pay(HttpServletRequest request) throws Exception{
        ServletInputStream is = request.getInputStream();
        byte[] bs = new byte[1024];
        int len = -1;
        StringBuilder builder = new StringBuilder();
        while((len = is.read(bs)) != -1){
            builder.append(new String(bs, 0,len));
        }
        String s = builder.toString();
        //使⽤帮助类将xml接⼝的字符串装换成map
        Map<String, String> map = WXPayUtil.xmlToMap(s);
        if(null != map && "success".equalsIgnoreCase(map.get("result_code"))){
            //⽀付成功
            //2.修改订单状态为“待发货/已⽀付”
            String orderId = map.get("out_trade_no");
            int i = orderService.updateOrderStatus(orderId, "2");
            System.out.println("--orderId:" + orderId);
            // 通过websocket连接，向前端推送消息
            WebSocketServer.sendMsg(orderId, "1");
            //3.响应微信⽀付平台
            if(i > 0){
                HashMap<String,String> resp = new HashMap<>();
                resp.put("return_code", "success");
                resp.put("return_msg", "OK");
                resp.put("appid", map.get("appid"));
                resp.put("result_code", "success");
                return WXPayUtil.mapToXml(resp);
            }
        }
        return null;
    }
}
