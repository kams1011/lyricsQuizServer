package kr.toy.lyricsQuizServer.user.controller;

import kr.toy.lyricsQuizServer.user.controller.port.UserService;
import kr.toy.lyricsQuizServer.user.domain.LoginType;
import kr.toy.lyricsQuizServer.user.domain.dto.UserCreate;
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

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserCreate userCreate){

        return ResponseEntity.created(URI.create("/users/" + "tmeptemp")).body("temptemp");
        //FIXME 임시로 넣은 값들 수정
    }


}
