package com.wangc.fmmall.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wangc.fmmall.dao.CategoryMapper;
import com.wangc.fmmall.entity.CategoryVO;
import com.wangc.fmmall.entity.CategoryVO2;
import com.wangc.fmmall.service.CategoryService;
import com.wangc.fmmall.vo.ResStatus;
import com.wangc.fmmall.vo.ResultVO;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public ResultVO listCategories() {
        List<CategoryVO> categoryVOS = null;
        try {
            // 查询Redis
            String cateories = stringRedisTemplate.boundValueOps("cateories").get();
            if(categoryVOS != null){
                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, CategoryVO.class);
                categoryVOS = objectMapper.readValue(cateories, javaType);
            }

            categoryVOS = categoryMapper.selectAllCategories2();
            stringRedisTemplate.boundValueOps("cateories").set(objectMapper.writeValueAsString(categoryVOS), 1, TimeUnit.DAYS);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResultVO(ResStatus.OK, "success", categoryVOS);
    }

    @Override
    public ResultVO listOneLevelCategories() {
        List<CategoryVO2> categoryVO2s = categoryMapper.selectOneLevelCategories();
        return new ResultVO(ResStatus.OK, "success",categoryVO2s);
    }
}
