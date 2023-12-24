package kr.toy.lyricsQuizServer.file.controller.port;

import kr.toy.lyricsQuizServer.file.domain.File;
import kr.toy.lyricsQuizServer.user.domain.User;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public interface FileService {


    File save(File file);

    File getFileBy(Long fileSeq);

//    File update();

    Map<String, Object> upload(MultipartFile multipartFile, User uploader) throws IOException, HttpMediaTypeNotSupportedException;

    void validateFile(MultipartFile file) throws IOException, HttpMediaTypeNotSupportedException;

    void delete(Long userSeq, Long fileSeq);
}
