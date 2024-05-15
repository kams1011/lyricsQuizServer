package kr.toy.lyricsQuizServer.memory;

import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import kr.toy.lyricsQuizServer.memory.Redis.RedisCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisMemoryGameRoomService implements MemoryService<GameRoom, Long>{

    private final HashOperations<String, Long, GameRoom> gameRoomHashOperations;

    private final String key = RedisCategory.GAME_ROOM.name();

    @Override
    public GameRoom getObject(Long id) {
        return gameRoomHashOperations.get(key, id);
    }

    @Override
    public List<GameRoom> getAll() { // FIXME 레디스가 실질적으로 싱글스레드로 작동하기 때문에 해당 로직을 Scan을 사용하여 변경해줘야함
        Set<Long> keySet = gameRoomHashOperations.keys(key);
        return gameRoomHashOperations.multiGet(key, keySet);
    }

    @Override
    public void putObject(Long id, GameRoom data) {
        gameRoomHashOperations.put(key, id, data);
    }

    @Override
    public void deleteObject(Long id) {
        gameRoomHashOperations.delete(key, id);
    }

}
