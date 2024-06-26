package kr.toy.lyricsQuizServer.game.controller.port;

import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import kr.toy.lyricsQuizServer.game.controller.response.UserInvitationInfo;
import kr.toy.lyricsQuizServer.game.domain.Game;
import kr.toy.lyricsQuizServer.game.domain.dto.GameCreate;

import kr.toy.lyricsQuizServer.game.domain.dto.GamePassword;
import kr.toy.lyricsQuizServer.game.domain.dto.StreamingInfo;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.domain.dto.UserInfo;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GameService {

    PageImpl<Game> getGameList(Pageable pageable); // 진행중인 상태 필요(시작 전, 진행중). 종료된 방은 안나오게.

    PageImpl<GameRoom> getGameListByWord(String word, Pageable pageable);

    Game create(User user, GameCreate gameCreate);

    void checkPassword(GamePassword gamePassword);

    void enter(Long gameRoomSeq, String password, User user);

    GameRoom getGameRoom(Long gameRoomSeq);

    GameRoom getGameRoomOrCreate(Long gameRoomSeq);

    UserInfo findUserInfo(User user);

    UserInfo findUserInfoOrCreate(User user, Long gameRoomSeq);

    UserInfo putUserInfo(UserInfo userInfo);

    List<UserInvitationInfo> getInvitableUsers(Pageable pageable); // FIXME Redis Paging

    void ready(Long gameRoomSeq, User user);

    void start(Long gameRoomSeq, User user);

    void invite(Long gameRoomSeq, User host, Long invitedUserSeq);

    void exit(Long gameRoomSeq, User user);

    UserInvitationInfo allowInvitation(User user, boolean isAllowed);

    boolean getMyInvitationInfo(User user);

    boolean isHost(Long roomId, User user);

    StreamingInfo getStreamingInfo(Long roomId);

    void setUserSessionId(UserInfo userinfo, GameRoom gameRoom, String sessionId);

    void streamingComplete(Long roomId, User player);

    boolean checkAnswer(Long roomId, String answer);

    void finishTimedOutGames();
}
