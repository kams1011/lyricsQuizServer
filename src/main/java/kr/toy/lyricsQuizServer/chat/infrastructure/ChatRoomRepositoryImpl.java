package kr.toy.lyricsQuizServer.chat.infrastructure;

import kr.toy.lyricsQuizServer.chat.domain.ChatRoom;
import kr.toy.lyricsQuizServer.chat.service.port.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ChatRoomRepositoryImpl implements ChatRoomRepository {


//    @Override
//    public List<ChatRoom> findAllRoom(Pageable pageable) {
//        return chatRoomJPARepository.findAll(pageable);
//    }

    @Override
    public ChatRoom findRoomById(Long chatRoomSeq) {
        return null;
    }

    @Override
    public ChatRoom save(ChatRoom chatRoom) {
        return null;
    }

    @Override
    public ChatRoom createChatRoom(String name) {
        return null;
    }
}
