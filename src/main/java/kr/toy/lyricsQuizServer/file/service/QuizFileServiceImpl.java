package kr.toy.lyricsQuizServer.file.service;

import kr.toy.lyricsQuizServer.file.controller.port.QuizFileService;
import kr.toy.lyricsQuizServer.file.domain.File;
import kr.toy.lyricsQuizServer.file.domain.QuizFile;
import kr.toy.lyricsQuizServer.file.infrastructure.QuizFileEntity;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizFileServiceImpl implements QuizFileService {

    private final QuizFileRepository quizFileRepository;

    @Override
    public QuizFile save(Quiz quiz, File file) {
        QuizFile quizFile = QuizFile.builder()
                .file(file)
                .quiz(quiz)
                .build();

        quizFileRepository.save(quizFile);

        return quizFile;
    }
}
