package com.wangc.fmmall.service;

import com.wangc.fmmall.entity.Orders;
import com.wangc.fmmall.vo.ResultVO;

import java.sql.SQLException;
import java.util.Map;

public interface OrderService {
    public Map<String, String> addOrder(String cids, Orders order) throws SQLException;

    public int updateOrderStatus(String orderId, String s);

    public ResultVO getOrderById(String orderId);

    public void closeOrder(String orderId);

}

