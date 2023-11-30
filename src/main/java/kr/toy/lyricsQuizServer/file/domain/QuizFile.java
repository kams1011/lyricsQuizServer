package kr.toy.lyricsQuizServer.file.domain;

import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import lombok.Builder;
import lombok.Getter;

@Getter
public class QuizFile {

    Long quizFileSeq;

    Quiz quiz;

    File file;


    @Builder
    public QuizFile(Long quizFileSeq, Quiz quiz, File file){
        this.quizFileSeq = quizFileSeq;
        this.quiz = quiz;
        this.file = file;
    }
}
