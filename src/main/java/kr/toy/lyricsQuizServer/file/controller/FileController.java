package kr.toy.lyricsQuizServer.file.controller;

import kr.toy.lyricsQuizServer.common.domain.Response;
import kr.toy.lyricsQuizServer.file.controller.port.FileService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;

@RestController
@Builder
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {


    private final FileService fileService;

    @PostMapping("")
    public ResponseEntity register(@RequestPart MultipartFile file) throws IOException, HttpMediaTypeNotSupportedException {

        String fileUrl = fileService.upload(file);
        return ResponseEntity.created(URI.create(fileUrl)).body(Response.success("업로드에 성공했습니다", fileUrl));

    }
}
