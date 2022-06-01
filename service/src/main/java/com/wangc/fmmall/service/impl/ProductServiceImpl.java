package com.wangc.fmmall.service.impl;

/**
 *  @author After拂晓
 */
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wangc.fmmall.Utils.PageHelper;
import com.wangc.fmmall.dao.ProductImgMapper;
import com.wangc.fmmall.dao.ProductMapper;
import com.wangc.fmmall.dao.ProductParamsMapper;
import com.wangc.fmmall.dao.ProductSkuMapper;
import com.wangc.fmmall.entity.*;
import com.wangc.fmmall.service.ProductService;
import com.wangc.fmmall.vo.ResStatus;
import com.wangc.fmmall.vo.ResultVO;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;
    @Resource
    private ProductImgMapper productImgMapper;

    @Resource
    private ProductSkuMapper productSkuMapper;

    @Resource
    private ProductParamsMapper productParamsMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RestHighLevelClient restHighLevelClient;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ResultVO listRecommengProds() {
        List<ProductVO> productVOS = productMapper.selectRecommendProds();

        return new ResultVO(ResStatus.OK, "success", productVOS);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ResultVO getProductBasicInfo(String productId) {
        // 先查Redis
        String productInfo = (String) stringRedisTemplate.boundHashOps("products").get(productId);
        if(productInfo != null){
            try {
                Product product = objectMapper.readValue(productInfo, Product.class);
                String imgsStr = (String) stringRedisTemplate.boundHashOps("productImg").get(productId);
                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, ProductImg.class);
                List<ProductImg> productImgs = objectMapper.readValue(imgsStr, javaType);
                String skusStr = (String) stringRedisTemplate.boundHashOps("productSkus").get(productId);
                JavaType javaType2 = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, ProductSku.class);
                List<ProductSku> productSkus = objectMapper.readValue(skusStr, javaType2);
                HashMap<String, Object> map = new HashMap<>();
                map.put("product", product);
                map.put("productImg", imgsStr);
                map.put("productSku", skusStr);
                return new ResultVO(ResStatus.OK, "success", map);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        //1.商品基本信息
        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId", productId);
        criteria.andEqualTo("productStatus", 1);
        List<Product> products = productMapper.selectByExample(example);
        if(products.size() == 0){
            return new ResultVO(ResStatus.NO, "商品不存在", null);
        }
        try {
            Product product = products.get(0);
            String jsonStr = objectMapper.writeValueAsString(product);
            stringRedisTemplate.boundHashOps("products").put(productId, jsonStr);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        //2.商品图片
        Example example1 = new Example(ProductImg.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("itemId", productId);
        List<ProductImg> productImgs = productImgMapper.selectByExample(example1);
        try {
            stringRedisTemplate.boundHashOps("productImg").put(productId, objectMapper.writeValueAsString(productImgs));
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        //3.商品套餐
        Example example2 = new Example(ProductSku.class);
        Example.Criteria criteria2 = example2.createCriteria();
        criteria2.andEqualTo("productId", productId);
        criteria2.andEqualTo("status", 1);
        List<ProductSku> productSkus = productSkuMapper.selectByExample(example2);
        try {
            stringRedisTemplate.boundHashOps("productSkus").put(productId, objectMapper.writeValueAsString(productSkus));
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("product", products.get(0));
        map.put("productImg", productImgs);
        map.put("productSku", productSkus);
        return new ResultVO(ResStatus.OK, "success", map);
    }

    @Override
    public ResultVO getProductParamsById(String productId) {
        Example example = new Example(ProductParams.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId", productId);
        List<ProductParams> productParams = productParamsMapper.selectByExample(example);
        if(productParams.size() == 0){
            return new ResultVO(ResStatus.NO, "三无商品", null);
        }
        return new ResultVO(ResStatus.OK, "success", productParams.get(0));
    }

    @Override
    public ResultVO getProductsByCateId(int cid, int pageNum, int limit) {
        int start = (pageNum - 1) * limit;
        List<ProductVO> productVOS = productMapper.selectProByCateId(cid, start, limit);
        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("categoryId", cid);
        int count = productMapper.selectCountByExample(example);
        int pageCount = count % limit == 0 ? count / limit : count / limit + 1;
        PageHelper<ProductVO> productVOPageHelper = new PageHelper<>(count, pageCount, productVOS);
        return new ResultVO(ResStatus.OK, "success", productVOPageHelper);
    }

    @Override
    public ResultVO listBrands(int cid) {
        List<String> brands = productMapper.selectBrandByCateId(cid);

        return new ResultVO(ResStatus.OK, "success", brands);
    }

    @Override
    public ResultVO searchProduct(String kwd, int pageNum, int limit) {
        ResultVO resultVO = null;
        try {
            //结果
//        kwd = "%" + kwd + "%";
            int start = (pageNum - 1) * limit;
            //使用ES替换数据库的模糊查询
            SearchRequest searchRequest = new SearchRequest("fmmallproductsindex2");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            //查询条件
            searchSourceBuilder.query(QueryBuilders.multiMatchQuery(kwd, "productName", "productSkuName"));
            //分页条件
            searchSourceBuilder.from(start);
            searchSourceBuilder.size(limit);
            //设置高亮显示
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            HighlightBuilder.Field field1 = new HighlightBuilder.Field("productName");
            HighlightBuilder.Field field2 = new HighlightBuilder.Field("productSkuName");
            highlightBuilder.field(field1);
            highlightBuilder.field(field2);
            highlightBuilder.preTags("<label style='color:red'>");
            highlightBuilder.postTags("</lable>");
            searchSourceBuilder.highlighter(highlightBuilder);
            searchRequest.source(searchSourceBuilder);
            //执行索引
            SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            //封装结果
            SearchHits hits = search.getHits();
            int count = (int) hits.getTotalHits().value;
            ArrayList<Product4ES> es = new ArrayList<>();
            Iterator<SearchHit> iterator = hits.iterator();
            while (iterator.hasNext()){
                SearchHit searchHit = iterator.next();
                Product4ES product4ES = objectMapper.readValue(searchHit.getSourceAsString(), Product4ES.class);
                //获取高亮字段
                Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
                HighlightField field3 = highlightFields.get("productName");
                if (field3 != null){
                    String highName = Arrays.toString(field3.fragments());
                    product4ES.setProductName(highName);
                }
                HighlightField field4 = highlightFields.get("productSkuName");
                if (field4 != null){
                    String highName = Arrays.toString(field4.fragments());
                    product4ES.setProductName(highName);
                }
                es.add(product4ES);
            }
            // 总页数
            int pageCount = (count % limit == 0 ? count / limit : count / limit + 1);

            // 封装
            resultVO = new ResultVO(ResStatus.OK, "success", new PageHelper<>(count, pageCount, es));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultVO;
    }

    @Override
    public ResultVO listBrandsByKey(String keyword) {
        keyword = "%" + keyword + "%";
        List<String> brands = productMapper.selectBrandByKeyword(keyword);
        return new ResultVO(ResStatus.OK, "success", brands);
    }
}
