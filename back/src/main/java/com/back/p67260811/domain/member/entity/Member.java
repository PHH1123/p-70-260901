package com.back.p67260811.domain.member.entity;

import com.back.p67260811.global.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
public class Member extends BaseEntity {

    @Column(unique = true)
    private String username;
    private String password;
    private String nickname;
    @Column(unique = true)
    private String apiKey;

    protected Member() {
    }

    public Member(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public Member(String username, String password, String nickname, String apiKey) {
        this(username, password, nickname);
        this.apiKey = apiKey;
    }

}
