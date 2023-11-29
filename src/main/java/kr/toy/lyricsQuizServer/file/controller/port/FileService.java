package kr.toy.lyricsQuizServer.file.controller.port;

import kr.toy.lyricsQuizServer.file.domain.File;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {


    File save(File file);

    File getFileBy(Long fileSeq);

//    File update();

    String upload(MultipartFile multipartFile) throws IOException, HttpMediaTypeNotSupportedException;

    void validateFile(MultipartFile file) throws IOException, HttpMediaTypeNotSupportedException;

    void delete(Long userSeq, Long fileSeq);
}
