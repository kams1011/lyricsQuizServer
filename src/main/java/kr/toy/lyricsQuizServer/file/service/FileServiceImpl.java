//package kr.toy.lyricsQuizServer.file.service;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import kr.toy.lyricsQuizServer.file.controller.port.FileService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//@Service
//@RequiredArgsConstructor
//public class FileServiceImpl implements FileService {
//    private final AmazonS3 amazonS3;
//
//    //FIXME 환경변수 ConfigurationProperties로 변경
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;
//
//    @Override
//    public String upload(MultipartFile multipartFile) throws IOException {
//
//        //FILE인지 이미지인지 확인 필요.
//        String originalFilename = multipartFile.getOriginalFilename();
//
//        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentLength(multipartFile.getSize());
//        metadata.setContentType(multipartFile.getContentType());
//
//        amazonS3.putObject(bucket, originalFilename, multipartFile.getInputStream(), metadata);
//        return amazonS3.getUrl(bucket, originalFilename).toString();
//    }
//
//    @Override
//    public void check() {
//        //RETURN IMAGE OR MUSIC
//
//    }
//}
