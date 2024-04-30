package kr.toy.lyricsQuizServer.quiz.infrastructure;

import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.domain.QuizContent;
import kr.toy.lyricsQuizServer.quiz.service.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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
    public Quiz save(Quiz quiz, QuizContent quizContent) {
        QuizEntity quizEntity = QuizEntity.fromModel(quiz);
        quizEntity.setQuizContentEntity(QuizContentEntity.fromModel(quizContent));
        quizJpaRepository.save(quizEntity);
        return quizEntity.toModel();
    }

    @Override
    public PageImpl<Quiz> getList(String keyword, Pageable pageable) {
        keyword = keyword == null ? "" : keyword;
        PageImpl<QuizEntity> pages = quizJpaRepository.findAllByTitleContainingAndIsDeletedIsFalse(keyword, pageable);
        return new PageImpl<>(pages.stream().map(data -> data.toModel()).collect(Collectors.toList()), pageable, pages.getTotalElements());
    }

    @Override
    @Transactional
    public Quiz delete(Long id) {
        QuizEntity quizEntity = quizJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        quizEntity.delete();
        return quizEntity.toModel();
    }
}
