package kr.toy.lyricsQuizServer.file.infrastructure;

import kr.toy.lyricsQuizServer.file.domain.File;
import kr.toy.lyricsQuizServer.file.service.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@RequiredArgsConstructor
@Repository
public class FileRepositoryImpl implements FileRepository {

    private final FileJpaRepository fileJpaRepository;


    @Override
    public File save(File file) {
        return fileJpaRepository.save(FileEntity.fromModel(file)).toModel();

    }

    @Override
    public File getBy(Long fileSeq) {
        File file = fileJpaRepository.findByFileSeqAndIsDeletedIsFalse(fileSeq)
                .orElseThrow(EntityNotFoundException::new)
                .toModel();

        return file;
    }

    @Override
    public void update() {

    }

    @Override
    @Transactional
    public void delete(Long fileSeq) {
        FileEntity fileEntity = fileJpaRepository.findByFileSeqAndIsDeletedIsFalse(fileSeq)
                .orElseThrow(EntityNotFoundException::new);

        fileEntity.delete();
    }
}
