package kr.toy.lyricsQuizServer.file.service;

import kr.toy.lyricsQuizServer.file.domain.File;
import kr.toy.lyricsQuizServer.file.infrastructure.FileEntity;

public interface FileRepository {

    File save(File file);

    File getBy(Long fileSeq);

    void update();

    void delete(Long fileSeq);




}
