package kr.toy.lyricsQuizServer.file.controller.port;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    String upload(MultipartFile multipartFile) throws IOException, HttpMediaTypeNotSupportedException;

    void validateFile(MultipartFile file) throws IOException, HttpMediaTypeNotSupportedException;

    void deleteFile(Long fileSeq);
}
