package kr.toy.lyricsQuizServer.file.domain;

import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class File {

    Long fileSeq;

    String name;

    String uniqueName; // 파일 고유 이름.

    FileExtension extension; // FIXME : ENUM으로 관리하는게 용이할거같음.

    Integer size; // FIXME : Size관련해서 수정

    User user;



    @Builder
    public File(Long fileSeq, String name, String uniqueName, FileExtension extension, Integer size, User user){
        this.fileSeq = fileSeq;
        this.name = name;
        this.uniqueName = uniqueName;
        this.extension = extension;
        this.size = size;
        this.user = user;
    }


}
