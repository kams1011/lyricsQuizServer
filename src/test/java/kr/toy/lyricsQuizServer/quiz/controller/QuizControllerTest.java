package kr.toy.lyricsQuizServer.quiz.controller;

import kr.toy.lyricsQuizServer.docs.quiz.QuizRestDocs;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.domain.QuizContent;
import kr.toy.lyricsQuizServer.quiz.domain.QuizContentType;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizContentCreate;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizCreate;
import kr.toy.lyricsQuizServer.user.domain.LoginType;
import kr.toy.lyricsQuizServer.user.domain.Role;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.domain.dto.UserCreate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuizController.class)
public class QuizControllerTest extends QuizRestDocs {


    @Test
    void quiz_create_test() throws Exception {
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
        Quiz quiz = initializeDummyData();

        //when
        when(quizService.create(any(), any())).thenReturn(quiz); // 이 데이터가 mockMVC에서 return됨.

        ResultActions perform = this.mockMvc
                .perform(RestDocumentationRequestBuilders
                        .post(quizApiUrl)
                        .content(objectMapper.writeValueAsString(quizCreate))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        perform.andExpect(status().isOk())
                .andDo(print())
                .andDo(document(documentPath,
                        requestFields(
                                fieldWithPath("title").description("유저 이름").type(JsonFieldType.STRING),
                                fieldWithPath("singer").description("가수").type(JsonFieldType.STRING),
                                fieldWithPath("information").description("곡 정보").type(JsonFieldType.STRING),
                                fieldWithPath("startTime").description("노래 중 퀴즈 시작 시간").type(JsonFieldType.STRING),
                                fieldWithPath("endTime").description("노래 중 퀴즈 끝 시간").type(JsonFieldType.STRING),
                                fieldWithPath("beforeLyrics").description("퀴즈 시간 이전 가사").type(JsonFieldType.STRING),
                                fieldWithPath("afterLyrics").description("퀴즈 시간 이후 가사").type(JsonFieldType.STRING),
                                fieldWithPath("answer").description("퀴즈 정답").type(JsonFieldType.STRING),
                                fieldWithPath("isDeleted").description("삭제여부").type(JsonFieldType.BOOLEAN).optional(),
                                fieldWithPath("quizContentCreate.quizContentType").description("퀴즈가 유튜브로 생성됐는지 파일업로드로 생성됐는지 여부. 각각 YOUTUBE / FILE 로 값을 받음.").type(JsonFieldType.STRING),
                                fieldWithPath("quizContentCreate.url").description("유튜브로 업로드했을 때 유튜브 경로").type(JsonFieldType.STRING).optional(),
                                fieldWithPath("quizContentCreate.fileSeq").description("파일로 업로드 했을 때 반환받은 파일 고유키").type(JsonFieldType.NUMBER).optional()
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
                                    fieldWithPath("data.updatedAt").type(JsonFieldType.STRING).description("퀴즈 수정시각").optional(),
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
    public void quiz_delete_test() throws Exception { // FIXME 공통코드 분리
        //given
        QuizContentCreate quizContentCreate = QuizContentCreate.builder()
                .quizContentType(QuizContentType.YOUTUBE)
                .url("https://youtu.be/Km71Rr9K-Bw?feature=shared")
                .build();

        Quiz quiz = initializeDummyData();

        ResultActions perform = this.mockMvc
                .perform(RestDocumentationRequestBuilders
                        .delete(quizApiUrl+"/{quizSeq}", quiz.getQuizSeq())
                );

        perform.andExpect(status().isOk())
                .andDo(print())
                .andDo(document(documentPath,
                        pathParameters(parameterWithName("quizSeq").description("퀴즈 고유키")),
                        responseFields(
                                this.responseCommon()
                        )
                )).andReturn();
    }


    @Test
    public void quiz_getList_test() throws Exception {

        List<Quiz> quizList = initializeDummyDataList();

        PageImpl<Quiz> responsePages = getPageImpl(quizList);

        when(quizService.getList(any(), any())).thenReturn(responsePages);
        ResultActions perform = this.mockMvc
                .perform(RestDocumentationRequestBuilders
                        .get(quizApiUrl));

        perform.andExpect(status().isOk())
                .andDo(print())
                .andDo(document(documentPath,
                        pathParameters(parameterWithName("keyword").description("검색어").optional()),
                        responseFields(
                                this.responseCommon()
                        )
                )).andReturn();

    }

    @Test
    public void quiz_get_test() throws Exception {

        Quiz quiz = initializeDummyData();

        when(quizService.find(quiz.getQuizSeq())).thenReturn(quiz);

        ResultActions perform = this.mockMvc
                .perform(RestDocumentationRequestBuilders
                        .get(quizApiUrl+"/{quizSeq}", quiz.getQuizSeq()));

        perform.andExpect(status().isOk())
                .andDo(print())
                .andDo(document(documentPath,
                        pathParameters(parameterWithName("quizSeq").description("퀴즈 고유키")),
                        responseFields(
                                this.responseCommon()
                        )
                )).andReturn();

    }



}
