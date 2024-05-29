package kr.toy.lyricsQuizServer.game.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import kr.toy.lyricsQuizServer.chat.domain.MessageType;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.domain.QuizContent;
import kr.toy.lyricsQuizServer.quiz.domain.QuizContentType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class StreamingInfo {

    MessageType type;

    Long roomId;

    String url;

    QuizContentType quizContentType;

    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    LocalTime startTime;

    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    LocalTime endTime;


    @Builder
    public StreamingInfo(Long roomId, String url, QuizContentType quizContentType, LocalTime startTime, LocalTime endTime){
        this.type = MessageType.STREAMING;
        this.roomId = roomId;
        this.url = url;
        this.quizContentType = quizContentType;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static StreamingInfo from(Long roomId, Quiz quiz, QuizContent quizContent){
        return StreamingInfo.builder()
                .roomId(roomId)
                .url(quizContent.getStreamingURL())
                .quizContentType(quizContent.getQuizContentType())
                .startTime(quiz.getPlayTime().getStartTime())
                .endTime(quiz.getPlayTime().getEndTime())
                .build();
    }
}
