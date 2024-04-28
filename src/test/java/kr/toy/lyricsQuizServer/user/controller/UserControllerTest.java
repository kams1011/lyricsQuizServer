package kr.toy.lyricsQuizServer.user.controller;

import kr.toy.lyricsQuizServer.config.ConfigurationProperties.SecurityProperties;
import kr.toy.lyricsQuizServer.config.JwtUtils;
import kr.toy.lyricsQuizServer.config.SecurityService;
import kr.toy.lyricsQuizServer.config.WebMVCConfig;
import kr.toy.lyricsQuizServer.user.controller.port.UserService;
import kr.toy.lyricsQuizServer.user.domain.Role;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.service.port.AuthServerAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)    // JUnit5 필수
@WebMvcTest(UserController.class)
@AutoConfigureRestDocs
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    AuthServerAPI authServerAPI;

    @MockBean
    SecurityService securityService;

    @MockBean
    SecurityProperties securityProperties;

    @MockBean
    JwtUtils jwtUtils;
//
//    private final String requestMapping = "/api/users/";
//
//
    @BeforeEach
    void setup(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//                .apply(springSecurity())    // FIXME : springSecurity 추가시 주석 해제
                .apply(documentationConfiguration(restDocumentation)).build();
    }

    @Test
    void getByEmail_메서드를_통해_유저_정보를_받아올_수_있다() throws Exception { // FIXME : 전부 200을 리턴하는 오류가 있음.
//        given(userService.getById(1L))
//                .willReturn(User.builder()
//                        .userSeq(1L)
//                        .email("kams1011@naver.com")
//                        .nickName("kams")
//                        .lastLoginAt(LocalDateTime.parse("2023-08-18T00:00:00"))
//                        .isBan(false)
//                        .isDeleted(false)
//                        .loginType(KAKAO)
//                        .role(Role.USER)
//                        .createdAt(LocalDateTime.parse("2023-08-18T00:00:00"))
//                        .updatedAt(LocalDateTime.parse("2023-08-18T00:00:00"))
//                        .build());


        ResultActions perform = this.mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/users/" + "1234")
        );

        perform.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()))
//                        requestParameters(
//                                parameterWithName("user").description("유저 이름").optional()
//                        ),
//                        responseFields(
//                                fieldWithPath("imgList").description("이미지 리스트"),
//                                fieldWithPath("imgList[].name").description("이미지 등록 회원 이름"),
//                                fieldWithPath("imgList[].img_url").description("이미지 url"),
//                                fieldWithPath("imgList[].reg_date").description("이미지 등록일시")
//                        ))
                );


    }



    @Test
    public void temp(){

    }



}
