package kr.toy.lyricsQuizServer.game.controller.response;

import kr.toy.lyricsQuizServer.game.domain.Game;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GameRoom {

    private final Long gameRoomSeq;

    private final String picture;

    private final String roomName;

    private final LocalDateTime startedAt;

    private final String managerName;

//    private final String topic;

    private final Integer attendeeLimit;

    private final Integer attendeeCount;


    @Builder
    public GameRoom(Long gameRoomSeq, String picture, String roomName, LocalDateTime startedAt,
                    String managerName,
//                    String topic,
                    Integer attendeeLimit, Integer attendeeCount) {
        this.gameRoomSeq = gameRoomSeq;
        this.picture = picture;
        this.roomName = roomName;
        this.startedAt = startedAt;
        this.managerName = managerName;
//        this.topic = topic;
        this.attendeeLimit = attendeeLimit;
        this.attendeeCount = attendeeCount;
    }


    public static GameRoom from(Game game){
        return GameRoom.builder()
                .gameRoomSeq(game.getGameRoomSeq())
//                .picture(game.getQuiz().get)
                .roomName(game.getRoomName())
                .startedAt(game.getStartedAt())
                .managerName(game.getManager().getNickName())
                .attendeeLimit(game.getAttendeeLimit())
                .attendeeCount(game.getAttendeeCount())
                .build();
    }


}
