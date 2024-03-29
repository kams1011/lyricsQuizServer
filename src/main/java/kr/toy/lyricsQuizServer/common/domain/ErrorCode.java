package kr.toy.lyricsQuizServer.common.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "ROOM-001", "방을 찾을 수 없습니다."),
    ROOM_FULL(HttpStatus.CONFLICT, "ROOM-002", "인원이 가득 찬 방입니다."),
    ROOM_ALREADY_START(HttpStatus.CONFLICT, "ROOM-003", "이미 시작된 방입니다."),
    ROOM_LOCK(HttpStatus.LOCKED, "ROOM-004", "비밀 방입니다."),
    USER_NOT_REGISTERED(HttpStatus.NO_CONTENT, "USER-001", "가입되지 않은 유저입니다.");

    private final HttpStatus httpStatus;	// HttpStatus
    private final String code;				// 임의 코드
    private final String message;			// 설명

}
