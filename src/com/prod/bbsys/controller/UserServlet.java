package com.prod.bbsys.controller;

import com.prod.bbsys.model.pojo.User;
import com.prod.bbsys.services.UserService;
import com.prod.bbsys.services.UserServiceIml;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="userServlet",urlPatterns = {"*.user"})
public class UserServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        PrintWriter out=response.getWriter();
        //获取url
        String url = request.getServletPath();
        String contextPath=request.getContextPath();

        //登录
        if(url.equals("/login.user")){//登录
            boolean flag=login(request,response);
            System.out.println("/login.user被访问，结果:"+flag);
            out.print(flag);
        }else if(url.equals("/signOut.user")){//注销
            signOut(request, response);
        }else if(url.equals("/alterPw.user")){//修改用户密码
            boolean flag=changePassword(request, response);
            out.print(flag);
        }else if(url.equals("register.user")){//注册

        }else{
            out.print("请求的接口不存在！");
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @method
     * @description 登录控制
     * @date: 2018/6/8
     * @author: prod
     * @param request
     * @param response
     * @return flag 登录结果
     */
    public boolean login(HttpServletRequest request,HttpServletResponse response){
        boolean flag=false;
        String user=request.getParameter("user");
        String pass=request.getParameter("pw");
        //out.print(user+pass);
        UserService userService=new UserServiceIml();
        int result=userService.loginService(user,pass);
        if(result==4) {//成功登陆
            HttpSession session = request.getSession();
            //向session中存入登录信息
            session.setAttribute("login", "true");
            session.setAttribute("user", user);
            session.setAttribute("pw1", pass);
            if(!user.equals("admin")){
                session.setAttribute("userType","platUser");
            }else{
                session.setAttribute("userType","manager");
            }
            flag=true;
        }
        return flag;
    }

    /**
     * @method
     * @description 注销登录控制
     * @date: 2018/6/8
     * @author: prod
     * @param request
     * @param response
     * @return
     */
    public void signOut(HttpServletRequest request,HttpServletResponse response) throws IOException {
        HttpSession session=request.getSession();
        String loginMes=(String)session.getAttribute("login");
        if(loginMes==null||!loginMes.equals("true")){
            response.sendRedirect(request.getContextPath()+"/loginError.html");
        }else{//销毁session
            session.invalidate();
            response.sendRedirect(request.getContextPath()+"/login.html");
        }
    }

    /**
     * @method
     * @description 修改密码
     * @date: 2018/6/8
     * @author: prod
     * @param request
     * @param response
     * @return
     */
    public boolean changePassword(HttpServletRequest request,HttpServletResponse response){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("user");
        //获取二级密码
        UserService userService=new UserServiceIml();
        User user=((UserServiceIml) userService).getUser(username);
        String pw2=user.getPwd2();
        //验证密码正确
        if(pw2!=null&&!pw2.equals("")){
            String old=request.getParameter("old");
            if(old.equals(pw2)){//密码正确
             //修改密码
                String newPw1=request.getParameter("npw1");
                String newPw2=request.getParameter("npw2");
                User newuser=new User();
                newuser.setUsername(username);
                newuser.setPwd1(newPw1);
                newuser.setPwd2(newPw2);
                ((UserServiceIml) userService).changePw(newuser);
                session.setAttribute("pw1",newPw1);
            }else{
                return false;
            }
        }else{
            return false;
        }
        return true;
    }
}
