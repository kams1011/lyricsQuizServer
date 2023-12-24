package kr.toy.lyricsQuizServer.file.controller;

import kr.toy.lyricsQuizServer.common.domain.Response;
import kr.toy.lyricsQuizServer.file.controller.port.FileService;
import kr.toy.lyricsQuizServer.user.domain.User;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

@RestController
@Builder
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {


    private final FileService fileService;

    @PostMapping("")
    public ResponseEntity register(@RequestPart MultipartFile file, User uploader) throws IOException, HttpMediaTypeNotSupportedException {

        Map<String, Object> result = fileService.upload(file, uploader);

        return ResponseEntity.created(URI.create(result.get("url").toString())).body(Response.success("업로드에 성공했습니다", result));

    }
}
