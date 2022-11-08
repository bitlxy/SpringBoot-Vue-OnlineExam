package com.exam.interceptor;

import com.exam.annotation.CheckLogin;
import com.exam.config.DataSourceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 登录拦截器
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            CheckLogin needLogin = method.getAnnotation(CheckLogin.class);
            if (null == needLogin) {
                needLogin = method.getDeclaringClass()
                        .getAnnotation(CheckLogin.class);
            }
            // 有登录验证注解，则校验登录
            if (null != needLogin) {
                Cookie[] cookies = request.getCookies();
                if (cookies == null) {
                    return true;
                }
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("role")) {
                        switch (cookie.getValue()){
                            // 管理员数据源
                            case "0" :
                                DataSourceContext.setDateSource("slave_2");
                                break;
                            // 教师数据源
                            case "1":
                                DataSourceContext.setDateSource("slave_1");
                                break;
                            // 学生数据源
                            case "2":
                                DataSourceContext.setDateSource("master");
                                break;
                        }
                    }
                }

            }

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
