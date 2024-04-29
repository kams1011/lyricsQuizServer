package kr.toy.lyricsQuizServer.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.toy.lyricsQuizServer.config.ApiControllerAdvice;
import kr.toy.lyricsQuizServer.config.ConfigurationProperties.SecurityProperties;
import kr.toy.lyricsQuizServer.config.JwtArgumentResolver;
import kr.toy.lyricsQuizServer.config.JwtUtils;
import kr.toy.lyricsQuizServer.config.SecurityService;
import kr.toy.lyricsQuizServer.user.service.port.AuthServerAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.mvc.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;

@Disabled
@WebMvcTest
@ExtendWith(RestDocumentationExtension.class)
@Import(RestDocConfig.class)
public abstract class RestDocsSupport {

    @MockBean
    protected AuthServerAPI authServerAPI;

    @MockBean
    protected SecurityService securityService;

    @MockBean
    protected SecurityProperties securityProperties;

    @MockBean
    protected JwtUtils jwtUtils;
    @Autowired
    protected ObjectMapper objectMapper;

    protected abstract Object getController();

    @Mock
    protected  JwtArgumentResolver jwtArgumentResolver;

    @Autowired
    protected RestDocumentationResultHandler restDoc;

    @Autowired
    protected MockMvc mockMvc;

    @BeforeEach
    public void setup(RestDocumentationContextProvider restDocumentation) throws Exception {
        when(jwtArgumentResolver.supportsParameter(any())).thenReturn(true);
        when(jwtArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(true);
        this.mockMvc = MockMvcBuilders.standaloneSetup(getController())
//                .setCustomArgumentResolvers(jwtArgumentResolver)
                .setControllerAdvice(new ApiControllerAdvice())
                .alwaysDo(restDoc)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
//                .apply(springSecurity())    // FIXME : springSecurity 추가시 주석 해제
                .apply(documentationConfiguration(restDocumentation)).build();
    }

    protected FieldDescriptor[] responseCommon(){
        return new FieldDescriptor[] {
                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                fieldWithPath("message").type(JsonFieldType.STRING).description("메시지").optional(),
//                subsectionWithPath("data").type(JsonFieldType.OBJECT).description("데이터가 없는 메서드도 있음.").optional(),
                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("에러코드").optional()
        };
    }

}
