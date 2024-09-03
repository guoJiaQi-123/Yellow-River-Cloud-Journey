package com.tyut.travel.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MemberInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取session（因为我们在登录控制器中，将用户信息存放到了session域中）
        HttpSession httpSession = request.getSession();
        //在session域中获取用户对象
        Object member = httpSession.getAttribute("member");
        //如果用户对象为空，则说明还没有登录，则拦截该请求并重定向到登录界面
        if(member==null){
            response.sendRedirect("/frontdesk/login");
            return false;
        }
        return true;
    }
}
