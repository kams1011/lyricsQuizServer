package kr.toy.lyricsQuizServer.quiz.controller.port;

import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.domain.dto.ChatMessage;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizCreate;

public interface QuizService {

    Quiz create(QuizCreate quizCreate);

    Quiz create(QuizCreate quizCreate, String youtubeUrl); // YoutubeUrl로 할 수 있는지 체크.

    Quiz update();

    Quiz delete();

    Quiz solve();

    void join(Long roomId, ChatMessage message);

    void chat(Long roomId, ChatMessage message);

    void retrieve();


}
