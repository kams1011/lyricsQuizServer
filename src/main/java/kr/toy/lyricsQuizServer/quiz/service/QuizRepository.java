package kr.toy.lyricsQuizServer.quiz.service;

import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.domain.QuizContent;

public interface QuizRepository {

    Quiz getById(Long id);

//    Quiz save(Quiz quiz);
    Quiz save(Quiz quiz, QuizContent quizContent);


    Quiz delete(Long id);

}
