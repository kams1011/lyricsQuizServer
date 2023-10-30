package kr.toy.lyricsQuizServer.quiz.service;

import kr.toy.lyricsQuizServer.quiz.domain.QuizContent;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizContentCreate;
import kr.toy.lyricsQuizServer.quiz.infrastructure.QuizContentEntity;

public interface QuizContentRepository {

    QuizContent getById(Long quizContentSeq);

    QuizContent save(QuizContent quizContent);
}
