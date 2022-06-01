package com.wangc.fmmall;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wangc.fmmall.dao.ProductMapper;
import com.wangc.fmmall.entity.Product4ES;
import com.wangc.fmmall.entity.ProductSku;
import com.wangc.fmmall.entity.ProductVO;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;

import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiApplication.class)
public class ImportESInfo {
    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Resource
    private ProductMapper productMapper;
    @Resource
    private ObjectMapper objectMapper;

    @Test
    public void CreateIndex() throws IOException {
        //ES创建索引
        CreateIndexRequest indexRequest = new CreateIndexRequest("fmmallproductsindex2");
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(indexRequest, RequestOptions.DEFAULT);
//        System.out.println(createIndexResponse.isAcknowledged());
    }

    @Test
    public void testImportData() throws IOException {
        //1. 从数据库查询数据
        List<ProductVO> productVOS = productMapper.selectProducts();
//        System.out.println(productVOS.size());

        //2. 将数据写入到ES
        int size = productVOS.size();
        for (int i = 0; i < size; i++) {
            ProductVO productVO = productVOS.get(i);
            String productId = productVO.getProductId();
            String productName = productVO.getProductName();
            Integer soldNum = productVO.getSoldNum();
            List<ProductSku> skus = productVO.getSkus();
            String skuName = skus.size() == 0 ? "" : skus.get(0).getSkuName();
            String skuImg = skus.size() == 0 ? "" : skus.get(0).getSkuImg();
            Integer sellPrice = skus.size() == 0 ? 0 : skus.get(0).getSellPrice();
            Product4ES product4ES = new Product4ES(productId, productName, skuImg, skuName, sellPrice, soldNum);
            IndexRequest request = new IndexRequest("fmmallproductsindex2");
            request.id(productId);
            request.source(objectMapper.writeValueAsString(product4ES), XContentType.JSON);
            IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
//            System.out.println((i + 1) + "-----" + indexResponse);
        }
    }
}
