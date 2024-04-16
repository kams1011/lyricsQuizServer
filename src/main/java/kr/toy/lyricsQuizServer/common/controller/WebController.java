package kr.toy.lyricsQuizServer.common.controller;

import org.springframework.stereotype.Controller;
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
    @RequestMapping(value = "/")
    public String viewMapping() {
        return "forward:/index.html";
    }
}
