package com.step.webServer.intercepror;

import com.step.webServer.util.SessionUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class AndroidLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute(SessionUtil.PATIENT_ID) == null) {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
            response.getWriter().println("" +
                    "{" +
                    "   \"error\": { " +
                    "           \"code\": \"待定\", " +
                    "           \"message\": \"未登录，请先执行POST /api/login\"" +
                    "               }" +
                    "}");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
                           ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) {

    }
}
