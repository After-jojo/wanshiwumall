package com.wangc.fmmall.controller;

import com.wangc.fmmall.service.CategoryService;
import com.wangc.fmmall.service.ProductService;
import com.wangc.fmmall.service.impl.IndexServiceImpl;
import com.wangc.fmmall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/index")
@Api(value = "首页数据显示接口", tags = "首页管理")
public class IndexController {

    @Resource
    private IndexServiceImpl indexService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private ProductService productService;
    @ApiOperation("首页轮播图接口")
    @GetMapping("/indeximg")
    public ResultVO listIndexImgs(){
        return indexService.listIndexImgs();
    }

    @GetMapping("/category-list")
    @ApiOperation("商品分类查询接口")
    public ResultVO listCatetory(){
        return categoryService.listCategories();
    }

    @GetMapping("/list-recommends")
    @ApiOperation("查询商品推荐接口")
    public ResultVO listRecommendProducts(){
        return productService.listRecommengProds();
    }
    @GetMapping("/category-recommends")
    @ApiOperation("分类推荐接口")
    public ResultVO listRecommendProductsByCate(){
        return categoryService.listOneLevelCategories();
    }

}












