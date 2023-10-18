package kr.toy.lyricsQuizServer.quiz.service;

import kr.toy.lyricsQuizServer.quiz.controller.port.QuizService;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.domain.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {


    @Override
    public Quiz create() {
        return null;
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
