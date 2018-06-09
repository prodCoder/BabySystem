package com.prod.bbsys.controller;

import com.google.gson.Gson;
import com.prod.bbsys.Utils.PageUtil;
import com.prod.bbsys.model.pojo.Goods;
import com.prod.bbsys.services.GoodsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: BabySystem
 * @Package: ${PACKAGE_NAME}
 * @ClassName: ${NAME}
 * @Description: java类作用描述
 * @Author: prod
 * @CreateDate: 2018/6/9 10:43
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/6/9 10:43
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@WebServlet("*.goods")
public class GoodsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        PrintWriter out=response.getWriter();
        String url=request.getServletPath();
        String contextPath=request.getContextPath();
        if(url.equals("/list.goods")){
            goodsList(request, response);
            System.out.println("/list.goods被访问");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @method
     * @description ajax方式返回分页的商品数据
     * @date: 2018/6/9
     * @author: prod
     * @param request
     * @param response
     * @return
     */
    public void goodsList(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter out=response.getWriter();
        String pageIndex=request.getParameter("pageIndex");
        String pageSize=request.getParameter("pageSize");

        PageUtil pageUtil=new PageUtil();
        pageUtil.setPageSize(Integer.valueOf(pageSize));
        pageUtil.setIndex(Integer.valueOf(pageIndex));

        GoodsService goodsService=new GoodsService();
        goodsService.getGoodsListByPage(pageUtil);//分页获取数据

        //向前端回传数据（页面总数、商品信息）
        List<Goods> goodsList=pageUtil.getData();
        int totalColumn=pageUtil.getTotalColumn();
        int pageNum=pageUtil.getPageNum();
        Map map=new HashMap<>();
        map.put("totalColumn",totalColumn);//总行数信息
        map.put("pageNum",pageNum);//总页面信息
        map.put("goodsList",goodsList);
        //将List转为json数据
        Gson gson=new Gson();
        //String jsonStr=gson.toJson(goodsList);
        String jsonStr=gson.toJson(map);
        out.write("("+jsonStr+")");
    }
}
