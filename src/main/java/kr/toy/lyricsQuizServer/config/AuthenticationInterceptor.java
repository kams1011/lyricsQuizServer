package kr.toy.lyricsQuizServer.config;

import kr.toy.lyricsQuizServer.config.ConfigurationProperties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final SecurityService securityService;

    private final SecurityProperties securityProperties;

    private final JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //FIXME 필터 적용 제외되는 endpoint 처리 필요, JWT가 유효하지 않을 때만 추가해주는 로직 필요
//        String refreshToken = securityService.resolveToken(request, securityProperties.cookieName().refreshTokenCookieName());
//
//        String reIssuedAccessToken = jwtUtils.accessTokenIssue(jwtUtils.getUserSeqIn(refreshToken));
//
//        securityService.setCookieWithToken(true, reIssuedAccessToken, response);

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
