package kr.toy.lyricsQuizServer.quiz.controller.port;

import kr.toy.lyricsQuizServer.quiz.domain.QuizContent;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizContentCreate;

public interface QuizContentService {

    QuizContent contentCreate(QuizContentCreate quizContentCreate);
}
