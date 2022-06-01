package com.wangc.fmmall.controller;

import com.github.wxpay.sdk.WXPay;
import com.wangc.fmmall.config.MyPayConfig;
import com.wangc.fmmall.entity.Orders;
import com.wangc.fmmall.service.OrderService;
import com.wangc.fmmall.vo.ResStatus;
import com.wangc.fmmall.vo.ResultVO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Api(value = "提供订单操作的接⼝",tags = "订单管理")
@RestController
@CrossOrigin
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;
    @PostMapping("/add")
    public ResultVO add(String cids, @RequestBody Orders order){
        ResultVO resultVO = null;
        try {
            Map<String, String> orderInfo = orderService.addOrder(cids, order);
            if(null == orderInfo.get("orderId")){
                resultVO = new ResultVO(ResStatus.OK, "提交订单失败！", null);
            }

            // 申请支付连接
            WXPay wxPay = new WXPay(new MyPayConfig());
            //设置当前订单信息
            HashMap<String, String> map = new HashMap<>();
            map.put("body", orderInfo.get("productNames"));
            map.put("out_trade_no", orderInfo.get("orderId"));   //当前⽀付交易交易号
            map.put("fee_type", "CNY");
            map.put("total_fee", order.getActualAmount() * 100 + "");          //⽀付⾦额
            map.put("trade_type", "NATIVE");    //交易类型
//            map.put("notify_url", "/pay/success");    //设置⽀付完成时的回调⽅法接⼝

            map.put("notify_url", "http://wangc.free.idcfengye.com/pay/success");   // 使用ngrok完成内网穿透，让微信支付平台得以访问
            Map<String, String> resp = wxPay.unifiedOrder(map);
            orderInfo.put("payUrl", resp.get("code_url"));
            resultVO = new ResultVO(ResStatus.OK, "提交订单成功！", orderInfo);
        } catch (SQLException e) {
            resultVO = new ResultVO(ResStatus.NO, "提交订单失败！", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultVO;
    }

    @GetMapping("/status/{oid}")
    public ResultVO getOrderStatus(@PathVariable("oid") String orderId, @RequestHeader("token") String token){
        return orderService.getOrderById(orderId);
    }
}
