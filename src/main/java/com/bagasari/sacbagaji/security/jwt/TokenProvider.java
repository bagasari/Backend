package com.bagasari.sacbagaji.security.jwt;

import com.bagasari.sacbagaji.exception.security.InvalidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * 토큰의 생성, 토큰의 유효성 검증등을 담당할 클래스
 */
@Component
public class TokenProvider implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";

    private final String secret;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;

    private Key key;

    // 의존성 주입
    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        this.secret = secret;
        this.accessTokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
        this.refreshTokenValidityInMilliseconds = tokenValidityInSeconds * 10000;
    }

    // 빈이 생성되고 의존성 주입을 받은 후에 주입받은 secret 값을 Base64 Decode해서 Key 변수에 할당
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(String email) {
        return generateToken(email, accessTokenValidityInMilliseconds);
    }

    public String generateRefreshToken(String email) {
        return generateToken(email, refreshTokenValidityInMilliseconds);
    }

    // Authentication 객체의 권한정보를 이용해서 토큰을 생성하는 메소드
    private String generateToken(String email, Long tokenValidityInMilliseconds) {
        // application.yml에서 설정했던 만료시간 설정
        long now = (new Date()).getTime();
        Date validity = new Date(now + tokenValidityInMilliseconds);

        // 토큰 생성하고 리턴
        return Jwts.builder()
                .claim("email", email)
//                .claim(AUTHORITIES_KEY, authorities)
                .setIssuedAt(new Date())
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // Token을 받아서 Authentication 객체를 리턴하는 메소드
    public Authentication getAuthentication(String token) {
        // 토큰으로 클레임 만들고
        Claims claims = parseClaims(token);

        // 클레임으로 권한 정보를 빼내어
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // 권한 정보들을 이용해서 유저 객체를 만들고
        User principal = new User(claims.getSubject(), "", authorities);

        // Authentication 객체 리턴
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // 토큰의 유효성 검증을 수행하는 메소드
    public boolean validateToken(String token) {
        // 토큰 파싱하여 발생하는 예외처리
        // 문제가 있는 경우 false
        // 정상적으로 처리된 경우 true
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
            throw new InvalidTokenException("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
            throw new InvalidTokenException("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
            throw new InvalidTokenException("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
            throw new InvalidTokenException("JWT 토큰이 잘못되었습니다.");
        }
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch(ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String getUserEmailFromToken(String token) {
        return getAuthentication(token).getName();
    }
}