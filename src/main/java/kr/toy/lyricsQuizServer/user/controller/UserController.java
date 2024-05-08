package kr.toy.lyricsQuizServer.user.controller;

import kr.toy.lyricsQuizServer.common.domain.ErrorCode;
import kr.toy.lyricsQuizServer.common.domain.Response;
import kr.toy.lyricsQuizServer.user.controller.port.UserService;
import kr.toy.lyricsQuizServer.user.domain.LoginType;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.domain.dto.UserCreate;
import kr.toy.lyricsQuizServer.user.service.port.AuthServerAPI;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;

@RestController
@Builder
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final AuthServerAPI authServerAPI;

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        return ResponseEntity.ok().body(Response.success(userService.getById(id)));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity getByEmail(@PathVariable String email){
        return ResponseEntity.ok().body(Response.success(userService.getByEmail(email)));
    }

    @GetMapping("/login")
    public ResponseEntity<Response> login(HttpServletResponse response,  @RequestParam LoginType loginType, @RequestParam String code){

        UserCreate userCreate = userService.loginHandler(response, loginType, code);

        if (userCreate == null) {
            return ResponseEntity.status(HttpStatus.OK).body(Response.success(null,"로그인에 성공했습니다."));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Response.fail("로그인에 실패했습니다.", userCreate, ErrorCode.USER_NOT_REGISTERED));
        }

    }
    @PostMapping("/signup")
    public ResponseEntity register(HttpServletResponse response, @Valid @RequestBody UserCreate userCreate){
        User user = userService.signUp(response, userCreate);

        return ResponseEntity.created(URI.create("/api/users/" + user.getUserSeq())).body(Response.success(user,"회원가입에 성공했습니다."));
    }


}
