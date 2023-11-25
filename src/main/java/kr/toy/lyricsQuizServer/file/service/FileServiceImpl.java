package kr.toy.lyricsQuizServer.file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import kr.toy.lyricsQuizServer.config.StorageProperties;
import kr.toy.lyricsQuizServer.file.controller.port.FileService;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final AmazonS3 amazonS3;

    private final StorageProperties storageProperties;

    @Override
    public String upload(MultipartFile multipartFile) throws IOException {

        //FILE인지 이미지인지 확인 필요.
        String originalFilename = multipartFile.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(storageProperties.getS3().getBucket(), originalFilename, multipartFile.getInputStream(), metadata);
        return amazonS3.getUrl(storageProperties.getS3().getBucket(), originalFilename).toString();
    }

    @Override
    public void check() {
        //RETURN IMAGE OR MUSIC
        MultipartFile file;
    }

    @Override
    public void deleteFile(Long fileSeq) {
        //FIXME fileSeq 로 originalFileName을 찾는 메서드 구현
        //FIXME 삭제할 권한이 있는지 체크
        //FIXME 현재 진행중인 게임이 있는지 체크

        String originalFilename = null;
        amazonS3.deleteObject(storageProperties.getS3().getBucket(), originalFilename);
    }
}
