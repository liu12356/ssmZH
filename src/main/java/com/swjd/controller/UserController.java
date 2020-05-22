package com.swjd.controller;

import com.swjd.bean.User;
import com.swjd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    //去登陆
    @RequestMapping("/toLogin")
    public String toLogin(Model model){
        User user=new User();
        model.addAttribute("user",user);

        return "login";
    }
    //做登录
    @RequestMapping("/doLogin")
    public String doLogin(Model model, User user, HttpSession session){
        User u=userService.login(user);
        if (u!=null){
            //账号密码没有问题
            if (u.getFlag().equals("1")){
                //账号密码存到session里面(登录成功把用户名存到session)
                session.setAttribute("activeName",u.getUname());
                return "redirect:/customerController/toMain";
            }else {
                //账号被禁用了
                model.addAttribute("user",user);
                model.addAttribute("errorMsg","该账号被禁用，请联系管理员");
                return "login";
            }
        }else {
            //账号密码错了
            User user1=new User();
            model.addAttribute("user",user1);
            model.addAttribute("errorMsg","账号或者密码错误");
            return "login";
        }
    }
    //去main.jsp
    @RequestMapping("/toMain")
    public String toMain(){
        return "main";
    }
    //去car.jsp
    @RequestMapping("/toCar")
    public String toCar(){
        return "car";
    }
    //去myTao.jsp
    @RequestMapping("/toMyTao")
    public String toMyTao(){
        return "myTao";
    }

    //退出账号
    @RequestMapping("/logOut")
    public String logOut(HttpSession session,Model model){
        //清空session
        session.invalidate();//让session过期的方法
        //传个空的对象
        User user=new User();
        model.addAttribute("user",user);
        /*return "login";*/
        return "redirect:/toLogin";
    }
}


