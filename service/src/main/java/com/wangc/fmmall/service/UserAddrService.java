package com.wangc.fmmall.service;

import com.wangc.fmmall.vo.ResultVO;

public interface UserAddrService {
    public ResultVO listAddrsByUid(int userId);
}
