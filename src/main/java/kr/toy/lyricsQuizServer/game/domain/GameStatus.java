package kr.toy.lyricsQuizServer.game.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GameStatus {

    READY(true),
    IN_PROGRESS(false),
    FINISHED(false);

    boolean isRoomOpen;

}
