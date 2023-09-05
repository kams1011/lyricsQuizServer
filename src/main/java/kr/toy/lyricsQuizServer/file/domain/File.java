package kr.toy.lyricsQuizServer.file.domain;

import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.user.domain.User;

public class File {


    String name;

    String uniqueName; // 파일 고유 이름.

    String extension; // FIXME : ENUM으로 관리하는게 용이할거같음.

    Integer size; // FIXME : Size관련해서 수정


//    User user;
//    Quiz quiz;


}
