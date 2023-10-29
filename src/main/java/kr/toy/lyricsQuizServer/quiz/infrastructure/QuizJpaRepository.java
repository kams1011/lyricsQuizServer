package kr.toy.lyricsQuizServer.quiz.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizJpaRepository extends JpaRepository<QuizEntity, Long> {

    Optional<QuizEntity> findByQuizSeqAndIsDeletedIsFalse(Long quizSeq);
}
