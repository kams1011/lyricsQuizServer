package kr.toy.lyricsQuizServer.game.infrastructure;

import kr.toy.lyricsQuizServer.game.domain.Game;
import kr.toy.lyricsQuizServer.game.domain.dto.GameCreate;
import kr.toy.lyricsQuizServer.game.service.port.GameRepository;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.service.QuizRepository;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class GameRepositoryImpl implements GameRepository {

    private final GameJpaRepository gameJpaRepository;

    @Override
    public Game findById(Long gameSeq) {
        return gameJpaRepository.findById(gameSeq)
                .map(GameEntity::toModel)
                .orElseThrow(() -> new IllegalArgumentException("게임을 찾을 수 없습니다."));
    }

    @Override
    public List<Game> findAll(Pageable pageable) {
        return gameJpaRepository.findAll(pageable)
                .stream().map(GameEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Game> findAllByRoomNameOrManagerName(String word, Pageable pageable) {
        return gameJpaRepository.findAllByRoomNameOrManager(word, pageable)
                .stream().map(GameEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Game save(User user, Game game, Quiz quiz) {
        GameEntity gameEntity = GameEntity.fromModel(user, game, quiz);
        return gameJpaRepository.save(gameEntity).toModel();
    }

}
