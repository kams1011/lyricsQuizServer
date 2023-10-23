package kr.toy.lyricsQuizServer.game.infrastructure;

import kr.toy.lyricsQuizServer.game.domain.Game;
import kr.toy.lyricsQuizServer.game.service.port.GameRepository;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class GameRepositoryImpl implements GameRepository {

    private final GameJpaRepository gameJpaRepository;
    private final UserRepository userRepository;

    @Override
    public List<Game> findAll(Pageable pageable) {
        return gameJpaRepository.findAll(pageable)
                .stream().map(GameEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Game> findAllByRoomNameOrManagerName(String word, Pageable pageable) {
        User user = userRepository.getByNickName(word);
        return gameJpaRepository.findAllByRoomNameOrManager(word, user, pageable)
                .stream().map(GameEntity::toModel)
                .collect(Collectors.toList());
    }

}
