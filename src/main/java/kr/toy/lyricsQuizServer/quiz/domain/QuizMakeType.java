package kr.toy.lyricsQuizServer.quiz.domain;

import lombok.Getter;

@Getter
public enum QuizMakeType {

    FILE(0),
    YOUTUBELINK(1);


    private String type;

    private int number;

    QuizMakeType(int number){
        this.number = number;
    }

}
