package kr.toy.lyricsQuizServer.file.infrastructure;

import kr.toy.lyricsQuizServer.file.domain.QuizFile;
import kr.toy.lyricsQuizServer.file.service.QuizFileRepository;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;

@Repository
@RequiredArgsConstructor
public class QuizFileRepositoryImpl implements QuizFileRepository {

    private final QuizFileJpaRepository quizFileJpaRepository;

    @Override
    public void save(QuizFile quizFile) {
        quizFileJpaRepository.save(QuizFileEntity.fromModel(quizFile));
    }

    @Override
    public QuizFile readBy(Long quizFileSeq) {
        return quizFileJpaRepository.findById(quizFileSeq)
                .orElseThrow(EntityNotFoundException::new)
                .toModel();
    }

    @Override
    public QuizFile readBy(Quiz quiz) {
        return quizFileJpaRepository.findByQuiz(quiz)
                .orElseThrow(EntityNotFoundException::new)
                .toModel();
    }


}
