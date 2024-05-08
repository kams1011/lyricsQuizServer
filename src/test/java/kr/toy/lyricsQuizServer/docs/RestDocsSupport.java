package kr.toy.lyricsQuizServer.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.toy.lyricsQuizServer.config.ApiControllerAdvice;
import kr.toy.lyricsQuizServer.config.ConfigurationProperties.SecurityProperties;
import kr.toy.lyricsQuizServer.config.JwtArgumentResolver;
import kr.toy.lyricsQuizServer.config.JwtUtils;
import kr.toy.lyricsQuizServer.config.SecurityService;
import kr.toy.lyricsQuizServer.file.domain.File;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.service.port.AuthServerAPI;
import lombok.Builder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;

@Disabled
@WebMvcTest
@ExtendWith(RestDocumentationExtension.class)
@Import(RestDocConfig.class)
public abstract class RestDocsSupport<T>{

    protected AtomicLong id = new AtomicLong(0);

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

    protected Long getId(){
        return id.incrementAndGet();
    }

    protected boolean optional = true;

    protected PageImpl<T> getPageImpl(List<T> list){
        PageImpl<T> pageImpl = new PageImpl(list);
        return pageImpl;
    };

    protected abstract PageImpl<T> getPageImpl();

    protected abstract <T> T initializeDummyData();

    protected <T> List<T> initializeDummyDataList(){
        List<T> list = new ArrayList<>();
        for (int i=0; i<10; i++) {
            list.add(initializeDummyData());
        }
        return list;
    }

    protected final String documentPath = "{class-name}/{method-name}";

    @BeforeEach
    public void setup(RestDocumentationContextProvider restDocumentation) throws Exception {
        initializeDummyData();
        initializeDummyDataList();
        when(jwtArgumentResolver.supportsParameter(any())).thenReturn(true);
        when(jwtArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(true);
        this.mockMvc = MockMvcBuilders.standaloneSetup(getController())
//                .setCustomArgumentResolvers(jwtArgumentResolver)
                .setControllerAdvice(new ApiControllerAdvice())
                .alwaysDo(restDoc)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
//                .apply(springSecurity())    // FIXME : springSecurity 추가시 주석 해제
                .apply(documentationConfiguration(restDocumentation)).build();
    }

    protected FieldDescriptor[] responseCommon(){
        return new FieldDescriptor[] {
                field("success", JsonFieldType.BOOLEAN, "성공여부"),
                field("message", JsonFieldType.STRING, "메시지", optional),
                subsectionWithPath("data").type(JsonFieldType.OBJECT).description("데이터가 없는 메서드도 있음.").optional(),
                field("errorCode", JsonFieldType.STRING, "에러코드", optional)
        };
    }

    protected FieldDescriptor field(String path, JsonFieldType type, String description, boolean... isOptional) {
        FieldDescriptor descriptor = fieldWithPath(path).type(type).description(description);
        if (isOptional.length > 0 && isOptional[0]) {
            descriptor = descriptor.optional();
        }
        return descriptor;
    }

}
