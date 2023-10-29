package kr.toy.lyricsQuizServer.quiz.service;

import kr.toy.lyricsQuizServer.quiz.controller.port.QuizService;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.domain.QuizContent;
import kr.toy.lyricsQuizServer.quiz.domain.dto.ChatMessage;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizContentCreate;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizCreate;
import kr.toy.lyricsQuizServer.quiz.infrastructure.QuizContentEntity;
import kr.toy.lyricsQuizServer.quiz.infrastructure.QuizEntity;
import kr.toy.lyricsQuizServer.user.controller.port.UserService;
import kr.toy.lyricsQuizServer.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final UserService userService;

    private final QuizRepository quizRepository;

    private final QuizContentRepository quizContentRepository;


    @Override
    @Transactional
    public Quiz create(QuizCreate quizCreate) {

        User maker = userService.getById(quizCreate.getUserSeq());
        Quiz quiz = Quiz.from(quizCreate, maker, LocalDateTime.now());

        QuizContent quizContent = quizContentRepository.save(quiz.getQuizContent());
        //FIXME 유튜브와 파일 분기처리해서 업로드하는 로직 필요
        quizRepository.save(quiz);

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

    @Override
    public void join(Long roomId, ChatMessage message) {

    }

    @Override
    public void chat(Long roomId, ChatMessage message) {

    }

    @Override
    public void retrieve() {

    }

    @Override
    public QuizContent contentCreate(QuizContentCreate quizContentCreate) {
        return quizContentRepository.save(QuizContent.from(quizContentCreate));
    }



}
