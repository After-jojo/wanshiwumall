package com.wangc.fmmall.controller;

import com.wangc.fmmall.entity.ShoppingCart;
import com.wangc.fmmall.service.ShoppingCartService;
import com.wangc.fmmall.vo.ResStatus;
import com.wangc.fmmall.vo.ResultVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/shopcart")
@CrossOrigin
@Api(value = "购物车接口", tags = "购物车管理")
public class ShopcartController {
    @Resource
    private ShoppingCartService shoppingCartService;

    @ApiOperation("查看购物车")
    @GetMapping("/list")
    @ApiImplicitParam(dataType = "int", name = "userId", value = "用户ID", required = true)
    public ResultVO listCarts(@RequestParam("userId") Integer userId, @RequestHeader("token") String token) {
        return shoppingCartService.listShoppingCartByUserId(userId);

    }
    @ApiOperation("添加购物车")
    @PostMapping("/add")
    public ResultVO addShoppingCart(@RequestBody ShoppingCart cart, @RequestHeader("token") String token){
        return shoppingCartService.addShoppingCart(cart);
    }
    @ApiOperation("更改购物车")
    @PutMapping("/update/{cid}/{cnum}")
    public ResultVO updateNum(@PathVariable("cid") Integer cartId,
                              @PathVariable("cnum") Integer cartNum,
                              @RequestHeader("token") String token){
        return shoppingCartService.updateCartNum(cartId, cartNum);
    }

    @GetMapping("/listbycids")
    @ApiImplicitParam(dataType = "String",name = "cids", value = "选择的购物⻋记录id",required = true)
    public ResultVO listByCids(String cids, @RequestHeader("token") String token){
        return shoppingCartService.listShopCartByCids(cids);

    }
}

