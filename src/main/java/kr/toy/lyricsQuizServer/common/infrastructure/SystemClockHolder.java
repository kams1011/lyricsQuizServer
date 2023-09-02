package kr.toy.lyricsQuizServer.common.infrastructure;

import kr.toy.lyricsQuizServer.common.service.port.ClockHolder;
import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
public class SystemClockHolder implements ClockHolder {

    @Override
    public long now(){
        return Clock.systemUTC().millis();
    }

}
