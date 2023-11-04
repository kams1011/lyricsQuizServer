package kr.toy.lyricsQuizServer.config;

import kr.toy.lyricsQuizServer.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {


    private final SecurityService securityService;

    private final JwtAuthenticationProvider jwtAuthenticationProvider;


//    @Bean
//    public JwtAuthenticationProvider jwtAuthenticationProvider() {
//        return new JwtAuthenticationProvider();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//                .addFilter(corsConfig.corsFilter())
                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .formLogin().disable()
                .httpBasic().disable()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), securityService));
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


}
