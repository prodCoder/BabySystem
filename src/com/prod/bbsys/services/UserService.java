package com.prod.bbsys.services;

import com.prod.bbsys.model.pojo.User;

public interface UserService {
    /**
     * @method
     * @description 登录接口
     * @date: 2018/6/7
     * @author: prod
     * @return
     */
    public int loginService(String user,String password);

    /**
     * @method
     * @description 注册
     * @date: 2018/6/7
     * @author: prod
     * @return 操作提示
     */
    public int register(User user);

}
