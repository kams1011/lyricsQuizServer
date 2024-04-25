package kr.toy.lyricsQuizServer.quiz.controller;

import kr.toy.lyricsQuizServer.quiz.controller.port.QuizService;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizContentCreate;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizCreate;
import kr.toy.lyricsQuizServer.user.controller.UserController;
import kr.toy.lyricsQuizServer.user.domain.Role;
import kr.toy.lyricsQuizServer.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static kr.toy.lyricsQuizServer.user.domain.LoginType.KAKAO;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(RestDocumentationExtension.class)    // JUnit5 필수
@WebMvcTest(QuizController.class)
public class QuizControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizService quizService;

    private final String requestMapping = "/api/quiz/";
    @BeforeEach
    void setup(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//                .apply(springSecurity())    // FIXME : springSecurity 추가시 주석 해제
                .apply(documentationConfiguration(restDocumentation)).build();
    }



//    @Test
//    void create_메서드를_통해_Quiz를_생성할_수_있다() throws Exception {
//
//        QuizCreate quizCreate = QuizCreate.builder()
//                .title("좋은밤좋은꿈")
//                .information("좋은밤좋은꿈")
//                .beforeLyrics("좋은")
//                .afterLyrics("밤")
//                .answer("좋은밤좋은꿈")
//                .singer("너드커넥션")
//                .quizContentCreate()
//                .build();
//        QuizContentCreate quizContentCreate = QuizContentCreate.builder()
//                .quizContentType()
//                .fileSeq()
//                .url()
//                .build();
//
//
//        given(quizService.create()).willReturn();
//
//        ResultActions perform = this.mockMvc.perform(
//                RestDocumentationRequestBuilders.get(requestMapping + "1")
//        );
//        perform.andExpect(status().isOk())
//                .andDo(print())
//                .andDo(document("user",
//                                preprocessRequest(prettyPrint()),
//                                preprocessResponse(prettyPrint()))
////                        requestParameters(
////                                parameterWithName("user").description("유저 이름").optional()
////                        ),
////                        responseFields(
////                                fieldWithPath("imgList").description("이미지 리스트"),
////                                fieldWithPath("imgList[].name").description("이미지 등록 회원 이름"),
////                                fieldWithPath("imgList[].img_url").description("이미지 url"),
////                                fieldWithPath("imgList[].reg_date").description("이미지 등록일시")
////                        ))
//                );
//
//
//    }



}
