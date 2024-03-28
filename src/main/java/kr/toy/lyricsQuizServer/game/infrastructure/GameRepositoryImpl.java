package kr.toy.lyricsQuizServer.game.infrastructure;

import kr.toy.lyricsQuizServer.game.domain.Game;
import kr.toy.lyricsQuizServer.game.domain.GameStatus;
import kr.toy.lyricsQuizServer.game.domain.dto.GameCreate;
import kr.toy.lyricsQuizServer.game.service.port.GameRepository;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.service.QuizRepository;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.NoSuchElementException;
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
    public PageImpl findAll(Pageable pageable) {
        Page<GameEntity> pages = gameJpaRepository.findAll(pageable);
        return new PageImpl<>(pages.stream().map(GameEntity::toModel).collect(Collectors.toList()), pageable, pages.getTotalElements());
    }

    @Override
    public PageImpl<Game> findAllByRoomNameOrManagerName(String word, Pageable pageable) {
        Page<GameEntity> pages = gameJpaRepository.findAllByRoomNameOrHost(word, pageable);
        return new PageImpl<>(pages.stream().map(GameEntity::toModel).collect(Collectors.toList()), pageable, pages.getTotalElements());
    }

    @Override
    public Game save(User user, Game game, Quiz quiz) {
        GameEntity gameEntity = GameEntity.fromModel(user, game, quiz);
        return gameJpaRepository.save(gameEntity).toModel();
    }



}
