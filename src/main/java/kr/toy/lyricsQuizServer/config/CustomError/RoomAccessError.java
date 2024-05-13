package kr.toy.lyricsQuizServer.config.CustomError;

import kr.toy.lyricsQuizServer.common.domain.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RoomAccessError extends RuntimeException {

    ErrorCode errorCode;
}
