package com.bagasari.sacbagaji.security.jwt;

import com.bagasari.sacbagaji.exception.CustomException;
import com.bagasari.sacbagaji.exception.ErrorCode;
import com.bagasari.sacbagaji.exception.security.EmptyJwtTokenException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT를 위한 커스텀 필터를 만들기 위한 클래스
 */
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final TokenProvider tokenProvider;

    @Override
    // 실제 필터링 로직
    // JWT 토큰의 인증정보를 현재 실행중인 SecurityContext에 저장하는 메소드
    public void doFilterInternal(HttpServletRequest servletRequest,
                                 HttpServletResponse servletResponse,
                                 FilterChain filterChain
    ) throws IOException, ServletException {

        String jwt = resolveToken(servletRequest); // request에서 토큰을 받아서
        String requestURI = (servletRequest).getRequestURI();

        // 유효성 검증하여 올바른 토큰인 경우
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Authentication authentication = tokenProvider.getAuthentication(jwt); // 토큰에서 Authentication 객체를 받아와서
            SecurityContextHolder.getContext().setAuthentication(authentication); // SecurityContext에 저장
            logger.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
        } else {
            logger.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
            throw new EmptyJwtTokenException("유효한 JWT 토큰이 없습니다");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    // 필터링을 하기 위해 필요한 토큰 정보를 Request Header에서 꺼내오는 메소드
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}