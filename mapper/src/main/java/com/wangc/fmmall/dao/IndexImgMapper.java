package com.wangc.fmmall.dao;

import com.wangc.fmmall.entity.IndexImg;
import com.wangc.fmmall.general.GeneralDAO;

import java.util.List;

public interface IndexImgMapper extends GeneralDAO<IndexImg> {
    // 查询轮播图
    public List<IndexImg> listIndexImgs();
}