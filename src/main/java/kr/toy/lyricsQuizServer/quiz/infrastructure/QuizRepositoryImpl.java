package kr.toy.lyricsQuizServer.quiz.infrastructure;

import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.service.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;

@Repository
@RequiredArgsConstructor
public class QuizRepositoryImpl implements QuizRepository {

    private final QuizJpaRepository quizJpaRepository;

    @Override
    public Quiz getById(Long id) {
        return quizJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException())
                .toModel();
    }

    @Override
    public Quiz save(Quiz quiz) {
        return quizJpaRepository.save(QuizEntity.fromModel(quiz)).toModel();
    }
}
