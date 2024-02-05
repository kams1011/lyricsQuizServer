package kr.toy.lyricsQuizServer.game.service;


import kr.toy.lyricsQuizServer.chat.controller.port.ChatService;
import kr.toy.lyricsQuizServer.game.controller.response.UserInvitationInfo;
import kr.toy.lyricsQuizServer.memory.Redis.RedisUtil;
import kr.toy.lyricsQuizServer.game.controller.port.GameService;
import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import kr.toy.lyricsQuizServer.game.domain.Game;
import kr.toy.lyricsQuizServer.game.domain.dto.GameCreate;
import kr.toy.lyricsQuizServer.game.domain.dto.GamePassword;
import kr.toy.lyricsQuizServer.game.service.port.GameRepository;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.service.QuizRepository;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.domain.dto.UserInfo;
import kr.toy.lyricsQuizServer.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    private final QuizRepository quizRepository;

    private final UserRepository userRepository;

    private final RedisUtil redisUtil;


    @Override
    public List<Game> getGameList(Pageable pageable) {
        return gameRepository.findAll(pageable);
    }

    @Override
    public List<Game> getGameListByWord(String word, Pageable pageable) {
        return gameRepository.findAllByRoomNameOrManagerName(word, pageable);
    }

    @Override
    public Game create(User user, GameCreate gameCreate) {
        //FIXME 다른 방에 참여하고 있는가 여부 (방장여부와 관계없이)
        Quiz quiz = quizRepository.getById(gameCreate.getQuizSeq());
        Game game = Game.from(gameCreate, user, quiz).create(LocalDateTime.now());
        game = gameRepository.save(user, game, quiz);
        saveGameInRedis(GameRoom.from(game));
        return game;
    }

    @Override
    public void checkPassword(GamePassword gamePassword) {
        Game game = gameRepository.findById(gamePassword.getRoomId());
        if (!game.getIsSecretRoom()){
            throw new IllegalStateException("비밀 방이 아닙니다.");
        } else if (!game.getPassword().equals(gamePassword.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Override
    public void enter(Long gameRoomSeq, String password, User user) {
        GameRoom gameRoom = getGameRoom(gameRoomSeq);
        UserInfo userInfo = findUserInfo(user);
        if (isRoomEnterAllowed(gameRoom, password, userInfo)) {
            gameRoom.enter(userInfo);
            saveGameInRedis(gameRoom);
            addAttendeeCount(gameRoomSeq);
        }
    }

    @Override
    public void ready(Long gameRoomSeq, User user) {
        GameRoom gameRoom = getGameRoom(gameRoomSeq);
        UserInfo userInfo = findUserInfo(user);
        gameRoom.isUserPresent(userInfo);
        gameRoom.ready(userInfo);
        saveGameInRedis(gameRoom);
    }

    @Override
    public void start(Long gameRoomSeq, User host) {
        GameRoom gameRoom = getGameRoom(gameRoomSeq);
        UserInfo hostInfo = findUserInfo(host);

        Game game = gameRepository.findById(gameRoom.getGameRoomSeq());
        game.start(LocalDateTime.now());
        gameRoom.isEveryoneReady(hostInfo); // 방장 이외의 인원이 전부 준비완료 상태인가.
        gameRoom.isHostPresent(hostInfo); // 시작 버튼을 누르는게 방장인가, 방장이 존재하는가.
        gameRoom.checkPlayerCount();
        gameRoom.start(LocalDateTime.now());
        saveGameInRedis(gameRoom);
        gameRepository.save(game.getHost(), game, game.getQuiz());
    }

    @Override
    public void invite(Long gameRoomSeq, User host, Long invitedUserSeq){ //FIXME 초대기능 추가
        GameRoom gameRoom = getGameRoom(gameRoomSeq);
        if (!gameRoom.isHost(UserInfo.from(host, gameRoomSeq, null))){
            throw new RuntimeException("초대는 호스트만 가능합니다."); // 내가 그 방 방장인지 여부를 확인한다.
        }
        // 어떤 방에 누구를 초대한다.
        // 초대 여부를 저장하지 않으니 초대 수락은 삭제하고 alert창에서 확인을 누르면 바로 입장할 수 있도록 변경.
    }

    @Override
    public void exit(Long gameRoomSeq, User user) {
        GameRoom gameRoom = getGameRoom(gameRoomSeq);
        UserInfo userInfo = findUserInfo(user);
        gameRoom.removeUser(userInfo);
        if (gameRoom.roomEmpty()) {
            roomClose(gameRoomSeq);
        } else {
            saveGameInRedis(gameRoom);
        }
    }

    @Override
    public void allowInvitation(User user, boolean isAllowed) {
        if (isAllowed){
            redisUtil.putInvitePendingInfoInRedis(user.getUserSeq());
        } else {
            redisUtil.deleteInvitedPendingInfoInRedis(user.getUserSeq());
        }

    }

    @Override
    public void getInvitableUsers() {
        List<Long> userSeqList = redisUtil.getAllInvitePendingInfoFromRedis();
        List<UserInvitationInfo> userInvitationInfoList = userSeqList.stream()
                .map(data -> userRepository.getById(data))
                .map(data -> UserInvitationInfo.from(data))
                .collect(Collectors.toList());
    }

    @Override
    public GameRoom getGameRoom(Long gameRoomSeq){
        GameRoom gameRoom = redisUtil.getGameRoomFromRedis(gameRoomSeq);
        return gameRoom;
    }

    @Override
    public GameRoom getGameRoomOrCreate(Long gameRoomSeq) {
        GameRoom gameRoom = getGameRoom(gameRoomSeq);
        if (gameRoom == null) { //FIXME InvalidDataAccessApiUsageException 정리하기.
            gameRoom = GameRoom.from(gameRepository.findById(gameRoomSeq));
            saveGameInRedis(gameRoom);
        }
        return gameRoom;
    }

    @Override
    public UserInfo findUserInfo(User user){
        UserInfo userInfo = redisUtil.getUserInfoFromRedis(user.getUserSeq());
        return userInfo;
    }

    @Override
    public UserInfo findUserInfoOrCreate(User user, Long gameRoomSeq){
        UserInfo userInfo = findUserInfo(user);
        if (userInfo == null) {
            userInfo = UserInfo.from(user, gameRoomSeq, null);
            putUserInfo(userInfo);
        }

        return userInfo;
    }

    @Override
    public UserInfo putUserInfo(UserInfo userInfo){
        redisUtil.putUserInfoInRedis(userInfo.getUserSeq(), userInfo);
        return userInfo;
    }


    public void addAttendeeCount(Long gameRoomSeq){
        Game game = gameRepository.findById(gameRoomSeq);
        game.join();
        gameRepository.save(game.getHost(),game, game.getQuiz());
    }

    public boolean isRoomEnterAllowed(GameRoom gameRoom, String password, UserInfo userInfo) {
        if (gameRoom == null) {
            throw new IllegalStateException("존재하지 않는 방입니다.");
        }
        if (!gameRoom.isRoomOpen(password)) {
            throw new IllegalArgumentException("비밀번호가 맞지 않습니다.");
        }
        if (gameRoom.isEntered(userInfo)) {
            throw new IllegalStateException("이미 입장한 방입니다.");
        }
        if (userInfo.inGame()) {
            throw new IllegalStateException("다른 방에 입장한 유저입니다.");
        }

        //FIXME 방장이 존재하지 않으면 방을 종료하는 로직.
        //FIXME 모든 인원이 준비를 마쳤는데 1분 내에 게임을 실행하지 않으면 방을 종료하는 로직.
        return true;
    }

    public GameRoom saveGameInRedis(GameRoom gameRoom) {
        redisUtil.putGameRoomInRedis(gameRoom.getGameRoomSeq(), gameRoom);
        return gameRoom;
    }

    public void roomClose(Long roomSeq){
        Game game = gameRepository.findById(roomSeq);
        game.exit();
        gameRepository.save(game.getHost(), game, game.getQuiz());
        redisUtil.deleteGameRoomInRedis(roomSeq);
    }


    //FIXME Game 생성 용 QuizSummary List 메서드 추가.

}
