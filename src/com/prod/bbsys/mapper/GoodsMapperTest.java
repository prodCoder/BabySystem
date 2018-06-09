package com.prod.bbsys.mapper;

import com.prod.bbsys.model.pojo.Goods;
import javafx.application.Application;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: BabySystem
 * @Package: com.prod.bbsys.mapper
 * @ClassName: GoodsMapperTest
 * @Description: java类作用描述
 * @Author: prod
 * @CreateDate: 2018/6/9 11:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/6/9 11:47
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class GoodsMapperTest {
    private ApplicationContext ac;

    public GoodsMapperTest(){
        ac=SpringContext.getSingleContextInstance();
    }

    public void selectGoodsListByPageTest(){
        Map map=new HashMap();
        map.put("start",0);
        map.put("size",5);
        GoodsMapper goodsMapper=(GoodsMapper)ac.getBean("goodsMapper");
        List<Goods> list=goodsMapper.selectGoodsListByPage(map);
        System.out.println(list);
    }

    public static void main(String args[]){
        GoodsMapperTest test = new GoodsMapperTest();
        test.selectGoodsListByPageTest();
    }
}
