package kr.toy.lyricsQuizServer.quiz.service;

import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.infrastructure.QuizEntity;

public interface QuizRepository {

    Quiz getById(Long id);

    Quiz save(QuizEntity quizEntity);


}
