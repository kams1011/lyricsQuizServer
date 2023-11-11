package kr.toy.lyricsQuizServer.file.domain;

import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.user.domain.User;

public class File {

    String name;

    String uniqueName; // 파일 고유 이름.

    String extension; // FIXME : ENUM으로 관리하는게 용이할거같음.

    Integer size; // FIXME : Size관련해서 수정

    User user;

    Quiz quiz; //File하고 퀴즈하고 매칭해야되나? 가진다면 Quiz가 가지고 있어야할듯.

}
