package com.eztrip.global.token;

import com.eztrip.entity.member.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenManager {

    @Value("${token.access-token-expiration-time}")
    private long accessTokenExpirationTime;

    @Value("${token.refresh-token-expiration-time}")
    private long refreshTokenExpirationTime;

    @Value("${token.secret}")
    private String tokenSecret;

    public JwtTokenDto createJwtTokenDto(Long id, String username, String nickname, Role role, String name, String image) {
        Date accessTokenExpireTime = createAccessTokenExpireTime();
        Date refreshTokenExpireTime = createRefreshTokenExpireTime();

        String accessToken = createAccessToken(id, username, nickname, role, name, image, accessTokenExpireTime);
        String refreshToken = createRefreshToken(id, refreshTokenExpireTime);

        return JwtTokenDto.builder()
                .id(id)
                .username(username)
                .nickname(nickname)
                .name(name)
                .image(image)
                .role(role)
                .grantType(GrantType.BEARER.getType())
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .refreshToken(refreshToken)
                .refreshTokenExpireTime(refreshTokenExpireTime)
                .build();
    }

    public Date createAccessTokenExpireTime() {
        return new Date(System.currentTimeMillis() + accessTokenExpirationTime);
    }

    public Date createRefreshTokenExpireTime() {
        return new Date(System.currentTimeMillis() + refreshTokenExpirationTime);
    }

    public String createAccessToken(Long id, String username, String nickname, Role role, String name, String image, Date expirationTime) {
        return Jwts.builder()
                .setSubject(TokenType.ACCESS.name())
                .setIssuedAt(new Date())
                .setExpiration(expirationTime)
                .claim("id", id)
                .claim("username", username)
                .claim("nickname",nickname)
                .claim("name",name)
                .claim("image",image)
                .claim("role", role.name())
                .signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes(StandardCharsets.UTF_8))
                .setHeaderParam("type", "JWT")
                .compact();
    }

    public String createRefreshToken(Long id, Date expirationTime) {
        return Jwts.builder()
                .setSubject(TokenType.REFRESH.name())
                .setIssuedAt(new Date())
                .setExpiration(expirationTime)
                .claim("id", id)
                .signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes(StandardCharsets.UTF_8))
                .setHeaderParam("type", "JWT")
                .compact();
    }



    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("토큰이 만료되었습니다.", e);
            return false;
        } catch (Exception e) {
            log.error("유효하지 않은 토큰입니다.", e);
            return false;
        }
    }

    public Claims getTokenClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("유효하지 않은 토큰입니다.", e);
            throw new RuntimeException("Invalid token");
        }
    }

    public String getUsernameFromToken(String token) {
        return getTokenClaims(token).get("username", String.class);
    }

    public Long getUserIdFromToken(String token) {
        return getTokenClaims(token).get("id", Long.class);
    }

    public String getRoleFromToken(String token) {
        return getTokenClaims(token).get("role", String.class);
    }



}