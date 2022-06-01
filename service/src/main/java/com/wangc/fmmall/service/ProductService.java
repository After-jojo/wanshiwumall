package com.wangc.fmmall.service;

import com.wangc.fmmall.vo.ResultVO;
import org.apache.ibatis.annotations.Param;

public interface ProductService {
    public ResultVO listRecommengProds();
    public ResultVO getProductBasicInfo(String productId);
    public ResultVO getProductParamsById(String productId);
    public ResultVO getProductsByCateId(int cid, int pageNum, int limit);
    public ResultVO listBrands(int cid);
    public ResultVO searchProduct(String kwd, int pageNum, int limit);
    ResultVO listBrandsByKey(String keyword);
}
