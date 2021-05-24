package com.dongogong.controller;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor  implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserSession userSession =
                (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        if (userSession == null) {
            String url = request.getRequestURL().toString();
            String query = request.getQueryString();
            ModelAndView modelAndView = new ModelAndView("loginForm");
            if (query != null) {
                modelAndView.addObject("loginForwardAction", url+"?"+query);
            }
            else {
                modelAndView.addObject("loginForwardAction", url);
            }
            throw new ModelAndViewDefiningException(modelAndView);
        }
        else {
            return true;
        }
    }
}