package com.wangc.fmmall.service.impl;

import com.wangc.fmmall.Utils.PageHelper;
import com.wangc.fmmall.dao.ProductCommentsMapper;
import com.wangc.fmmall.entity.ProductComments;
import com.wangc.fmmall.entity.ProductCommentsVO;
import com.wangc.fmmall.service.ProductCommentsService;
import com.wangc.fmmall.vo.ResStatus;
import com.wangc.fmmall.vo.ResultVO;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class ProductCommentsServiceImpl implements ProductCommentsService {
    @Resource
    private ProductCommentsMapper productCommentsMapper;
    @Override
    public ResultVO listCommontsByProId(String pid, int pageNum, int limit) {
        Example example = new Example(ProductComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId", pid);
        // 总评论数
        int count = productCommentsMapper.selectCountByExample(example);
//        List<ProductCommentsVO> productCommentsVOS = productCommentsMapper.selectCommontsByProductId(pid);
        // 总页数
        int pageCount = count % limit == 0 ? count / limit : count / limit + 1;
        // 当前页数据
        int start = (pageNum - 1) * limit;
        List<ProductCommentsVO> productCommentsVOS = productCommentsMapper.selectCommontsByProductId(pid, start, limit);

        return new ResultVO(ResStatus.OK, "success", new PageHelper<ProductCommentsVO>(count, pageCount, productCommentsVOS));
    }

    @Override
    public ResultVO getCommCountByProId(String pid) {
        Example example = new Example(ProductComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId", pid);
        int total = productCommentsMapper.selectCountByExample(example);

        Example example1 = new Example(ProductComments.class);
        Example.Criteria criteria1 = example.createCriteria();
        criteria1.andEqualTo("commType", 1);
        int goodNum = productCommentsMapper.selectCountByExample(example);

        Example example2 = new Example(ProductComments.class);
        Example.Criteria criteria2 = example.createCriteria();
        criteria.andEqualTo("commType", 0);
        int midNum = productCommentsMapper.selectCountByExample(example);

        Example example3 = new Example(ProductComments.class);
        Example.Criteria criteria3 = example.createCriteria();
        criteria.andEqualTo("commType", -1);
        int badNum = productCommentsMapper.selectCountByExample(example);

        double goodPercent = Double.parseDouble(goodNum + "") / total;
        String goodPercentValue = (goodPercent * 100 + "").substring(0, (goodPercent + "").lastIndexOf((".")) + 3);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("goodNum", goodNum);
        map.put("badNum", badNum);
        map.put("goodPercent", goodPercentValue);
        return new ResultVO(ResStatus.OK, "success", map);
    }
}
