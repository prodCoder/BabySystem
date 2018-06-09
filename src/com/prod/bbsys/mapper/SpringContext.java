package com.prod.bbsys.mapper;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ProjectName: BabySystem
 * @Package: com.prod.bbsys.mapper
 * @ClassName: SpringContext
 * @Description: SpringContext对象单例管理
 * @Author: prod
 * @CreateDate: 2018/6/8 14:52
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/6/8 14:52
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SpringContext {
    private static ApplicationContext ac;
    private static String path="spring/applicationContext.xml";

    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        SpringContext.path = path;
    }

    public static ApplicationContext getSingleContextInstance(){
        return ac==null?new ClassPathXmlApplicationContext(path):ac;
    }/**
     * @method
     * @description 单例模式的实现
     * @date: 2018/6/8
     * @author: prod
     * @param
     * @return
     */
}
