package kr.toy.lyricsQuizServer.quiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kr.toy.lyricsQuizServer.config.ApiControllerAdvice;
import kr.toy.lyricsQuizServer.config.ConfigurationProperties.SecurityProperties;
import kr.toy.lyricsQuizServer.config.JwtArgumentResolver;
import kr.toy.lyricsQuizServer.config.JwtUtils;
import kr.toy.lyricsQuizServer.config.SecurityService;
import kr.toy.lyricsQuizServer.quiz.controller.port.QuizService;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.domain.QuizContent;
import kr.toy.lyricsQuizServer.quiz.domain.QuizContentType;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizContentCreate;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizCreate;
import kr.toy.lyricsQuizServer.user.controller.UserController;
import kr.toy.lyricsQuizServer.user.domain.LoginType;
import kr.toy.lyricsQuizServer.user.domain.Role;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.domain.dto.UserCreate;
import kr.toy.lyricsQuizServer.user.service.port.AuthServerAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static kr.toy.lyricsQuizServer.user.domain.LoginType.KAKAO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)    // JUnit5 필수
@WebMvcTest(QuizController.class)
public class QuizControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizService quizService;


    @MockBean
    AuthServerAPI authServerAPI;

    @MockBean
    SecurityService securityService;

    @MockBean
    SecurityProperties securityProperties;

    @MockBean
    JwtUtils jwtUtils;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    private JwtArgumentResolver jwtArgumentResolver;


    private final String requestMapping = "/api/quiz/";
    @BeforeEach
    void setup(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        this.mockMvc = MockMvcBuilders.standaloneSetup(webApplicationContext)
                .setCustomArgumentResolvers(jwtArgumentResolver)
                .setControllerAdvice(new ApiControllerAdvice())
//                .apply(springSecurity())    // FIXME : springSecurity 추가시 주석 해제
                .apply(documentationConfiguration(restDocumentation)).build();
    }



    @Test
    void create_메서드를_통해_Quiz를_생성할_수_있다() throws Exception {

        //given
        QuizContentCreate quizContentCreate = QuizContentCreate.builder()
                .quizContentType(QuizContentType.YOUTUBE)
                .url("https://youtu.be/Km71Rr9K-Bw?feature=shared")
                .build();
        QuizCreate quizCreate = QuizCreate.builder()
                .title("좋은밤좋은꿈")
                .information("좋은밤좋은꿈")
                .startTime(LocalTime.parse("00:01:01"))
                .endTime(LocalTime.parse("00:01:05"))
                .beforeLyrics("좋은")
                .afterLyrics("밤")
                .answer("좋은밤좋은꿈")
                .singer("너드커넥션")
                .quizContentCreate(quizContentCreate)
                .build();
        QuizContent quizContent = QuizContent.from(quizContentCreate);
        UserCreate userCreate = UserCreate.builder()
                .email("kams1011@naver.com")
        .loginType(LoginType.NAVER)
        .nickName("kams")
        .build();
        User user = User.from(userCreate, LocalDateTime.now());

        String requestBody = objectMapper.writeValueAsString(quizCreate);

        //when
        when(jwtArgumentResolver.supportsParameter(any())).thenReturn(true);
        ResultActions perform = this.mockMvc
                .perform(RestDocumentationRequestBuilders
                        .post(requestMapping)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        perform.andExpect(status().is(404))
                .andDo(print())
                .andDo(document("quiz",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("title").description("유저 이름"),
                                parameterWithName("singer").description("가수"),
                                parameterWithName("information").description("곡 정보"),
                                parameterWithName("startTime").description("노래 중 퀴즈 시작 시간"),
                                parameterWithName("endTime").description("노래 중 퀴즈 끝 시간"),
                                parameterWithName("beforeLyrics").description("퀴즈 시간 이전 가사"),
                                parameterWithName("afterLyrics").description("퀴즈 시간 이후 가사"),
                                parameterWithName("answer").description("퀴즈 정답"),
                                parameterWithName("QuizContentCreate.quizContentType").description("퀴즈가 유튜브로 생성됐는지 파일업로드로 생성됐는지 여부. 각각 YOUTUBE / FILE 로 값을 받음."),
                                parameterWithName("QuizContentCreate.url").description("유튜브로 업로드했을 때 유튜브 경로").optional(),
                                parameterWithName("QuizContentCreate.fileSeq").description("파일로 업로드 했을 때 반환받은 파일 고유키").optional()
                        ),
                        responseFields(
                                fieldWithPath("quizSeq").description("퀴즈 고유키"),
                                fieldWithPath("isDeleted").description("삭제여부"),
                                fieldWithPath("title").description("유저 이름"),
                                fieldWithPath("singer").description("가수"),
                                fieldWithPath("information").description("곡 정보"),
                                fieldWithPath("beforeLyrics").description("퀴즈 시간 이전 가사"),
                                fieldWithPath("afterLyrics").description("퀴즈 시간 이후 가사"),
                                fieldWithPath("answer").description("퀴즈 정답"),
                                fieldWithPath("createdAt").description("퀴즈 생성시각"),
                                fieldWithPath("updatedAt").description("퀴즈 수정시각"),
                                fieldWithPath("maker").description("퀴즈 생성인. 고유키만 보내는 것으로 변경가능성 있음."),
                                fieldWithPath("startTime").description("노래 중 퀴즈 시작 시간"),
                                fieldWithPath("endTime").description("노래 중 퀴즈 끝 시간"),
                                fieldWithPath("QuizContent.quizContentSeq").description("퀴즈 컨텐츠 고유키"),
                                fieldWithPath("QuizContent.quizContentType").description("퀴즈가 유튜브로 생성됐는지 파일업로드로 생성됐는지 여부. 각각 YOUTUBE / FILE"),
                                fieldWithPath("QuizContent.detail").description("퀴즈 재생용 고유값"),
                                fieldWithPath("QuizContent.YoutubeURL").description("유튜브 영상재생을 위한 상수")
                        )
                ));
    }



}
