package kr.toy.lyricsQuizServer.config.CustomError;

import kr.toy.lyricsQuizServer.common.domain.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DomainValidError extends RuntimeException{

    ErrorCode errorCode;
}
