package kr.toy.lyricsQuizServer.game.service;


import kr.toy.lyricsQuizServer.chat.domain.InvitationInfo;
import kr.toy.lyricsQuizServer.common.domain.ErrorCode;
import kr.toy.lyricsQuizServer.config.CustomError.RoomAccessError;
import kr.toy.lyricsQuizServer.game.controller.response.UserInvitationInfo;
import kr.toy.lyricsQuizServer.memory.Redis.RedisCategory;
import kr.toy.lyricsQuizServer.memory.Redis.RedisUtil;
import kr.toy.lyricsQuizServer.game.controller.port.GameService;
import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import kr.toy.lyricsQuizServer.game.domain.Game;
import kr.toy.lyricsQuizServer.game.domain.dto.GameCreate;
import kr.toy.lyricsQuizServer.game.domain.dto.GamePassword;
import kr.toy.lyricsQuizServer.game.service.port.GameRepository;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.domain.QuizContent;
import kr.toy.lyricsQuizServer.quiz.domain.dto.StreamingInfo;
import kr.toy.lyricsQuizServer.quiz.service.QuizContentRepository;
import kr.toy.lyricsQuizServer.quiz.service.QuizRepository;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.domain.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final QuizRepository quizRepository;
    private final QuizContentRepository quizContentRepository;
    private final RedisUtil redisUtil;


    @Override
    public PageImpl<Game> getGameList(Pageable pageable) {
        return gameRepository.findAll(pageable);
    }

    @Override
    public PageImpl<GameRoom> getGameListByWord(String word, Pageable pageable) {
        PageImpl<Game> pages = gameRepository.findAllByRoomNameOrManagerName(word, pageable);

        List<GameRoom> gameRooms = pages.stream().map(data -> redisUtil.getGameRoomFromRedis(data.getGameRoomSeq())).collect(Collectors.toList());
        return new PageImpl<>(gameRooms, pageable, pages.getTotalElements());
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
        GameRoom gameRoom = getGameRoomOrCreate(gameRoomSeq);
        UserInfo userInfo = findUserInfoOrCreate(user, gameRoomSeq); // Redis 서버가 꺼졌을 때를 방지
        if (isRoomEnterAllowed(gameRoom, password, userInfo)) {
            gameRoom.enterUser(userInfo);
            userInfo.enterGameRoom(gameRoomSeq);
            saveGameInRedis(gameRoom);
            addAttendeeCount(gameRoomSeq);
        }
    }

    @Override
    public void ready(Long gameRoomSeq, User user) {
        GameRoom gameRoom = getGameRoom(gameRoomSeq);
        UserInfo userInfo = findUserInfo(user);
        if (!gameRoom.isUserPresent(userInfo)) {
            throw new NoSuchElementException("유저가 존재하지 않습니다.");
        }
        gameRoom.ready(userInfo);
        saveGameInRedis(gameRoom);
    }

    @Override
    public void start(Long gameRoomSeq, User host) {
        GameRoom gameRoom = getGameRoom(gameRoomSeq);
        UserInfo hostInfo = findUserInfo(host);
        Game game = gameRepository.findById(gameRoom.getGameRoomSeq());
        game.start(LocalDateTime.now());
        if (!gameRoom.isEveryoneReady(hostInfo)) {
            throw new IllegalStateException("준비완료 되지 않은 참여자가 있습니다."); // 방장 이외의 인원이 전부 준비완료 상태인가.
        }
        gameRoom.isHostPresent(hostInfo); // 시작 버튼을 누르는게 방장인가, 방장이 존재하는가.
        gameRoom.checkPlayerCount();
        gameRoom.start(LocalDateTime.now());
        saveGameInRedis(gameRoom);
        
        gameRepository.save(game.getHost(), game, game.getQuiz());

    }

    @Override
    public void invite(Long gameRoomSeq, User host, Long invitedUserSeq){
        GameRoom gameRoom = getGameRoom(gameRoomSeq);
        if (!gameRoom.isHost(UserInfo.from(host, gameRoomSeq, null))){
            throw new RuntimeException("초대는 호스트만 가능합니다."); // 내가 그 방 방장인지 여부를 확인한다.
        }
        UserInfo userInfo = redisUtil.getUserInfoFromRedis(invitedUserSeq);
        if (isRoomEnterAllowed(gameRoom, null, userInfo)) {
            String hostName = host.getNickName();
            redisUtil.invite(RedisCategory.INVITE_PENDING, InvitationInfo.init(host.getUserSeq(), hostName, gameRoomSeq,
                    gameRoom.getRoomName(), invitedUserSeq));
        }
    }

    @Override
    public void exit(Long gameRoomSeq, User user) {
        GameRoom gameRoom = getGameRoom(gameRoomSeq);
        UserInfo userInfo = findUserInfo(user);
        gameRoom.removeUser(userInfo);
        if (gameRoom.roomEmpty()) {
            roomClose(gameRoom);
        } else {
            saveGameInRedis(gameRoom);
        }
    }

    @Override
    public UserInvitationInfo allowInvitation(User user, boolean isAllowed) {
        UserInvitationInfo userInvitationInfo = UserInvitationInfo.from(user);
        if (isAllowed){
            redisUtil.putInvitePendingInfoInRedis(userInvitationInfo);
        } else {
            redisUtil.deleteInvitedPendingInfoInRedis(userInvitationInfo);
        }
        return userInvitationInfo;
    }

    @Override
    public boolean getMyInvitationInfo(User user) {
        Long index = redisUtil.getInvitePendingInfoFromRedis(UserInvitationInfo.from(user));
        if (index == -1) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isHost(Long roomId, User user) {
        GameRoom gameRoom = getGameRoom(roomId);
        return gameRoom.isHost(UserInfo.from(user, roomId, null));
    }

    @Override
    public List<UserInvitationInfo> getInvitableUsers(Pageable pageable) { //FIXME 페이징 추가 + 방장은 제외하고 가져오기
        return redisUtil.getAllInvitePendingInfoFromRedis();
    }

    @Override
    public GameRoom getGameRoom(Long gameRoomSeq){
        GameRoom gameRoom = redisUtil.getGameRoomFromRedis(gameRoomSeq); // FIXME Redis 데이터 정합성 해결 필요.
        return gameRoom;
    }

    @Override
    public GameRoom getGameRoomOrCreate(Long gameRoomSeq) {
        return Optional.ofNullable(getGameRoom(gameRoomSeq))
                .orElseGet(() -> { //FIXME InvalidDataAccessApiUsageException 정리하기.
                    GameRoom gameRoom = GameRoom.from(gameRepository.findById(gameRoomSeq));
                    if (!gameRoom.isReady()) {
                        throw new IllegalStateException("방이 준비상태가 아닙니다.");
                    }
                    saveGameInRedis(gameRoom);
                    return gameRoom;
                });
    }

    @Override
    public UserInfo findUserInfo(User user){
        UserInfo userInfo = redisUtil.getUserInfoFromRedis(user.getUserSeq());
        return userInfo;
    }

    @Override
    public UserInfo findUserInfoOrCreate(User user, Long gameRoomSeq){
        return Optional.ofNullable(findUserInfo(user))
                .orElseGet(() -> {
                    UserInfo userInfo = UserInfo.from(user, gameRoomSeq, null);
                    putUserInfo(userInfo);
                    return userInfo;
                });
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
            throw new RoomAccessError(ErrorCode.ROOM_NOT_FOUND);
        }
        if (!gameRoom.passwordCheck(password)) {
            throw new RoomAccessError(ErrorCode.ROOM_LOCK);
        }
        if (!gameRoom.isWithinCapacity()) {
            throw new RoomAccessError(ErrorCode.ROOM_FULL);
        }
        if (!gameRoom.isReady()) {
            throw new RoomAccessError(ErrorCode.ROOM_ALREADY_START);
        }
        if (gameRoom.isEntered(userInfo)) {
            throw new RoomAccessError(ErrorCode.USER_ALREADY_JOINED);
        } else if (userInfo.inGame() && !gameRoom.isUserPresent(userInfo)) {
            throw new RoomAccessError(ErrorCode.USER_ALREADY_JOINED);
        }
        //FIXME 방장이 존재하지 않으면 방을 종료하는 로직.
        //FIXME 모든 인원이 준비를 마쳤는데 1분 내에 게임을 실행하지 않으면 방을 종료하는 로직.
        return true;
    }

    @Override
    public void setUserSessionId(UserInfo userinfo, GameRoom gameRoom, String sessionId){
        gameRoom.getUserList()
                .stream()
                .filter(data -> data.getUserSeq().equals(userinfo.getUserSeq()))
                .findFirst()
                .ifPresent(data -> data.setSessionId(sessionId));
        saveGameInRedis(gameRoom);
    }

    @Override
    public void streamingComplete(Long roomId, User player) {
        GameRoom gameRoom = redisUtil.getGameRoomFromRedis(roomId);
        gameRoom.streamingComplete(player.getUserSeq());
        redisUtil.putGameRoomInRedis(roomId, gameRoom);
    }

    @Override
    public boolean checkAnswer(Long roomId, String answer) {
        Game game = gameRepository.findById(roomId);
        Quiz quiz = game.getQuiz();
        return quiz.isCorrect(answer);
    }

    @Override
    @Scheduled(cron = "0 */2 * * * *")
    public void finishTimedOutGames() {
        List<GameRoom> gameRoomList = findTimedOutGames();
        System.out.println("Cron START");
        try {
            for (int i=0; i<gameRoomList.size(); i++) {
                roomClose(gameRoomList.get(i));
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("cron 작업 중 에러가 발생했습니다. " + e.getMessage());
        }
    }


    public List<GameRoom> findTimedOutGames(){
        List<GameRoom> gameRoomList = redisUtil.getAllGameRoomFromRedis();

        gameRoomList.stream()
                .filter(data -> data.isExpiredByCreatedAt(LocalDateTime.now())
                || data.isExpiredByStartedAt(LocalDateTime.now()))
                .collect(Collectors.toList());

        return gameRoomList;
    }

    public GameRoom saveGameInRedis(GameRoom gameRoom) {
        redisUtil.putGameRoomInRedis(gameRoom.getGameRoomSeq(), gameRoom);
        return gameRoom;
    }

    @Transactional
    public void roomClose(GameRoom gameRoom) {
        Game game = gameRepository.findById(gameRoom.getGameRoomSeq());
        game.exit();
        gameRepository.save(game.getHost(), game, game.getQuiz());
        redisUtil.deleteGameRoomInRedis(gameRoom.getGameRoomSeq());
        deleteUserInfoInRoom(gameRoom);
    }

    public void deleteUserInfoInRoom(GameRoom gameRoom){
        gameRoom.getUserList().stream()
                .map(data -> data.exit())
                .forEach(data -> redisUtil.putUserInfoInRedis(data.getUserSeq(), data));
    }

    @Override
    public StreamingInfo getStreamingInfo(Long roomId) {
        Game game = gameRepository.findById(roomId);
        Quiz quiz = game.getQuiz();
        StreamingInfo streamingInfo = StreamingInfo.from(quiz, quiz.getQuizContent());
        // File upload시 S3에서 URL가져오는 로직.
        // File과 Quiz와 Game을 매핑하는 로직.
        // Quiz 정보로 Game에서 잘라오는 로직.
        return streamingInfo;
    }


    //FIXME Game 생성 용 QuizSummary List 메서드 추가.

}
