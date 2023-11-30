package kr.toy.lyricsQuizServer.file.service;

import kr.toy.lyricsQuizServer.file.domain.QuizFile;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;

public interface QuizFileRepository {


    void save(QuizFile quizFile);

    QuizFile readBy(Long quizFileSeq);

    QuizFile readBy(Quiz quiz);
}
