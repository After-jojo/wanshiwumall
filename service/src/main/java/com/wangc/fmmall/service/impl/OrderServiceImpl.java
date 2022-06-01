package com.wangc.fmmall.service.impl;

import com.wangc.fmmall.dao.OrderItemMapper;
import com.wangc.fmmall.dao.OrdersMapper;
import com.wangc.fmmall.dao.ProductSkuMapper;
import com.wangc.fmmall.dao.ShoppingCartMapper;
import com.wangc.fmmall.entity.OrderItem;
import com.wangc.fmmall.entity.Orders;
import com.wangc.fmmall.entity.ProductSku;
import com.wangc.fmmall.entity.ShoppingCartVO;
import com.wangc.fmmall.service.OrderService;
import com.wangc.fmmall.vo.ResStatus;
import com.wangc.fmmall.vo.ResultVO;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private ShoppingCartMapper shoppingCartMapper;

    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ProductSkuMapper productSkuMapper;

    @Resource
    private RedissonClient redissonClient;

    private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Override
    @Transactional
    public Map<String, String> addOrder(String cids, Orders order) throws SQLException {
        logger.info("订单添加开始...");
        Map<String, String> map = new HashMap<>();
        // 根据cids 查询
        String[] split = cids.split(",");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            list.add(Integer.parseInt(split[i]));
        }
        List<ShoppingCartVO> shoppingCartVOS = shoppingCartMapper.selectShopCartByCids(list);
        int size = shoppingCartVOS.size();
        //记录加锁成功的商品
        String[] skuIds = new String[size];
        boolean isLock = true;
        Map<String, RLock> locks = new HashMap<>();
        for (int i = 0; i < size; i++) {
            // 为当前商品创建锁
            String skuId = shoppingCartVOS.get(i).getSkuId();
            boolean b = false;
            try {
                RLock lock = redissonClient.getLock(skuId);
                b = lock.tryLock(10, 3, TimeUnit.SECONDS);
                if(b) {
                    skuIds[i] = skuId;
                    locks.put(skuId, lock);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!b) isLock = false;
        }
        if(!isLock){
            for(String skuId: skuIds){
                if(skuId != null && !"".equals(skuId)){
                    locks.get(skuId).unlock();
                }
            }
        }
        // 校验库存
        StringBuilder sb = new StringBuilder();
        boolean flag = true;
        shoppingCartVOS = shoppingCartMapper.selectShopCartByCids(list);
        for(ShoppingCartVO vo : shoppingCartVOS){
            if(Integer.parseInt(vo.getCartNum()) > vo.getSkuStock()){
                flag = false;
                break;
            }
            sb.append(vo.getProductName());
            sb.append(',');
        }
        if(!flag){
            return null;
        }
        //生成订单
        logger.info("库存校验通过");
        order.setUntitled(sb.toString());
        order.setCreateTime(new Date());
        order.setStatus("1");
        String orderId = UUID.randomUUID().toString().replace("-", " ");
        order.setOrderId(orderId);
        ordersMapper.insert(order);

        //保存商品快照
        for(ShoppingCartVO vo : shoppingCartVOS){
            int cartNum = Integer.parseInt(vo.getCartNum());
            String itemId = System.currentTimeMillis() + "" + (new Random().nextInt(8999) + 1000);
            OrderItem orderItem = new OrderItem(itemId, orderId, vo.getProductId(), vo.getProductName(), vo.getProductImg()
                                    , vo.getSkuId(), vo.getSkuName(), vo.getSellPrice(), cartNum, vo.getSellPrice() * cartNum, new Date(), new Date(), 0);
            orderItemMapper.insert(orderItem);
        }


        //扣减库存  (根据商品sku id 修改)
        for(ShoppingCartVO vo : shoppingCartVOS){
            String skuId = vo.getSkuId();
            int newStock = vo.getSkuStock() - Integer.parseInt(vo.getCartNum());
            ProductSku productSku = new ProductSku();
            productSku.setStock(newStock);
            productSku.setSkuId(skuId);
            productSkuMapper.updateByPrimaryKeySelective(productSku);
        }

        // 删除完成的购物车
        for(int cid : list){
            shoppingCartMapper.deleteByPrimaryKey(cid);
        }
        logger.info("订单添加成功");
        map.put("orderId", orderId);
        map.put("productNames", sb.toString());

        return map;
    }

    @Override
    public int updateOrderStatus(String orderId, String s) {
        Orders orders = new Orders();
        orders.setOrderId(orderId);
        orders.setStatus(s);
        return ordersMapper.updateByPrimaryKeySelective(orders);
    }

    @Override
    public ResultVO getOrderById(String orderId) {
        Orders orders = ordersMapper.selectByPrimaryKey(orderId);
        return new ResultVO(ResStatus.OK, "success", orders);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)    //设置隔离级别为串行(禁止并发)
    public void closeOrder(String orderId) {
        Orders orders1 = new Orders();
        orders1.setOrderId(orderId);
        orders1.setStatus("6");   //将订单关闭
        orders1.setCloseType(1);    //超时未支付
        ordersMapper.updateByPrimaryKeySelective(orders1);

        // 还原库存(product_sku)
        Example example1 = new Example(OrderItem.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("orderId", orderId);
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example1);
        int size2 = orderItems.size();
        for (int j = 0; j < size2; j++) {
            OrderItem orderItem = orderItems.get(j);
            ProductSku productSku = productSkuMapper.selectByPrimaryKey(orderItem.getSkuId());
            productSku.setStock(productSku.getStock() + orderItem.getBuyCounts());
            productSkuMapper.updateByPrimaryKeySelective(productSku);
        }
    }
}
