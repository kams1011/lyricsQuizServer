package kr.toy.lyricsQuizServer.game.domain.dto;

import lombok.Getter;


@Getter
public class GamePassword {

    Long roomId;

    String password;


//    @Builder
//    @JsonCreator
//    public GamePassword(@JsonProperty("gameRoomSeq") Long gameRoomSeq,
//                        @JsonProperty("password") String password){
//        this.gameRoomSeq = gameRoomSeq;
//        this.password = password;
//    }

}
