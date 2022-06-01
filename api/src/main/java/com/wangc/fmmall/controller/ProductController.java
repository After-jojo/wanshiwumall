package com.wangc.fmmall.controller;

import com.wangc.fmmall.service.ProductCommentsService;
import com.wangc.fmmall.service.ProductService;
import com.wangc.fmmall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(value = "提供商品信息的接⼝",tags = "商品管理")
@RestController
@CrossOrigin
@RequestMapping("/product")
public class ProductController {
    @Resource
    private ProductService productService;
    @Resource
    private ProductCommentsService productCommentsService;
    @ApiOperation("商品信息查询接⼝")
    @GetMapping("/detail-info/{pid}")
    public ResultVO getProductBasicInfo(@PathVariable("pid") String pid){
        return productService.getProductBasicInfo(pid);
    }

    @ApiOperation("商品参数信息查询接⼝")
    @GetMapping("/detail-params/{pid}")
    public ResultVO getProductParams(@PathVariable("pid") String pid){
        return productService.getProductParamsById(pid);
    }

    @ApiOperation("商品评论信息查询接⼝")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int",name =
                    "pageNum", value = "当前页码",required = true),
            @ApiImplicitParam(dataType = "int",name =
                    "limit", value = "每页显示条数",required = true)
    })
    @GetMapping("/detail-commonts/{pid}/{page_num}/{limit}")
    public ResultVO getProductCommonts(@PathVariable("pid") String pid, int pageNum, int limit){
        return productCommentsService.listCommontsByProId(pid, pageNum, limit);
    }
    @ApiOperation("商品评论信息查询接⼝")
    @GetMapping("/detail-commontscount/{pid}")
    public ResultVO getProductCommontsCount(@PathVariable("pid") String pid){
        return productCommentsService.getCommCountByProId(pid);
    }

    @ApiOperation("根据类别查询商品接⼝")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int",name =
                    "pageNum", value = "当前页码",required = true),
            @ApiImplicitParam(dataType = "int",name =
                    "limit", value = "每页显示条数",required = true)
    })
    @GetMapping("/listbucid/{cid}")
    public ResultVO getProductsByCate(@PathVariable("cid") int cid, int pageNum, int limit){
        return productService.getProductsByCateId(cid, pageNum, limit);
    }

    @ApiOperation("根据类别查询品牌接⼝")
    @GetMapping("/listbrands/{cid}")
    public ResultVO getBrandsByCate(int cid){
        return productService.listBrands(cid);
    }

    @ApiOperation("根据关键字查询商品接⼝")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string",name =
                    "keyword", value = "关键字",required = true),
            @ApiImplicitParam(dataType = "int",name =
                    "pageNum", value = "当前页码",required = true),
            @ApiImplicitParam(dataType = "int",name =
                    "limit", value = "每页显示条数",required = true)
    })
    @GetMapping("/listbykeyword")
    public ResultVO searchProducts(String keyword, int pageNum, int limit){
        return productService.searchProduct(keyword, pageNum, limit);
    }

    @ApiOperation("根据关键字查询品牌接⼝")
    @GetMapping("/listbrandsbykeyword")
    public ResultVO getBrandsByKeyword(String keyword){
        return productService.listBrandsByKey(keyword);
    }
}
