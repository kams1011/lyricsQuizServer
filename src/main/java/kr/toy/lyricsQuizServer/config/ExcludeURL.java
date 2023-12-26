package kr.toy.lyricsQuizServer.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum ExcludeURL {

    회원가입("/api/users/signup"),
    유저정보조회("/api/users/info"),
    로그인("/api/users/login"),
    유저조회("/api/users");

    private String URL;


    ExcludeURL(String URL){
        this.URL = URL;
    }

    public static Boolean isExcludeURL(final String URL){
        return Arrays.stream(ExcludeURL.values())
                .filter(data -> data.URL.startsWith(URL))
                .findFirst()
                .isPresent();
    }


}
