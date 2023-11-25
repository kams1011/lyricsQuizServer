package kr.toy.lyricsQuizServer.file.controller;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@Builder
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {


    @PostMapping("")
    public void temp(@RequestPart MultipartFile file) throws IOException {


    }
}
