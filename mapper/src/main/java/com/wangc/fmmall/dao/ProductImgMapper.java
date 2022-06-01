package com.wangc.fmmall.dao;

import com.wangc.fmmall.entity.ProductImg;
import com.wangc.fmmall.general.GeneralDAO;

import java.util.List;

public interface ProductImgMapper extends GeneralDAO<ProductImg> {

    public List<ProductImg> selectImgByProductId(int produceId);
}