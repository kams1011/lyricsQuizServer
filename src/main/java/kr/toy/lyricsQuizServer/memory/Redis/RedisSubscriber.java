package kr.toy.lyricsQuizServer.memory.Redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.toy.lyricsQuizServer.chat.controller.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisSubscriber {

    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;

    /**
     * Redis에서 메시지가 발행(publish)되면 대기하고 있던 Redis Subscriber가 해당 메시지를 받아 처리한다.
     */
    public void sendMessage(String publishMessage) {
        try {
            ChatMessage chatMessage = objectMapper.readValue(publishMessage, ChatMessage.class);
            messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getRoomId(), chatMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void invite(String publishMessage){
        // 접속한 모든 인원이 초대 메세지를 보내는 건 너무 비효율적임.
        // 초대 받기 모드를 설정하면 초대 Topic을 구독하는 걸로 하는게 나을듯.
        // 구독할 때 Redis에 초대 인원 목록을 추가함.
        // 그럼 초대시에 해당 Redis에서 값을 가져오면 됨.
        try {
//            ChatMessage chatMessage = objectMapper.readValue(publishMessage, ChatMessage.class);
//            messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getRoomId(), chatMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void streamingVideo(){
//        SimpMessagingTemplate simpleMessageTemplate;
//        simpleMessageTemplate.convertAndSendToUser();
//    }

}
