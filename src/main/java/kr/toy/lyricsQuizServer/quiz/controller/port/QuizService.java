package kr.toy.lyricsQuizServer.quiz.controller.port;

import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.domain.QuizContent;
import kr.toy.lyricsQuizServer.quiz.domain.dto.ChatMessage;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizContentCreate;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizCreate;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizDetailToCreateRoom;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.List;

public interface QuizService {

    List<Quiz> getList(String keyword, Pageable pageable);

    Quiz find(Long quizSeq);

    Quiz create(QuizCreate quizCreate); // YoutubeUrl로 할 수 있는지 체크.

    Quiz update();

    Quiz delete(Long quizSeq);

    Boolean solve(Long quizSeq, String answer);

    void join(Long roomId, ChatMessage message);

    void chat(Long roomId, ChatMessage message);

    void retrieve();



}
