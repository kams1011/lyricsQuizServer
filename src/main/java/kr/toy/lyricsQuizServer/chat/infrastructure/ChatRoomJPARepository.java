package kr.toy.lyricsQuizServer.chat.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomJPARepository extends JpaRepository<ChatRoomEntity, Long> {
}
