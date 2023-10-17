package kr.toy.lyricsQuizServer.quiz.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    private String content;
    private String sender;

    public ChatMessage(String content, String sender) {
        this.content = content;
        this.sender = sender;
    }
}
