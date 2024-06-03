package kr.toy.lyricsQuizServer.game.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;


@Getter
public class GamePassword {

    Long roomId;

    String password;


    @Builder
    @JsonCreator
    public GamePassword(@JsonProperty("gameRoomSeq") Long roomId,
                        @JsonProperty("password") String password){
        this.roomId = roomId;
        this.password = password;
    }
    //FIXME 정상작동하는지 확인

}
