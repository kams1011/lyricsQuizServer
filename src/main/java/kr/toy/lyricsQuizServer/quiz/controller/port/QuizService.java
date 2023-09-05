package kr.toy.lyricsQuizServer.quiz.controller.port;

import kr.toy.lyricsQuizServer.quiz.domain.Quiz;

public interface QuizService {


    Quiz create();

    Quiz update();

    Quiz delete();

    Quiz solve();



}
