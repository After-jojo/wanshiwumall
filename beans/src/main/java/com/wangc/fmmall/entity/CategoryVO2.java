package com.wangc.fmmall.entity;
import java.util.List;

public class CategoryVO2 {
    private Integer categoryId;

    private String categoryName;

    private Integer categoryLevel;


    private Integer parentId;


    private String categoryIcon;

    private String categorySlogan;


    private String categoryPic;

    public List<ProductVO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductVO> products) {
        this.products = products;
    }

    private String categoryBgColor;

    private List<ProductVO> products;

    public CategoryVO2(Integer categoryId, String categoryName, Integer categoryLevel, Integer parentId, String categoryIcon, String categorySlogan, String categoryPic, String categoryBgColor, List<ProductVO> products) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryLevel = categoryLevel;
        this.parentId = parentId;
        this.categoryIcon = categoryIcon;
        this.categorySlogan = categorySlogan;
        this.categoryPic = categoryPic;
        this.categoryBgColor = categoryBgColor;
        this.products = products;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }


    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    public Integer getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(Integer categoryLevel) {
        this.categoryLevel = categoryLevel;
    }


    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }


    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }


    public String getCategorySlogan() {
        return categorySlogan;
    }


    public void setCategorySlogan(String categorySlogan) {
        this.categorySlogan = categorySlogan;
    }


    public String getCategoryPic() {
        return categoryPic;
    }


    public void setCategoryPic(String categoryPic) {
        this.categoryPic = categoryPic;
    }


    public String getCategoryBgColor() {
        return categoryBgColor;
    }


    public void setCategoryBgColor(String categoryBgColor) {
        this.categoryBgColor = categoryBgColor;
    }

    public CategoryVO2() {
    }

    @Override
    public String toString() {
        return "CategoryVO2{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryLevel=" + categoryLevel +
                ", parentId=" + parentId +
                ", categoryIcon='" + categoryIcon + '\'' +
                ", categorySlogan='" + categorySlogan + '\'' +
                ", categoryPic='" + categoryPic + '\'' +
                ", categoryBgColor='" + categoryBgColor + '\'' +
                ", products=" + products +
                '}';
    }
}