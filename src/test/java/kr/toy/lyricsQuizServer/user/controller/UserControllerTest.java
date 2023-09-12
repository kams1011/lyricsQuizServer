package kr.toy.lyricsQuizServer.user.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)    // JUnit5 필수
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String requestMapping = "/api/users/";
    @BeforeEach
    void setup(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//                .apply(springSecurity())    // FIXME : springSecurity 추가시 주석 해제
                .apply(documentationConfiguration(restDocumentation)).build();
    }

    @Test
    void getByEmail_메서드를_통해_유저_정보를_받아올_수_있다() throws Exception {
        ResultActions perform = this.mockMvc.perform(
                RestDocumentationRequestBuilders.get(requestMapping + "3")
        );

        perform.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-get")    // 설정한 값으로 스니펫 폴더가 생성됨
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
}
