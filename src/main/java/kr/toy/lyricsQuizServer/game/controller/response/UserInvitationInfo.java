package kr.toy.lyricsQuizServer.game.controller.response;

import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.domain.dto.UserInfo;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserInvitationInfo implements Serializable {

    Long userSeq;

    String nickName;


    @Builder
    public UserInvitationInfo(Long userSeq ,String nickName){
        this.userSeq = userSeq;
        this.nickName = nickName;
    }

    public static UserInvitationInfo from(User user){
        UserInvitationInfo userInvitationInfo = UserInvitationInfo.builder()
                .userSeq(user.getUserSeq())
                .nickName(user.getNickName())
                .build();

        return userInvitationInfo;
    }
}
