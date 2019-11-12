package com.huaita.ssoserver.controller;

import com.huaita.ssoserver.bean.User;
import com.huaita.ssoserver.util.JwtUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {


   static List<String> list = new ArrayList<String >();

    @RequestMapping(value="/preLogin",method= RequestMethod.GET)
    public String preLogin(String url,HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        //判断有没有人登录过这个session
        if(null == session){
           model.addAttribute("url",url);
           return "login";
        }else{
          String token = (String)session.getAttribute("token");
          return "redirect:http:"+url+"?token="+token;
        }
    }

    @RequestMapping("/loginll")
    @ResponseBody
    public String login(String userName,String password,String url,HttpServletRequest request){
       if("Jack".equals(userName) && "123456".equals(password)){
           String token = null;
           User user = new User();
           user.setId("11111");
           user.setName(userName);
            token = JwtUtil.geneJsonWebToken(user);
            list.add(token);
            request.getSession().setAttribute("token",token);
            request.getSession().setAttribute("SESSION_KEY_"+token,user);
            return "rediect:http://"+url+"?token="+token;
       }else{
           return "用户名或者密码不正确";
       }
    }

    @RequestMapping(value="/checkToken")
    @ResponseBody
    public String checkToken(String token){
        if(list.contains(token)&&JwtUtil.checkJWT(token)!=null){
            return "correct";
        }
        return "incorrect";
    }

    /**
     * 用户获取user
     * @param request
     * @return
     */
    @RequestMapping(value="/getUser")
    @ResponseBody
    public User getUser(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(null != session){
            String token = (String)session.getAttribute("token");
            User user = (User) session.getAttribute("SESSION_KEY_"+token);
            return user;
        }
        return null;
    }

    /**
     * 登出的一些操作  需要远程操作
     * @return
     */
    @RequestMapping(value="/logOut")
    @ResponseBody
    public String logOut(){
      return null;
    }
}
