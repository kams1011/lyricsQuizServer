package kr.toy.lyricsQuizServer.game.domain.dto;


import lombok.Getter;

@Getter
public class GameCreate {

        private Long userSeq;

        private String roomName;

        private Boolean isSecretRoom;

        private String password;

        private Integer attendeeLimit;

        private Long quizSeq;

        public GameCreate(Long userSeq, String roomName, Boolean isSecretRoom, String password, Integer attendeeLimit, Long quizSeq){
                this.userSeq = userSeq;
                this.roomName = roomName;
                this.isSecretRoom = isSecretRoom;
                this.password = password;
                this.attendeeLimit = attendeeLimit;
                this.quizSeq = quizSeq;
        }

        //여기서 ToModel로 바꿔야될듯.

}
