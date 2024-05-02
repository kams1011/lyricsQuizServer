package kr.toy.lyricsQuizServer.user.controller;

import kr.toy.lyricsQuizServer.docs.user.UserRestDocs;
import kr.toy.lyricsQuizServer.user.domain.LoginType;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.domain.dto.UserCreate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
public class UserControllerTest extends UserRestDocs {

    @Test
    void getById_test() throws Exception {

        User user = initializeDummyData();

        when(userService.getById(user.getUserSeq())).thenReturn(user);

        ResultActions perform = this.mockMvc
                .perform(RestDocumentationRequestBuilders
                        .get(apiUrl+"/{id}", user.getUserSeq())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                );

        perform.andExpect(status().isOk())
                .andDo(print())
                .andDo(document(documentPath,
                        pathParameters(parameterWithName("id").description("유저 고유키")),
                        responseFields(
                                this.responseCommon())
                                .and(
                                        fieldWithPath("data.userSeq").type(JsonFieldType.NUMBER).description("유저 고유키"),
                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("유저 이메일").optional(),
                                        fieldWithPath("data.nickName").type(JsonFieldType.STRING).description("유저 닉네임"),
                                        fieldWithPath("data.lastLoginAt").type(JsonFieldType.STRING).description("마지막 접속시간"),
                                        fieldWithPath("data.isBan").type(JsonFieldType.BOOLEAN).description("밴 여부"),
                                        fieldWithPath("data.isDeleted").type(JsonFieldType.BOOLEAN).description("삭제여부"),
                                        fieldWithPath("data.loginType").type(JsonFieldType.STRING).description("로그인 타입"),
                                        fieldWithPath("data.role").type(JsonFieldType.STRING).description("권한"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("유저 가입 시각"),
                                        fieldWithPath("data.updatedAt").type(JsonFieldType.STRING).description("유저 정보 수정 시각").optional()
                                )
                )).andReturn();
    }



    @Test
    public void getByEmail_test() throws Exception {

        User user = initializeDummyData();

        when(userService.getByEmail(user.getEmail())).thenReturn(user);

        ResultActions perform = this.mockMvc
                .perform(RestDocumentationRequestBuilders
                        .get(apiUrl+"/email/{email}", user.getEmail())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                );

        perform.andExpect(status().isOk())
                .andDo(print())
                .andDo(document(documentPath,
                        pathParameters(parameterWithName("email").description("유저 고유키")),
                        responseFields(
                                this.responseCommon())
                                .and(
                                        fieldWithPath("data.userSeq").type(JsonFieldType.NUMBER).description("유저 고유키"),
                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("유저 이메일").optional(),
                                        fieldWithPath("data.nickName").type(JsonFieldType.STRING).description("유저 닉네임"),
                                        fieldWithPath("data.lastLoginAt").type(JsonFieldType.STRING).description("마지막 접속시간"),
                                        fieldWithPath("data.isBan").type(JsonFieldType.BOOLEAN).description("밴 여부"),
                                        fieldWithPath("data.isDeleted").type(JsonFieldType.BOOLEAN).description("삭제여부"),
                                        fieldWithPath("data.loginType").type(JsonFieldType.STRING).description("로그인 타입"),
                                        fieldWithPath("data.role").type(JsonFieldType.STRING).description("권한"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("유저 가입 시각"),
                                        fieldWithPath("data.updatedAt").type(JsonFieldType.STRING).description("유저 정보 수정 시각").optional()
                                )
                )).andReturn();

    }

    @Test
    public void login_test(){

    }

    @Test
    public void register_test(){

    }




}
