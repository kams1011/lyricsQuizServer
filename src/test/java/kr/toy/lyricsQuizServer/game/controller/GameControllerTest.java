package kr.toy.lyricsQuizServer.game.controller;

import kr.toy.lyricsQuizServer.common.domain.Response;
import kr.toy.lyricsQuizServer.config.ResponseType;
import kr.toy.lyricsQuizServer.docs.game.GameRestDocs;
import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import kr.toy.lyricsQuizServer.game.domain.Game;
import kr.toy.lyricsQuizServer.game.domain.dto.GamePassword;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
public class GameControllerTest extends GameRestDocs {

    @Test
    public void get_game_list_test() throws Exception {
        List<Game> gameList = initializeDummyDataList();

        PageImpl<Game> responsePages = getPageImpl(gameList);

        List<GameRoom> gameRoomList = responsePages.stream().map(data -> GameRoom.from(data))
                .collect(Collectors.toList());

        when(gameService.getGameListByWord(any(), any())).thenReturn(new PageImpl<>(gameRoomList));

        ResultActions perform = this.mockMvc
                .perform(RestDocumentationRequestBuilders
                        .get(apiUrl)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                );

        perform.andExpect(status().isOk())
                .andDo(print())
                .andDo(document(documentPath,
                        pathParameters(parameterWithName("keyword").description("검색어").optional()),
                        responseFields(
                                this.commonResponse()
                        ).and(

                        )
                )).andReturn();


    }

    @Test
    public void get_quiz_summary_list_test() throws Exception {


    }
//
//    @Test
//    public void create_test(){
//
//
//    }
//
    @Test
    public void check_game_password_test() throws Exception {
        Game game = initializeDummyData();

        GamePassword gamePassword = GamePassword.builder().roomId(game.getGameRoomSeq())
                .password("1234")
                .build();

        gameService.checkPassword(gamePassword);
        verify(gameService).checkPassword(gamePassword);
        ResultActions perform = this.mockMvc
                .perform(RestDocumentationRequestBuilders
                        .post(apiUrl + "/password")
                        .content(objectMapper.writeValueAsString(gamePassword))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                );
        perform.andExpect(status().isOk())
                .andDo(print())
                .andDo(document(documentPath,
                        requestFields(
                                fieldWithPath("roomId").description("방 번호").type(JsonFieldType.NUMBER),
                                fieldWithPath("password").description("비밀번호").type(JsonFieldType.STRING)
                        ),
                        responseFields(
                                this.commonResponse())
                )).andReturn();


    }

    @Test
    public void get_user_is_host_test() throws Exception {
        Game game = initializeDummyData();


        when(gameRepository.save(any(), any(), any())).thenReturn(game);


        ResultActions perform = this.mockMvc
                .perform(RestDocumentationRequestBuilders
                        .get(apiUrl+"/host?roomId=" + game.getGameRoomSeq())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                );

        perform.andExpect(status().isOk())
                .andDo(print())
                .andDo(document(documentPath,
                        requestParameters(parameterWithName("roomId").description("게임 고유키")),
//                        pathParameters(parameterWithName("roomId").description("게임 고유키")),
                        responseFields(
                                this.commonResponse(ResponseType.BOOLEAN))
                        .and(field("data", JsonFieldType.BOOLEAN, "성공 여부"))
//                                .and(
//                                        field("data.userSeq", JsonFieldType.NUMBER, "유저 고유키"),
//                                        field("data.email", JsonFieldType.STRING, "유저 이메일", optional),
//                                        field("data.nickName", JsonFieldType.STRING, "유저 닉네임"),
//                                        field("data.lastLoginAt", JsonFieldType.STRING, "마지막 접속시간"),
//                                        field("data.isBan", JsonFieldType.BOOLEAN, "밴 여부"),
//                                        field("data.isDeleted", JsonFieldType.BOOLEAN, "삭제여부"),
//                                        field("data.loginType", JsonFieldType.STRING, "로그인 타입"),
//                                        field("data.role", JsonFieldType.STRING, "권한"),
//                                        field("data.createdAt", JsonFieldType.STRING, "유저 가입 시각"),
//                                        field("data.updatedAt", JsonFieldType.STRING, "유저 정보 수정 시각", optional)
//                                )
                )).andReturn();
    }

//    @Test
//    public void enter_test(){
//    }
//
//    @Test
//    public void allow_invitation_test(){
//
//
//    }
//
//    @Test
//    public void get_my_invitation_status_test(){
//
//
//    }
//
//    @Test
//    public void game_start_test(){
//
//
//    }
//
//    @Test
//    public void get_invitable_users_test(){
//
//
//    }
//
//    @Test
//    public void invite_test(){
//
//
//    }
//
//    @Test
//    public void ready_test(){
//
//
//    }
//
//    @Test
//    public void streaming_complete_test(){
//
//
//    }
//
//    @Test
//    public void check_answer_test(){
//
//
//    }
//
//



}
