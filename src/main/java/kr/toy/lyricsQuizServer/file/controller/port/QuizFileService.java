package kr.toy.lyricsQuizServer.file.controller.port;

import kr.toy.lyricsQuizServer.file.domain.File;
import kr.toy.lyricsQuizServer.file.domain.QuizFile;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;

public interface QuizFileService {

    QuizFile save(Quiz quiz, File file);
}
