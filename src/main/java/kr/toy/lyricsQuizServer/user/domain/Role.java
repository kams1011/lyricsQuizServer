package kr.toy.lyricsQuizServer.user.domain;

import lombok.Getter;

@Getter
public enum Role {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    String role;

    Role(String role){
        this.role = role;
    }


}
