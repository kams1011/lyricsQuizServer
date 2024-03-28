package kr.toy.lyricsQuizServer.quiz.infrastructure;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizJpaRepository extends JpaRepository<QuizEntity, Long> {

    Optional<QuizEntity> findByQuizSeqAndIsDeletedIsFalse(Long quizSeq);


    PageImpl<QuizEntity> findAllByTitleContainingAndIsDeletedIsFalse(String title, Pageable pageable);
}
