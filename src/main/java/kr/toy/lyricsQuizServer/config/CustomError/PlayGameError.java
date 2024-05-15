package kr.toy.lyricsQuizServer.config.CustomError;

import kr.toy.lyricsQuizServer.common.domain.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlayGameError extends RuntimeException{

    ErrorCode errorCode;
}
