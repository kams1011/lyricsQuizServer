package kr.toy.lyricsQuizServer.game.service;


import kr.toy.lyricsQuizServer.game.controller.port.GameService;
import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import kr.toy.lyricsQuizServer.game.domain.Game;
import kr.toy.lyricsQuizServer.game.service.port.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    @Override
    public List<Game> getGameList(Pageable pageable) {
        return gameRepository.findAll(pageable);
    }

    @Override
    public List<Game> getGameListByWord(String word, Pageable pageable) {
        return gameRepository.findAllByRoomNameOrManagerName(word, pageable);
    }

    @Override
    public void 방_생성() {

    }

    @Override
    public void 같이_할_사람_검색() {

    }
}
