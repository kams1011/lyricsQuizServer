package kr.toy.lyricsQuizServer.config.CustomError;


import kr.toy.lyricsQuizServer.common.domain.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticationCookieNotFoundException extends RuntimeException{

    ErrorCode errorCode;
}
