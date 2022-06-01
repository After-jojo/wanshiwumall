package com.wangc.fmmall.controller;

import com.wangc.fmmall.entity.Users;
import com.wangc.fmmall.service.UserService;
import com.wangc.fmmall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
@CrossOrigin
@Api(value = "提供⽤户的登录和注册接⼝",tags = "⽤户管理")
public class UserController {
    @Resource
    private UserService userService;

    @ApiOperation("⽤户注册接⼝")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string",name =
                    "username", value = "⽤户注册账号",required = true),
            @ApiImplicitParam(dataType = "string",name =
                    "password", value = "⽤户注册密码",required = true)
    })
    @PostMapping("/regist")
    public ResultVO regist(@RequestBody Users user/*@RequestParam("username") String username,@RequestParam("password") String password*/){
        return userService.userResgit(user.getUsername(), user.getPassword());
    }

    @ApiOperation("⽤户登录接⼝")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string",name =
                    "username", value = "⽤户登录账号",required = true),
            @ApiImplicitParam(dataType = "string",name =
                    "password", value = "⽤户登录密码",required = true)
    })
    @GetMapping("/login")
    public ResultVO login(@RequestParam("username") String username,@RequestParam("password") String password){
        ResultVO resultVO = userService.checkLogin(username, password);
        System.out.println(resultVO);
        return resultVO;
    }
}
