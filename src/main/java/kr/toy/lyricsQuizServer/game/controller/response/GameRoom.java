package kr.toy.lyricsQuizServer.game.controller.response;

import kr.toy.lyricsQuizServer.game.domain.Game;
import kr.toy.lyricsQuizServer.game.domain.GameStatus;
import kr.toy.lyricsQuizServer.user.domain.dto.UserInfo;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Getter
public class GameRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long gameRoomSeq;

    private String roomName;

    private LocalDateTime createdAt;

    private LocalDateTime startedAt;

    private String hostName;

    private final Long hostSeq;

    private String topic;

    private Integer attendeeLimit;

    private GameStatus gameStatus;

    private Boolean isSecretRoom;

    private String password;

    private List<UserInfo> userList;

    private Set<Long> streamingCompleteUserSet;

    private final int START_EXPIRED_MINUTE = 30;
    private final int CREATE_EXPIRED_MINUTE = 60;


    @Builder
    public GameRoom(Long gameRoomSeq, String roomName, LocalDateTime createdAt, LocalDateTime startedAt, String hostName, Long hostSeq,
                    String topic, Integer attendeeLimit, GameStatus gameStatus,
                    Boolean isSecretRoom, String password, List<UserInfo> userList, Set<Long> streamingCompleteUserSet) {
        this.gameRoomSeq = gameRoomSeq;
        this.roomName = roomName;
        this.createdAt = createdAt;
        this.startedAt = startedAt;
        this.hostName = hostName;
        this.hostSeq = hostSeq;
        this.topic = topic;
        this.attendeeLimit = attendeeLimit;
        this.gameStatus = gameStatus;
        this.isSecretRoom = isSecretRoom;
        this.password = password;
        this.userList = userList;
        this.streamingCompleteUserSet = streamingCompleteUserSet;
    }

    public static GameRoom from(Game game){
        return GameRoom.builder()
                .gameRoomSeq(game.getGameRoomSeq())
                .roomName(game.getRoomName())
                .topic(game.getQuiz().getTitle()) //FIXME Query 확인하기
                .createdAt(game.getCreatedAt())
                .startedAt(game.getStartedAt())
                .hostName(game.getHost().getNickName())
                .hostSeq(game.getHost().getUserSeq())
                .attendeeLimit(game.getAttendeeLimit())
                .gameStatus(game.getGameStatus())
                .isSecretRoom(game.getIsSecretRoom())
                .password(game.getPassword())
                .userList(userInfoListInitFrom(game))
                .streamingCompleteUserSet(new HashSet<>())
                .build();
    }

    public Boolean isWithinCapacity(){
        return this.attendeeLimit >= this.userList.size();
    }

    public Boolean isReady(){
        return this.gameStatus == GameStatus.READY;
    }

    public Boolean passwordCheck(String password){ // FIXME Service로 뺴기
        if (!this.isSecretRoom) {
            return true;
        } else if (this.isSecretRoom && StringUtils.hasText(password) && this.password.equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    public static List<UserInfo> userInfoListInitFrom(Game game){
        List<UserInfo> userInfoList = new ArrayList<>(game.getAttendeeLimit());
        userInfoList.add(UserInfo.from(game.getHost(), game.getGameRoomSeq(), null));
        return userInfoList;
    }

    public void enterUser(UserInfo userInfo){
        this.userList.add(userInfo);
    }

    public boolean isEntered(UserInfo user){
        if (this.userList.contains(user)) {
            return true;
        } else {
            return false;
        }
    }
    public boolean isUserPresent(UserInfo user){
        boolean isUserPresent = this.userList.stream()
                .filter(data -> data.getUserSeq().equals(user.getUserSeq()))
                .findFirst().isPresent();

        return isUserPresent;
    }

    public void isHostPresent(UserInfo host){
        boolean attendeeList = this.userList.stream()
                .filter(data -> data.getUserSeq().equals(hostSeq))
                .findFirst()
                .isPresent();
        if (!attendeeList || !isHost(host)) {
            throw new NoSuchElementException("호스트가 존재하지 않습니다.");
        }
    }

    public boolean isEveryoneReady(){
        if (getUserList().stream().filter(user -> !user.isReady() && !isHost(user))
                .findAny().isPresent()) {
            return false;
        }
        return true;
    }

    public void ready(UserInfo user) {
        if (isHost(user)) {
            throw new IllegalArgumentException("호스트는 준비완료 할 수 없습니다.");
        }
        findUser(user.getUserSeq()).ready();
    }

    public boolean isHost(UserInfo user) {
        return user.getUserSeq().equals(hostSeq);
    }

    private UserInfo findUser(Long userSeq) {
        return userList.stream()
                .filter(data -> data.getUserSeq().equals(userSeq))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    public void checkPlayerCount(){
        //FIXME 배포 시 해당 부분 수정해서 배포하기
//        if (userList.size()  <= 1) {
//            throw new IllegalStateException("게임 시작 인원이 너무 적습니다.");
//        }
    }

    public void streamingComplete(Long userSeq){
        if (!isUserPresent(findUser(userSeq))) {
            throw new NoSuchElementException("유저가 존재하지 않습니다.");
        }
        if (!isGameStart()) {
            throw new IllegalStateException("게임이 시작되지 않았습니다.");
        }

        this.streamingCompleteUserSet.add(userSeq);
    }

    public boolean isGameStart(){
        return this.gameStatus == GameStatus.IN_PROGRESS;
    }

    public void streamingFail(Long userSeq){
        this.streamingCompleteUserSet.remove(userSeq);
    }

    public boolean isAllMemberStreamingComplete(){
         return this.userList.stream()
                .allMatch(data -> this.streamingCompleteUserSet.contains(data.getUserSeq()));
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

    public boolean isExpiredByCreatedAt(LocalDateTime now){
        return this.createdAt.isBefore(now.minusMinutes(CREATE_EXPIRED_MINUTE));
    }

    public boolean isExpiredByStartedAt(LocalDateTime now){
        if (this.startedAt == null) {
            return false;
        }
        return this.startedAt.isBefore(now.minusMinutes(START_EXPIRED_MINUTE));
    }

    @Override
    public String toString() {
        return "GameRoom{" +
                "gameRoomSeq=" + gameRoomSeq +
                ", roomName='" + roomName + '\'' +
                ", startedAt=" + startedAt +
                ", hostName='" + hostName + '\'' +
                ", hostSeq=" + hostSeq +
                ", topic='" + topic + '\'' +
                ", attendeeLimit=" + attendeeLimit +
                ", gameStatus=" + gameStatus +
                ", isSecretRoom=" + isSecretRoom +
                ", password='" + password + '\'' +
                ", userList=" + userList +
                '}';
    }
}
