package kr.toy.lyricsQuizServer.docs.user;

import kr.toy.lyricsQuizServer.docs.RestDocsSupport;
import kr.toy.lyricsQuizServer.quiz.controller.QuizController;
import kr.toy.lyricsQuizServer.quiz.controller.port.QuizService;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.service.QuizRepository;
import kr.toy.lyricsQuizServer.user.controller.UserController;
import kr.toy.lyricsQuizServer.user.controller.UserControllerTest;
import kr.toy.lyricsQuizServer.user.controller.port.UserService;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.service.port.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public class UserRestDocs extends RestDocsSupport<User> {

    @MockBean
    protected UserService userService;

    @MockBean
    protected UserRepository userRepository;

    protected final String apiUrl = "/api/user";
    @Autowired
    protected UserController userController;

    @Override
    protected Object getController() {
        return this.userController;
    }

    @Override
    protected PageImpl<User> getPageImpl(List<User> list) {
        return null;
    }

    @Override
    protected PageImpl<User> getPageImpl() {
        return null;
    }

    @Override
    protected <T> T initializeDummyData() {
        return null;
    }

    @Override
    protected <T> List<T> initializeDummyDataList() {
        return null;
    }
}
