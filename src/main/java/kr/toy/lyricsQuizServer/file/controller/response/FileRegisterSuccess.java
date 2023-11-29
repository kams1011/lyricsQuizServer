package kr.toy.lyricsQuizServer.file.controller.response;

import lombok.Builder;
import lombok.Getter;


@Getter
public class FileRegisterSuccess {

    private final Long fileSeq;

    private final String fileUrl;


    @Builder
    public FileRegisterSuccess(Long fileSeq, String fileUrl){
        this.fileSeq = fileSeq;
        this.fileUrl = fileUrl;
    }
}
