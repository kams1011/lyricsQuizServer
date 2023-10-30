package kr.toy.lyricsQuizServer.quiz.service;

import kr.toy.lyricsQuizServer.quiz.controller.port.QuizContentService;
import kr.toy.lyricsQuizServer.quiz.domain.QuizContent;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizContentCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizContentServiceImpl implements QuizContentService {

    private final QuizContentRepository quizContentRepository;

    @Override
    public QuizContent contentCreate(QuizContentCreate quizContentCreate) {
        QuizContent quizContent = QuizContent.from(quizContentCreate);
        quizContent.detailFromUrl(quizContentCreate.getUrl());
        return quizContentRepository.save(quizContent);
    }
}
