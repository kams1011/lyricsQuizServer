package kr.toy.lyricsQuizServer.chat.service;

import kr.toy.lyricsQuizServer.chat.controller.dto.ChatMessage;
import kr.toy.lyricsQuizServer.chat.controller.port.ChatService;
import kr.toy.lyricsQuizServer.chat.domain.MessageType;
import kr.toy.lyricsQuizServer.config.Redis.RedisUtil;
import kr.toy.lyricsQuizServer.game.service.port.GameRepository;
import kr.toy.lyricsQuizServer.user.domain.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final RedisUtil redisUtil;

    //단일 ChannelTOpic이 아니라 다중 ChannelTopic을 사용해야함.
    //ChannelTopic 종류도 여러개 생성해야함. -> Enum으로 관리해서 각 채널토픽을 생성하는 메서드를 만들자.

    @Override
    public void sendMessage(ChatMessage message, UserInfo user) {
        String nickName = user.getNickName();
        message.setSender(nickName);
        if (message.getType().equals(MessageType.ENTER)) {
            message = message.join(nickName);
        }
        redisUtil.publish(message);
    }




}
