package kr.toy.lyricsQuizServer.quiz.infrastructure;

import kr.toy.lyricsQuizServer.quiz.domain.QuizContent;
import kr.toy.lyricsQuizServer.quiz.service.QuizContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuizContentRepositoryImpl implements QuizContentRepository {

    private final QuizContentJpaRepository quizContentJpaRepository;



    @Override
    public QuizContent getById() {
        return null;
    }


    @Override
    public QuizContent save() {
        return null;
    }
}
