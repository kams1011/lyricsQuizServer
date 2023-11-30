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

    FileExtension extension;

    Integer size; // FIXME : Size관련해서 수정

    User user; // 올린 사람은 상관이 없을거 같긴 함.

    String fileCategory; // FIXME : ENUM으로 변경하고 관련 로직 추가.

    Boolean isDeleted;



    @Builder
    public File(Long fileSeq, String name, String uniqueName, FileExtension extension, Integer size, User user, Boolean isDeleted){
        this.fileSeq = fileSeq;
        this.name = name;
        this.uniqueName = uniqueName;
        this.extension = extension;
        this.size = size;
        this.user = user;
        this.isDeleted = isDeleted;
    }


}
