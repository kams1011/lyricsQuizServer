package kr.toy.lyricsQuizServer.memory.Redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.toy.lyricsQuizServer.chat.controller.dto.ChatMessage;
import kr.toy.lyricsQuizServer.chat.domain.InvitationInfo;
import kr.toy.lyricsQuizServer.game.domain.dto.StreamingInfo;
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

    public void sendStreamingInfo(String publishMessage) {
        try {
            StreamingInfo streamingInfo = objectMapper.readValue(publishMessage, StreamingInfo.class);
            messagingTemplate.convertAndSend("/sub/chat/room/" + streamingInfo.getRoomId(), streamingInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void invite(String publishMessage){
        try {
            InvitationInfo invitationInfo = objectMapper.readValue(publishMessage, InvitationInfo.class);
            messagingTemplate.convertAndSend("/sub/invitation/" +invitationInfo.getInvitedUserSeq() , invitationInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void streamingVideo(){
//        SimpMessagingTemplate simpleMessageTemplate;
//        simpleMessageTemplate.convertAndSendToUser();
//    }

}
