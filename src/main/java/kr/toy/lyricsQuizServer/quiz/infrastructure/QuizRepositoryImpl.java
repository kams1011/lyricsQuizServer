package kr.toy.lyricsQuizServer.quiz.infrastructure;

import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.service.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Repository
@RequiredArgsConstructor
public class QuizRepositoryImpl implements QuizRepository {

    private final QuizJpaRepository quizJpaRepository;

    @Override
    public Quiz getById(Long id) {
        return quizJpaRepository.findByQuizSeqAndIsDeletedIsFalse(id)
                .orElseThrow(EntityNotFoundException::new)
                .toModel();
    }

    @Override
    public Quiz save(Quiz quiz) {
        return quizJpaRepository.save(QuizEntity.fromModel(quiz)).toModel();
    }

    @Override
    @Transactional
    public Quiz delete(Long id) {
        QuizEntity quizEntity = quizJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        quizEntity.delete();
        return quizEntity.toModel();
    }
}
