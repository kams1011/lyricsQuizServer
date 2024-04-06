package kr.toy.lyricsQuizServer.quiz.domain.dto;

import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.domain.QuizContent;
import kr.toy.lyricsQuizServer.quiz.domain.QuizContentType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StreamingInfo {

    String url;

    QuizContentType quizContentType;

    LocalTime startTime;

    LocalTime endTime;


    @Builder
    public StreamingInfo(String url, QuizContentType quizContentType, LocalTime startTime, LocalTime endTime){
        this.url = url;
        this.quizContentType = quizContentType;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static StreamingInfo from(Quiz quiz, QuizContent quizContent){
        return StreamingInfo.builder()
                .url(quizContent.getStreamingURL())
                .quizContentType(quizContent.getQuizContentType())
                .startTime(quiz.getStartTime())
                .endTime(quiz.getEndTime())
                .build();
    }
}