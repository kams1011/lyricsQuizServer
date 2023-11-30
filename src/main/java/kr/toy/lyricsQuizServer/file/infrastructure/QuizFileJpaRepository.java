package kr.toy.lyricsQuizServer.file.infrastructure;

import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizFileJpaRepository extends JpaRepository<QuizFileEntity, Long> {


    Optional<QuizFileEntity> findByQuiz(Quiz quiz);
}
