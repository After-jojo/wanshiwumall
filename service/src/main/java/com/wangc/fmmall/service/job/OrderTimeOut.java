package com.wangc.fmmall.service.job;

import com.github.wxpay.sdk.WXPay;
import com.wangc.fmmall.dao.OrderItemMapper;
import com.wangc.fmmall.dao.OrdersMapper;
import com.wangc.fmmall.dao.ProductSkuMapper;
import com.wangc.fmmall.entity.OrderItem;
import com.wangc.fmmall.entity.Orders;
import com.wangc.fmmall.entity.ProductSku;
import com.wangc.fmmall.service.OrderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderTimeOut {
    WXPay wxPay = new WXPay(new MyPayConfig());
    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private OrderItemMapper orderItemMapper;
    @Resource
    private ProductSkuMapper productSkuMapper;
    @Resource
    private OrderService orderService;

    @Transactional
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void checkOrder(){
        //查询过期订单 30mins
        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", "1");
        Date time = new Date(System.currentTimeMillis() - 30 * 60 * 1000);
        criteria.andLessThan("createTime", time);
        List<Orders> orders = ordersMapper.selectByExample(example);
        //超时后确认订单确实未支付, 如确实 则关闭微信支付平台
        int size = orders.size();
        try {
            for (int i = 0; i < size; i++) {
                Orders order = orders.get(i);
                HashMap<String, String> params = new HashMap<>();
                params.put("out_trade_no", order.getOrderId());
                Map<String, String> res = wxPay.orderQuery(params);  //支付状态信息
                if ("success".equalsIgnoreCase(res.get("trade_state"))){  // 支付了
                    Orders orders1 = new Orders();
                    orders1.setOrderId(order.getOrderId());
                    orders1.setStatus("2");
                    ordersMapper.updateByPrimaryKeySelective(orders1);
                }else if("notpay".equalsIgnoreCase(res.get("trade_state"))){   // 确实未支付 将订单关闭 (超时未支付)
                    //先关闭当前订单微信支付链接
                    Map<String, String> map = wxPay.closeOrder(params);

                    //关闭订单
                    orderService.closeOrder(order.getOrderId());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}











