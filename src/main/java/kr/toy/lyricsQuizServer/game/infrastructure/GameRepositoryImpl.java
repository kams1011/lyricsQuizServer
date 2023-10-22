package kr.toy.lyricsQuizServer.game.infrastructure;

import kr.toy.lyricsQuizServer.game.domain.Game;
import kr.toy.lyricsQuizServer.game.service.port.GameRepository;
import kr.toy.lyricsQuizServer.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class GameRepositoryImpl implements GameRepository {

    private final GameJpaRepository gameJpaRepository;

    @Override
    public List<Game> findAll() {
        return gameJpaRepository.findAll().stream()
                .map(gameEntity -> gameEntity.toModel())
                .collect(Collectors.toList());
    }

    @Override
    public Game findByTopic(String topic) {
        return gameJpaRepository.findByTopic(topic)
                .orElseThrow(() -> new EntityNotFoundException())
                .toModel();
    }

    @Override
    public Game findByManager(User manager) {
        return gameJpaRepository.findByManager(manager)
                .orElseThrow(() -> new EntityNotFoundException())
                .toModel();
    }
}
