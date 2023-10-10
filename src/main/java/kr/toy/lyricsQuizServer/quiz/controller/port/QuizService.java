package kr.toy.lyricsQuizServer.quiz.controller.port;

import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.domain.dto.ChatMessage;

public interface QuizService {


    Quiz create();

    Quiz update();

    Quiz delete();

    Quiz solve();

    void join(Long roomId, ChatMessage message);

    void chat(Long roomId, ChatMessage message);


}
