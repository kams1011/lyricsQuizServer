package kr.toy.lyricsQuizServer.user.domain.dto;

import kr.toy.lyricsQuizServer.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInfo {

    Long userSeq;

    String nickName;

    Long gameRoomSeq; // FIXME Boolean으로 변경하는게 나으면 Boolean으로 변경.

    @Builder
    public UserInfo(Long userSeq, String nickName, Long gameRoomSeq){
        this.userSeq = userSeq;
        this.nickName = nickName;
        this.gameRoomSeq = gameRoomSeq;
    }


    public static UserInfo from(User user){
        UserInfo userInfo = UserInfo.builder()
                .userSeq(user.getUserSeq())
                .nickName(user.getNickName())
                .build();
        return userInfo;
    }

    public void enter(Long gameRoomSeq){
        this.gameRoomSeq = gameRoomSeq;
    }

    public void exit(){
        this.gameRoomSeq = null;
    }

    public void isGaming(){
        return;
    }
}
