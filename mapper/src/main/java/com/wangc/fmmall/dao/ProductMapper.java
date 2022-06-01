package com.wangc.fmmall.dao;

import com.wangc.fmmall.entity.Product;
import com.wangc.fmmall.entity.ProductVO;
import com.wangc.fmmall.general.GeneralDAO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper extends GeneralDAO<Product> {
    public List<ProductVO> selectRecommendProds();
    public List<ProductVO> selectTop6ByCate(int cid);
    // 要分页查询

    /**
     *
     * @param cid 三级分类id
     * @param start 起始索引
     * @param limit 记录数
     * @return List<ProductVO>
     */
    public List<ProductVO> selectProByCateId(@Param("cid") int cid, @Param("start") int start, @Param("limit") int limit);
    public List<String> selectBrandByCateId(int cid);

    /**
     * 根据关键字模糊查询
     * @param kwd
     * @param start
     * @param limit
     * @return
     */
    public List<ProductVO> selectProByKeyWord(@Param("kwd") String kwd,
                                              @Param("start") int start,
                                              @Param("limit") int limit);

    /**
     * 根据关键字查询商品的品牌列表
     * @param keyword
     * @return
     */
    List<String> selectBrandByKeyword(String keyword);

    List<ProductVO> selectProducts();
}