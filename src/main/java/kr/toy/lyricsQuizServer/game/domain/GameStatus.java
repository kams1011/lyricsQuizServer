package kr.toy.lyricsQuizServer.game.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GameStatus {

    READY,
    IN_PROGRESS,
    FINISHED;

}
