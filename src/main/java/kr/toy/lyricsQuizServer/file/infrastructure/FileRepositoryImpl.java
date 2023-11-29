package kr.toy.lyricsQuizServer.file.infrastructure;

import kr.toy.lyricsQuizServer.file.domain.File;
import kr.toy.lyricsQuizServer.file.service.FileRepository;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepository {

    private final FileJpaRepository fileJpaRepository;


    @Override
    public File save(File file) {
        fileJpaRepository.save(FileEntity.fromModel(file));
        return file;
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
