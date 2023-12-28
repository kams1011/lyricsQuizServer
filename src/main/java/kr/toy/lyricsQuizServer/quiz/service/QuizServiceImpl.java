package kr.toy.lyricsQuizServer.quiz.service;

import kr.toy.lyricsQuizServer.file.controller.port.FileService;
import kr.toy.lyricsQuizServer.file.controller.port.QuizFileService;
import kr.toy.lyricsQuizServer.file.domain.File;
import kr.toy.lyricsQuizServer.file.service.QuizFileRepository;
import kr.toy.lyricsQuizServer.quiz.controller.port.QuizContentService;
import kr.toy.lyricsQuizServer.quiz.controller.port.QuizService;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.domain.QuizContent;
import kr.toy.lyricsQuizServer.quiz.domain.QuizContentType;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizContentCreate;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizCreate;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizDetailToCreateRoom;
import kr.toy.lyricsQuizServer.quiz.infrastructure.QuizContentEntity;
import kr.toy.lyricsQuizServer.quiz.infrastructure.QuizEntity;
import kr.toy.lyricsQuizServer.user.controller.port.UserService;
import kr.toy.lyricsQuizServer.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final UserService userService;

    private final QuizRepository quizRepository;

    private final QuizContentService quizContentService;

    private final QuizFileService quizFileService;

    private final FileService fileService;

    @Override
    public List<Quiz> getList(String keyword, Pageable pageable) {
        return quizRepository.getList(keyword, pageable);
    }

    @Override
    public Quiz find(Long quizSeq) {
        return quizRepository.getById(quizSeq);
    }


    @Override
    @Transactional
    public Quiz create(User maker, QuizCreate quizCreate) {

        QuizContent quizContent = quizContentService.contentCreate(quizCreate.getQuizContentCreate());
        Quiz quiz = Quiz.from(quizCreate, quizContent, maker, LocalDateTime.now());

        quiz = quizRepository.save(quiz, quizContent);
        if (quizCreate.getQuizContentCreate().getQuizContentType().equals(QuizContentType.FILE)){
            File file = fileService.getFileBy(quizCreate.getQuizContentCreate().getFileSeq());
            quizFileService.save(quiz, file);
        }

        return quiz;
    }

    @Override
    public Quiz update() {
        return null;
    }

    @Override
    public Quiz delete(Long quizSeq) {
        return quizRepository.delete(quizSeq);
    }

    @Override
    public Boolean solve(Long quizSeq, String answer) {
        Quiz quiz = quizRepository.getById(quizSeq);
        return quiz.isCorrect(answer);
    }


}
