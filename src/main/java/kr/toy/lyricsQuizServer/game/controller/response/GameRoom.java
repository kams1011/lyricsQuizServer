package kr.toy.lyricsQuizServer.game.controller.response;

import kr.toy.lyricsQuizServer.game.domain.Game;
import kr.toy.lyricsQuizServer.game.domain.GameStatus;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class GameRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Long gameRoomSeq;

    private final String roomName;

    private final LocalDateTime startedAt;

    private final String managerName;

    private final String topic;

    private final Integer attendeeLimit;

    private final Integer attendeeCount;

    private final GameStatus gameStatus;

    @Builder
    public GameRoom(Long gameRoomSeq, String roomName, LocalDateTime startedAt, String managerName, String topic, Integer attendeeLimit, Integer attendeeCount, GameStatus gameStatus) {
        this.gameRoomSeq = gameRoomSeq;
        this.roomName = roomName;
        this.startedAt = startedAt;
        this.managerName = managerName;
        this.topic = topic;
        this.attendeeLimit = attendeeLimit;
        this.attendeeCount = attendeeCount;
        this.gameStatus = gameStatus;
    }


    public static GameRoom from(Game game){
        return GameRoom.builder()
                .gameRoomSeq(game.getGameRoomSeq())
                .roomName(game.getRoomName())
                .topic(game.getQuiz().getTitle()) //FIXME Query 확인하기
                .startedAt(game.getStartedAt())
                .managerName(game.getManager().getNickName())
                .attendeeLimit(game.getAttendeeLimit())
                .attendeeCount(game.getAttendeeCount())
                .gameStatus(game.getGameStatus())
                .build();
    }


}
