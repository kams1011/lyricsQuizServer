package kr.toy.lyricsQuizServer.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken { 
    // 로그인하는 로직.
    private final Object principal;
    private final String jwt;

//    public JwtAuthenticationToken(Object principal) {
//        super(null);
//        this.principal = principal;
//        this.jwt = null;
//        setAuthenticated(false);
//    }

    public JwtAuthenticationToken(Object principal, String jwt) {
        super(null);
        this.principal = principal;
        this.jwt = jwt;
        setAuthenticated(false);
    }
    public JwtAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities, String jwt) {
        super(authorities);
        this.principal = principal;
        this.jwt = jwt;
        super.setAuthenticated(true); // must use super, as we override
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public String getJwt() { return this.jwt; }

}

