package kr.toy.lyricsQuizServer.file.controller;

import kr.toy.lyricsQuizServer.file.controller.port.FileService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Builder
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {


    private final FileService fileService;

    @PostMapping("")
    public void temp(@RequestPart MultipartFile file) throws IOException, HttpMediaTypeNotSupportedException {
        fileService.validateFile(file);
    }
}
