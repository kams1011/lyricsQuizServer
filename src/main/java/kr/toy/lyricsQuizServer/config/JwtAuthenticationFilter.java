package kr.toy.lyricsQuizServer.config;

import kr.toy.lyricsQuizServer.config.ConfigurationProperties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    
    private final SecurityProperties securityProperties;

    private final AuthenticationManager authenticationManager;

    private final SecurityService securityService;

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // FIXME NoSuchElement catch
        try {
            String accessToken = securityService.resolveToken(request, securityProperties.cookieName().accessTokenCookieName());
            authenticateByJwt(accessToken, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        chain.doFilter(request, response);
    }


    public String refreshTokenCheck(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshToken = "";
        try {
            refreshToken = securityService.resolveToken(request, securityProperties.cookieName().refreshTokenCookieName());
        } catch (JwtInvalidException | NullPointerException | NoSuchElementException e){
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증되지 않은 사용자입니다.");
            //FIXME 로그인을 새로 하게 만드는 로직 추가.
        }
        return refreshToken;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        String path = request.getRequestURI();
        return ExcludeURL.isExcludeURL(path);
    }


    private void authenticateByJwt(String accessToken, HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            Authentication authentication = authenticationManager.authenticate(new JwtAuthenticationToken(jwtUtils.getUserSeqIn(accessToken), accessToken));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException e) {
            renewAccessToken(request, response);
        }
    }

    private void renewAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshToken = refreshTokenCheck(request, response);
        if (response.getStatus() != HttpStatus.UNAUTHORIZED.value()) {
            String accessToken = jwtUtils.accessTokenIssue(jwtUtils.getUserSeqIn(refreshToken));
            securityService.setCookieWithToken(true, accessToken, response);
        }
    }
}
