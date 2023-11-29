package kr.toy.lyricsQuizServer.file.service;

import kr.toy.lyricsQuizServer.file.domain.File;
import kr.toy.lyricsQuizServer.file.infrastructure.FileEntity;

public interface FileRepository {

    void save(File file);

    void read(Long fileSeq);

    void update();

    void delete();




}
