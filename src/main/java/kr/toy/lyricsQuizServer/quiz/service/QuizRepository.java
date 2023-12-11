package kr.toy.lyricsQuizServer.quiz.service;

import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.domain.QuizContent;

import java.awt.print.Pageable;
import java.util.List;

public interface QuizRepository {

    Quiz getById(Long id);

//    Quiz save(Quiz quiz);
    Quiz save(Quiz quiz, QuizContent quizContent);

    List<Quiz> getList(String keyword, Pageable pageable);

    Quiz delete(Long id);

}
