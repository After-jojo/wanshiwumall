package com.wangc.fmmall.dao;

import com.wangc.fmmall.entity.Category;
import com.wangc.fmmall.entity.CategoryVO;
import com.wangc.fmmall.entity.CategoryVO2;
import com.wangc.fmmall.general.GeneralDAO;

import java.util.List;

public interface CategoryMapper extends GeneralDAO<Category> {
    public List<CategoryVO> selectAllCategories2();

    public List<CategoryVO> selectAllCategories(int parentId);

    public List<CategoryVO2> selectOneLevelCategories();
}