package com.wangc.fmmall.service.impl;

import com.wangc.fmmall.dao.UserAddrMapper;
import com.wangc.fmmall.entity.UserAddr;
import com.wangc.fmmall.service.UserAddrService;
import com.wangc.fmmall.vo.ResStatus;
import com.wangc.fmmall.vo.ResultVO;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserAddrServiceImpl implements UserAddrService {
    @Resource
    private UserAddrMapper userAddrMapper;
    @Override
    public ResultVO listAddrsByUid(int userId) {
        Example example = new Example(UserAddr.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("status", 1);
        List<UserAddr> userAddrs = userAddrMapper.selectByExample(example);
        return new ResultVO(ResStatus.OK, "success", userAddrs);
    }
}
