package kr.toy.lyricsQuizServer.quiz.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.toy.lyricsQuizServer.config.*;
import kr.toy.lyricsQuizServer.config.ConfigurationProperties.SecurityProperties;
import kr.toy.lyricsQuizServer.docs.RestDocsSupport;
import kr.toy.lyricsQuizServer.quiz.controller.port.QuizService;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.domain.QuizContent;
import kr.toy.lyricsQuizServer.quiz.domain.QuizContentType;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizContentCreate;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizCreate;
import kr.toy.lyricsQuizServer.quiz.service.QuizRepository;
import kr.toy.lyricsQuizServer.user.domain.LoginType;
import kr.toy.lyricsQuizServer.user.domain.Role;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.domain.dto.UserCreate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuizController.class)
public class QuizControllerTest extends RestDocsSupport {

    @MockBean
    private QuizService quizService;
    @Autowired
    QuizController quizController;

    @MockBean
    QuizRepository quizRepository;


    private final String quizUrl = "/api/quiz";

    @Override
    protected Object getController() {
        return this.quizController;
    }

//    @BeforeEach
//    public void setup(RestDocumentationContextProvider restDocumentation) throws Exception {
//        super.setup(restDocumentation);
//    }

    @Test
    void create_를_통해_Quiz를_생성할_수_있다() throws Exception {
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


        Quiz quiz = Quiz.from(quizCreate, quizContent, user, LocalDateTime.now());

        //when
        when(quizService.create(any(), any())).thenReturn(quiz); // 이 데이터가 mockMVC에서 return됨.

        ResultActions perform = this.mockMvc
                .perform(RestDocumentationRequestBuilders
                        .post(quizUrl)
                        .content(objectMapper.writeValueAsString(quizCreate))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        perform.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("quiz",
                        requestFields(
                                fieldWithPath("title").description("유저 이름"),
                                fieldWithPath("singer").description("가수"),
                                fieldWithPath("information").description("곡 정보"),
                                fieldWithPath("startTime").description("노래 중 퀴즈 시작 시간"),
                                fieldWithPath("endTime").description("노래 중 퀴즈 끝 시간"),
                                fieldWithPath("beforeLyrics").description("퀴즈 시간 이전 가사"),
                                fieldWithPath("afterLyrics").description("퀴즈 시간 이후 가사"),
                                fieldWithPath("answer").description("퀴즈 정답"),
                                fieldWithPath("isDeleted").description("삭제여부"),
                                fieldWithPath("quizContentCreate.quizContentType").description("퀴즈가 유튜브로 생성됐는지 파일업로드로 생성됐는지 여부. 각각 YOUTUBE / FILE 로 값을 받음."),
                                fieldWithPath("quizContentCreate.url").description("유튜브로 업로드했을 때 유튜브 경로").optional(),
                                fieldWithPath("quizContentCreate.fileSeq").description("파일로 업로드 했을 때 반환받은 파일 고유키").optional()
                        ),
                        responseFields(
                                this.responseCommon())
                                .and(
                                    fieldWithPath("data.isDeleted").type(JsonFieldType.BOOLEAN).description("삭제여부"),
                                    fieldWithPath("data.quizSeq").type(JsonFieldType.NUMBER).description("삭제여부").optional(),
                                    fieldWithPath("data.title").type(JsonFieldType.STRING).description("유저 이름"),
                                    fieldWithPath("data.singer").type(JsonFieldType.STRING).description("가수"),
                                    fieldWithPath("data.information").type(JsonFieldType.STRING).description("곡 정보"),
                                    fieldWithPath("data.beforeLyrics").type(JsonFieldType.STRING).description("퀴즈 시간 이전 가사"),
                                    fieldWithPath("data.afterLyrics").type(JsonFieldType.STRING).description("퀴즈 시간 이후 가사"),
                                    fieldWithPath("data.answer").type(JsonFieldType.STRING).description("퀴즈 정답"),
                                    fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("퀴즈 생성시각"),
                                    fieldWithPath("data.updatedAt").type(JsonFieldType.STRING).description("퀴즈 수정시각"),
                                    subsectionWithPath("data.maker").type(JsonFieldType.OBJECT).description("퀴즈 생성인. 고유키만 보내는 것으로 변경가능성 있음.").optional(),
                                    fieldWithPath("data.startTime").type(JsonFieldType.STRING).description("노래 중 퀴즈 시작 시간"),
                                    fieldWithPath("data.endTime").type(JsonFieldType.STRING).description("노래 중 퀴즈 끝 시간"),
                                    fieldWithPath("data.quizContent.quizContentSeq").type(JsonFieldType.NUMBER).description("퀴즈 컨텐츠 고유키").optional(),
                                    fieldWithPath("data.quizContent.quizContentType").type(JsonFieldType.STRING).description("퀴즈가 유튜브로 생성됐는지 파일업로드로 생성됐는지 여부. 각각 YOUTUBE / FILE"),
                                    fieldWithPath("data.quizContent.detail").type(JsonFieldType.STRING).description("퀴즈 재생용 고유값").optional(),
                                    fieldWithPath("data.quizContent.streamingURL").type(JsonFieldType.STRING).description("유튜브 영상재생을 위한 상수").optional(),
                                    fieldWithPath("data.quizContent.youtubeURL").type(JsonFieldType.STRING).description("유튜브 영상재생을 위한 상수").optional()
                                )
                )).andReturn();
    }


    @Test
    public void delete_를_통해_quiz를_삭제_할_수_있다() throws Exception { // FIXME 공통코드 분리
        //given
        QuizContentCreate quizContentCreate = QuizContentCreate.builder()
                .quizContentType(QuizContentType.YOUTUBE)
                .url("https://youtu.be/Km71Rr9K-Bw?feature=shared")
                .build();
        QuizContent quizContent = QuizContent.builder()
                .quizContentType(QuizContentType.YOUTUBE)
                .quizContentSeq(1L)
                .detail("KK")
                .build();
        User maker = User.builder().userSeq(1L)
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
                .quizSeq(1L)
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

        ResultActions perform = this.mockMvc
                .perform(RestDocumentationRequestBuilders
                        .delete(quizUrl+"/{quizSeq}", quiz.getQuizSeq())
                );

        perform.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("quiz",
                        pathParameters(parameterWithName("quizSeq").description("퀴즈 고유키")),
                        responseFields(
                                this.responseCommon()
                        )
                )).andReturn();
    }



}
