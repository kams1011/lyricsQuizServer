package kr.toy.lyricsQuizServer.file.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import kr.toy.lyricsQuizServer.config.SecurityService;
import kr.toy.lyricsQuizServer.config.ConfigurationProperties.StorageProperties;
import kr.toy.lyricsQuizServer.file.controller.port.FileService;
import kr.toy.lyricsQuizServer.file.domain.File;
import kr.toy.lyricsQuizServer.file.domain.FileExtension;
import kr.toy.lyricsQuizServer.user.domain.Role;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final AmazonS3Client amazonS3;

    private final StorageProperties storageProperties;

    private final FileRepository fileRepository;

    private final UserRepository userRepository;

    private final SecurityService securityService;

    @Override
    public File save(File file) {

        return fileRepository.save(file);
    }

    @Override
    public File getFileBy(Long fileSeq) {
        return fileRepository.getBy(fileSeq);
    }


    //FIXME 주석처리해놓은 UPDATE, DELETE 구현.
    //FIXME STORAGE UPLOAD 부분을 따로 클래스 분리할지 생각.

    @Override
    @Transactional
    public Map<String, Object> upload(MultipartFile file, User uploader) throws IOException, HttpMediaTypeNotSupportedException {

        validateFile(file);
        String originalFilename = file.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        File fileDomain = File.builder()
                .name(file.getName())
                .uniqueName(originalFilename)
                .extension(getFileExtension(file.getContentType()))
                .size(file.getSize())
                .isDeleted(false)
                .user(uploader)
                .build();

        fileDomain = save(fileDomain);

        Map<String, Object> result = new HashMap<>();

        amazonS3.putObject(storageProperties.getS3().getBucket(), originalFilename, file.getInputStream(), metadata);
        String url = amazonS3.getUrl(storageProperties.getS3().getBucket(), originalFilename) + "";
        result.put("url", url);
        result.put("id", fileDomain.getFileSeq());
        return result;
    }

    @Override
    public void validateFile(MultipartFile file) throws IOException, HttpMediaTypeNotSupportedException {
        String fileType = file.getContentType();

         FileExtension fileExtension = getFileExtension(fileType);

         checkFileSize(file);

         checkFileSignature(file, fileExtension);
    }

    @Override
    public void delete(User user, Long fileSeq) {
        File file = getFileBy(fileSeq);

        hasAuthority(user, fileSeq);

        //FIXME 현재 진행중인 게임이 있는지 체크 -> Redis 구현 후 제작
        
        amazonS3.deleteObject(storageProperties.getS3().getBucket(), file.getUniqueName());
    }


    public FileExtension getFileExtension(String fileType) throws HttpMediaTypeNotSupportedException {
        FileExtension fileExtension = Arrays.stream(FileExtension.values())
                .filter(data -> data.getType().equals(fileType))
                .findFirst().orElseThrow(() -> new HttpMediaTypeNotSupportedException("유효하지 않은 파일 확장자입니다."));

        return fileExtension;
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

    public Boolean hasAuthority(User user, Long fileSeq){
        File file = fileRepository.getBy(fileSeq);

        if(file.getUser().equals(user)) {
            return true;
        } else if(user.getRole().equals(Role.ADMIN)){
           return true;
        }

        return false;
    }

//    public void temp(){
//        FFmpegFrameGrabber a = new FFmpegFrameGrabber();
//        FFmpegFrameRecorder b = new FFmpegFrameRecorder();
//        b.
//    }






}
