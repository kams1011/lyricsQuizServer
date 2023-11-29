package kr.toy.lyricsQuizServer.file.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;


public interface FileJpaRepository extends JpaRepository<FileEntity, Long> {


}
