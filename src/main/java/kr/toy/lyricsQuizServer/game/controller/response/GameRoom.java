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
import java.util.NoSuchElementException;
import java.util.Optional;

@Getter
public class GameRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long gameRoomSeq;

    private String roomName;

    private LocalDateTime startedAt;

    private String hostName;

    private Long hostSeq;

    private String topic;

    private Integer attendeeLimit;

    private GameStatus gameStatus;

    private Boolean isSecretRoom;

    private String password;

    private List<UserInfo> userList;

    @Builder
    public GameRoom(Long gameRoomSeq, String roomName, LocalDateTime startedAt, String hostName, Long hostSeq,
                    String topic, Integer attendeeLimit, GameStatus gameStatus,
                    Boolean isSecretRoom, String password, List<UserInfo> userList) {
        this.gameRoomSeq = gameRoomSeq;
        this.roomName = roomName;
        this.startedAt = startedAt;
        this.hostName = hostName;
        this.hostSeq = hostSeq;
        this.topic = topic;
        this.attendeeLimit = attendeeLimit;
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
                .hostName(game.getHost().getNickName())
                .hostSeq(game.getHost().getUserSeq())
                .attendeeLimit(game.getAttendeeLimit())
                .gameStatus(game.getGameStatus())
                .isSecretRoom(game.getIsSecretRoom())
                .password(game.getPassword())
                .userList(userInfoListFrom(game))
                .build();
    }

    public Boolean isCapacityExceeded(){
        return this.attendeeLimit <= this.userList.size();
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
        userInfoList.add(UserInfo.from(game.getHost(), game.getGameRoomSeq(), null));
        return userInfoList;
    }

    public void enter(UserInfo userInfo){
        this.userList.add(userInfo);
    }

    public boolean isEntered(UserInfo user){
        if (this.userList.contains(user)) {
            return true;
        } else {
            return false;
        }
    }
    public void isUserPresent(UserInfo user){
        if (!this.userList.contains(user)) {
            throw new NoSuchElementException("유저가 존재하지 않습니다.");
        }
    }

    public void isHostPresent(UserInfo host){
        if (!this.userList.contains(host) || !isHost(host)) {
            throw new NoSuchElementException("호스트가 존재하지 않습니다.");
        }
    }

    public void isEveryoneReady(UserInfo host){
        if (getUserList().stream().filter(user -> !user.isReady() && !user.equals(host)).findAny().isPresent()) {
            throw new IllegalStateException("준비완료 되지 않은 참여자가 있습니다.");
        }
    }

    public void ready(UserInfo user) {
        if (isHost(user)) {
            throw new IllegalArgumentException("호스트는 준비완료 할 수 없습니다.");
        }
        findUser(user)
                .ifPresent(data -> data.ready());
    }

    private boolean isHost(UserInfo user) {
        return user.getUserSeq() == hostSeq;
    }

    private Optional<UserInfo> findUser(UserInfo user) {
        return userList.stream()
                .filter(data -> data.equals(user))
                .findFirst();
    }

    public void checkPlayerCount(){
        if (userList.size()  <= 1) {
            throw new IllegalStateException("게임 시작 인원이 너무 적습니다.");
        }
    }

    public void start(LocalDateTime startedAt){
        this.gameStatus = GameStatus.IN_PROGRESS;
        this.startedAt = startedAt;
    }

    public void removeUser(UserInfo userInfo){
        this.userList.remove(userInfo);
    }

    public boolean roomEmpty(){
        return this.getUserList().isEmpty();
    }


}
