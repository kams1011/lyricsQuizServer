package kr.toy.lyricsQuizServer.docs.quiz;

import kr.toy.lyricsQuizServer.docs.RestDocsSupport;
import kr.toy.lyricsQuizServer.quiz.controller.QuizController;
import kr.toy.lyricsQuizServer.quiz.controller.port.QuizService;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.domain.QuizContent;
import kr.toy.lyricsQuizServer.quiz.domain.QuizContentType;
import kr.toy.lyricsQuizServer.quiz.service.QuizRepository;
import kr.toy.lyricsQuizServer.user.domain.LoginType;
import kr.toy.lyricsQuizServer.user.domain.Role;
import kr.toy.lyricsQuizServer.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class QuizRestDocs extends RestDocsSupport<Quiz> {


    @MockBean
    protected QuizService quizService;

    @MockBean
    protected QuizRepository quizRepository;

    protected final String quizApiUrl = "/api/quiz";
    @Autowired
    protected QuizController quizController;

    @Override
    protected Object getController() {
        return this.quizController;
    }

    @Override
    protected PageImpl<Quiz> getPageImpl() {
        return null;
    }


    @Override
    protected Quiz initializeDummyData() {
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

        return quiz;
    }


//    @BeforeEach
//    public void setup(RestDocumentationContextProvider restDocumentation) throws Exception {
//        super.setup(restDocumentation);
//    }


}
