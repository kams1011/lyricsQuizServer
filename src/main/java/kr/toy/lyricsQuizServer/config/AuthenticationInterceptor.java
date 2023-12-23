package kr.toy.lyricsQuizServer.config;

import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request
            , HttpServletResponse response
            , Object handler, ModelAndView modelAndView)
            throws Exception {
        // 요청 후처리 로직 구현
    }

    @Override
    public void afterCompletion(HttpServletRequest request
            , HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 예외 처리 로직 구현
    }
}
