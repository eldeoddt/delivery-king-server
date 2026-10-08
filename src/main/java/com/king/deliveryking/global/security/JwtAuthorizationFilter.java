package com.king.deliveryking.global.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// 요청마다 Authorization 헤더의 JWT를 검증하고 인증 정보를 SecurityContext에 등록
// @Component로 등록하면 서블릿 필터로도 중복 등록되므로 WebSecurityConfig에서 생성한다.
@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtUtil.getJwtFromHeader(request);

        // 토큰이 없거나 유효하지 않으면 인증 없이 진행 → 인증이 필요한 경로는 401
        if (token != null && jwtUtil.validateToken(token)) {
            Claims info = jwtUtil.getUserInfoFromToken(token);
            try {
                setAuthentication(Long.valueOf(info.getSubject()));
            } catch (NumberFormatException | UsernameNotFoundException e) {
                log.error(e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

    // 인증 처리
    private void setAuthentication(Long userId) {
        UserDetails userDetails = userDetailsService.loadUserById(userId);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
}
