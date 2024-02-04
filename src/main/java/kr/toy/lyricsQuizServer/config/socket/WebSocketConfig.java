package kr.toy.lyricsQuizServer.config.socket;

import kr.toy.lyricsQuizServer.config.JwtUtils;
import kr.toy.lyricsQuizServer.memory.Redis.RedisUtil;
import kr.toy.lyricsQuizServer.game.controller.port.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;

import java.util.List;


@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtUtils jwtUtils;

    private final RedisUtil redisUtil;

    private final GameService gameService;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub");
        config.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/chat/message").setAllowedOrigins("https://localhost:8080").withSockJS();
        registry.addEndpoint("/ws-stomp").setAllowedOrigins("https://localhost:8080")
                .addInterceptors(new SocketInterceptor()).withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new StompHandler(jwtUtils, gameService));
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new SocketJwtArgumentResolver(jwtUtils, redisUtil));
    }

}

