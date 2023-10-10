package kr.toy.lyricsQuizServer.quiz.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatRoom {

    private final Long id;
    private final Map<String, String> participants = new ConcurrentHashMap<>();

    public ChatRoom(Long id) {
        this.id = id;
    }

    public void join(String username) { // JWT 안에 아이디로 비교한다.
        participants.putIfAbsent(username, username);
    }

}
