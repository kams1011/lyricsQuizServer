package kr.toy.lyricsQuizServer.chat.domain;

public enum TopicType {

    GAME_ROOM, ETC;

    public String topicConverter(Long arg){
        return this.name() + "_" + arg;
    }



}
