package kr.toy.lyricsQuizServer.memory;

import kr.toy.lyricsQuizServer.config.Redis.RedisCategory;
import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisMemoryGameRoomService<GameRoom, Long> implements MemoryService<GameRoom, Long>{

    private final HashOperations<String, Long, GameRoom> opsHashGameRoom;

    private final String key = RedisCategory.GAME_ROOM.name();

    @Override
    public GameRoom getObject(Long id, HashOperations<String, Long, GameRoom> opsHash) {
        return opsHashGameRoom.get(key, id);
    }

    @Override
    public void putObject(Long id, GameRoom data, HashOperations<String, Long, GameRoom> opsHash) {
        opsHashGameRoom.put(key, id, data);
    }

}
