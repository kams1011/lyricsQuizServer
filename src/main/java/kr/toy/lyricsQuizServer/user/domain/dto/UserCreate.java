package kr.toy.lyricsQuizServer.user.domain.dto;

import kr.toy.lyricsQuizServer.user.domain.LoginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
public class UserCreate {

    @NotBlank
    private final String email; // FIXME GITHUB는 Email형식으로 들어오지 않을 수 있어서 일단 Email Validation은 제외함.

    @Size(min=2, max=10)
    @NotBlank
    private final String nickName;

    @NotNull
    private final LoginType loginType;

    @Builder
    public UserCreate(String email, String nickName, LoginType loginType){
        this.email = email;
        this.nickName = nickName;
        this.loginType = loginType;
    }
}
