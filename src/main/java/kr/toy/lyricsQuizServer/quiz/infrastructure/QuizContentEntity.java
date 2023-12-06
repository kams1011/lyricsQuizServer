package kr.toy.lyricsQuizServer.quiz.infrastructure;

import kr.toy.lyricsQuizServer.quiz.domain.QuizContent;
import kr.toy.lyricsQuizServer.quiz.domain.QuizContentType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "quiz_content_entity")
public class QuizContentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizContentSeq;

    @Enumerated(value = EnumType.STRING)
    private QuizContentType quizContentType;

    private String detail;


    @Builder
    public QuizContentEntity(Long quizContentSeq, QuizContentType quizContentType, String detail){
        this.quizContentSeq = quizContentSeq;
        this.quizContentType = quizContentType;
        this.detail = detail;
    }


    public QuizContent toModel(){
        return QuizContent.builder()
                .quizContentSeq(quizContentSeq)
                .quizContentType(quizContentType)
                .detail(detail)
                .build();
    }


    public static QuizContentEntity fromModel(QuizContent quizContent){
        return QuizContentEntity.builder()
                .quizContentSeq(quizContent.getQuizContentSeq())
                .quizContentType(quizContent.getQuizContentType())
                .detail(quizContent.getDetail())
                .build();
    }
}
