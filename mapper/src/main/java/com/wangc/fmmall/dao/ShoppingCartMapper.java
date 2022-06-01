package com.wangc.fmmall.dao;

import com.wangc.fmmall.entity.ShoppingCart;
import com.wangc.fmmall.entity.ShoppingCartVO;
import com.wangc.fmmall.general.GeneralDAO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShoppingCartMapper extends GeneralDAO<ShoppingCart> {
    public List<ShoppingCartVO> selectShopCartByUserId(int userId);

    public int updateCartNumByCartId(@Param("cartId") int cartId,
                                     @Param("cartNum") int cartNum);

    public List<ShoppingCartVO> selectShopCartByCids(List<Integer> cids);

}