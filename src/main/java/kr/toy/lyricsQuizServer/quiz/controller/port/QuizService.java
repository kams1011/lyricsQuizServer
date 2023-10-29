package kr.toy.lyricsQuizServer.quiz.controller.port;

import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.domain.QuizContent;
import kr.toy.lyricsQuizServer.quiz.domain.dto.ChatMessage;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizContentCreate;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizCreate;

import javax.transaction.Transactional;

public interface QuizService {

    Quiz create(QuizCreate quizCreate); // YoutubeUrl로 할 수 있는지 체크.

    Quiz update();

    Quiz delete(Long quizSeq);

    Boolean solve(Long quizSeq, String answer);

    void join(Long roomId, ChatMessage message);

    void chat(Long roomId, ChatMessage message);

    void retrieve();

    QuizContent contentCreate(QuizContentCreate quizContentCreate);


}
