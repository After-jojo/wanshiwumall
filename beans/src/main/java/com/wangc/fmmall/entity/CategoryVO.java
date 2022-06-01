package com.wangc.fmmall.entity;

import java.util.List;

public class CategoryVO extends Category{
    private List<CategoryVO> categories;
    public List<CategoryVO> getCategories(){
        return categories;
    }

    public void setCategories(List<CategoryVO> categories) {
        this.categories = categories;
    }


}
