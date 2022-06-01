package com.wangc.fmmall.entity;

public class Product4ES {
    private String productId;
    private String productName;
    private String productImg;
    private String productSkuName;
    private double productSkuPrice;
    private int soldNum;
    public Product4ES() {
    }

    public void setProductSkuPrice(double productSkuPrice) {
        this.productSkuPrice = productSkuPrice;
    }

    public int getSoldNum() {
        return soldNum;
    }

    public void setSoldNum(int soldNum) {
        this.soldNum = soldNum;
    }

    public Product4ES(String productId, String productName, String productImg, String productSkuName, double productSkuPrice, int soldNum) {
        this.productId = productId;
        this.productName = productName;
        this.productImg = productImg;
        this.productSkuName = productSkuName;
        this.productSkuPrice = productSkuPrice;
        this.soldNum = soldNum;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductSkuName() {
        return productSkuName;
    }

    public void setProductSkuName(String productSkuName) {
        this.productSkuName = productSkuName;
    }

    @Override
    public String toString() {
        return "Product4ES{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productImg='" + productImg + '\'' +
                ", productSkuName='" + productSkuName + '\'' +
                ", productSkuPrice=" + productSkuPrice +
                ", soldNum=" + soldNum +
                '}';
    }

    public void setProductSkuPrice(Double productSkuPrice) {
        this.productSkuPrice = productSkuPrice;
    }

}
