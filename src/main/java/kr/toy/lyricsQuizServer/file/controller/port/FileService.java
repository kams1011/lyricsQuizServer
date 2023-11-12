package kr.toy.lyricsQuizServer.file.controller.port;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    String upload(MultipartFile multipartFile) throws IOException;

    void check(); //FileTypeCheck

    void deleteFile(Long fileSeq);
}
