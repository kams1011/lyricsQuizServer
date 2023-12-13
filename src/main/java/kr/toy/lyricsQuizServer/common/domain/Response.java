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

    public static Response success(String message, Object data){
        Response response = Response.builder()
                .success(true)
                .message(message)
                .data(data)
                .build();

        return response;
    }

    public static Response success(String message){
        Response response = Response.builder()
                .success(true)
                .message(message)
                .data(null)
                .build();

        return response;
    }

    public static Response success(Object data){
        Response response = Response.builder()
                .success(true)
                .message(null)
                .data(data)
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
}
