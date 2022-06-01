package com.wangc.fmmall.dao;

import com.wangc.fmmall.entity.ProductComments;
import com.wangc.fmmall.entity.ProductCommentsVO;
import com.wangc.fmmall.general.GeneralDAO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCommentsMapper extends GeneralDAO<ProductComments> {

    public List<ProductCommentsVO> selectCommontsByProductId(@Param("productId") String productId,
                                                             @Param("start") int start,
                                                             @Param("limit") int limit);
}