package com.wangc.fmmall.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wangc.fmmall.dao.IndexImgMapper;
import com.wangc.fmmall.entity.IndexImg;
import com.wangc.fmmall.service.IndexService;
import com.wangc.fmmall.vo.ResStatus;
import com.wangc.fmmall.vo.ResultVO;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {
    @Resource
    private IndexImgMapper indexImgMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public ResultVO listIndexImgs() {
        List<IndexImg> indexImgs = null;
        try {
            // 先从Redis中查询轮播图信息
            String imgsStr = stringRedisTemplate.opsForValue().get("indexImgs");
            if(imgsStr != null){
                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, IndexImg.class);
                indexImgs = objectMapper.readValue(imgsStr, javaType);
            }
            indexImgs = indexImgMapper.listIndexImgs();
            stringRedisTemplate.boundValueOps("indexImgs").set(objectMapper.writeValueAsString(indexImgs));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if(indexImgs.size() == 0){
            return new ResultVO(ResStatus.NO, "fail", null);
        }
        return new ResultVO(ResStatus.OK, "success", indexImgs);
    }
}
