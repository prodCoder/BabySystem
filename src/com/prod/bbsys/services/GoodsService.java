package com.prod.bbsys.services;

import com.google.gson.Gson;
import com.prod.bbsys.Utils.PageUtil;
import com.prod.bbsys.mapper.GoodsMapper;
import com.prod.bbsys.mapper.SpringContext;
import com.prod.bbsys.model.pojo.Goods;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: BabySystem
 * @Package: com.prod.bbsys.services
 * @ClassName: GoodsService
 * @Description: 关于商品的服务封装类
 * @Author: prod
 * @CreateDate: 2018/6/9 13:26
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/6/9 13:26
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class GoodsService {
    private ApplicationContext ac;
    public GoodsService(){
        ac= SpringContext.getSingleContextInstance();
    }

    /**
     * @method
     * @description 获取分页数据
     * @date: 2018/6/9
     * @author: prod
     * @param util
     * @return
     */
    public PageUtil getGoodsListByPage(PageUtil util){//获取分页数据

        //获取util中封装的每页行数
        int pageSize=util.getPageSize();
        //获取数据库数据总行数
        GoodsMapper goodsMapper=(GoodsMapper)ac.getBean("goodsMapper");
        int totalColumn=goodsMapper.selectColumnAmount();
        util.setTotalColumn(totalColumn);
        int pageNum=util.getPageNum();//设置util pageNum的值
        //获取util中封装的当前页面
        int indexPage=util.getIndex();
        //获取数据
        Map<String,Integer> map=new HashMap<>();
        int start=(indexPage-1)*pageSize;//查询起始行
        map.put("start",start);
        map.put("size",pageSize);
        List<Goods> list=goodsMapper.selectGoodsListByPage(map);
        util.setData(list);
        return util;
    }

    public static void main(String args[]){
        GoodsService service=new GoodsService();
        PageUtil util=new PageUtil();
        util.setIndex(3);
        util.setPageSize(5);
        service.getGoodsListByPage(util);
        int totalColumn=util.getTotalColumn();
        int pageNum=util.getPageNum();
        Map map=new HashMap<>();

        Gson gson=new Gson();
        List<Goods> goodsList=util.getData();
        map.put("totalColumn",totalColumn);
        map.put("pageNum",pageNum);
        map.put("goodList",goodsList);
        String jsonStr=gson.toJson(map);
        String jsonStrList=gson.toJson(goodsList);
        System.out.println(jsonStr);
        System.out.println(jsonStrList);

    }
}
