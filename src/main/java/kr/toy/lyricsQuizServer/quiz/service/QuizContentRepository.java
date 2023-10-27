package kr.toy.lyricsQuizServer.quiz.service;

import kr.toy.lyricsQuizServer.quiz.domain.QuizContent;

public interface QuizContentRepository {


    QuizContent getById();

    QuizContent save();
}
