package kr.toy.lyricsQuizServer.game.controller.response;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import kr.toy.lyricsQuizServer.game.domain.Game;
import kr.toy.lyricsQuizServer.game.domain.GameStatus;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.domain.dto.UserInfo;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private final Boolean isSecretRoom;

    private final String password;

    private List<UserInfo> userList;

    @Builder
    public GameRoom(Long gameRoomSeq, String roomName, LocalDateTime startedAt, String managerName,
                    String topic, Integer attendeeLimit, Integer attendeeCount, GameStatus gameStatus,
                    Boolean isSecretRoom, String password, List<UserInfo> userList) {
        this.gameRoomSeq = gameRoomSeq;
        this.roomName = roomName;
        this.startedAt = startedAt;
        this.managerName = managerName;
        this.topic = topic;
        this.attendeeLimit = attendeeLimit;
        this.attendeeCount = attendeeCount;
        this.gameStatus = gameStatus;
        this.isSecretRoom = isSecretRoom;
        this.password = password;
        this.userList = userList;
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
                .isSecretRoom(game.getIsSecretRoom())
                .password(game.getPassword())
                .userList(userInfoListFrom(game))
                .build();
    }

    public Boolean isCapacityExceeded(){
        return this.attendeeLimit <= this.attendeeCount;
    }

    public Boolean isReady(){
        return this.gameStatus == GameStatus.READY;
    }

    public Boolean passwordCheck(String password){
        if (!this.isSecretRoom) {
            return true;
        } else if (this.isSecretRoom && StringUtils.hasText(password) && this.password.equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean isRoomOpen(String password){ // FIXME 변수명 변경
        if (!isCapacityExceeded() && passwordCheck(password) && isReady()) {
            return true;
        } else {
            return false;
        }
    }

    public static List<UserInfo> userInfoListFrom(Game game){
        List<UserInfo> userInfoList = new ArrayList<>(game.getAttendeeLimit());
        userInfoList.add(UserInfo.from(game.getManager(), game.getGameRoomSeq(), null));
        return userInfoList;
    }

    public void enter(UserInfo userInfo){
        this.userList.add(userInfo);
    }

    public boolean isEntered(User user){
        if (this.userList.contains(user)) {
            return true;
        } else {
            return false;
        }
    }
}
