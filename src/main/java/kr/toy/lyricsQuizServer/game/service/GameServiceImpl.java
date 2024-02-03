package kr.toy.lyricsQuizServer.game.service;


import kr.toy.lyricsQuizServer.chat.controller.port.ChatService;
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
import kr.toy.lyricsQuizServer.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
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
    public void enter(Long gameRoomSeq) {
//        String topicName = String.valueOf(gameRoomSeq);
//        ChannelTopic topic = topics.get(topicName);
//        if (topic == null) {
//            topic = new ChannelTopic(topicName);
//            redisMessageListener.addMessageListener(redisSubscriber, topic);
//            topics.put(topicName, topic);
//        }
    }

    @Override
    public void 같이_할_사람_검색() {

    }

    public void ready(Long gameRoomSeq, User user){

        gameRepository.findById(gameRoomSeq);

        // 준비완료 상태를 보내야될듯?
    }

    public void validate(){

    }

    public void baseValidate(){ //FIXME 변수명 변경
        // 방장이 존재하는가.( 메시지 보낼 떄도 적용)
        // -> StompHandler에서 검증하고 성능상 문제가 있다면 삭제해도 될듯.
    }

    public void entryValidate(Long gameRoomSeq, User user){
        Game game = gameRepository.findById(gameRoomSeq);
        baseValidate();
        game.join(); // 입장 시 로직이므로 Redis에 추가해주기. << 기존 Enter메서드와 통합필요
    }

    public void readyValidate(){
        //1. Game에 접속한 유저인가.

    }

    public void startValidate() {
        //방장 이외에 인원이 존재하는가. 시작 버튼을 누르는게 방장인가. 방장 이외의 인원이 전부 준비완료 상태인가.
    }

    //FIXME 초대기능 추가
    //FIXME Game 생성 용 QuizSummary List 메서드 추가.
}
