package kr.toy.lyricsQuizServer.quiz.infrastructure;

import kr.toy.lyricsQuizServer.quiz.domain.QuizContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizContentJpaRepository extends JpaRepository<QuizContentEntity, Long> {


}
