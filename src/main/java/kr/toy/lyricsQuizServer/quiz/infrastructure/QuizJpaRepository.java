package kr.toy.lyricsQuizServer.quiz.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizJpaRepository extends JpaRepository<QuizEntity, Long> {
}
