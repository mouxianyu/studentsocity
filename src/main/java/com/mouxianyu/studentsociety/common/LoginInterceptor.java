package com.mouxianyu.studentsociety.common;

import com.mouxianyu.studentsociety.common.constant.Constant;
import com.mouxianyu.studentsociety.pojo.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user =(User) request.getSession().getAttribute(Constant.USER);
        if (user == null) {
            String requestURI = request.getRequestURI();
            String[] strings = requestURI.split("/");
            if("client".equals(strings[1])){
                response.sendRedirect("/client/toLogin");
            }else {
                response.sendRedirect("/user/toLogin");
            }
            return false;
        }
        return true;
    }
}
