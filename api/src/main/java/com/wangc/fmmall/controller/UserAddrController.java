package com.wangc.fmmall.controller;

import com.wangc.fmmall.service.UserAddrService;
import com.wangc.fmmall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@Api(value = "提供收货地址相关接⼝", tags = "收货地址管理")
@RequestMapping("/user_addr")
public class UserAddrController {
    @Resource
    private UserAddrService userAddrService;
    @GetMapping("/list")
    @ApiOperation("查询收货地址")
    @ApiImplicitParam(dataType = "int", name = "userId", value = "用户ID", required = true)
    public ResultVO listAddr(Integer userId, @RequestHeader("token") String token){
        return userAddrService.listAddrsByUid(userId);
    }
}
