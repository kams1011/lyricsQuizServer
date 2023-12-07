package kr.toy.lyricsQuizServer.quiz.domain.dto;

import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.domain.QuizContent;
import kr.toy.lyricsQuizServer.user.domain.User;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class QuizSearch {

    private Long quizSeq;

    private String title;

    private String singer;

    private String information;

    private String beforeLyrics;

    private String afterLyrics;

    private String answer;


    @Builder
    public QuizSearch(Long quizSeq, String title, String singer, String information, String beforeLyrics, String afterLyrics, String answer){
        this.quizSeq = quizSeq;
        this.title = title;
        this.singer = singer;
        this.information = information;
        this.beforeLyrics = beforeLyrics;
        this.afterLyrics = afterLyrics;
        this.answer = answer;
    }

    public QuizSearch fromModel(Quiz quiz){
        QuizSearch quizSearch = QuizSearch.builder()
                .quizSeq(quiz.getQuizSeq())
                .title(quiz.getTitle())
                .singer(quiz.getSinger())
                .information(quiz.getInformation())
                .beforeLyrics(quiz.getBeforeLyrics())
                .afterLyrics(quiz.getAfterLyrics())
                .answer(quiz.getAnswer())
                .build();
        return quizSearch;
    }

}
