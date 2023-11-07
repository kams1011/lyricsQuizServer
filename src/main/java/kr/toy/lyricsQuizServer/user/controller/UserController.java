package kr.toy.lyricsQuizServer.user.controller;

import kr.toy.lyricsQuizServer.user.controller.port.UserService;
import kr.toy.lyricsQuizServer.user.domain.LoginType;
import kr.toy.lyricsQuizServer.user.domain.dto.UserCreate;
import kr.toy.lyricsQuizServer.user.service.port.AuthServerAPI;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@Builder
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final AuthServerAPI authServerAPI;

//    private final OauthConfig oauthConfig;

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        return ResponseEntity.ok().body(userService.getById(id));
    } // FIXME : response dto로 변경

    @GetMapping("/email/{email}")
    public void getByEmail(@PathVariable String email){
        userService.getByEmail(email);
    } // FIXME : response dto로 변경

    @GetMapping("/login")
    public void login(){

    }
    @PostMapping("/signup")
    public ResponseEntity register(@RequestBody UserCreate userCreate){
        System.out.println("SIGUP!@#!@$");
        return ResponseEntity.created(URI.create("/users/" + "tmeptemp")).body("temptemp");
        //FIXME 임시로 넣은 값들 수정
    }

    @GetMapping("/info/{loginType}/{accessToken}") //FIXME OAuthController 생성해서 분리할 지 생각
    public ResponseEntity getUserInfoBy(@PathVariable LoginType loginType, @PathVariable String accessToken){
        System.out.println("필터는 통과");
//        return ResponseEntity.ok(authServerAPI.getUserInfoBy(loginType, accessToken));
        return ResponseEntity.ok(authServerAPI.getAccessToken(loginType, accessToken));
    }

}
