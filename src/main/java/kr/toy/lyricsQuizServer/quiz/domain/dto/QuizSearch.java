package kr.toy.lyricsQuizServer.quiz.domain.dto;

import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import lombok.Builder;
import lombok.Getter;

@Getter
public class QuizSearch {


    private Long quizSeq;

    private String title;

    private String singer;

    private String answer;


    @Builder
    public QuizSearch(Long quizSeq, String title, String singer, String answer){
        this.quizSeq = quizSeq;
        this.title = title;
        this.singer = singer;
        this.answer = answer;
    }

    public static QuizSearch fromModel(Quiz quiz){

        QuizSearch quizSearch = QuizSearch.builder()
                .quizSeq(quiz.getQuizSeq())
                .title(quiz.getTitle())
                .singer(quiz.getSinger())
                .answer(quiz.getAnswer())
                .build();

        return quizSearch;
    }
}
