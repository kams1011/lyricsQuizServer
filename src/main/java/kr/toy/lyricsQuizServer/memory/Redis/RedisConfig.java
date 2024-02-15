package kr.toy.lyricsQuizServer.memory.Redis;

import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import kr.toy.lyricsQuizServer.game.controller.response.UserInvitationInfo;
import kr.toy.lyricsQuizServer.user.domain.dto.UserInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

@Configuration
public class RedisConfig {

//
//    /**
//     * 단일 Topic 사용을 위한 Bean 설정
//     */
//    @Bean
//    public ChannelTopic channelTopic() {
//        return new ChannelTopic(RedisCategory.GAME_ROOM.name());
//    }

    /**
     * redis에 발행(publish)된 메시지 처리를 위한 리스너 설정
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListener(RedisConnectionFactory connectionFactory,
                                                              MessageListenerAdapter sendMessageListenerAdapter,
                                                              MessageListenerAdapter inviteListenerAdapter,
                                                              ChannelTopic channelTopic) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(sendMessageListenerAdapter, channelTopic);
        container.addMessageListener(inviteListenerAdapter, channelTopic);
        return container;
    }

    /**
     * RedisSubscriber 클래스의  sendMessage 클래스 가 호출됨.
     */
    @Bean
    public MessageListenerAdapter sendMessageListenerAdapter(RedisSubscriber subscriber) {

        return new MessageListenerAdapter(subscriber, "sendMessage");
    }

    @Bean
    public MessageListenerAdapter inviteListenerAdapter(RedisSubscriber subscriber) {

        return new MessageListenerAdapter(subscriber, "invite");
    }

    /**
     * 어플리케이션에서 사용할 redisTemplate 설정
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        return redisTemplate;
    }

    @Bean
    public HashOperations<String, Long, GameRoom> gameRoomHashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    @Bean
    public HashOperations<String, Long, UserInfo> userInfoHashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    @Bean
    public ListOperations<String, Object> invitePendingHashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }

}
