package com.wangc.fmmall.service.impl;

import com.wangc.fmmall.dao.ShoppingCartMapper;
import com.wangc.fmmall.entity.ShoppingCart;
import com.wangc.fmmall.entity.ShoppingCartVO;
import com.wangc.fmmall.service.ShoppingCartService;
import com.wangc.fmmall.vo.ResStatus;
import com.wangc.fmmall.vo.ResultVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Resource
    private ShoppingCartMapper shoppingCartMapper;
    @Override
    public ResultVO addShoppingCart(ShoppingCart cart) {
        int insert = shoppingCartMapper.insert(cart);
        if(insert == 0){
            return new ResultVO(ResStatus.NO, "fail", null);
        }
        return new ResultVO(ResStatus.OK, "success", null);
    }

    @Override
    public ResultVO listShoppingCartByUserId(int userId) {
        List<ShoppingCartVO> shoppingCartVOS = shoppingCartMapper.selectShopCartByUserId(userId);
        return new ResultVO(ResStatus.OK, "success", shoppingCartVOS);
    }

    @Override
    public ResultVO updateCartNum(int cartId, int cartNum) {
        int i = shoppingCartMapper.updateCartNumByCartId(cartId, cartNum);
        if(i>0){
            return new ResultVO(ResStatus.OK,"update success",null);
        }else{
            return new ResultVO(ResStatus.NO,"update fail",null);
        }
    }

    @Override
    public ResultVO listShopCartByCids(String cids) {
        String[] split = cids.split(",");
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            list.add(Integer.parseInt(split[i]));
        }
        List<ShoppingCartVO> shoppingCartVOS = shoppingCartMapper.selectShopCartByCids(list);
        return new ResultVO(ResStatus.OK, "success", shoppingCartVOS);
    }
}
