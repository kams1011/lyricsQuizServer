package kr.toy.lyricsQuizServer.game.service;


import kr.toy.lyricsQuizServer.game.controller.port.GameService;
import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import kr.toy.lyricsQuizServer.game.domain.Game;
import kr.toy.lyricsQuizServer.game.infrastructure.GameEntity;
import kr.toy.lyricsQuizServer.game.service.port.GameRepository;
import kr.toy.lyricsQuizServer.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    @Override
    public List<Game> 로비_게임리스트_조회() {
        return gameRepository.findAll();
    }

    @Override
    public List<GameEntity> findAllByRoomNameOrManager(String roomName, User manager) {

    }

    @Override
    public void 방_생성() {

    }

    @Override
    public void 같이_할_사람_검색() {

    }
}
