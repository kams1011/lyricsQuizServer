package kr.toy.lyricsQuizServer.quiz.domain;

import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizContentCreate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class QuizContent {

    private Long quizContentSeq;

    private QuizContentType quizContentType;

    private String detail;

    @Builder
    public QuizContent(Long quizContentSeq, QuizContentType quizContentType, String detail){
        this.quizContentSeq = quizContentSeq;
        this.quizContentType = quizContentType;
        this.detail = detail;
    }

    public static QuizContent from(QuizContentCreate quizContentCreate){
        return QuizContent.builder()
                .quizContentType(quizContentCreate.getQuizContentType())
                .detail(quizContentCreate.getDetail())
                .build();
    }
}
