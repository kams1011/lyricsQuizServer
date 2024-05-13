package kr.toy.lyricsQuizServer.config;

import kr.toy.lyricsQuizServer.common.domain.ErrorCode;
import kr.toy.lyricsQuizServer.common.domain.Response;
import kr.toy.lyricsQuizServer.config.CustomError.DomainValidError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ApiControllerAdvice {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));

        return ResponseEntity.badRequest().body(Response.fail("파라미터 오류입니다.", errors, null));
    }


    @ExceptionHandler(IOException.class)
    public ResponseEntity<Response> handleIOExceptions(IOException e){

        return ResponseEntity.badRequest().body(Response.fail(e.getMessage(), null, null));

    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<Response> AuthenticationException(IOException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Response.fail(e.getMessage(), null, null));

    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Response> handleFileValidationExceptions(HttpMediaTypeNotSupportedException e){

        return ResponseEntity.badRequest().body(Response.fail(e.getMessage(), null, null));

    }

    @ExceptionHandler(JwtInvalidException.class)
    public ResponseEntity<Response> handleJwtInvalidException(JwtInvalidException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Response.fail("JWT가 만료됐습니다.", null, ErrorCode.JWT_EXPIRED));
    }

    @ExceptionHandler(DomainValidError.class)
    public ResponseEntity<Response> domainValidNotPassException(DomainValidError e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.fail(e.getErrorCode().getMessage(), null, e.getErrorCode()));
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class,
            NoSuchElementException.class, RuntimeException.class, NullPointerException.class})
    public ResponseEntity<Response> handleException(Exception e){
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Response.fail(e.getMessage(), null, null));
    }




}
