package com.prod.bbsys.services;

import com.prod.bbsys.mapper.SpringContext;
import com.prod.bbsys.mapper.UserMapper;
import com.prod.bbsys.model.pojo.User;
import org.springframework.context.ApplicationContext;

/**
 * @ProjectName: BabySystem
 * @Package: com.prod.bbsys.services
 * @ClassName: UserServiceIml
 * @Description: UserService实现类
 * @Author: prod
 * @CreateDate: 2018/6/7 17:36
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/6/7 17:36
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class UserServiceIml implements UserService{
    private ApplicationContext ac;
    //登录验证结果
    private final int USER_INVALID_USER=0;//无效账号
    private final int USER_INVALID_PASSOWRD1=1;//密码1无效
    private final int USER_INVALID_PASSOWRD2=2;//密码2无效
    private final int USER_WRONG_PASSWORD=3;//密码错误
    private final int USER_LOGIN_SUCCESS=4;//登录成功
    private final int USER_REGISTER_SUCCESS=5;//注册成功


    public UserServiceIml(){
        ac= SpringContext.getSingleContextInstance();
    }
    /**
     * @method
     * @description 利用dao获取User信息
     * @date: 2018/6/7
     * @author: prod
     * @param username
     * @return
     */
    public User getUser(String username) {
        UserMapper userMapper=(UserMapper)ac.getBean("userMapper");
        User userInfo=userMapper.selectByUsername(username);
        return userInfo;
    }

    /**
     * @method
     * @description 登录验证
     * @date: 2018/6/7
     * @author: prod
     * @param user
     * @param password
     * @return 验证结果
     */
    @Override
    public int loginService(String user, String password) {
        //判断0.账号密码不能为空1.账号是否存在2.密码是否正确
        if(user!=null&&!user.equals("")){
            if(password!=null&&!password.equals("")){
                User user1=this.getUser(user);
                System.out.println(user1);
                if(user1==null){
                    return USER_INVALID_USER;
                }else{
                    //有效账号
                    String pw1=user1.getPwd1();
                    if(!pw1.equals(password)){//密码错误
                        return USER_WRONG_PASSWORD;
                    }
                }
            }else{
                return USER_INVALID_PASSOWRD1;
            }
        }else{
            return USER_INVALID_USER;
        }
        return USER_LOGIN_SUCCESS;
    }

    /**
     * @method
     * @description 用dao向数据库插入user
     * @date: 2018/6/7
     * @author: prod
     * @param user
     * @return
     */
    public int insertUser(User user){
        UserMapper userMapper=(UserMapper)ac.getBean("userMapper");
        int result=userMapper.insertSelective(user);
        return result;
    }

    /**
     * @method
     * @description 账号注册验证
     * @date: 2018/6/7
     * @author: prod
     * @param user
     * @return
     */
    @Override
    public int register(User user) {
        String userId=user.getUserId();
        String pw1=user.getPwd1();
        String pw2=user.getPwd2();
        //判断各个值是否有效
        if(userId!=null&&!userId.equals("")){
            if(pw1!=null&&!pw1.equals("")){
                if(pw2!=null&&!pw2.equals("")){
                    User user1=this.getUser(userId);
                    if(user1==null){//该账号不存在
                        //向数据库插入数据
                        this.insertUser(user);
                    }else{
                        return USER_INVALID_USER;
                    }
                }else{
                    return USER_INVALID_PASSOWRD2;
                }
            }else{
                return USER_INVALID_PASSOWRD1;
            }
        }else{
            return USER_INVALID_USER;
        }
        return USER_REGISTER_SUCCESS;
    }

    public void changePw(User user){
        UserMapper userMapper=(UserMapper)ac.getBean("userMapper");
        userMapper.updateByUsername(user);
    }

    public static void main(String args[]){
        User user=new User();
        user.setUserId("user");
        user.setPwd1("123456");
        user.setPwd2("123user");
        UserServiceIml service =new UserServiceIml();
       // int result=service.register(user);
        User user1=service.getUser("user");
        System.out.println(user1);
    }
}
