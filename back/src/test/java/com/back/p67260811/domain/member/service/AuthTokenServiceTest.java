package com.back.p67260811.domain.member.service;

import com.back.p67260811.domain.member.entity.Member;
import com.back.p67260811.domain.member.repository.MemberRepository;
import com.back.p67260811.standard.ut.Ut;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AuthTokenServiceTest {

    @Autowired
    private AuthTokenService authTokenService;

    @Autowired
    private MemberRepository memberRepository;

    // 토근 만료 기간: 10분
    private final long expireMillis = 1000L * 60 * 10;
    private final String secretPattern = "abcdefghijklmnopqrstuvwxyz1234567890abcdefghijklmnopqrstuvwxyz1234567890";

    @Test
    @DisplayName("authTokenService 서비스가 존재한다.")
    void t1() {
        assertThat(authTokenService).isNotNull();
    }

    @Test
    @DisplayName("jjwt 최신 방식으로 JWT 생성, {name=\"Paul\", age=23}")
    void t2() {

        Map<String, Object> payload = Map.of("name", "Paul", "age", 23);

        String jwt = Ut.jwt.toString(secretPattern, expireMillis, payload);

        Map<String, Object> parsedPayload = Ut.jwt.payload(jwt, secretPattern);

        assertThat(parsedPayload)
                .containsAllEntriesOf(payload);

        assertThat(jwt).isNotBlank();

        System.out.println("jwt = " + jwt);
    }

    @Test
    @DisplayName("Ut.jwt.toString 를 통해서 JWT 생성, {name=\"Paul\", age=23}")
    void t3() {

        Map<String, Object> payload = Map.of("name", "Paul", "age", 23);

        String jwt = Ut.jwt.toString(
                secretPattern,
                expireMillis,
                payload
        );

        assertThat(jwt).isNotBlank();

        boolean validResult = Ut.jwt.isValid(jwt, secretPattern);
        assertThat(validResult).isTrue();

        Map<String, Object> parsedPayload = Ut.jwt.payload(jwt, secretPattern);

        assertThat(parsedPayload)
                .containsAllEntriesOf(payload);

        System.out.println("jwt = " + jwt);
    }

    @Test
    @DisplayName("AuthTokenService를 통해서 accessToken 생성")
    void t4() {

        Member member1 = memberRepository.findByUsername("user3").get();
        String accessToken = authTokenService.genAccessToken(member1);
        assertThat(accessToken).isNotBlank();

        System.out.println("accessToken = " + accessToken);

    }
}