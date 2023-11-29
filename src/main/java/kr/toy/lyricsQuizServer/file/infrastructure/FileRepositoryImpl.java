package kr.toy.lyricsQuizServer.file.infrastructure;

import kr.toy.lyricsQuizServer.file.domain.File;
import kr.toy.lyricsQuizServer.file.service.FileRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepository {

    private final FileJpaRepository fileJpaRepository;


    @Override
    public void save(File file) {

    }

    @Override
    public void read(Long fileSeq) {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}
