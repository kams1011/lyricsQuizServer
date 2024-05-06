package kr.toy.lyricsQuizServer.config;

import kr.toy.lyricsQuizServer.config.ConfigurationProperties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {


    private final SecurityService securityService;

    private final SecurityProperties securityProperties;

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private final JwtUtils jwtUtils;

//    @Bean
//    public JwtAuthenticationProvider jwtAuthenticationProvider() {
//        return new JwtAuthenticationProvider();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().permitAll();
        http.cors();
        http
//                .authorizeRequests()
//                .antMatchers("/favicon.ico", "/js/**", "/css/**", "/docs/*").permitAll()
//                .addFilter(corsConfig.corsFilter())
                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .formLogin().disable()
                .httpBasic().disable()
                .addFilterBefore(new JwtAuthenticationFilter(securityProperties, authenticationManager(), securityService, jwtUtils ), UsernamePasswordAuthenticationFilter.class);
        //FIXME addFilterBefore로 바꾼 부분 정상작동되는지 확인
        return http.build(); 
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager() {
        return authentication -> jwtAuthenticationProvider.authenticate(authentication);
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .mvcMatchers("/favicon.io", "/js/*", "/css/*", "/docs/*")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }


}
