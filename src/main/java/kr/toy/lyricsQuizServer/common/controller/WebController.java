package kr.toy.lyricsQuizServer.common.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Web Mapping Controller
 */
@Controller
public class WebController {

    /**
     * Vue Project
     *
     * @return
     */
    @RequestMapping(value = {"/", "/login/**", "/user/**", "/room/**", "/quiz/**"})
    public String viewMapping() {
        System.out.println("여기 들어는 옴?");
        return "forward:/index.html";
    }

}
