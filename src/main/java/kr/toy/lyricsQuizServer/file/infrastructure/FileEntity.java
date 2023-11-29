package kr.toy.lyricsQuizServer.file.infrastructure;

import kr.toy.lyricsQuizServer.file.domain.File;
import kr.toy.lyricsQuizServer.file.domain.FileExtension;
import kr.toy.lyricsQuizServer.game.domain.Game;
import kr.toy.lyricsQuizServer.user.infrastructure.UserEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class FileEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long fileSeq;

    String name;

    String uniqueName; // 파일 고유 이름.

    FileExtension extension;

    Integer size; // FIXME : Size관련해서 수정

    @ManyToOne
    @JoinColumn(name = "userSeq")
    UserEntity user;

    @Builder
    public FileEntity(Long fileSeq, String name, String uniqueName, FileExtension extension, Integer size, UserEntity user){
        this.fileSeq = fileSeq;
        this.name = name;
        this.uniqueName = uniqueName;
        this.extension = extension;
        this.size = size;
        this.user = user;
    }


    public File toModel(){
        File file = File.builder()
                .fileSeq(fileSeq)
                .name(name)
                .uniqueName(uniqueName)
                .extension(extension)
                .size(size)
                .user(user.toModel())
                .build();

        return file;
    }


    public static FileEntity fromModel(File file){
        FileEntity fileEntity = FileEntity.builder()
                .fileSeq(file.getFileSeq())
                .name(file.getName())
                .uniqueName(file.getUniqueName())
                .extension(file.getExtension())
                .size(file.getSize())
                .user(UserEntity.fromModel(file.getUser()))
                .build();
        return fileEntity;
    }





}
