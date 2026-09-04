package com.back.p67260811.domain.member.service;

import com.back.p67260811.domain.member.entity.Member;
import com.back.p67260811.standard.ut.Ut;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthTokenService {

    // 토근 만료 기간: 10분
    private final long expireMillis = 1000L * 60 * 10;
    private final String secretPattern= "abcdefghijklmnopqrstuvwxyz1234567890abcdefghijklmnopqrstuvwxyz1234567890";

    public String genAccessToken(Member member) {
        return Ut.jwt.toString(
                secretPattern,
                expireMillis,
                Map.of("id", member.getId(), "username", member.getUsername())
        );
    }

}
