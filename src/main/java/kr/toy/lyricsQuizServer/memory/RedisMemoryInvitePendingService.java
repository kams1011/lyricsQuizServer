package kr.toy.lyricsQuizServer.memory;

import kr.toy.lyricsQuizServer.game.controller.response.UserInvitationInfo;
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

    private static final int PAGE_SIZE = 10;

    @Override
    public Object getObject(Object userInvitationInfo) { //FIXME 수정필요
//        Long index = invitePendingListOperations.indexOf(key, userInfoSeq); // RedisVersion문제로 현재는 작동되지 않음.
        Long index = Long.valueOf(getAll().indexOf(userInvitationInfo));
        return index;
    }

    @Override
    public List getAll() {
        return invitePendingListOperations.range(key, 0, -1);  //끝 인덱스가 -1이면 리스트의 처음부터 끝까지의 모든 값을 가져옴.
    }
    public List getAll(int pageNumber){
        int pageStart = pageNumber * PAGE_SIZE;
        int pageEnd = (pageNumber + 1) * PAGE_SIZE -1;
        return invitePendingListOperations.range(key, pageStart, pageEnd);
    }

    @Override
    public void putObject(Object id, Object invitationInfo) {
        invitePendingListOperations.rightPush(key, invitationInfo);
    }

    @Override
    public void deleteObject(Object userInfoSeq) {
        invitePendingListOperations.remove(key, 1, userInfoSeq);
    }



}
