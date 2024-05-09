package kr.toy.lyricsQuizServer.quiz.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizCreate;
import kr.toy.lyricsQuizServer.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class Quiz {

    private Long quizSeq;

    private Boolean isDeleted;

    private String title;

    private String singer;

    private String information;

    private String beforeLyrics;

    private String afterLyrics;

    private String answer;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt; // JPAAuditing 사용하면 어떻게 변경할지 생각

    private User maker;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
//    private LocalTime startTime; // 노래 구간 정보.
//
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
//    private LocalTime endTime;

    private PlayTime playTime;

    private QuizContent quizContent;


    @Builder
    public Quiz(Long quizSeq, Boolean isDeleted, String title, String singer, String information,
//                LocalTime startTime, LocalTime endTime,
                PlayTime playTime,
                String beforeLyrics,
                String afterLyrics, String answer, LocalDateTime createdAt, LocalDateTime updatedAt, User maker, QuizContent quizContent){
        this.quizSeq = quizSeq;
        this.isDeleted = isDeleted;
        this.title = title;
        this.singer = singer;
        this.information = information;
        this.playTime = playTime;
//        this.startTime = startTime;
//        this.endTime = endTime;
        this.beforeLyrics = beforeLyrics;
        this.afterLyrics = afterLyrics;
        this.answer = answer;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.maker = maker;
        this.quizContent = quizContent;
    }


    public static Quiz from(QuizCreate quizCreate, QuizContent quizContent, User maker, LocalDateTime createdAt){
        return Quiz.builder()
                .isDeleted(false)
                .title(quizCreate.getTitle())
                .singer(quizCreate.getSinger())
                .information(quizCreate.getInformation())
                .playTime(new PlayTime(quizCreate.getStartTime(), quizCreate.getEndTime()))
//                .startTime(quizCreate.getStartTime())
//                .endTime(quizCreate.getEndTime())
                .beforeLyrics(quizCreate.getBeforeLyrics())
                .afterLyrics(quizCreate.getAfterLyrics())
                .answer(quizCreate.getAnswer())
                .createdAt(createdAt)
                .updatedAt(createdAt)
                .maker(maker)
                .quizContent(quizContent)
                .build();
    }


    public Boolean isCorrect(String answer){
        if (this.answer.equals(answer)){
            return true;
        } else {
            return false;
        }
    }


    @Getter
    public static class PlayTime{

        private static final int PLAY_TIME_GAP_MAX = 30;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
        private final LocalTime startTime;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
        private final LocalTime endTime;

        public PlayTime(LocalTime startTime, LocalTime endTime) {
            validatePlayTimeGap(startTime, endTime);
            this.startTime = startTime;
            this.endTime = endTime;
        }

        private void validatePlayTimeGap(LocalTime startTime, LocalTime endTime){
            if (Duration.between(startTime, endTime).getSeconds() > PLAY_TIME_GAP_MAX) {
                throw new IllegalArgumentException("시작 시간과 끝 시간의 차이는 30초를 넘을 수 없습니다.");
            }
        }
    }


}
