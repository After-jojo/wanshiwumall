package com.wangc.fmmall.dao;

import com.wangc.fmmall.entity.ProductSku;
import com.wangc.fmmall.general.GeneralDAO;

import java.util.List;

public interface ProductSkuMapper extends GeneralDAO<ProductSku> {
    public List<ProductSku> selectLowerPriceByProductId(String pid);
}