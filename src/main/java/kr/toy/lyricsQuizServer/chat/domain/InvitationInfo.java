package kr.toy.lyricsQuizServer.chat.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class InvitationInfo {


    Long hostSeq;

    String hostNickName;

    Long roomSeq;

    String roomName;

    Long invitedUserSeq;

    String message;




    @Builder
    public InvitationInfo(Long hostSeq, String hostNickName, Long roomSeq,
                          String roomName, Long invitedUserSeq, String message){
        this.hostSeq = hostSeq;
        this.hostNickName = hostNickName;
        this.roomSeq = roomSeq;
        this.roomName = roomName;
        this.invitedUserSeq = invitedUserSeq;
        this.message = message;
    }


    public static InvitationInfo init(Long hostSeq, String hostNickName, Long roomSeq,
                                      String roomName, Long invitedUserSeq){
        InvitationInfo invitationInfo = InvitationInfo.builder()
                .hostSeq(hostSeq)
                .hostNickName(hostNickName)
                .roomSeq(roomSeq)
                .roomName(roomName)
                .invitedUserSeq(invitedUserSeq)
                .message(hostNickName + "님이 " + roomName + "방에 초대하셨습니다.")
                .build();
        return invitationInfo;
    }
}
