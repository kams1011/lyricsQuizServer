package kr.toy.lyricsQuizServer.quiz.infrastructure;

import kr.toy.lyricsQuizServer.quiz.domain.QuizContent;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizContentCreate;
import kr.toy.lyricsQuizServer.quiz.service.QuizContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;

@Repository
@RequiredArgsConstructor
public class QuizContentRepositoryImpl implements QuizContentRepository {

    private final QuizContentJpaRepository quizContentJpaRepository;

    @Override
    public QuizContent getById(Long quizContentSeq) {
        return quizContentJpaRepository.findById(quizContentSeq).orElseThrow(NoSuchElementException::new).toModel();
    }

    @Override
    public QuizContent save(QuizContent quizContent) {
        return quizContentJpaRepository.save(QuizContentEntity.fromModel(quizContent)).toModel();

    }
}
