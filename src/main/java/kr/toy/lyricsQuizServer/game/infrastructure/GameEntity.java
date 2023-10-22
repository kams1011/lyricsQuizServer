package kr.toy.lyricsQuizServer.game.infrastructure;

import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.infrastructure.UserEntity;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "game_entity")
public class GameEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameRoomSeq;

    private LocalDateTime startTime;

    @ManyToOne
    @JoinColumn(name = "userSeq")
    private UserEntity manager;

    private String topic;

    private Integer attendeeLimit;

    private Integer attendeeCount;

//    @ManyToOne
//    @JoinColumn(name = "quiz_id")
//    private QuizEntity quiz;
}
