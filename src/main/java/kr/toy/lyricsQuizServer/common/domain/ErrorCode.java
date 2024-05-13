package kr.toy.lyricsQuizServer.common.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
@AllArgsConstructor
public enum ErrorCode {

    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "ROOM-001", "방을 찾을 수 없습니다."),
    ROOM_FULL(HttpStatus.CONFLICT, "ROOM-002", "인원이 가득 찬 방입니다."),
    ROOM_ALREADY_START(HttpStatus.CONFLICT, "ROOM-003", "이미 시작된 방입니다."),
    ROOM_LOCK(HttpStatus.LOCKED, "ROOM-004", "비밀 방입니다."),
    USER_NOT_REGISTERED(HttpStatus.NO_CONTENT, "USER-001", "가입되지 않은 유저입니다."),
    USER_ALREADY_JOINED(HttpStatus.NOT_ACCEPTABLE, "USER-002", "이미 게임에 참여한 유저입니다."),
    COOKIE_NOT_FOUND(HttpStatus.NOT_FOUND, "AUTH-001", "로그인 쿠키 정보가 없습니다."),
    JWT_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH-001", "로그인 쿠키 정보가 없습니다."),
    GAME_TIME_ERROR(HttpStatus.BAD_REQUEST, "GAME-001", "시작 시간과 끝 시간의 차이는 30초를 넘을 수 없습니다.");

    private final HttpStatus httpStatus;	// HttpStatus
    private final String code;				// 임의 코드
    private final String message;			// 설명


}
