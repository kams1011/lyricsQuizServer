package kr.toy.lyricsQuizServer.quiz.service;

import kr.toy.lyricsQuizServer.quiz.domain.Quiz;

public interface QuizRepository {

    Quiz getById(Long id);
}
