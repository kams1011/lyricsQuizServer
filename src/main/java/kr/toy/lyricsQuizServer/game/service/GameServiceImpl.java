package kr.toy.lyricsQuizServer.game.service;


import kr.toy.lyricsQuizServer.chat.controller.port.ChatService;
import kr.toy.lyricsQuizServer.chat.service.ChatServiceImpl;
import kr.toy.lyricsQuizServer.config.Redis.RedisUtil;
import kr.toy.lyricsQuizServer.game.controller.port.GameService;
import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import kr.toy.lyricsQuizServer.game.domain.Game;
import kr.toy.lyricsQuizServer.game.domain.dto.GameCreate;
import kr.toy.lyricsQuizServer.game.domain.dto.GamePassword;
import kr.toy.lyricsQuizServer.game.infrastructure.GameEntity;
import kr.toy.lyricsQuizServer.game.service.port.GameRepository;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.service.QuizRepository;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.domain.dto.UserInfo;
import kr.toy.lyricsQuizServer.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final QuizRepository quizRepository;
    private final ChatService chatService;


    @Override
    public List<Game> getGameList(Pageable pageable) {
        return gameRepository.findAll(pageable);
    }

    @Override
    public List<Game> getGameListByWord(String word, Pageable pageable) {
        return gameRepository.findAllByRoomNameOrManagerName(word, pageable);
    }

    @Override
    public Game create(User user, GameCreate gameCreate) { //FIXME 혼자는 시작할 수 없게 수정.
        Quiz quiz = quizRepository.getById(gameCreate.getQuizSeq());
        Game game = Game.from(gameCreate, user, quiz).create(LocalDateTime.now());
        game = gameRepository.save(user, game, quiz);
        chatService.createGameRoom(GameRoom.from(game));
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
        GameRoom gameRoom = chatService.getGameRoom(gameRoomSeq);
        UserInfo userInfo = chatService.findUserInfoOrCreate(user, gameRoomSeq);
        if (isRoomEnterAllowed(gameRoom, password, user, userInfo)) {
            gameRoom.enter(userInfo);
            chatService.createGameRoom(gameRoom);
            addAttendeeCount(gameRoomSeq);
        }
    } // FIXME ChatService에서 enter와 GameService에서의 enter가 동시에 존재하니 정리 필요.

    public void addAttendeeCount(Long gameRoomSeq){
        Game game = gameRepository.findById(gameRoomSeq);
        game.join();
        gameRepository.save(game.getHost(),game, game.getQuiz());
    }

    public boolean isRoomEnterAllowed(GameRoom gameRoom, String password, User user, UserInfo userInfo) {
        if (gameRoom == null) {
            throw new IllegalStateException("존재하지 않는 방입니다.");
        }
        if (!gameRoom.isRoomOpen(password)) {
            throw new IllegalArgumentException("비밀번호가 맞지 않습니다.");
        }
        if (gameRoom.isEntered(user)) {
            throw new IllegalStateException("이미 입장한 방입니다.");
        }
        if (userInfo.inGame()) {
            throw new IllegalStateException("다른 방에 입장한 유저입니다.");
        }

        //FIXME 방장이 존재하지 않으면 방을 종료하는 로직.
        //FIXME 모든 인원이 준비를 마쳤는데 1분 내에 게임을 실행하지 않으면 방을 종료하는 로직.
        return true;
    }


    @Override
    public void 같이_할_사람_검색() {

    }

    public void ready(Long gameRoomSeq, User user){
        gameRepository.findById(gameRoomSeq);
    }

    public void validate(){

    }
    // 준비완료 상태를 보내야될듯? 방장 이외의 인원이 전부 준비완료 상태인가.

    public void entryValidate(Long gameRoomSeq, User user){
        Game game = gameRepository.findById(gameRoomSeq);
        game.join(); // 방이 준비단계인가 + 입장 시 로직이므로 Redis에 추가해주기. << 기존 Enter메서드와 통합필요
    }

    public void readyValidate(User user){
        //1. Game에 접속한 유저인가.
    }

    public void startValidate(Long gameRoomSeq, User user) {
        Game game = gameRepository.findById(gameRoomSeq);
        //방장이 존재하는가.
        game.isHostCheck(user); // 시작 버튼을 누르는게 방장인가.
        game.checkPlayerCount(); // 방장 이외에 인원이 존재하는가.

        // 방장 이외의 인원이 전부 준비완료 상태인가.
    }

    //FIXME 초대기능 추가
    //FIXME Game 생성 용 QuizSummary List 메서드 추가.
}
