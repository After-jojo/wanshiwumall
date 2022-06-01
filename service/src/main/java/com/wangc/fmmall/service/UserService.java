package com.wangc.fmmall.service;

import com.wangc.fmmall.vo.ResultVO;

public interface UserService {
    public ResultVO userResgit(String name, String pwd);
    public ResultVO checkLogin(String name, String pwd);
}
