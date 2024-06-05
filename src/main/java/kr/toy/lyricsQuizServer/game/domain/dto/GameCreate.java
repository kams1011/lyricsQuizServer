package kr.toy.lyricsQuizServer.game.domain.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameCreate {

        private String roomName;

        private Boolean isSecretRoom;

        private String password;

        private Integer attendeeLimit;

        private Long quizSeq;


        @Builder
        public GameCreate(String roomName, Boolean isSecretRoom, String password, Integer attendeeLimit, Long quizSeq){
                this.roomName = roomName;
                this.isSecretRoom = isSecretRoom;
                this.password = password;
                this.attendeeLimit = attendeeLimit;
                this.quizSeq = quizSeq;
        }

        //여기서 ToModel로 바꿔야될듯.

}
