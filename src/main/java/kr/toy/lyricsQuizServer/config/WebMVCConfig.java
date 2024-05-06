package kr.toy.lyricsQuizServer.config;

import kr.toy.lyricsQuizServer.config.ConfigurationProperties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    private final SecurityService securityService;

    private final SecurityProperties securityProperties;

    private final JwtUtils jwtUtils;


    @Override
    public void addCorsMappings(CorsRegistry registry){
            registry.addMapping("/**")
//                    .allowedOrigins("http://localhost:8080", "https://localhost:8080")
                    .allowedOrigins("https://lyricsquizkaams.site", "https://lyricsquizkaams.site:8080")
                    .allowedMethods("*")
                    .allowedHeaders("*")
                    .allowCredentials(true)
//                    .allowedMethods(HttpMethod.GET.name(), HttpMethod.HEAD.name(), HttpMethod.POST.name(), HttpMethod.OPTIONS.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name())
                    .exposedHeaders("status")
                    .exposedHeaders("Set-Cookie");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new JwtArgumentResolver(securityService, jwtUtils, securityProperties));
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**/*")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource requestedResource = location.createRelative(resourcePath);
                        return requestedResource.exists() && requestedResource.isReadable() ? requestedResource : new ClassPathResource("/static/index.html");
                    }
                });
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new AuthenticationInterceptor(securityService, securityProperties, jwtUtils));
////                .excludePathPatterns("/css/**", "/images/**", "/js/**");
//    }
}
