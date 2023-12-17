package kr.toy.lyricsQuizServer.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // FIXME NoSuchElement catch
        String accessToken = securityService.resolveToken(request, securityProperties.cookieName().accessTokenCookieName);

        String refreshToken;

        try {
            Authentication authentication = authenticationManager.authenticate(new JwtAuthenticationToken(securityService.getEmailIn(accessToken), accessToken));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            refreshToken = refreshTokenCheck(request, response);
            if(response.getStatus() == HttpServletResponse.SC_UNAUTHORIZED){
                return;
            }
            String reIssuedAccessToken = securityService.accessTokenIssue(securityService.getUserSeqIn(refreshToken));
            securityService.setCookieWithToken(true, reIssuedAccessToken, response);
        } catch (Exception e){
            e.printStackTrace();
        }
        chain.doFilter(request, response);
    }


    public String refreshTokenCheck(HttpServletRequest request, HttpServletResponse response){
        String refreshToken = "";
        try {
            refreshToken = securityService.resolveToken(request, securityProperties.cookieName().refreshTokenCookieName);
        } catch (JwtInvalidException | NullPointerException | NoSuchElementException e){
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            //1. Cookie가 없을 때,  -> NullPoint
            //2. Cookie는 있는데 안에 Jwt가 없을때, - NoSuchElementException
            //3. Cookie도 있고 Jwt도 있는데 Jwt관련 에러들이 발생할때. - JwtInvalidException
            //FIXME 로그인을 새로 하게 만드는 로직 추가.
        }
        return refreshToken;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] excludePath = {"/api/users/signup", "/api/users/info", "/api/users", "/api/file"};
        String path = request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }

}
