package kr.toy.lyricsQuizServer.quiz.domain;

import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizContentCreate;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
public class QuizContent {

    private Long quizContentSeq;


    private QuizContentType quizContentType;

    private String detail; // URL을 변환해서 뒤의 detail만 받으면 됨.

    @Builder
    public QuizContent(Long quizContentSeq, QuizContentType quizContentType, String detail){
        this.quizContentSeq = quizContentSeq;
        this.quizContentType = quizContentType;
        this.detail = detail;
    }

    public static QuizContent from(QuizContentCreate quizContentCreate){
        return QuizContent.builder()
                .quizContentType(quizContentCreate.getQuizContentType())
                .build();
    }

    public String detailFromUrl(String url){
        if (this.quizContentType.equals(QuizContentType.FILE)) {

        } else if (this.quizContentType.equals(QuizContentType.YOUTUBE)){
            detail = url.split(".be/|v=|embed/")[1].split("\\?")[0];
            //FIXME 잘못된 URL일시 ArrayIndexOutOfBoundsException 발생하는 부분 해결.
        }
        return detail;
    }
}
