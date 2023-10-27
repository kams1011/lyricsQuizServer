package kr.toy.lyricsQuizServer.quiz.domain;

import lombok.Getter;

@Getter
public enum QuizContentType {

    FILE(0),
    YOUTUBE(1);


    private String type;

    private int number;

    QuizContentType(int number){
        this.number = number;
    }

}
