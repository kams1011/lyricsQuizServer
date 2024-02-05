package kr.toy.lyricsQuizServer.memory;

import kr.toy.lyricsQuizServer.memory.Redis.RedisCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RedisMemoryInvitePendingService implements MemoryService{

    private final ListOperations<String, Object> invitePendingListOperations;

    private final String key = RedisCategory.INVITE_PENDING.name();

    @Override
    public Object getObject(Object userInfoSeq) { //FIXME 수정필요
//        Long index = invitePendingListOperations.indexOf(key, userInfoSeq); // RedisVersion문제로 현재는 작동되지 않음.
        Long index = Long.valueOf(getAll().indexOf(String.valueOf(userInfoSeq)));
        if (index == -1) {
            throw new NoSuchElementException("초대 허용 정보가 없습니다.");
        } else {
            return index;
        }
    }

    @Override
    public List getAll() {
        return invitePendingListOperations.range(key, 0, -1);  //끝 인덱스가 -1이면 리스트의 처음부터 끝까지의 모든 값을 가져옴.
    }

    @Override
    public void putObject(Object id, Object userInfoSeq) {
        invitePendingListOperations.rightPush(key, userInfoSeq);
    }

    @Override
    public void deleteObject(Object userInfoSeq) {
        invitePendingListOperations.remove(key, 1, userInfoSeq);
    }

}
