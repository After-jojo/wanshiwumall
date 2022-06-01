package com.wangc.fmmall.service;

import com.wangc.fmmall.vo.ResultVO;

public interface CategoryService {
    public ResultVO listCategories();

    public ResultVO listOneLevelCategories();
}
