package kr.toy.lyricsQuizServer.user.controller;

import kr.toy.lyricsQuizServer.user.controller.port.UserService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
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


    @GetMapping("/{id}")
    public void getById(@PathVariable Long id){
        userService.getById(id);
    }


    @GetMapping("/{email}")
    public void getByEmail(@PathVariable String email){
        userService.getByEmail(email);
    }



}
