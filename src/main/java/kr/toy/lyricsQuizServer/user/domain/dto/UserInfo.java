package kr.toy.lyricsQuizServer.user.domain.dto;

import kr.toy.lyricsQuizServer.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    Long userSeq;

    String nickName;

    Long gameRoomSeq; // FIXME Boolean으로 변경하는게 나으면 Boolean으로 변경.

    String sessionId;

    @Builder
    public UserInfo(Long userSeq, String nickName, Long gameRoomSeq, String sessionId){
        this.userSeq = userSeq;
        this.nickName = nickName;
        this.gameRoomSeq = gameRoomSeq;
        this.sessionId = sessionId;
    }

    public static UserInfo from(User user, Long gameRoomSeq, String sessionId){
        UserInfo userInfo = UserInfo.builder()
                .userSeq(user.getUserSeq())
                .nickName(user.getNickName())
                .gameRoomSeq(gameRoomSeq)
                .sessionId(sessionId)
                .build();
        return userInfo;
    }

//    public static UserInfo from(User user, Long gameRoomSeq){
//        UserInfo userInfo = UserInfo.builder()
//                .userSeq(user.getUserSeq())
//                .nickName(user.getNickName())
//                .gameRoomSeq(gameRoomSeq)
//                .build();
//        return userInfo;
//    }

    public void enter(Long gameRoomSeq){
        this.gameRoomSeq = gameRoomSeq;
    }

    public void exit(){
        this.gameRoomSeq = null;
    }

    public void inGame(){
        return;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userSeq=" + userSeq +
                ", nickName='" + nickName + '\'' +
                ", gameRoomSeq=" + gameRoomSeq +
                '}';
    }
}
