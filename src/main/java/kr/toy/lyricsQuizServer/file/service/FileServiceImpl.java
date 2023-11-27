package kr.toy.lyricsQuizServer.file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import kr.toy.lyricsQuizServer.config.StorageProperties;
import kr.toy.lyricsQuizServer.file.controller.port.FileService;
import kr.toy.lyricsQuizServer.file.domain.FileExtension;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMessage;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final AmazonS3 amazonS3;

    private final StorageProperties storageProperties;

    @Override
    public String upload(MultipartFile file) throws IOException, HttpMediaTypeNotSupportedException {

        validateFile(file);
        //FILE인지 이미지인지 확인 필요.
        String originalFilename = file.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        amazonS3.putObject(storageProperties.getS3().getBucket(), originalFilename, file.getInputStream(), metadata);
        return amazonS3.getUrl(storageProperties.getS3().getBucket(), originalFilename).toString();
    }

    @Override
    public void validateFile(MultipartFile file) throws IOException, HttpMediaTypeNotSupportedException {
        String fileType = file.getContentType();

            FileExtension fileExtension = Arrays.stream(FileExtension.values())
                    .filter(data -> data.getType().equals(fileType))
                    .findFirst().orElseThrow(() -> new HttpMediaTypeNotSupportedException("유효하지 않은 파일 확장자입니다."));

            checkFileSize(file);

            checkFileSignature(file, fileExtension);

    }

    @Override
    public void deleteFile(Long fileSeq) {
        //FIXME fileSeq 로 originalFileName을 찾는 메서드 구현
        //FIXME 삭제할 권한이 있는지 체크
        //FIXME 현재 진행중인 게임이 있는지 체크

        String originalFilename = null;
        amazonS3.deleteObject(storageProperties.getS3().getBucket(), originalFilename);
    }


    public boolean checkFileSignature(MultipartFile file, FileExtension fileExtension) throws IOException{
        int signatureLength = fileExtension.getCode().length;
        boolean isValid;

        if (fileExtension.equals(FileExtension.AVI)) {
            isValid = fileExtension.checkAVIFileSignature(file);
        } else {
            isValid = file.getBytes().equals(Arrays.copyOfRange(file.getBytes(), 0, signatureLength));
        }

        return isValid;
    }

    public void checkFileSize(MultipartFile file) throws FileSizeLimitExceededException {
        if(file.getSize() > 10485760){
            throw new FileSizeLimitExceededException("파일 크기를 초과했습니다.", file.getSize(), 10485760);
        }
    }
}
