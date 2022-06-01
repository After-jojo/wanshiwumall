package com.wangc.fmmall;

import com.wangc.fmmall.dao.CategoryMapper;
import com.wangc.fmmall.dao.ProductCommentsMapper;
import com.wangc.fmmall.dao.ProductMapper;
import com.wangc.fmmall.dao.ShoppingCartMapper;
import com.wangc.fmmall.entity.CategoryVO2;
import com.wangc.fmmall.entity.ProductCommentsVO;
import com.wangc.fmmall.entity.ProductVO;
import com.wangc.fmmall.entity.ShoppingCartVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiApplication.class)
public class ApiApplicationTests {
    @Resource
    private ProductMapper productMapper;
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private ProductCommentsMapper productCommentsMapper;
    @Resource
    private ShoppingCartMapper shoppingCartMapper;
    @Test
    public void test(){
        List<ProductVO> productVOS = productMapper.selectRecommendProds();
        for (ProductVO vo: productVOS  ) {
            System.out.println(vo);
        }
    }
    @Test
    public void test2(){
        List<CategoryVO2> categoryVO2s = categoryMapper.selectOneLevelCategories();
        for (CategoryVO2 c: categoryVO2s) {
            System.out.println(c);
        }
    }
    @Test
    public void list(){
        String cids = "1,8";
        String[] split = cids.split(",");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            list.add(Integer.parseInt(split[i]));
        }
        List<ShoppingCartVO> shoppingCartVOS = shoppingCartMapper.selectShopCartByCids(list);
        for(ShoppingCartVO vo : shoppingCartVOS){
            System.out.println(vo);
        }
    }
}
