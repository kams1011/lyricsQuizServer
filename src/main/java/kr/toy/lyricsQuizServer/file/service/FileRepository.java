package kr.toy.lyricsQuizServer.file.service;

import kr.toy.lyricsQuizServer.file.domain.File;

public interface FileRepository {

    File save(File file);

    File getBy(Long fileSeq);

    void update();

    void delete(Long fileSeq);




}
