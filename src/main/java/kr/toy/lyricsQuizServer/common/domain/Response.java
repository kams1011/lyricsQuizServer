package kr.toy.lyricsQuizServer.common.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Response {

    Boolean success;

    String message;

    Object data;

    ErrorCode errorCode;

    @Builder
    public Response(Boolean success, String message, Object data, ErrorCode errorCode){
        this.success = success;
        this.message = message;
        this.data = data;
        this.errorCode = errorCode;
    }

    public static Response success(Object data, String... message){
        String checkedMessage = message.length > 0 ? message[0] : null;

        Response response = Response.builder()
                .success(true)
                .data(data)
                .message(checkedMessage)
                .build();

        return response;
    }

    public static Response fail(String message, Object data, ErrorCode errorCode){
        Response response = Response.builder()
                .success(false)
                .message(message)
                .data(data)
                .errorCode(errorCode)
                .build();

        return response;
    }

    public static Response returnBoolean(Object data, String... message){
        String checkedMessage = message.length > 0 ? message[0] : null;

        Response response = Response.builder()
                .success(true)
                .data(data)
                .message(checkedMessage)
                .build();

        return response;
    }
}
