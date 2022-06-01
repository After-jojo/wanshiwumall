package com.wangc.fmmall.service.impl;

import com.wangc.fmmall.Utils.MD5Utils;
import com.wangc.fmmall.dao.UsersMapper;
import com.wangc.fmmall.entity.Users;
import com.wangc.fmmall.service.UserService;
import com.wangc.fmmall.vo.ResStatus;
import com.wangc.fmmall.vo.ResultVO;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UsersMapper usersMapper;

    @Override
    @Transactional
    public ResultVO userResgit(String name, String pwd) {
        synchronized (this){
            Example example = new Example(Users.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("username" , name);
            List<Users> users = usersMapper.selectByExample(example);
            if(users.size() == 0){
                String md5Pwd = MD5Utils.md5(pwd);
                Users user = new Users();
                user.setUsername(name);
                user.setPassword(md5Pwd);
                user.setUserImg("img/default.png");
                user.setUserRegtime(new Date());
                user.setUserModtime(new Date());
                int row = usersMapper.insertUseGeneratedKeys(user);
                if(row == 1){
                    return new ResultVO(ResStatus.OK, "注册成功", user);
                }else{
                    return new ResultVO(ResStatus.NO, "注册失败", null);
                }
            }else {
                return new ResultVO(ResStatus.NO, "用户名已被注册", null);
            }
        }
    }

    @Override
    public ResultVO checkLogin(String name, String pwd) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", name);
        List<Users> users = usersMapper.selectByExample(example);
        if(users.size() == 0){
            return new ResultVO(ResStatus.NO,"登录失败，用户名不存在！", null);
        }
        String md5pwd = MD5Utils.md5(pwd);
        if(md5pwd.equals(users.get(0).getPassword())){
            // 登录成功，使用jwt 生成token
            JwtBuilder builder = Jwts.builder();
            String token = builder.setSubject(name) //主题 ,token中携带的数据
                    .setIssuedAt(new Date())  // 设置token的生成时间
                    .setId(users.get(0).getUserId() + "")
                    .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                    .signWith(SignatureAlgorithm.HS256, "jojo123")
                    .compact();

            return new ResultVO(ResStatus.OK, token, users.get(0));
        }else{
            return new ResultVO(ResStatus.NO,"登录失败，密码错误！", null);
        }
    }
}
