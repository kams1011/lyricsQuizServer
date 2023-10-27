package kr.toy.lyricsQuizServer.quiz.service;

import kr.toy.lyricsQuizServer.quiz.controller.port.QuizService;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.domain.dto.ChatMessage;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizCreate;
import kr.toy.lyricsQuizServer.quiz.infrastructure.QuizEntity;
import kr.toy.lyricsQuizServer.user.controller.port.UserService;
import kr.toy.lyricsQuizServer.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final UserService userService;

    private final QuizRepository quizRepository;


    @Override
    public Quiz create(QuizCreate quizCreate) {
        return null;
    }

    @Override
    public Quiz create(QuizCreate quizCreate, String youtubeUrl) {

        User maker = userService.getById(quizCreate.getUserSeq());
        Quiz quiz = Quiz.from(quizCreate, maker, LocalDateTime.now());
        quizRepository.save(QuizEntity.fromModel(quiz));

        return quiz;
    }

    @Override
    public Quiz update() {
        return null;
    }

    @Override
    public Quiz delete() {
        return null;
    }

    @Override
    public Quiz solve() {
        return null;
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
    
    
}
