package kr.toy.lyricsQuizServer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:8080", "https://localhost:8080")
                    .allowedMethods("*")
                    .allowedHeaders("*")
                    .allowCredentials(true)
//                    .allowedMethods(HttpMethod.GET.name(), HttpMethod.HEAD.name(), HttpMethod.POST.name(), HttpMethod.OPTIONS.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name())
                    .exposedHeaders("status")
                    .exposedHeaders("Set-Cookie");
    }
}
