package kr.toy.lyricsQuizServer.user.controller;

import kr.toy.lyricsQuizServer.user.controller.port.UserService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
