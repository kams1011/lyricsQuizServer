package kr.toy.lyricsQuizServer.quiz.domain.dto;

import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class QuizDetailToCreateRoom {

    private Long quizSeq;

    private String title;

    private String singer;

    private String information;

    private String beforeLyrics;

    private String afterLyrics;

    private String answer;

    private LocalTime startTime;

    private LocalTime endTime;


    @Builder
    public QuizDetailToCreateRoom(Long quizSeq, String title, String singer, String information, String beforeLyrics, String afterLyrics, String answer, LocalTime startTime, LocalTime endTime){
        this.quizSeq = quizSeq;
        this.title = title;
        this.singer = singer;
        this.information = information;
        this.beforeLyrics = beforeLyrics;
        this.afterLyrics = afterLyrics;
        this.answer = answer;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static QuizDetailToCreateRoom fromModel(Quiz quiz){
        QuizDetailToCreateRoom quizToCreateRoom = QuizDetailToCreateRoom.builder()
                .quizSeq(quiz.getQuizSeq())
                .title(quiz.getTitle())
                .singer(quiz.getSinger())
                .information(quiz.getInformation())
                .beforeLyrics(quiz.getBeforeLyrics())
                .afterLyrics(quiz.getAfterLyrics())
                .answer(quiz.getAnswer())
                .startTime(quiz.getPlayTime().getStartTime())
                .endTime(quiz.getPlayTime().getEndTime())
                .build();
        return quizToCreateRoom;
    }

}
