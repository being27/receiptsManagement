package com.tt.oa.interceptor;

import com.tt.oa.entity.Staff;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    //如果没有进行登录操作，则return false，并跳转到登录页面
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        Staff staff = (Staff) request.getSession().getAttribute("staff");
        //session中的staff对象为空则说明用户并没有进行登录操作，跳转到登录页面
        if (staff == null){
            response.sendRedirect("/init/toLoginPage");
            return false;
        }
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
