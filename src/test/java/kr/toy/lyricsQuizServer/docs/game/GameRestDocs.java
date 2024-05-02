package kr.toy.lyricsQuizServer.docs.game;

import kr.toy.lyricsQuizServer.docs.RestDocsSupport;
import kr.toy.lyricsQuizServer.docs.quiz.QuizRestDocs;
import kr.toy.lyricsQuizServer.game.controller.GameController;
import kr.toy.lyricsQuizServer.game.controller.port.GameService;
import kr.toy.lyricsQuizServer.game.domain.Game;
import kr.toy.lyricsQuizServer.game.domain.GameStatus;
import kr.toy.lyricsQuizServer.game.service.port.GameRepository;
import kr.toy.lyricsQuizServer.quiz.controller.QuizController;
import kr.toy.lyricsQuizServer.quiz.controller.port.QuizService;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.domain.QuizContent;
import kr.toy.lyricsQuizServer.quiz.domain.QuizContentType;
import kr.toy.lyricsQuizServer.quiz.service.QuizRepository;
import kr.toy.lyricsQuizServer.user.domain.LoginType;
import kr.toy.lyricsQuizServer.user.domain.Role;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.service.port.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class GameRestDocs extends RestDocsSupport<Game> {

    @MockBean
    protected GameService gameService;

    @MockBean
    protected GameRepository gameRepository;

    @Autowired
    protected GameController gameController;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected QuizRepository quizRepository;

    protected final String apiUrl = "/api/game";

    @Override
    protected Object getController() {
        return gameController;
    }

    @Override
    protected PageImpl<Game> getPageImpl() {
        return null;
    }

    @Override
    protected Game initializeDummyData() {
        User host = User.builder()
                .userSeq(getId())
                .email("kams1011@naver.com")
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .isDeleted(false)
                .isBan(false)
                .nickName("kams")
                .loginType(LoginType.NAVER)
                .lastLoginAt(LocalDateTime.now())
                .build();

        userRepository.save(host);

        QuizContent quizContent = QuizContent.builder()
                .quizContentType(QuizContentType.YOUTUBE)
                .quizContentSeq(getId())
                .detail("KK")
                .build();
        User maker = User.builder().userSeq(getId())
                .isDeleted(false)
                .isBan(false)
                .nickName("kams")
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .email("kams1011@naver.com")
                .role(Role.USER)
                .lastLoginAt(LocalDateTime.now())
                .loginType(LoginType.NAVER)
                .build();
        Quiz quiz = Quiz.builder()
                .quizSeq(getId())
                .isDeleted(false)
                .title("좋은밤")
                .singer("좋은꿈")
                .information("정보")
                .startTime(LocalTime.parse("01:01"))
                .endTime(LocalTime.parse("01:03"))
                .beforeLyrics("이전가사")
                .afterLyrics("이후가사")
                .answer("정답")
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .maker(maker)
                .quizContent(quizContent)
                .build();

        quizRepository.save(quiz, quizContent);


        Game game = Game.builder()
                .gameStatus(GameStatus.READY)
                .isSecretRoom(false)
                .isDeleted(false)
                .createdAt(LocalDateTime.now())
                .attendeeCount(1)
                .attendeeLimit(5)
                .roomName("GameRoom")
                .host(host)
                .quiz(quiz)
                .startedAt(null)
                .build();
        return game;
    }
}
