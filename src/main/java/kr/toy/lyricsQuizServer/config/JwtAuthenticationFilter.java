package kr.toy.lyricsQuizServer.config;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    
    //FIXME 3개 다 SecurityProperties로 관리
    @Value("${jwt.secret}")
    private String jwtSecret;
    
    private final String accessTokenCookieName = "yml로관리";

    private final String refreshTokenCookieName = "yml로관리";

    private final Integer maxAge = 1234; //FIXME MAXAGE를 변수로 받지 않고 고정할지 여부

    private final AuthenticationManager authenticationManager;

    private final SecurityService securityService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request.getRequestURI().equals("login/callback")) {
            //쿠키에 AccessToken이 없고,
            //어쨌건 Oauth 인증을 거쳐서 무언가 accessToken이 들어옴.
            //OauthAccessToken과 LoginType URL에 요청을 보내서 사용자 정보를 받아옴.
            //성공시 accessToken발급 로직 실행
            //실패시 return
        }



        String accessToken = securityService.resolveToken(request, accessTokenCookieName);
        String refreshToken;
        //1. 회원가입 으로 들어오는 로직.
        //2. 로그인로직.
        //3. 사실 회원가입 로직은 로그인 로직 이후 DB에 정보가 없으면 자동으로 리다이렉트 해도 될듯.

        try {
            Authentication authentication = authenticationManager.authenticate(new JwtAuthenticationToken("principal_을 넣으세요", accessToken));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException e) {
            refreshToken = refreshTokenCheck(request, response);
            if(response.getStatus() == HttpServletResponse.SC_UNAUTHORIZED){
                return;
            }
            String reIssuedAccessToken = securityService.accessTokenIssue(securityService.getUserSeqIn(refreshToken));
            securityService.setCookie(accessTokenCookieName, reIssuedAccessToken, true, maxAge, response);
        } catch (Exception e){
            e.printStackTrace();
        }
        chain.doFilter(request, response);
    }


    public String refreshTokenCheck(HttpServletRequest request, HttpServletResponse response){
        String refreshToken = "";
        try {
            refreshToken = securityService.resolveToken(request, refreshTokenCookieName);
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

}
