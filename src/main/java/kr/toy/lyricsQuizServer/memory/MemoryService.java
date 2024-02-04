package kr.toy.lyricsQuizServer.memory;


import java.util.List;

public interface MemoryService<T, K> {

    T getObject(K id); // FIXME 좀 더 추상화 하는 방법 찾아보기 (지금은 Redis에 강하게 종속돼있음.)

    List<T> getAll();

    void putObject(K id, T data);

    void deleteObject(K id);

}
