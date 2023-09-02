package kr.toy.lyricsQuizServer.common.infrastructure;

import kr.toy.lyricsQuizServer.common.service.port.UUIDHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SystemUUIDHolder implements UUIDHolder {

    @Override
    public String makeUUID(){
        return UUID.randomUUID().toString();
    }
}
