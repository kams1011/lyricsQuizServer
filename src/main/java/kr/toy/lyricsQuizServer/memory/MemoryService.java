package kr.toy.lyricsQuizServer.memory;

import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import org.springframework.data.redis.core.HashOperations;

public interface MemoryService<T, K> {

//    <T, K> T getObject(String category, K id, HashOperations<String, K, T> opsHash);
    T getObject(K id, HashOperations<String, K, T> opsHash); // FIXME 좀 더 추상화 하는 방법 찾아보기 (지금은 Redis에 강하게 종속돼있음.)

    void putObject(K id, T data, HashOperations<String, K, T> opsHash);

//    <T, K> void putObject(String category, K id, T data, HashOperations<String, K, T> opsHash);
}
