package kr.toy.lyricsQuizServer.game.infrastructure;

import kr.toy.lyricsQuizServer.game.domain.Game;
import kr.toy.lyricsQuizServer.game.service.port.GameRepository;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class GameRepositoryImpl implements GameRepository {

    private final GameJpaRepository gameJpaRepository;
    private final UserRepository userRepository;
    @Override
    public List<Game> findAll() {
        return gameJpaRepository.findAll().stream()
                .map(gameEntity -> gameEntity.toModel())
                .collect(Collectors.toList());
    }

    @Override
    public List<Game> findAllByRoomNameOrManager(String topic) {
        User user = userRepository.get
        return gameJpaRepository.findAllByRoomNameOrManager(topic)
                .orElseThrow(() -> new EntityNotFoundException())
                .toModel();
    }

}
