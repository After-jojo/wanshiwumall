package com.wangc.fmmall.service;

import com.wangc.fmmall.entity.ShoppingCart;
import com.wangc.fmmall.vo.ResultVO;

public interface ShoppingCartService {
    public ResultVO addShoppingCart(ShoppingCart cart);
    public ResultVO listShoppingCartByUserId(int userId);
    public ResultVO updateCartNum(int cartId,int cartNum);
    public ResultVO listShopCartByCids(String cids);
}
