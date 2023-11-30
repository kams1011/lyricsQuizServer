package kr.toy.lyricsQuizServer.file.infrastructure;

import kr.toy.lyricsQuizServer.file.domain.File;
import kr.toy.lyricsQuizServer.file.domain.QuizFile;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.infrastructure.QuizEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class QuizFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long quizFileSeq;

    @OneToOne
    @JoinColumn(name = "quiz_seq")
    QuizEntity quiz;

    @OneToOne
    @JoinColumn(name = "file_seq")
    FileEntity file;


    @Builder
    public QuizFileEntity(Long quizFileSeq, QuizEntity quizEntity, FileEntity fileEntity){
        this.quizFileSeq = quizFileSeq;
        this.quiz = quizEntity;
        this.file = fileEntity;
    }



    public QuizFile toModel(){
        QuizFile quizFile = QuizFile.builder()
                .quizFileSeq(quizFileSeq)
                .quiz(quiz.toModel())
                .file(file.toModel())
                .build();

        return quizFile;
    }


    public static QuizFileEntity fromModel(QuizFile quizFile){
        QuizFileEntity quizFileEntity = QuizFileEntity.builder()
                .quizFileSeq(quizFile.getQuizFileSeq())
                .quizEntity(QuizEntity.fromModel(quizFile.getQuiz()))
                .fileEntity(FileEntity.fromModel(quizFile.getFile()))
                .build();

        return quizFileEntity;
    }

}
