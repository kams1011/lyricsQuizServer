package kr.toy.lyricsQuizServer.game.domain.dto;


import lombok.Getter;

@Getter
public class GameCreate {


        Long userSeq;

        String roomName;

        Integer attendeeLimit;

        Long quizSeq;


        public GameCreate(Long userSeq, String roomName, Integer attendeeLimit, Long quizSeq){
                this.userSeq = userSeq;
                this.roomName = roomName;
                this.attendeeLimit = attendeeLimit;
                this.quizSeq = quizSeq;
        }

        //여기서 ToModel로 바꿔야될듯.

}
