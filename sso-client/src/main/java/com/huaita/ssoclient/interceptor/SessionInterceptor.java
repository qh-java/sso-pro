package com.huaita.ssoclient.interceptor;

import com.huaita.ssoclient.config.SsoProperty;
import com.huaita.ssoclient.util.SendHttpUtil;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private SsoProperty ssoProperty;

    public static SessionInterceptor sessionInterceptor;

    @PostConstruct
    public void init(){
        sessionInterceptor = this;
        sessionInterceptor.ssoProperty = this.ssoProperty;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       HttpSession session = request.getSession();
       if(null != session && "login".equals(session.getAttribute("login"))){
           return true;
       }
        if(null == ssoProperty){
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            ssoProperty = (SsoProperty)factory.getBean("ssoProperty");
        }
       String token = request.getParameter("token");
       if(token != null){

          String reqUrl = ssoProperty.getAuthCheck() ;
          String content = "token="+ token;
          //到sso的服务端请求是否
          String result = SendHttpUtil.send(reqUrl,content);
          if("correct".equals(result)){
              request.getSession().setAttribute("login","login");
              return true;
          }
       }
        String url = request.getRequestURI();
        response.sendRedirect(ssoProperty.getAuthPreLogin()+"?url="+url);
        return false;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
